package com.example.lilialobato.pawstragram.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.adapters.AdapterPost;
import com.example.lilialobato.pawstragram.beans.Post;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentHome extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> dataSet;
    ImageView imageView;
    StorageReference mStorageRef;
    private static final int SELECTED_PIC = 1;
    private static final int PICK_FROM_GALLERY = 2;


    public FragmentHome(){
        dataSet = new ArrayList<Post>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_post_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        imageView = rootView.findViewById(R.id.btn_camara);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery();
            }
        });
        mAdapter = new AdapterPost(getActivity(), dataSet);
        recyclerView.setAdapter(mAdapter);

        mStorageRef = FirebaseStorage.getInstance().getReference();


        return rootView;
    }

    public void getImageFromGallery(){
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTED_PIC);
            }
        }
        catch (Exception e){}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, SELECTED_PIC);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    if (uri != null) {
                        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(projection[0]);
                            String filepath = cursor.getString(columnIndex);
                            cursor.close();
                            Bitmap bitmap = BitmapFactory.decodeFile(filepath);

                            // TODO change this to the actual profile picture

                            Bitmap reziedPost = resize(bitmap, 600, 400);
                            uploadImage(filepath);
                            Bitmap profile = BitmapFactory.decodeResource(getResources(), R.drawable.delete_profile_image);
                            dataSet.add(new Post("Bruno", reziedPost, profile ));
                        }
                    }
                }
                break;
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    private void uploadImage(Bitmap bm){
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String imageName = System.currentTimeMillis() + ".jpg";
            final StorageReference storageReference = mStorageRef.child("images/users/" + user.getUid() + "/"+ imageName);

             // Log.e("test", path.getPath());
             // UploadTask uploadTask = storageReference.putFile(Uri.fromFile(new File(path.getPath())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = storageReference.putBytes(data);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    int errorCode = ((StorageException) exception).getErrorCode();
                    String errorMessage = exception.getMessage();

                    Log.e("test", errorMessage);
                    Log.e("test", errorCode+"");
                    Log.e("test", ((StorageException) exception).getHttpResultCode() + "");
                    Log.e("test", exception.getLocalizedMessage() + "");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
        catch (Exception e){
            Log.e("test", e.toString());
        }
    }

    private void uploadImage(String path){
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String imageName = System.currentTimeMillis() + "";
        Log.e("test", path);
        StorageReference storageReference = mStorageRef.child("images/users/" + user.getUid() + "/"+ imageName);
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(new File(path)));
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

}


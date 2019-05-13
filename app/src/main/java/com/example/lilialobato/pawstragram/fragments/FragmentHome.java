    package com.example.lilialobato.pawstragram.fragments;

    import android.Manifest;
    import android.app.Fragment;
    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.database.Cursor;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.support.annotation.NonNull;
    import android.support.v4.app.ActivityCompat;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.util.Base64;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;

    import com.android.volley.NetworkResponse;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.ServerError;
    import com.android.volley.VolleyError;
    import com.android.volley.VolleyLog;
    import com.android.volley.toolbox.HttpHeaderParser;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.lilialobato.pawstragram.ActivityLogin;
    import com.example.lilialobato.pawstragram.MainMenu;
    import com.example.lilialobato.pawstragram.R;
    import com.example.lilialobato.pawstragram.adapters.AdapterPost;
    import com.example.lilialobato.pawstragram.beans.ImageUrl;
    import com.example.lilialobato.pawstragram.beans.Post;
    import com.example.lilialobato.pawstragram.beans.User;
    import com.example.lilialobato.pawstragram.utils.DownloadImageTask;
    import com.google.android.gms.tasks.Continuation;
    import com.google.android.gms.tasks.OnCompleteListener;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.storage.FileDownloadTask;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;


    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.ByteArrayOutputStream;

    import java.io.File;
    import java.io.InputStream;
    import java.io.UnsupportedEncodingException;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;


    import static android.app.Activity.RESULT_OK;

    public class FragmentHome extends Fragment {
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private ArrayList<Post> dataSet;
        ImageView imageView;
        private StorageReference mStorageRef;

        private static final int SELECTED_PIC = 1;
        private static final int PICK_FROM_GALLERY = 2;
        private boolean initialLoad;

        private Bitmap profile;


        public FragmentHome(){
            dataSet = new ArrayList<Post>();
            initialLoad = false;
            profile = ActivityLogin.user.getProfile();
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

            if(!initialLoad){
                loadImages();
                initialLoad = true;
            }

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
                                uploadImage(reziedPost);
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

        public String encodeToBase64(Bitmap bm){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();

            return Base64.encodeToString(b, Base64.DEFAULT);
        }

        public Bitmap decodeBase64(String input)
        {
            byte[] decodedByte = Base64.decode(input, 0);
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }

        private void uploadImage(Bitmap bm){

            final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            final String imagename = System.currentTimeMillis()+ ".jpg";
            final StorageReference ref = mStorageRef.child("images/"+currentFirebaseUser.getUid()+"/"+ imagename);
            UploadTask uploadTask = ref.putBytes(data);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();


                        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com"+ downloadUri.getPath());
                        httpsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                try {
                                    // loads the image to bitmap
                                     StoreImageRef(currentFirebaseUser.getUid(),imagename, uri.toString());
                                    new DownloadImage().execute(uri.toString());
                                } catch (Exception e){
                                    Log.e("test", e.toString());
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }

        public void loadImages () {
            try {
                final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                final String url = "https://8muyysna62.execute-api.us-east-2.amazonaws.com/dev/getposts";
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                JSONObject parameters = new JSONObject();

                parameters.put("uid", currentFirebaseUser.getUid());
                JSONObject body = new JSONObject();
                body.put("body", parameters.toString());
                final String requestBody = body.toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            List<ImageUrl> images = new Gson().fromJson(jsonObject.getString("body"), new TypeToken<List<ImageUrl>>(){}.getType());


                            for (ImageUrl imageUrl : images){
                                new DownloadImage().execute(imageUrl.getUri());
                            }


                        } catch (Exception e) {
                            Log.e("test", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        NetworkResponse response;
                        response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);

                                Log.e("test", res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {

                    @Override
                    public byte[] getBody(){
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                };
                queue.add(stringRequest);
            }catch (Exception e){

            }

        }

        public void StoreImageRef (String uid, String name, String uri) {
            try {
                final String url = "https://8muyysna62.execute-api.us-east-2.amazonaws.com/dev/post";
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                JSONObject parameters = new JSONObject();
                parameters.put("uri", uri);
                parameters.put("name", name);
                parameters.put("uid", uid);
                JSONObject body = new JSONObject();
                body.put("body", parameters.toString());
                final String requestBody = body.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        NetworkResponse response;
                        response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                Log.e("test", res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {

                    @Override
                    public byte[] getBody(){
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                };
                queue.add(stringRequest);
            }catch (Exception e){
                Log.e("Error", e.toString());
            }
        }


        public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
            public DownloadImage() {}

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.toString());

                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {

                dataSet.add(new Post(ActivityLogin.user.getName(), result, profile, false ));
                mAdapter.notifyDataSetChanged();
            }
        }





    }


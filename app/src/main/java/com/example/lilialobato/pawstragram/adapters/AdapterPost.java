package com.example.lilialobato.pawstragram.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.beans.Post;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    private ArrayList<Post> mDataSet;
    private Context context;

    public AdapterPost(Context context, ArrayList<Post> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public AdapterPost.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.profilePicture.setImageBitmap(mDataSet.get(position).getProfileImage());
        holder.content.setImageBitmap(mDataSet.get(position).getContent());
        holder.username.setText(mDataSet.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profilePicture;
        public TextView username;
        public Button menu, like, share;
        public ImageView content;

        public ViewHolder(View v) {
            super(v);
            profilePicture = v.findViewById(R.id.profile_picture);
            username = v.findViewById(R.id.item_post_user);
            menu = v.findViewById(R.id.item_post_menu);
            like = v.findViewById(R.id.item_post_like);
            share = v.findViewById(R.id.item_post_share);
            content = v.findViewById(R.id.item_post_content); //
            // content = viewHolder.item_post_content;

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /// button click event
                    content.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(content.getDrawingCache());
                    File cachePath = new File("/DCIM/Camera/image.jpg");
                    try {
                        cachePath.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(cachePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Intent share = new Intent(Intent.ACTION_SEND);
                    Uri phototUri = Uri.parse("/DCIM/Camera/image.jpg");
                    share.setData(phototUri);
                    share.setType("image/*");
                    share.putExtra(Intent.EXTRA_STREAM, phototUri);
                    view.getContext().startActivity(Intent.createChooser(share, "Share via"));


                }
            });
        }


    }
}

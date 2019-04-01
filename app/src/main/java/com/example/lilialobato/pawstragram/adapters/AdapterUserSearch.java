package com.example.lilialobato.pawstragram.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.beans.UserSearch;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUserSearch extends RecyclerView.Adapter<AdapterUserSearch.ViewHolder>{
    private ArrayList<UserSearch> mDataSet;
    private Context context;

    public AdapterUserSearch(Context context, ArrayList<UserSearch> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public AdapterUserSearch.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_search, parent, false);
        AdapterUserSearch.ViewHolder vh = new AdapterUserSearch.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterUserSearch.ViewHolder holder, final int position) {
        holder.profilePicture.setImageBitmap(mDataSet.get(position).getProfileImage());
        holder.name.setText(mDataSet.get(position).getName());
        holder.username.setText(mDataSet.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profilePicture;
        public TextView username, name;


        public ViewHolder(View v) {
            super(v);
            profilePicture = v.findViewById(R.id.item_user_search_profile_picture);
            username = v.findViewById(R.id.item_user_search_username);
            name = v.findViewById(R.id.item_user_search_name);
        }
    }
}

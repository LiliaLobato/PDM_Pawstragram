package com.example.lilialobato.pawstragram.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lilialobato.pawstragram.ActivityCamara;
import com.example.lilialobato.pawstragram.ActivityEditProfile;
import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.adapters.AdapterPost;
import com.example.lilialobato.pawstragram.beans.Post;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> dataSet;
    ImageView imageView;

    public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_post_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        imageView = (ImageView) rootView.findViewById(R.id.btn_camara);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityCamara.class);
                startActivity(intent);
            }
        });

        dataSet = new ArrayList<Post>();
        // delete later

        Bitmap profile = BitmapFactory.decodeResource(getResources(), R.drawable.delete_profile_image);
        Bitmap posty = BitmapFactory.decodeResource(getResources(), R.drawable.p1);

        dataSet.add(new Post("Bruno", posty, profile ));
        dataSet.add(new Post("Lilia", posty, profile ));
        //end delete later
        mAdapter = new AdapterPost(getActivity(), dataSet);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}


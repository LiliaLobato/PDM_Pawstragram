package com.example.lilialobato.pawstragram.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.adapters.AdapterPost;
import com.example.lilialobato.pawstragram.beans.Post;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> dataSet;

    public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_post_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

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

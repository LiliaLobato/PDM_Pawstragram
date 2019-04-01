package com.example.lilialobato.pawstragram.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.adapters.AdapterUserSearch;
import com.example.lilialobato.pawstragram.beans.UserSearch;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UserSearch> dataSet;

    public FragmentSearch() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_search_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        dataSet = new ArrayList<UserSearch>();
        // delete later

        Bitmap profile = BitmapFactory.decodeResource(getResources(), R.drawable.delete_profile_image);

        dataSet.add(new UserSearch(profile,"Bruno", "Bruno" ));
        dataSet.add(new UserSearch(profile,"Lilia", "Lilia" ));
        //end delete later
        mAdapter = new AdapterUserSearch(getActivity(), dataSet);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}

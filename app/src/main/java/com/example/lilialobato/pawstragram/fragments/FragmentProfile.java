package com.example.lilialobato.pawstragram.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lilialobato.pawstragram.ActivityEditProfile;
import com.example.lilialobato.pawstragram.R;



public class FragmentProfile extends Fragment {

    ImageView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.btn_post);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityEditProfile.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


}

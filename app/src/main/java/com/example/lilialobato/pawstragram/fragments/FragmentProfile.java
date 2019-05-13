package com.example.lilialobato.pawstragram.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lilialobato.pawstragram.ActivityEditProfile;
import com.example.lilialobato.pawstragram.ActivityLogin;
import com.example.lilialobato.pawstragram.R;
import com.example.lilialobato.pawstragram.adapters.AdapterGrid;


public class FragmentProfile extends Fragment {

    ImageView imageView, profileImage;
    TextView username, description;
    GridView posts;

    private boolean initialLoad;

    public FragmentProfile(){
        initialLoad = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.btn_post);
        profileImage = rootView.findViewById(R.id.profile_image);
        username = rootView.findViewById(R.id.display_name_tv);
        description = rootView.findViewById(R.id.description);
        posts = rootView.findViewById(R.id.images_grid_layout);


        profileImage.setImageBitmap(ActivityLogin.user.getProfile());
        username.setText(ActivityLogin.user.getName());
        description.setText(ActivityLogin.user.getDesciption());
        posts.setAdapter(new AdapterGrid(getActivity(), ActivityLogin.user.getPosts()));



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityEditProfile.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        profileImage.setImageBitmap(ActivityLogin.user.getProfile());
        username.setText(ActivityLogin.user.getName());
        description.setText(ActivityLogin.user.getDesciption());
    }
}

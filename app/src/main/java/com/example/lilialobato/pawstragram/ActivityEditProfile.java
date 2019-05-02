package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lilialobato.pawstragram.fragments.FragmentProfile;

public class ActivityEditProfile extends Activity {

    FragmentManager fragmentManager;
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void goreturn(View v) {
        exit = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentProfile());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

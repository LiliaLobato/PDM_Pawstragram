package com.example.lilialobato.pawstragram;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.view.View;
import android.widget.LinearLayout;


import com.example.lilialobato.pawstragram.beans.User;
import com.example.lilialobato.pawstragram.fragments.FragmentHome;
import com.example.lilialobato.pawstragram.fragments.FragmentProfile;
import com.example.lilialobato.pawstragram.fragments.FragmentSearch;
import com.google.gson.Gson;

public class MainMenu extends Activity {


    FragmentHome fragmentHome;
    FragmentProfile fragmentProfile;
    FragmentSearch fragmentSearch;


    LinearLayout container;

    FragmentManager fragmentManager;

    boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        fragmentManager = getFragmentManager();


        container = findViewById(R.id.container);
        fragmentHome = new FragmentHome();
        fragmentProfile = new FragmentProfile();
        fragmentSearch = new FragmentSearch();



        openMenu(new View(this));
    }

    @Override
    public void onBackPressed() {
        if(exit){
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentHome);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        exit = true;
    }

    public void openMenu(View v) {
        exit = true;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentHome);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void goPerfil(View v) {
        exit = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentProfile);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openSearch(View v){
        exit = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentSearch);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}

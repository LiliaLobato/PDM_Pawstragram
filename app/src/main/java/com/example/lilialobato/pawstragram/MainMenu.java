package com.example.lilialobato.pawstragram;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.LinearLayout;


import com.example.lilialobato.pawstragram.fragments.FragmentHome;
import com.example.lilialobato.pawstragram.fragments.FragmentProfile;
import com.example.lilialobato.pawstragram.fragments.FragmentSearch;

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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        container = findViewById(R.id.container);
        fragmentTransaction.replace(R.id.container, new FragmentHome());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        exit = true;

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
        fragmentTransaction.replace(R.id.container, new FragmentHome());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        exit = true;
    }

    public void openMenu(View v) {
        exit = true;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentHome());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void goPerfil(View v) {
        exit = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentProfile());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void openSearch(View v){
        exit = false;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FragmentSearch());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}

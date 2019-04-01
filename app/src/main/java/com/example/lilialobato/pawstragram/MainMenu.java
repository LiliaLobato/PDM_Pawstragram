package com.example.lilialobato.pawstragram;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.widget.LinearLayout;


import com.example.lilialobato.pawstragram.fragments.FragmentHome;

public class MainMenu extends Activity {


    FragmentHome fragmentHome;
    LinearLayout container;

    FragmentManager fragmentManager;


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





    }


}

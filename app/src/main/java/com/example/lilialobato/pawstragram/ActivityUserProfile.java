package com.example.lilialobato.pawstragram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ActivityUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

/*
Creando una nueva escucha para los elementos del Grid
 */
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        /*
        Iniciar una nueva actividad al presionar la foto
         */
                Intent i = new Intent(ActivityUserProfile.this, ActivityDetails.class);
                i.putExtra("position",position);//Posición del elemento
                startActivity(i);

            }
        });
    }

}

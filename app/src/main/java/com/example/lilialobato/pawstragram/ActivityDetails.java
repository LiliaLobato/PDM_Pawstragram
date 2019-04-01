package com.example.lilialobato.pawstragram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        /*
Recibiendo el identificador de la imagen
 */
        Intent i = getIntent();
        int position = i.getIntExtra("position", -1);// -1 si no se encontró la referencia
        ImageAdapter adapter = new ImageAdapter(this);

/*
Seteando el recurso en el ImageView
 */
        ImageView originalImage = (ImageView)findViewById(R.id.originalImage);
        originalImage.setImageResource(adapter.getThumbId(position));
    }
}

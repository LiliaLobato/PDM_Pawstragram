package com.example.lilialobato.pawstragram;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    // Contexto de la aplicación
    private Context mContext;

    // Array de identificadores
    private Integer[] mThumbIds = {
            R.drawable.p1,R.drawable.p2, R.drawable.p3,
            R.drawable.p4,R.drawable.p5, R.drawable.p6,
            R.drawable.p7,R.drawable.p8, R.drawable.p9
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getThumbId(int position){return mThumbIds[position];}

    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView a retornar
        ImageView imageView;

        if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300,300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        //Setear la imagen desde el recurso drawable
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}
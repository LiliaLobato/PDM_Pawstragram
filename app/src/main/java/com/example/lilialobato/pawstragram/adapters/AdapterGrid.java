package com.example.lilialobato.pawstragram.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.lilialobato.pawstragram.beans.Post;

import java.util.ArrayList;

public class AdapterGrid extends BaseAdapter {

    private ArrayList<Post> elements;
    private Context context;

    public AdapterGrid(Context context, ArrayList<Post> elements){
        this.context = context;
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Bitmap getItem(int i) {
        return elements.get(i).getContent();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            // imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) view;
        }
        imageView.setImageBitmap(elements.get(i).getContent());
        return imageView;
    }
}

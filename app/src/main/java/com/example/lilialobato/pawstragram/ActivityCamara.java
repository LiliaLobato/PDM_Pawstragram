package com.example.lilialobato.pawstragram;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//TODO errores en camara, implementar tomar foto
public class ActivityCamara extends AppCompatActivity {
    private static final int SELECTED_PIC = 1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imageView = findViewById(R.id.activity_main_image);
    }

    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    if (uri != null) {
                        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(projection[0]);
                            String filepath = cursor.getString(columnIndex);
                            cursor.close();
                            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            imageView.setBackground(drawable);
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Prueba ASF", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                    }
                }
                break;
        }
    }
}

package com.example.lilialobato.pawstragram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityEditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void goreturn(View v) {
        Intent intent = new Intent(this, ActivityUserProfile.class);
        this.startActivity(intent);
    }
}

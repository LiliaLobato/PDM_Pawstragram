package com.example.lilialobato.pawstragram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActivitySignup extends AppCompatActivity {
    final Button button = (Button) findViewById(R.id.btn_signup_back);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void OnClickListener(View v) {
            startActivity(new Intent(this,ActivityLogin.class));
        }
}
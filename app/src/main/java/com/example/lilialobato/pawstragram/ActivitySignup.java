package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActivitySignup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, ActivityLogin.class);
        this.startActivity(intent);
    }

    public void login(View v){
        Intent intent = new Intent(this, MainMenu.class);
        this.startActivity(intent);
    }

}
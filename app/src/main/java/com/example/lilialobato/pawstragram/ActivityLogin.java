package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class ActivityLogin extends Activity {
    private static final String EMAIL = "email";
    LoginButton loginButton;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        callbackManager = CallbackManager.Factory.create();
        Toast.makeText(this,"token = " + AccessToken.getCurrentAccessToken(), Toast.LENGTH_LONG).show();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("login", "login success");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("login", "login cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("login", "login error");
                    }

                });
    }

    public void login(View v){
        Intent intent = new Intent(this, MainMenu.class);
        this.startActivity(intent);
    }
    public void signup(View v){
        Intent intent = new Intent(this, ActivitySignup.class);
        this.startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

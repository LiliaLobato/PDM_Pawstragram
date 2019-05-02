package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ActivityLogin extends Activity {

    LinearLayout mLoginContainer;
    AnimationDrawable mAnimationDrawable;

    EditText username_et,password_et;
    ProgressDialog mProgrssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Animation config
        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
        mAnimationDrawable = (AnimationDrawable) mLoginContainer.getBackground();
        mAnimationDrawable.setEnterFadeDuration(5000);
        mAnimationDrawable.setExitFadeDuration(2000);

        username_et = (EditText) findViewById(R.id.user_name);
        password_et = (EditText) findViewById(R.id.user_password);
        mProgrssDialog = new ProgressDialog(this);
    }

    public void login(View v){
        logIn();
    }
    public void signup(View v){
        Intent intent = new Intent(this, ActivitySignup.class);
        this.startActivity(intent);
    }

    private void logIn(){

        mProgrssDialog.setTitle("Log In");
        mProgrssDialog.setMessage("Please wait....");
        mProgrssDialog.show();

        final String username = username_et.getText().toString();
        final String password = password_et.getText().toString();

        if(TextUtils.isEmpty(username)){
            username_et.setError("Please fill in this field");
            username_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(password)){
            password_et.setError("Please fill in this field");
            password_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        finish();
        Intent signUpIntent = new Intent(this,MainMenu.class);
        startActivity(signUpIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }

}

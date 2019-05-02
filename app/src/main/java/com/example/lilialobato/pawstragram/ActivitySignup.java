package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivitySignup extends Activity {

    LinearLayout mLoginContainer;
    AnimationDrawable mAnimationDrawable;

    EditText username_et, password_et, password_confirm_et;
    ProgressDialog mProgrssDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Animation config
        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
        mAnimationDrawable = (AnimationDrawable) mLoginContainer.getBackground();
        mAnimationDrawable.setEnterFadeDuration(5000);
        mAnimationDrawable.setExitFadeDuration(2000);


        //login design varibales
        username_et = (EditText) findViewById(R.id.activity_signup_username_edit);
        password_et = (EditText) findViewById(R.id.activity_signup_password_edit);
        password_confirm_et = (EditText) findViewById(R.id.activity_signup_password_confirm);
        mProgrssDialog = new ProgressDialog(this);

    }

    public void goBack(View v) {
        finish();
        Intent intent = new Intent(this, ActivityLogin.class);
        this.startActivity(intent);
    }

    public void login(View v){
        register();
    }

    private void register() {


        mProgrssDialog.setTitle("Creating your account");
        mProgrssDialog.setMessage("Please wait....");
        mProgrssDialog.show();

        final String username = username_et.getText().toString();
        final String password = password_et.getText().toString();
        String password_confirm = password_confirm_et.getText().toString();

        if (TextUtils.isEmpty(username)) {
            username_et.setError("Please fill in this field");
            username_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            password_et.setError("Please fill in this field");
            password_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(password_confirm)) {
            password_confirm_et.setError("Please fill in this field");
            password_confirm_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        if (!password.equals(password_confirm)) {
            password_et.setError("Password charachters don't match!");
            password_et.requestFocus();
            mProgrssDialog.dismiss();
            return;
        }

        finish();
        Intent intent = new Intent(this, MainMenu.class);
        this.startActivity(intent);
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
package com.example.lilialobato.pawstragram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lilialobato.pawstragram.beans.ImageUrl;
import com.example.lilialobato.pawstragram.beans.Post;
import com.example.lilialobato.pawstragram.beans.User;
import com.example.lilialobato.pawstragram.fragments.FragmentHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogin extends Activity {

    LinearLayout mLoginContainer;
    AnimationDrawable mAnimationDrawable;

    EditText username_et,password_et;
    ProgressDialog mProgrssDialog;
    private FirebaseAuth mAuth;

    private static final String TAG = "ActivityLogin";

    public static User user;

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
        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View v){
        logIn();
    }
    public void signup(View v){
        Intent intent = new Intent(this, ActivitySignup.class);
        this.startActivity(intent);
    }

    private void logIn(){
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
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("test", user.getUid());
                            getUserInfo(user.getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void getUserInfo(String uid){
        try {
            final String url = "https://8muyysna62.execute-api.us-east-2.amazonaws.com/dev/getuserinfo";
            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject parameters = new JSONObject();

            parameters.put("uid", uid);
            JSONObject body = new JSONObject();
            body.put("body", parameters.toString());
            final String requestBody = body.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response

                    try {

                        JSONObject jsonObject = new JSONObject(new JSONObject(response).getString("body"));
                        Log.e("test", jsonObject.toString());

                        ArrayList<Post> posts = new Gson().fromJson(jsonObject.getString("posts"), new TypeToken<ArrayList<Post>>(){}.getType());

                        user = new User(jsonObject.getString("name"),
                                jsonObject.getString("description"), Integer.parseInt(jsonObject.getString("followCount")),
                                posts.size(), Integer.parseInt(jsonObject.getString("followingCount")),
                                null, posts);

                        //TODO load profile image
                        if (jsonObject.getString("profileUrl") != null) {
                            new DownloadImage().execute(jsonObject.getString("profileUrl"));

                        } else {
                            user.setProfile(BitmapFactory.decodeResource(getResources(), R.drawable.delete_profile_image));
                        }




                    } catch (Exception e) {
                        Log.e("test", e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    NetworkResponse response;
                    response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);

                            Log.e("test", res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }
                }
            }) {

                @Override
                public byte[] getBody(){
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

            };
            queue.add(stringRequest);
        }catch (Exception e){

        }
    }
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        public DownloadImage() {}

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.toString());

            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            user.setProfile(result);

            finish();
            Intent signUpIntent = new Intent(ActivityLogin.this, MainMenu.class);
            startActivity(signUpIntent);
        }
    }
}

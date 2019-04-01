package com.example.lilialobato.pawstragram.beans;

import android.graphics.Bitmap;

public class Post {
    private String username;
    private Bitmap content;
    private Bitmap profileImage;

    public Post(String username, Bitmap image, Bitmap profileImage) {
        this.username = username;
        this.content = image;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getContent() {
        return content;
    }

    public void setContent(Bitmap content) {
        this.content = content;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }
}

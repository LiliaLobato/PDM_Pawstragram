package com.example.lilialobato.pawstragram.beans;

import android.graphics.Bitmap;

public class Post {
    private String username;
    private Bitmap content;
    private Bitmap profileImage;
    private Boolean like;

    public Post(String username, Bitmap image, Bitmap profileImage) {
        this.username = username;
        this.content = image;
        this.profileImage = profileImage;
    }

    public Post(String username, Bitmap content, Bitmap profileImage, Boolean like) {
        this.username = username;
        this.content = content;
        this.profileImage = profileImage;
        this.like = like;
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

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }
}

package com.example.lilialobato.pawstragram.beans;

import android.graphics.Bitmap;

public class UserSearch {
    private Bitmap profileImage;
    private String username, name;

    public UserSearch(Bitmap profileImage, String username, String name) {
        this.profileImage = profileImage;
        this.username = username;
        this.name = name;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

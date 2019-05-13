package com.example.lilialobato.pawstragram.beans;

import android.graphics.Bitmap;

import java.util.ArrayList;


public class User {
    private String name, desciption;
    private int followCount, postCount, followingCount;
    private Bitmap profile;
    private boolean following = false;

    private ArrayList<Post> posts;




    // for app user
    public User(String name, String desciption, int followCount, int postCount, int followingCount, Bitmap profile, ArrayList<Post> posts) {
        this.name = name;
        this.desciption = desciption;
        this.followCount = followCount;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.profile = profile;
        this.posts = posts;
    }

    // for pawstagramer

    public User(String name, String desciption, int followCount, int postCount, int followingCount, Bitmap profile, boolean following, ArrayList<Post> posts) {
        this.name = name;
        this.desciption = desciption;
        this.followCount = followCount;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.profile = profile;
        this.following = following;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

}

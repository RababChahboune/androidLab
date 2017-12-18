package com.example.learning.androidlabwork.Model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

/**
 * Created by Rabab Chahboune on 12/6/2017.
 */

public class Post {
    String imageId;
    String title;
    String description;
    double latitude;
    double longitude;
    //FirebaseUser user;


    public Post() {
    }

    public Post(String imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    public Post(String imageId, String title, String description, double latitude, double longitude) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        //this.user = user;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /*public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }*/
}

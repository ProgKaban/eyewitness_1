package com.example.fierbaseauth.firebase.entities;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.io.Serializable;

public class Event implements Serializable {
    public String id, title, description, location, createdBy, creatorUID;



    public Event(String title, String description, String location, String createdBy, String creatorUID){
        this.title = title;
        this.description = description;
        this.location = location;
        this.createdBy = createdBy;
        this.creatorUID = creatorUID;
    }


    public Event(){

    }
    public void printDebug(){
        Log.d(TAG, "printDebug: " + title);
    }

}

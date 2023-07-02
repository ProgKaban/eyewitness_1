package com.example.fierbaseauth.firebase.data;

import java.io.Serializable;

public enum Entity {
    EVENT("event")
    ;
    private final String KEY;
    Entity(String KEY){
        this.KEY = KEY;
    }
    public String getKEY(){
        return KEY;
    }
}

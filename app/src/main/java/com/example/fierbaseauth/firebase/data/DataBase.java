package com.example.fierbaseauth.firebase.data;

import androidx.annotation.NonNull;

import com.example.fierbaseauth.firebase.entities.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Objects;

public class DataBase {
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;
    public DataBase(Entity entity){
        databaseReference = FirebaseDatabase.getInstance().getReference(Entity.EVENT.getKEY());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private Query createQuery(){
        return databaseReference;
    }
    private Query createQuery(String key){
        return databaseReference.child(key);
    }

    public void addValueEventListener(ValueEventListener valueEventListener){
        databaseReference.addValueEventListener(valueEventListener);
    }

    public void deleteObject(String key){
        createQuery(key).getRef().removeValue();
    }
    public void deleteObject(String key, ValueEventListener valueEventListener){
        Query query = createQuery(key);
        query.addListenerForSingleValueEvent(valueEventListener);
        query.getRef().removeValue();
    }
    public void addObject(Object object){
        createQuery().getRef().push().setValue(object);
    }
    public void addObject(Object object, ValueEventListener valueEventListener){
        Query query = createQuery();
        query.addListenerForSingleValueEvent(valueEventListener);
        query.getRef().push().setValue(object);
    }


    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
}

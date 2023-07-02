package com.example.fierbaseauth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fierbaseauth.adapters.EventListViewAdapter;
import com.example.fierbaseauth.firebase.data.DataBase;
import com.example.fierbaseauth.firebase.data.Entity;
import com.example.fierbaseauth.firebase.entities.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyEvents extends AppCompatActivity implements EventListViewAdapter.OnEventItemClickListener {

    RecyclerView recyclerEventList;
    EventListViewAdapter adapter;
    FirebaseAuth firebaseAuth;
    DataBase dataBase;
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        initViews();

    }

    private void initViews(){
        events = new ArrayList<>();
        initRecyclerView();
        firebaseAuth = FirebaseAuth.getInstance();
        dataBase = new DataBase(Entity.EVENT);
        dataBase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (events.size() > 0) events.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    Event event = null;
                    try {
                        event = ds.getValue(Event.class);
                    }catch (Exception e){
                        Log.e(TAG, "onDataChange: "+ e.getMessage());
                    }
                    assert event != null;
                    if(!Objects.equals(event.creatorUID, firebaseAuth.getUid())) continue;
                    event.id = ds.getKey();
                    events.add(event);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initRecyclerView(){
        adapter = new EventListViewAdapter(this, events, this);
        recyclerEventList = findViewById(R.id.reccler_myeventList);
        recyclerEventList.setLayoutManager(new LinearLayoutManager(this));
        recyclerEventList.setAdapter(adapter);
    }

    @Override
    public void onEventClick(View view, Event event, int itemPos) {
        DialogInterface.OnClickListener dialogClickListener = (dialogInterface, i) -> {
            switch (i){
                case DialogInterface.BUTTON_POSITIVE:
                    dataBase.deleteObject(event.id);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete \""+event.title+"\" event?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }
}
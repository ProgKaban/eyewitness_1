package com.example.fierbaseauth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fierbaseauth.firebase.data.DataBase;
import com.example.fierbaseauth.firebase.data.Entity;
import com.example.fierbaseauth.firebase.entities.Event;
import com.example.fierbaseauth.adapters.EventListViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventListViewAdapter.OnEventItemClickListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    DataBase dataBaseConnection;

    private ArrayList<Event> events;
    private RecyclerView recyclerEventList;
    private EventListViewAdapter adapter;

    private FloatingActionButton btnToAddEvent, btnExit, btnToMyEventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authCheck();
        initViews();
    }

    private void initRecyclerView(){
        adapter = new EventListViewAdapter(this, events, this);
        recyclerEventList = findViewById(R.id.reccler_eventList);
        recyclerEventList.setLayoutManager(new LinearLayoutManager(this));
        recyclerEventList.setAdapter(adapter);
    }

    private void initViews(){
        dataBaseConnection = new DataBase(Entity.EVENT);
        events = new ArrayList<>();
        initRecyclerView();
//        FireBaseConnection fireBaseConnection = new FireBaseConnection(FireBaseConnection.Entity.EVENT, Event.class);
//        fireBaseConnection.addUpdateDataListener(adapter, events);
        dataBaseConnection.addValueEventListener(new ValueEventListener() {
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
                    event.id = ds.getKey();
                    events.add(event);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnToAddEvent = findViewById(R.id.btn_to_add_event);
        btnExit = findViewById(R.id.btn_exit);
        btnToMyEventList = findViewById(R.id.btn_to_my_event_list);

        btnToAddEvent.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEventActivity.class);
            startActivity(intent);
        });
        btnExit.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
        btnToMyEventList.setOnClickListener(view -> {
            startActivity(new Intent(this, MyEvents.class));
        });
    }

    private void authCheck(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onEventClick(View view, Event event, int itemPos) {

        Intent intent = new Intent(this, IntroduceEvent.class);
        intent.putExtra("event", event);
        startActivity(intent);

    }
}
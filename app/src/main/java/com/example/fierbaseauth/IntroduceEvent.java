package com.example.fierbaseauth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fierbaseauth.firebase.entities.Event;

public class IntroduceEvent extends AppCompatActivity {

    TextView detailed_title, detailed_description, detailed_location, detailed_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce_event);
        initViews();
        Event event = (Event) getIntent().getSerializableExtra("event");
        if(event == null) return;

        detailed_title.setText(event.title);
        detailed_description.setText(event.description);
        detailed_location.setText(event.location);
        detailed_by.setText(event.createdBy);
    }

    private void initViews(){
        detailed_title = findViewById(R.id.detailed_title);
        detailed_description = findViewById(R.id.detailed_description);
        detailed_location = findViewById(R.id.detailed_location);
        detailed_by = findViewById(R.id.detailed_by);
    }

}
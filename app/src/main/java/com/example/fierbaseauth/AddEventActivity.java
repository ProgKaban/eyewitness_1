package com.example.fierbaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.fierbaseauth.firebase.data.DataBase;
import com.example.fierbaseauth.firebase.data.Entity;
import com.example.fierbaseauth.firebase.entities.Event;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddEventActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DataBase dataBaseConnection;
    private Button addBtn;
    private TextInputEditText tv_title, tv_description, tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        authCheck();
        initViews();
    }

    private void initViews(){
        tv_title = findViewById(R.id.title);
        tv_description = findViewById(R.id.description);
        tv_location = findViewById(R.id.location);
        addBtn = findViewById(R.id.btn_add);
        dataBaseConnection = new DataBase(Entity.EVENT);

        addBtn.setOnClickListener(view -> {
            String title, description, location;
            title = String.valueOf(tv_title.getText());
            description = String.valueOf(tv_description.getText());
            location = String.valueOf(tv_location.getText());
            Event event = new Event(title, description, location, user.getDisplayName(), user.getUid());
            dataBaseConnection.addObject(event);
            finish();
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

}
package com.example.fierbaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splachScreen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Запускаем следующую activity после задержки fd
                Intent i = new Intent(splachScreen.this, Login.class);
                startActivity(i);

                // Закрываем текущую activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
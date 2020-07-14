package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.androidfinalexam.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstTime();
            }
        }, 1000);

    }

    private void isFirstTime(){

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);

        if ( isFirstTime ){
            SharedPreferences.Editor editor  = sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            startActivity( new Intent( SplashScreenActivity.this, OnboardActivity.class ));
            finish();
        }else{
            startActivity( new Intent( SplashScreenActivity.this, HomeActivity.class ));
            finish();
        }

    }

}

package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.LoginFragment;
import com.example.androidfinalexam.models.RegisterFragmet;

public class AuthActivitis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_activitis);

        getSupportFragmentManager().beginTransaction().replace(R.id.framauth_id, new RegisterFragmet()).commit();


    }
}

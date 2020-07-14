package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.LoginFragment;
import com.example.androidfinalexam.models.RegisterFragmet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthActivitis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_activitis);

        getSupportFragmentManager().beginTransaction().replace(R.id.framauth_id, new LoginFragment()).commit();


    }
}

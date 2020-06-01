package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.adapters.SlideBrandAdapter;
import com.example.androidfinalexam.models.CategoryFrgment;
import com.example.androidfinalexam.models.HomeFragment;
import com.example.androidfinalexam.models.ItemBrand;
import com.example.androidfinalexam.models.NotificationFragment;
import com.example.androidfinalexam.models.PersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        if ( savedInstanceState == null ){
            getSupportFragmentManager().beginTransaction().replace(R.id.framContainer, new HomeFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.home_id:
                        fragment = new HomeFragment();
                        break;

                    case R.id.category_id:
                        fragment = new CategoryFrgment();
                        break;
                    case R.id.notifications_id:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.person_id:
                        fragment = new PersonFragment();
                        break;

                }
                fragmentTransaction.replace(R.id.framContainer, fragment);
                fragmentTransaction.commit();
                return true;

            }
        });



    }



    private void initView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }



}

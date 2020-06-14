package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.Cart;
import com.example.androidfinalexam.models.HomeFragment;
import com.example.androidfinalexam.models.NotificationFragment;
import com.example.androidfinalexam.models.PersonFragment;
import com.example.androidfinalexam.models.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static SharedPreferences sharedPreferences;
    public static  SharedPreferences.Editor editor;
    public static ArrayList<Cart> cartArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        getUser();
        putCart();
        loadData();


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

                    case R.id.search_id:
                        fragment = new SearchFragment();
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

    private void getUser() {

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            String date = intent.getStringExtra("date");

                sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

                // put data into sharedPreferences
                editor = sharedPreferences.edit();

                if ( email == null && date == null ){
                    //check info from
                     email = HomeActivity.sharedPreferences.getString("taikhoan", "");
                     date = HomeActivity.sharedPreferences.getString("ngaydangky", "");
                }else {
                    editor.putString("id", id);
                    editor.putString("taikhoan", email);
                    editor.putString("matkhau", password);
                    editor.putString("ngaydangky", date);
                }

                editor.apply();
    }

    private void initView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if ( cartArrayList != null ){

        }else{
            cartArrayList = new ArrayList<Cart>();
        }

    }

    public void putCart(){
        sharedPreferences = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();

        if ( cartArrayList.size() > 0 ){
            String json = gson.toJson(cartArrayList);
            editor.putString("CartList", json);
            editor.apply();
        }else {
            String json = sharedPreferences.getString("CartList", null);
            editor.putString("CartList", json);
            editor.apply();
        }

    }

    public void loadData(){
        sharedPreferences = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CartList", null);

        try {
            JSONArray jsonArray = new JSONArray(json);
            cartArrayList.clear();

            for ( int i = 0; i < jsonArray.length(); i++ ){
                JSONObject object = (JSONObject) jsonArray.get(i);
                cartArrayList.add( new Cart(
                        object.getInt("id"),
                        object.getString("namePro"),
                        object.getString("imgPro"),
                        object.getDouble("price"),
                        object.getInt("amount")
                ) );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}

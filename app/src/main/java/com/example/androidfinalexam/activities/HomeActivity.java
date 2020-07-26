package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidfinalexam.CheckConnection;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.Cart;
import com.example.androidfinalexam.models.HomeFragment;
import com.example.androidfinalexam.models.NotificationFragment;
import com.example.androidfinalexam.models.PersonFragment;
import com.example.androidfinalexam.models.Products;
import com.example.androidfinalexam.models.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences sharedLogin;
    public static SharedPreferences.Editor editor;
    public static ArrayList<Cart> cartArrayList;
    public static ArrayList<Products> listProLove = new ArrayList<Products>();
    private FloatingActionButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){

            initView();
            getUser();
            putCart();
            loadData();
            //getProductLove();


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


        }else {
            Toast.makeText(this, "Kiểm tra lại kết nối của bạn !", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn thoát ứng dụng không?");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void getUser() {

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            String date = intent.getStringExtra("date");


        sharedLogin = getSharedPreferences("login", MODE_PRIVATE);

                // put data into sharedPreferences
                editor = sharedLogin.edit();

                if ( email == null && date == null ){
                    //check info from
                     email = HomeActivity.sharedLogin.getString("taikhoan", "");
                     date = HomeActivity.sharedLogin.getString("ngaydangky", "");
                }else {
                    editor.putString("id", id);
                    editor.putString("taikhoan", email);
                    editor.putString("matkhau", password);
                    editor.putString("ngaydangky", date);
                }

                editor.apply();
    }

    private void initView() {
        HomeActivity.cartArrayList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if ( cartArrayList != null ){

        }else{
            cartArrayList = new ArrayList<Cart>();
        }

        // thông tin tác giả
        profile = (FloatingActionButton) findViewById(R.id.profile_id);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

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

       if ( json != null ){
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

    private void getProductLove(){

        HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = HomeActivity.sharedPreferences.getString("ProductLove", null);

        if ( json != null ){
            try {
                JSONArray jsonArray = new JSONArray(json);
                for ( int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    int id = object.getInt("id");
                    int id_brand = object.getInt("id_b");
                    int id_categories =  object.getInt("id_categories");
                    String name = object.getString("name");
                    String image = object.getString("image");
                    int amounts = object.getInt("amounts");
                    double price = object.getDouble("price");
                    String products_desc = object.getString("products_desc");
                    double star =  object.getDouble("star");

                    listProLove.add( new Products( id, id_brand, id_categories, name, image, amounts, price, products_desc, star ) );

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }



}

package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.adapters.YourCommentAdapter;
import com.example.androidfinalexam.models.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class YourCommentActivity extends AppCompatActivity {

    private ImageView imgBack, imgCart;
    private RecyclerView recyclerView;
    private ArrayList<Comment> mData = new ArrayList<>();
    private YourCommentAdapter yourCommentAdapter;
    private String url = UrlApi.yourComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_comment);

        // functions
        initView();
        setOnClick();
        getData();

    }

    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);

        // setUprecyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        yourCommentAdapter = new YourCommentAdapter( YourCommentActivity.this, mData );
        recyclerView.setAdapter(yourCommentAdapter);

    }

    private void setOnClick(){

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( YourCommentActivity.this, CartActivity.class ));
            }
        });

    }

    private void getData(){


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        mData.add( new Comment(
                                jsonObject.getInt("id"),
                                jsonObject.getInt("id_user"),
                                jsonObject.getInt("id_products"),
                                jsonObject.getString("content"),
                                jsonObject.getString("img"),
                                jsonObject.getInt("star"),
                                jsonObject.getString("date"),
                                jsonObject.getString("name"),
                                jsonObject.getString("image")
                        ) );
                    }
                    yourCommentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(YourCommentActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();

                String id_user = HomeActivity.sharedLogin.getString("id", "");
                parrams.put("id_user", id_user);
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

}

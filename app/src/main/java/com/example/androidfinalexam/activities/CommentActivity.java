package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.androidfinalexam.adapters.CommentAdapter;
import com.example.androidfinalexam.models.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    private ImageView imgBack, imgCart;
    private Button btnBinhLuan;
    private String id_user;
    private String id_products, star, countComment;
    private AppCompatRatingBar appCompatRatingBar;
    private TextView txtStar, txtSoBinhLuan, txtSoBinhLuan1, txtSoBinhLuan2, txtSoBinhLuan3, txtSoBinhLuan4, txtSoBinhLuan5;
    private RecyclerView recyclerView;
    public static ArrayList<Comment> mData = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private String url = UrlApi.getComment;
    private ProgressBar progressBar5, progressBar4, progressBar3, progressBar2, progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // functions
        initView();
        getDataIntent();
        setOnClick();
        setUpRecyclerview();
        getData();
        setData();

    }

    private void getData() {

        mData.clear();
        commentAdapter.notifyDataSetChanged();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("data");

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
                                jsonObject.getString("email")
                        ) );
                    }


                    commentAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CommentActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                parrams.put("id_products", id_products);
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void getDataIntent(){
        // getIntent
        Intent intent = getIntent();
        id_products = intent.getStringExtra("id_products");
        star = intent.getStringExtra("star");
        countComment = intent.getStringExtra("countComment");
    }

    private void setData(){

        txtStar.setText(star);
        txtSoBinhLuan.setText(countComment + " nhận xét");
        appCompatRatingBar.setRating(Float.parseFloat(star));

        // process bar
        progressBar1.setMax(Integer.parseInt(countComment));
        progressBar2.setMax(Integer.parseInt(countComment));
        progressBar3.setMax(Integer.parseInt(countComment));
        progressBar4.setMax(Integer.parseInt(countComment));
        progressBar5.setMax(Integer.parseInt(countComment));

        // đếm số cmt của từng sao
       // lấy data
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    int star5 =  0, star4 = 0, star3 = 0, star2 = 0, star1 = 0;

                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                        switch ( jsonObject.getInt("star") ){
                            case 1:
                                star1++;
                                txtSoBinhLuan1.setText(String.valueOf(star1));
                                progressBar1.setProgress(star1);
                                break;
                            case 2:
                                star2++;
                                txtSoBinhLuan2.setText(String.valueOf(star2));
                                progressBar2.setProgress(star2);
                                break;
                            case 3:
                                star3++;
                                txtSoBinhLuan3.setText(String.valueOf(star3));
                                progressBar3.setProgress(star3);
                                break;
                            case 4:
                                star4++;
                                txtSoBinhLuan4.setText(String.valueOf(star4));
                                progressBar4.setProgress(star4);
                                break;
                            case 5:
                                star5++;
                                txtSoBinhLuan5.setText(String.valueOf(star5));
                                progressBar5.setProgress(star5);
                                break;
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CommentActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                parrams.put("id_products", id_products);
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void setOnClick() {

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( CommentActivity.this, CartActivity.class ));
            }
        });

        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get id_user
                //check info from
                String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
                String date = HomeActivity.sharedLogin.getString("ngaydangky", "");

                if ( ( taikhoan != "" ) && ( date != "" ) ){
                    // nếu có info
                    id_user = HomeActivity.sharedLogin.getString("id", "");
                    Intent intent = new Intent( CommentActivity.this,  VietBinhLuanActivity.class);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("id_products", id_products);
                    startActivity(intent);

                }else {
                    startActivity( new Intent( CommentActivity.this, AuthActivitis.class ) );
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                mData.clear();
            }
        });

    }

    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);
        btnBinhLuan = (Button) findViewById(R.id.btnVietBinhLuan);
        appCompatRatingBar = (AppCompatRatingBar) findViewById(R.id.rating_bar_total_id);
        txtStar = (TextView) findViewById(R.id.star_id);
        txtSoBinhLuan = (TextView) findViewById(R.id.so_binhLuan);
        txtSoBinhLuan1 = (TextView) findViewById(R.id.so_comment1);
        txtSoBinhLuan2 = (TextView) findViewById(R.id.so_comment2);
        txtSoBinhLuan3 = (TextView) findViewById(R.id.so_comment3);
        txtSoBinhLuan4 = (TextView) findViewById(R.id.so_comment4);
        txtSoBinhLuan5 = (TextView) findViewById(R.id.so_comment5);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar5 = (ProgressBar) findViewById(R.id.progressBar_id_5);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar_id_4);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar_id_3);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar_id_2);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_id_1);

    }

    private void setUpRecyclerview(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter( CommentActivity.this, mData );
        recyclerView.setAdapter(commentAdapter);
    }

}

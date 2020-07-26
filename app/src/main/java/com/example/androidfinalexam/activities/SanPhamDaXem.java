package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.androidfinalexam.adapters.ProductAdapter;
import com.example.androidfinalexam.models.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamDaXem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Products> mData;
    private ProductAdapter productAdapter;
    private ImageView imgBack, imgCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_da_xem);

        // functions
        initView();
        getData();
        setOnClick();
        setUpRecyclerview();

    }

    private void getData() {
        String url = UrlApi.xemSanPhamDaXem;

        // nhận id users
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        // gửi data lên server
        RequestQueue queue = Volley.newRequestQueue(SanPhamDaXem.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        mData.add( new Products(
                                jsonObject.getInt("id"),
                                jsonObject.getInt("id_brand"),
                                jsonObject.getInt("id_categories"),
                                jsonObject.getString("name"),
                                jsonObject.getString("image"),
                                jsonObject.getInt("amounts"),
                                jsonObject.getDouble("price"),
                                jsonObject.getString("products_desc"),
                                jsonObject.getDouble("star")
                        ) );
                    }

                    productAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SanPhamDaXem.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                parrams.put("id_users", id);
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mData = new ArrayList<Products>();
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
                startActivity( new Intent( SanPhamDaXem.this, CartActivity.class ) );
            }
        });

    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL) );
        productAdapter = new ProductAdapter(SanPhamDaXem.this, mData);
        recyclerView.setAdapter(productAdapter);
    }

}

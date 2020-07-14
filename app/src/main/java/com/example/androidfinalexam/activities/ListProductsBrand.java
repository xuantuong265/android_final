package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.adapters.MobileProAdapter;
import com.example.androidfinalexam.adapters.ProductAdapter;
import com.example.androidfinalexam.models.Products;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListProductsBrand extends AppCompatActivity {

    private ImageView imgBack, imgCart;
    private RecyclerView recyclerView;
    private ProductAdapter mobileProAdapter;
    public ArrayList<Products> mData = new ArrayList<>();
    private int brand_id;
    private View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_brand);

        // ánh xạ
        initView();
        setOnclick();
        setUpRecyclerview();
        getData();
        loadMoreData();



    }

    private void loadMoreData() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        mobileProAdapter = new ProductAdapter(ListProductsBrand.this, mData);
        recyclerView.setAdapter(mobileProAdapter);
    }

    private void getData() {
        String url = UrlApi.listProductsBrand;
        Intent intent = getIntent();
        brand_id = intent.getIntExtra("id", -1);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for ( int i = 0; i < jsonArray.length(); i++ ){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            int id_brand = jsonObject.getInt("id_brand");
                            int id_categories = jsonObject.getInt("id_categories");
                            String name = jsonObject.getString("name");
                            String image = jsonObject.getString("image");
                            int amounts = jsonObject.getInt("amounts");
                            double price = jsonObject.getDouble("price");
                            String products_desc = jsonObject.getString("products_desc");
                            double star = jsonObject.getDouble("star");

                            mData.add( new Products(id, id_brand, id_categories, name, image, amounts, price, products_desc, star) );

                        }

                        mobileProAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                parrams.put("id_brand", String.valueOf(brand_id));
                return parrams;
            }
        };
        queue.add(stringRequest);


    }

    private void setOnclick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( ListProductsBrand.this, CartActivity.class ) );
            }
        });
    }

    private void initView() {

        // ánh xạ
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        recyclerView = (RecyclerView) findViewById(R.id.recy_pro_brand_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);

    }


}

package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.androidfinalexam.adapters.ProductBrandAdapter;
import com.example.androidfinalexam.models.Products;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListProductsBrand extends AppCompatActivity {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private ProductBrandAdapter productBrandAdapter;
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        productBrandAdapter = new ProductBrandAdapter(ListProductsBrand.this, mData);
        recyclerView.setAdapter(productBrandAdapter);
    }

    private void getData() {
        String url = "http://192.168.0.27/Laravel/clonetiki/api/getProBrand";
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
                            String name = jsonObject.getString("name");
                            String image = jsonObject.getString("image");
                            int amounts = jsonObject.getInt("amounts");
                            double price = jsonObject.getDouble("price");
                            String products_desc = jsonObject.getString("products_desc");
                            double star = jsonObject.getDouble("star");

                            mData.add( new Products(id, id_brand, name, image, amounts, price, products_desc, star) );

                        }

                        productBrandAdapter.notifyDataSetChanged();

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
    }

    private void initView() {

        // ánh xạ
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        recyclerView = (RecyclerView) findViewById(R.id.recy_pro_brand_id);
        footerView = (View) findViewById(R.id.proressbar_id);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);

    }


}

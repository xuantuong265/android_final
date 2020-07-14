package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.androidfinalexam.adapters.MobileProAdapter;
import com.example.androidfinalexam.adapters.ProductAdapter;
import com.example.androidfinalexam.models.DetailOrders;
import com.example.androidfinalexam.models.Orders;
import com.example.androidfinalexam.models.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XemThemDienThoai extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Products> mData;
    private ProductAdapter mobileProAdapter;
    private ImageView imgBack, imgCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_them_dien_thoai);

        // functions
        initView();
        getData();
        setOnClick();
        setUpRecyclerview();

    }

    private void getData() {
        String url = UrlApi.getProCate;

        // gửi data lên server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject object = (JSONObject) jsonArray.get(i);

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
                Toast.makeText(getApplicationContext(), "Lỗi " + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                Intent intent = getIntent();

                int id_cate = intent.getIntExtra("id_categories", 0);
                parrams.put("id_categories", String.valueOf(id_cate));
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
                startActivity( new Intent( XemThemDienThoai.this, CartActivity.class ) );
            }
        });

    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL) );
        mobileProAdapter = new ProductAdapter(XemThemDienThoai.this, mData);
        recyclerView.setAdapter(mobileProAdapter);
    }

}

package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.androidfinalexam.adapters.OrderAdapter;
import com.example.androidfinalexam.models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonHangChoXuLy extends AppCompatActivity {

    private ImageView imgBack;
    private String url = UrlApi.getOrder;
    private RecyclerView recyclerView;
    private ArrayList<Orders> mData = new ArrayList<Orders>();
    private OrderAdapter orderAdapter;
    private LinearLayout thongBao, haveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_cho_xu_ly);

        // functions
        initView();
        setOnClick();
        getOrder();

    }

    private void checkData() {
        if ( mData.size() > 0 ){
            haveData.setVisibility(View.VISIBLE);
            thongBao.setVisibility(View.GONE);
        }else {
            haveData.setVisibility(View.GONE);
            thongBao.setVisibility(View.VISIBLE);
        }
    }

    private void getOrder() {


        // gửi data lên server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        mData.add( new Orders(
                                object.getInt("id"),
                                object.getInt("id_user"),
                                object.getString("name"),
                                object.getString("address"),
                                object.getString("phone"),
                                object.getDouble("total"),
                                object.getInt("status"),
                                object.getString("date")
                        ) );

                        checkData();
                    }

                    orderAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
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
        // gọi hàm setUp
        setUpRecyclerview();

    }

    private void setOnClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        thongBao = (LinearLayout) findViewById(R.id.thongbao_id);
        haveData = (LinearLayout) findViewById(R.id.haveData_id);
    }

    private void setUpRecyclerview(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter( DonHangChoXuLy.this, mData );
        recyclerView.setAdapter(orderAdapter);
    }

}

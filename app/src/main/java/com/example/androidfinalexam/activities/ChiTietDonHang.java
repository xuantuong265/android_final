package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.androidfinalexam.adapters.DetailAdapter;
import com.example.androidfinalexam.models.DetailOrders;
import com.example.androidfinalexam.models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietDonHang extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DetailOrders> arrayList = null;
    private String url = UrlApi.getOrderDetail;
    private DetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        // functions
        initView();
        getData();

    }



    private void getData() {
        // gửi data lên server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                        for ( int i = 0; i < jsonArray.length(); i++ ){
                            JSONObject object = (JSONObject) jsonArray.get(i);

                            int id = object.getInt("id");
                            int id_orders = object.getInt("id_orders");
                            int id_products = object.getInt("id_products");
                            String name_products = object.getString("name_products");
                            double price = object.getDouble("price");
                            int amounts = object.getInt("amounts");

                            arrayList.add( new DetailOrders( 1, 2, 3, "i phỏe", 349384, 3 ) );

                        }

                    Toast.makeText(ChiTietDonHang.this, "DATAA" + arrayList.get(0).getName_products(), Toast.LENGTH_SHORT).show();
                    detailAdapter.notifyDataSetChanged();

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
                Intent intent = getIntent();
                Orders orders = (Orders) intent.getSerializableExtra("data");

                int id_orders = orders.getId();
                parrams.put("id_orders", String.valueOf(id_orders));
                return parrams;
            }
        };
        queue.add(stringRequest);
        // gọi hàm setUp
        setUpRecyclerview();

    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        detailAdapter = new DetailAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(detailAdapter);

    }









    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        arrayList = new ArrayList<DetailOrders>();
           }
}

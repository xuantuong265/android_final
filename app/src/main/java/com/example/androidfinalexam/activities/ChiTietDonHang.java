package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.example.androidfinalexam.adapters.DetailAdapter;
import com.example.androidfinalexam.models.DetailOrders;
import com.example.androidfinalexam.models.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietDonHang extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgBack;
    private TextView txtName, txtPhone, txtAddress, txtTamTinh, txtPhiVanChuyen, txtThanhTien;
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
        setOnClick();
        getInfo();

    }

    private void getInfo() {
        Intent intent = getIntent();
        Orders orders = (Orders) intent.getSerializableExtra("data");
        // set info
        txtName.setText( orders.getName() );
        txtAddress.setText( orders.getAddress() );
        txtPhone.setText( orders.getPhone() );

        // set tiền
        double tamTinh = orders.getTotal();
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        txtTamTinh.setText(decimalFormat.format(tamTinh) + "đ.");

        // phí vận chuyển
        double phiVanChuyen = ( orders.getTotal() * 5 ) / 100;
        txtPhiVanChuyen.setText(decimalFormat.format(phiVanChuyen) + "đ.");

        // thành tiền
        double thanhTien = ( tamTinh + phiVanChuyen );
        txtThanhTien.setText(decimalFormat.format(thanhTien) + "đ.");

    }

    private void setOnClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                        String image = object.getString("image");
                        double price = object.getDouble("price");
                        int amounts = object.getInt("amounts");

                        arrayList.add( new DetailOrders( id, id_orders, id_products, name_products, image, price, amounts ) );
                    }
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
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        txtName = (TextView) findViewById(R.id.name_id);
        txtAddress = (TextView) findViewById(R.id.address_id);
        txtPhone = (TextView) findViewById(R.id.phone_id);
        txtTamTinh = (TextView) findViewById(R.id.tamtinh_id);
        txtPhiVanChuyen = (TextView) findViewById(R.id.phi_van_chuyen_id);
        txtThanhTien = (TextView) findViewById(R.id.thanhtien_id);
    }
}

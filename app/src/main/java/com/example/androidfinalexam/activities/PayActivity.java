package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.androidfinalexam.models.Products;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    private ImageView imgBack;
    private EditText edtName, edtAdress, edtPhone;
    private Button btnAccept, btnCancel;
    private  double thanhTien = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // functions
        initView();
        setOnClick();


    }


    private void setOnClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accept();
            }
        });

    }

    private void Accept() {
        progressDialog.setTitle("Đang xác nhận đơn hàng");
        progressDialog.show();
        // get data from edt
        final String name = edtName.getText().toString().trim();
        final String address = edtAdress.getText().toString().trim();
        final String phone = edtPhone.getText().toString().trim();

        // tính tổng tiền
        double tamTinh = 0;

        for ( int i = 0; i < HomeActivity.cartArrayList.size(); i++ ){
            tamTinh += HomeActivity.cartArrayList.get(i).getPrice();
        }

        //set tiền
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // thành tiền ( có thuế )
        thanhTien = tamTinh + ( tamTinh * 5 ) / 100;

        // gửi data lên server
        String url = UrlApi.insertOrders;
        RequestQueue queue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if ( jsonObject.getBoolean("status") ){
                        JSONObject object = jsonObject.getJSONObject("orders");

                        final int id_orders = object.getInt("id"); // id đơn hàng

                        String urlDetail = UrlApi.insertDetailOrders;

                        // thêm giỏ hàng vào chi tiết hóa đơn
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, urlDetail , new Response.Listener<String>() {
                            @Override
                            public void onResponse(String deatil) {

                                try {
                                    JSONObject deatail = new JSONObject(deatil);

                                    if ( deatail.getBoolean("status") ){
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        HomeActivity.cartArrayList.clear();
                                        startActivity(intent);
                                        Toast.makeText(PayActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(PayActivity.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(PayActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Lỗi cua chi tiet dơn hang " + error, Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                // gửi sản phẩm giỏ hàng lên serve
                                JSONArray jsonArray = new JSONArray();
                                for ( int i = 0; i < HomeActivity.cartArrayList.size(); i++ ){
                                    JSONObject datas = new JSONObject();
                                    try {
                                        datas.put("id_orders", id_orders);
                                        datas.put("id_products", HomeActivity.cartArrayList.get(i).getId());
                                        datas.put("name_products", HomeActivity.cartArrayList.get(i).getNamePro());
                                        datas.put("price", HomeActivity.cartArrayList.get(i).getPrice());
                                        datas.put("amounts", HomeActivity.cartArrayList.get(i).getAmount());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(datas);
                                }

                                // đấy lên
                                Map<String, String> carts = new HashMap<String, String>();
                                carts.put("mang", jsonArray.toString());

                                return carts;
                            }
                        };
                        requestQueue.add(request);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi cua don hang", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                String id_user = HomeActivity.sharedPreferences.getString("id", "0");
                // ngày tháng năm
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = formatter.format(date);
                parrams.put("id_user", String.valueOf(id_user));
                parrams.put("name", name);
                parrams.put("address", address);
                parrams.put("phone", phone);
                parrams.put("total", String.valueOf(thanhTien));
                parrams.put("status", "0");
                parrams.put("date", strDate);
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        edtName = (EditText) findViewById(R.id.name_id);
        edtAdress = (EditText) findViewById(R.id.address_id);
        edtPhone = (EditText) findViewById(R.id.phone_id);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        progressDialog = new ProgressDialog(this);
    }
}

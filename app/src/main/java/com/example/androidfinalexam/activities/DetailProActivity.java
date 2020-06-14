package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.example.androidfinalexam.models.Cart;
import com.example.androidfinalexam.models.HomeFragment;
import com.example.androidfinalexam.models.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DetailProActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgAvt, imgBack;
    private TextView txtNamePro, txtPricePro, txtDescPro, txtXemThem, txtThuGon, txtSoluong;
    private Button btnAddCart;
    private EditText edtAmounts;
    private Products products;
    private AppCompatRatingBar appCompatRatingBar;
    private String url = UrlApi.updateProduct;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);

        // functions
        initView();
        getData();
        setOnClick();
    }

    private void setOnClick() {

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });
        appCompatRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateStar(rating);
            }
        });


    }

    private void updateStar(final float rating ) {

        // gửi data
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for ( int i = 0; i < jsonArray.length(); i++ ){

                        JSONObject object = jsonArray.getJSONObject(i);
                        double star = object.getDouble("star");
                        // lấy id của sản phẩm dc trả về
                        int id_product = object.getInt("id");

                      // mảng products
                        int id = 0;
                        for ( int j = 0; j < HomeFragment.productsArrayList.size(); j++ ){
                            id = HomeFragment.productsArrayList.get(j).getId();

                            if ( id == id_product ){
                                HomeFragment.productsArrayList.get(j).setStar(star);
                            }

                        }

                    }

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

                // gửi id và rating lên serve
                Intent intent = getIntent();
                products = (Products) intent.getSerializableExtra("data");
                int id = products.getId();

                parrams.put("id", String.valueOf(id));
                parrams.put("star", String.valueOf(rating));

                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void addCart() {

        int amount = Integer.parseInt(edtAmounts.getText().toString()); // sản phẩm mua

        // kiểm tra số lượng sp mua có nhiều hơn sản phẩm trong kho
        int soluong = products.getAmounts(); // sản phẩm trong kho
        if ( soluong > amount ){
            // kiểm tra giở hàng đã có sản phẩm hay chưa
            if ( HomeActivity.cartArrayList.size() > 0 ){

                boolean isCheck = false;

                for ( int i = 0; i < HomeActivity.cartArrayList.size(); i++ ){

                    // nếu sản phẩm thêm vào có cùng id với sp trong giỏ hàng thì caapjp nhật lại sl và tổng tiền

                    if ( HomeActivity.cartArrayList.get(i).getId() == products.getId() ){
                        Toast.makeText(this, "vô", Toast.LENGTH_SHORT).show();
                        HomeActivity.cartArrayList.get(i).setAmount(HomeActivity.cartArrayList.get(i).getAmount() + amount);
                        if ( HomeActivity.cartArrayList.get(i).getAmount() > 10 ){
                            HomeActivity.cartArrayList.get(i).setAmount(10);
                        }
                        HomeActivity.cartArrayList.get(i).setPrice( products.getPrice() * HomeActivity.cartArrayList.get(i).getAmount() );
                        isCheck = true;
                    }
                }

                if ( isCheck == false ){
                    int id = products.getId();
                    double price = amount * ( products.getPrice() );
                    String imgPro = products.getImage();
                    String namePro = products.getName();

                    // thêm vào mảng giỏ hàng
                    HomeActivity.cartArrayList.add( new Cart( id, namePro, imgPro, price, amount ));
                }

                String json = gson.toJson(HomeActivity.cartArrayList);
                HomeActivity.editor.putString("CartList", json);
                HomeActivity.editor.apply();

            }else {

                // giỏ hàng chưa có sản phẩm thì thêm sp vào giỏ hàng

                // lấy số lượng sản phẩm trên giỏ hàng
                int id = products.getId();
                double price = amount * ( products.getPrice() );
                String imgPro = products.getImage();
                String namePro = products.getName();

                // thêm vào mảng giỏ hàng
                HomeActivity.cartArrayList.add( new Cart( id, namePro, imgPro, price, amount ));

                String json = gson.toJson(HomeActivity.cartArrayList);
                HomeActivity.editor.putString("CartList", json);
                HomeActivity.editor.apply();

            }
        }else {
            Toast.makeText(this, "Sản phẩm trong kho đã gần hết :v", Toast.LENGTH_SHORT).show();
        }



        // chuyển qua màn hình giỏ hàng và gửi data qua
        if ( amount > 0 ){
            Intent intent = new Intent( getApplicationContext(), CartActivity.class );
            startActivity(intent);
        }else {
            Toast.makeText(this, "Vui lòng chọn số lượng sản phẩm !!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("data");
        txtNamePro.setMaxLines(2);
        txtNamePro.setEllipsize(TextUtils.TruncateAt.END);
        txtNamePro.setText(products.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        txtPricePro.setText("Giá: " + decimalFormat.format(products.getPrice()) + "đ.");
        Picasso.get().load(UrlApi.getImage + products.getImage()).into(imgAvt);
        txtSoluong.setText("Còn lại: " + products.getAmounts() + " sản phẩm.");

        // rating
        float rating = (float) products.getStar();
        appCompatRatingBar.setRating(rating);

        // set data mô tả
        txtDescPro.setMaxLines(5);
        txtDescPro.setText(Html.fromHtml(products.getProducts_desc()));
        txtXemThem.setVisibility(View.VISIBLE);
        txtThuGon.setVisibility(View.GONE);
        txtXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescPro.setMaxLines(100000);
                txtDescPro.setText(Html.fromHtml(products.getProducts_desc()));
                txtXemThem.setVisibility(View.GONE);
                txtThuGon.setVisibility(View.VISIBLE);
            }
        });

        txtThuGon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescPro.setMaxLines(5);
                txtXemThem.setVisibility(View.VISIBLE);
                txtThuGon.setVisibility(View.GONE);
            }
        });

    }

    private void initView() {
        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtNamePro = (TextView) findViewById(R.id.name_products_id);
        txtPricePro = (TextView) findViewById(R.id.price_products_id);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart_id);
        edtAmounts = (EditText) findViewById(R.id.amounts_id);
        txtDescPro = (TextView) findViewById(R.id.deatil_pro_id);
        imgAvt = (ImageView) findViewById(R.id.img_avt_products_id);
        txtXemThem = (TextView) findViewById(R.id.xemThem_id);
        txtThuGon = (TextView) findViewById(R.id.xemIt_id);
        txtSoluong = (TextView) findViewById(R.id.soluong);

        appCompatRatingBar = (AppCompatRatingBar) findViewById(R.id.rating_bar_id);

        HomeActivity.sharedPreferences = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        HomeActivity.editor = HomeActivity.sharedPreferences.edit();

    }
}

package com.example.androidfinalexam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.models.Cart;
import com.example.androidfinalexam.models.DetailOrders;
import com.example.androidfinalexam.models.HomeFragment;
import com.example.androidfinalexam.models.Orders;
import com.example.androidfinalexam.models.Products;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailProActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgAvt, imgBack, imgLove, imgCart;
    private TextView txtNamePro, txtPricePro, txtSoluong, txtComment;
    private Button btnAddCart;
    private EditText edtAmounts;
    private HtmlTextView txtDescPro;
    private Products products;
    private AppCompatRatingBar appCompatRatingBar;
    private String url = UrlApi.updateProduct;
    private String path = UrlApi.getProduct;
    private String themSanPhamDaXem = UrlApi.themSanPhamDaXem;
    private Gson gson = new Gson();
    private int id_products;
    private  String star = "";
    private int id;
    private String countComment;
    private ArrayList<Products> listProLove = new ArrayList<Products>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);

        // functions
        initView();
        getProductLove();
        //getProduct();
        getData();
        countComment();
        setOnClick();
        checkProLove();
        xemSanPham();
    }

    private void checkProLove(){

        // lấy data trong productLove
        HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = HomeActivity.sharedPreferences.getString("ProductLove", null);
        // so sánh sản phẩm hiện tại với mảng sản phẩm yêu thích
        if ( json != null ){

            try {
                JSONArray jsonArray = new JSONArray(json);
                for ( int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    int id_proLove = jsonObject.getInt("id");
                    if ( id == id_proLove ){
                        imgLove.setImageResource(R.drawable.ic_favorite_black_24dp);
                        break;
                    }else {
                        imgLove.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void loveProduct() {

        // lấy thông tin của sản phẩm đó
        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("data");
        // lưu vào mảng
        // kiểm tra xem mản có rỗng hay không
        if ( listProLove.size() > 0 ){

            boolean isCheck = false;

            for ( int i = 0; i < listProLove.size(); i++ ){
                int id = listProLove.get(i).getId();
                if ( id == products.getId() ){
                   listProLove.remove(i);
                  //  HomeActivity.listProLove.set(i, products);
                    isCheck = true;
                    imgLove.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(this, "Bạn đã gỡ yêu thích <3", Toast.LENGTH_SHORT).show();
                }
            }

            if ( isCheck == false ){
                listProLove.add(products);
                imgLove.setImageResource(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(this, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();

            }

            // lưu vào share
            HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
            HomeActivity.editor = HomeActivity.sharedPreferences.edit();
            String json = gson.toJson(listProLove);
            HomeActivity.editor.putString("ProductLove", json);
            HomeActivity.editor.apply();

        }else {

            listProLove.add(products);
            // lưu vào share
            HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
            HomeActivity.editor = HomeActivity.sharedPreferences.edit();
            String json = gson.toJson(listProLove);
            HomeActivity.editor.putString("ProductLove", json);
            HomeActivity.editor.apply();
            imgLove.setImageResource(R.drawable.ic_favorite_black_24dp);
            Toast.makeText(this, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();

        }



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


        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( DetailProActivity.this, CommentActivity.class );
                intent.putExtra("id_products",String.valueOf(id_products));
                intent.putExtra("star", star);
                intent.putExtra("countComment", countComment);
                startActivity(intent);
            }
        });

        imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loveProduct();
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( DetailProActivity.this, CartActivity.class ) );
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( DetailProActivity.this, HomeActivity.class ) );
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
                        if (HomeFragment.productsArrayList.size() > 0){
                            for ( int j = 0; j < HomeFragment.productsArrayList.size(); j++ ){
                                id = HomeFragment.productsArrayList.get(j).getId();

                                if ( id == id_product ){
                                    HomeFragment.productsArrayList.get(j).setStar(star);
                                }

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

       // int amount = Integer.parseInt(edtAmounts.getText().toString()); // sản phẩm mua

        int amount = 1;

        // kiểm tra số lượng sp mua có nhiều hơn sản phẩm trong kho
        int soluong = products.getAmounts(); // sản phẩm trong kho
        if ( soluong > amount ){
            // kiểm tra giở hàng đã có sản phẩm hay chưa
            if ( HomeActivity.cartArrayList.size() > 0 ){

                boolean isCheck = false;

                for ( int i = 0; i < HomeActivity.cartArrayList.size(); i++ ){

                    // nếu sản phẩm thêm vào có cùng id với sp trong giỏ hàng thì caapjp nhật lại sl và tổng tiền

                    if ( HomeActivity.cartArrayList.get(i).getId() == products.getId() ){
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
            // chuyển qua màn hình giỏ hàng và gửi data qua
            if ( amount > 0 ){
                Intent intent = new Intent( getApplicationContext(), CartActivity.class );
                startActivity(intent);
            }else {
                Toast.makeText(this, "Vui lòng chọn số lượng sản phẩm !!!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Sản phẩm trong kho đã gần hết :v", Toast.LENGTH_SHORT).show();
        }





    }

    public void getData() {
        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("data");
        id_products = products.getId();
        id = id_products;
        txtNamePro.setMaxLines(2);
        txtNamePro.setMaxWidth(200);
        txtNamePro.setEllipsize(TextUtils.TruncateAt.END);
        txtNamePro.setText(products.getName());
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        txtPricePro.setText("Giá: " + decimalFormat.format(products.getPrice()) + "đ.");
        Picasso.get().load(UrlApi.getImage + products.getImage()).into(imgAvt);
        txtSoluong.setText("Còn lại: " + products.getAmounts() + " sản phẩm.");



        // set data mô tả

        txtDescPro.setHtml(products.getProducts_desc(),new HtmlHttpImageGetter(txtDescPro, null, true));

    }

    private void initView() {
        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        txtNamePro = (TextView) findViewById(R.id.name_products_id);
        txtPricePro = (TextView) findViewById(R.id.price_products_id);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart_id);
        txtDescPro = (HtmlTextView) findViewById(R.id.deatil_pro_id);
        imgAvt = (ImageView) findViewById(R.id.img_avt_products_id);
        txtSoluong = (TextView) findViewById(R.id.soluong);
        txtComment = (TextView) findViewById(R.id.danhgia_id);
        imgLove = (ImageView) findViewById(R.id.imgLove_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);

        appCompatRatingBar = (AppCompatRatingBar) findViewById(R.id.rating_bar_id);

        HomeActivity.sharedPreferences = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        HomeActivity.editor = HomeActivity.sharedPreferences.edit();

    }

    private void countComment(){

        String path = UrlApi.countComment;

        // gửi data lên server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject object = new JSONObject(response);
                    countComment = object.getString("count");
                    JSONArray jsonArray = object.getJSONArray("star");
                    for ( int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                         star = String.valueOf(jsonObject.getDouble("star"));
                    }

                    // set data textview
                    txtComment.setText("Xem tất cả " + countComment + " nhận xét");
                            // rating
                    appCompatRatingBar.setRating(Float.parseFloat(star));

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
                parrams.put("id_products", String.valueOf(id_products));
                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void getProductLove(){

        HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = HomeActivity.sharedPreferences.getString("ProductLove", null);

        if ( json != null ){
            try {
                JSONArray jsonArray = new JSONArray(json);
                for ( int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    int id = object.getInt("id");
                    int id_brand = object.getInt("id_b");
                    int id_categories =  object.getInt("id_categories");
                    String name = object.getString("name");
                    String image = object.getString("image");
                    int amounts = object.getInt("amounts");
                    double price = object.getDouble("price");
                    String products_desc = object.getString("products_desc");
                    double star =  object.getDouble("star");

                    listProLove.add( new Products( id, id_brand, id_categories, name, image, amounts, price, products_desc, star ) );

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void xemSanPham(){

        // check có đăng nhập hay chưa
        // đăng nhập mới tính xem
        String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
        String id = HomeActivity.sharedLogin.getString("id", "");

        if ( ( taikhoan != "" ) ){

            RequestQueue queue = Volley.newRequestQueue(DetailProActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, themSanPhamDaXem, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetailProActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parrams = new HashMap<>();
                    parrams.put("id_users", id);
                    parrams.put("id_products", String.valueOf(id_products));
                    return parrams;
                }
            };
            queue.add(stringRequest);

        }
    }


}

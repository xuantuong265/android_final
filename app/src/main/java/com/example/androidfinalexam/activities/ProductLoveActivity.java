package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.adapters.MobileProAdapter;
import com.example.androidfinalexam.adapters.ProductAdapter;
import com.example.androidfinalexam.adapters.ProductLoveAdapter;
import com.example.androidfinalexam.models.Products;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductLoveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Products> mData = new ArrayList<Products>();
    private ProductAdapter productLoveAdapter;
    private ImageView imgBack;
    private ImageView imgCart;
    private LinearLayout coSanPham, thongBao;
    private Gson gson = new Gson();
    private ArrayList<Products> listProLove = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_love);

        // functions
        initView();
        getProductLove();
        setUpRecyclerview();
        checkData();
        getData();
        setOnCLick();


    }

    private void setOnCLick() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( ProductLoveActivity.this, HomeActivity.class ) );
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), CartActivity.class ));
            }
        });

    }

    private void initView() {

        //ánh xạ
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);
        coSanPham = (LinearLayout) findViewById(R.id.coSanPham_id);
        thongBao = (LinearLayout) findViewById(R.id.thongbao_id);

    }

    private void getData(){

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

                    mData.add( new Products( id, id_brand, id_categories, name, image, amounts, price, products_desc, star ) );
                }

                productLoveAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            productLoveAdapter.notifyDataSetChanged();

        }else {

        }



    }

    private void setUpRecyclerview(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL) );
        productLoveAdapter = new ProductAdapter(ProductLoveActivity.this, mData);
        recyclerView.setAdapter(productLoveAdapter);
    }

    private void checkData(){

        HomeActivity.sharedPreferences = getSharedPreferences("ProductLoveList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = HomeActivity.sharedPreferences.getString("ProductLove", null);

        if ( listProLove.size() > 0 ){
            thongBao.setVisibility(View.GONE);
            coSanPham.setVisibility(View.VISIBLE);
        }else{
            thongBao.setVisibility(View.VISIBLE);
            coSanPham.setVisibility(View.GONE);
        }

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

}

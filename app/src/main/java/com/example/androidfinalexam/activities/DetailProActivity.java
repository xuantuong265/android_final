package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgAvt, imgBack;
    private TextView txtNamePro, txtPricePro, txtDescPro;
    private Button btnAddCart;
    private EditText edtAmounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pro);

        // functions
        initView();
        getData();

    }

    private void getData() {
        Intent intent = getIntent();
        Products products = (Products) intent.getSerializableExtra("data");
        txtNamePro.setText(products.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        txtPricePro.setText("Giá: " + decimalFormat.format(products.getPrice()) + "đ.");
        txtDescPro.setText(products.getProducts_desc());
        Picasso.get().load(products.getImage()).into(imgAvt);


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

    }
}

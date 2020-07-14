package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.adapters.CartAdapter;
import com.example.androidfinalexam.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private static CartAdapter cartAdapter;
    private static LinearLayout linearLayout;
    private static LinearLayout linearThongBao;
    private Button btnAddCart, btnBack;
    private static TextView txtTamTinh;
    private static TextView txtThanhTien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // functions
        initView();
        setOnClick();
        checkData();
        setMoney();
    }

    public static void setMoney() {
        double tamTinh = 0;
        double thanhTien = 0;
        for ( int i = 0; i < HomeActivity.cartArrayList.size(); i++ ){
            tamTinh += HomeActivity.cartArrayList.get(i).getPrice();
        }

        //set tiền
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        txtTamTinh.setText(decimalFormat.format(tamTinh) + "đ");

        // thành tiền ( có thuế )
        thanhTien = tamTinh + ( tamTinh * 5 ) / 100;
        txtThanhTien.setText(decimalFormat.format(thanhTien) + " đ");

    }

    public static void checkData() {
        if ( HomeActivity.cartArrayList.size() > 0 ){
            cartAdapter.notifyDataSetChanged();
            linearLayout.setVisibility(View.VISIBLE);
            linearThongBao.setVisibility(View.GONE);
        }else {
            cartAdapter.notifyDataSetChanged();
            linearLayout.setVisibility(View.GONE);
            linearThongBao.setVisibility(View.VISIBLE);
        }
    }

    private void setOnClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check info from
                String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
                String date = HomeActivity.sharedLogin.getString("ngaydangky", "");

                if ( ( taikhoan != "" ) && ( date != "" ) ){
                    // nếu có info
                  Intent intent = new Intent( getApplicationContext(), PayActivity.class );
                  startActivity(intent);

                }else {
                   Intent intent = new Intent( getApplicationContext(), AuthActivitis.class );
                   startActivity(intent);
                }

            }
        });

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        linearLayout = (LinearLayout) findViewById(R.id.haveData_id);
        linearThongBao = (LinearLayout) findViewById(R.id.thongbao_id);
        btnAddCart = (Button) findViewById(R.id.btnAddCart);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtTamTinh = (TextView) findViewById(R.id.tamtinh_id);
        txtThanhTien = (TextView) findViewById(R.id.thanhtien_id);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(getApplicationContext(), HomeActivity.cartArrayList);
        recyclerView.setAdapter(cartAdapter);
    }
}

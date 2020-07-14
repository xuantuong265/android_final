package com.example.androidfinalexam.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VietBinhLuanActivity extends AppCompatActivity {

    private ImageView imgBack, imgCart;
    private ImageView imvUpload, imgUpload;
    private AppCompatRatingBar appCompatRatingBar;
    private EditText edtComment;
    private TextView txtStatus;
    private Button btnGui;
    private int star = 0;
    private int REQUEST_CODE_IMAGE = 1;
    private int PICK_IMAGE_CODE = 10;
    private Bitmap bitmap;
    private String id_user, id_products;
    private String url = UrlApi.insertComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_binh_luan);

        //functions
        initView();
        setOnClick();

        // get intent
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        id_products = intent.getStringExtra("id_products");

    }

    private void setOnClick() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( VietBinhLuanActivity.this, CartActivity.class ));
            }
        });

        appCompatRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                switch ((int) rating){
                    case 1:
                        txtStatus.setText("Rất không hài lòng");
                        star = (int) rating;
                        edtComment.setHint("Hãy chia sẽ những điều không tốt về sản phẩm này nhé.");
                        break;
                    case 2:
                        txtStatus.setText("Không hài lòng");
                        star = (int) rating;
                        edtComment.setHint("Hãy chia sẽ vì sao bạn không thích về sản phẩm này.");
                        break;
                    case 3:
                        txtStatus.setText("Bình thường");
                        star = (int) rating;
                        edtComment.setHint("Hãy chia sẽ vì sao bạn chưa thực sự thích về sản phẩm này.");
                        break;
                    case 4:
                        txtStatus.setText("Hài lòng");
                        star = (int) rating;
                        edtComment.setHint("Hãy chia sẽ vì sao bạn thích về sản phẩm này.");
                        break;
                    case 5:
                        txtStatus.setText("Cực kỳ hài lòng");
                        star = (int) rating;
                        edtComment.setHint("Hãy chia sẽ những điều mà bạn thích về sản phẩm này.");
                        break;
                }
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        imvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    private void upload() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){

                        Toast.makeText(VietBinhLuanActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent( VietBinhLuanActivity.this, HomeActivity.class ) );

                    }else {
                        Toast.makeText(VietBinhLuanActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VietBinhLuanActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();

                // get data from edt
                String content = edtComment.getText().toString().trim();
                parrams.put("id_user", id_user);
                parrams.put("id_products", id_products);
                parrams.put("image", imageToString(bitmap));
                parrams.put("content", content);
                parrams.put("star", String.valueOf(star));
                // ngày tháng năm
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = formatter.format(date);
                parrams.put("date", strDate);

                return parrams;
            }
        };
        queue.add(stringRequest);

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null ){

            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private String imageToString(Bitmap bitmap){
       if ( bitmap != null ){
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
           byte [] imgBytes = byteArrayOutputStream.toByteArray();
           return Base64.encodeToString(imgBytes, Base64.DEFAULT);
       }else {
           return "";
       }

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back_id);
        imgCart = (ImageView) findViewById(R.id.imgCart_id);
        imvUpload = (ImageView) findViewById(R.id.imUpload_id);
        btnGui = (Button) findViewById(R.id.btnGui);
        edtComment = (EditText) findViewById(R.id.edt_cmt_id);
        txtStatus = (TextView) findViewById(R.id.trangThai_id);
        appCompatRatingBar = (AppCompatRatingBar) findViewById(R.id.rating_bar_id);
        imgUpload = (ImageView) findViewById(R.id.imgUpload_id);
    }
}

package com.example.androidfinalexam.models;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.activities.AuthActivitis;
import com.example.androidfinalexam.activities.Contacts;
import com.example.androidfinalexam.activities.DonHangChoXuLy;
import com.example.androidfinalexam.activities.DonHangThanhCong;
import com.example.androidfinalexam.activities.HomeActivity;
import com.example.androidfinalexam.activities.ProductLoveActivity;
import com.example.androidfinalexam.activities.SanPhamDaXem;
import com.example.androidfinalexam.activities.YourCommentActivity;

public class PersonFragment extends Fragment {

    private View view;
    private LinearLayout layout_info;
    private ImageView imgAvt, imgBack;
    private TextView txtInfo;
    private LinearLayout txtDonHangChoXuLy, txtDonHangThanhCong, txtComment, txtProductLove, txtContact, txtDaXem;
    private TextView txtDate, txtDangNhap;
    private Button btnLogout;
    private ProgressDialog progressDialog;

    public PersonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.person_layout, container, false);

        initView();
        setOnclick();
        ckeckInfo();

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void ckeckInfo() {

        //check info from
        String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
        String date = HomeActivity.sharedLogin.getString("ngaydangky", "");

        if ( ( taikhoan != "" ) && ( date != "" ) ){
            // nếu có info
            txtInfo.setText( taikhoan.toString() );
            txtDate.setVisibility(View.VISIBLE);
            txtDate.setText( "Thành viên từ ngày: " + date );
            txtDangNhap.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);

        }else {
            txtInfo.setText( "Chào mừng bạn đến với Tiki" );
            txtDate.setVisibility(View.GONE);
            txtDangNhap.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }

    }

    private void setOnclick() {

        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), AuthActivitis.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.editor = HomeActivity.sharedLogin.edit();
                HomeActivity.editor.clear();
                HomeActivity.editor.apply();
                progressDialog.show();

                Intent intent = new Intent( getActivity(), HomeActivity.class );
                startActivity(intent);

            }
        });

        txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Contacts.class));
            }
        });

        txtDonHangChoXuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check info from
                String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
                String date = HomeActivity.sharedLogin.getString("ngaydangky", "");

                if ( ( taikhoan != "" ) && ( date != "" ) ){
                    // nếu có info
                    startActivity( new Intent( getActivity(), DonHangChoXuLy.class) );
                }else {
                    startActivity( new Intent( getActivity(), AuthActivitis.class ) );
                }

            }
        });

        txtProductLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), ProductLoveActivity.class ) );
            }
        });

        txtDonHangThanhCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check info from
                String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
                String date = HomeActivity.sharedLogin.getString("ngaydangky", "");

                if ( ( taikhoan != "" ) && ( date != "" ) ){
                    // nếu có info
                    startActivity( new Intent( getActivity(), DonHangThanhCong.class) );
                }else {
                    startActivity( new Intent( getActivity(), AuthActivitis.class ) );
                }

            }
        });

        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YourCommentActivity.class);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), HomeActivity.class ) );
            }
        });

        txtDaXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = HomeActivity.sharedLogin.getString("id", "");
                if ( ( id != "" ) ){

                    Intent intent = new Intent(getActivity(), SanPhamDaXem.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                }else {
                    startActivity( new Intent( getActivity(), AuthActivitis.class ) );
                }

            }
        });

    }

    private void initView() {
        imgBack = (ImageView) view.findViewById(R.id.img_back_id);
        layout_info = (LinearLayout) view.findViewById(R.id.layout_info);
        txtDate = (TextView) view.findViewById(R.id.date_id);
        txtInfo = (TextView) view.findViewById(R.id.email_id);
        txtDangNhap = (TextView) view.findViewById(R.id.dangnhap_id);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        txtContact = (LinearLayout) view.findViewById(R.id.contact_id);
        txtDonHangChoXuLy = (LinearLayout) view.findViewById(R.id.donhang_choxuly_id);
        txtProductLove = (LinearLayout) view.findViewById(R.id.product_love_id);
        txtDaXem = (LinearLayout) view.findViewById(R.id.san_pham_da_xem_id);
        txtDonHangThanhCong = (LinearLayout) view.findViewById(R.id.orders_success_id);
        txtComment = (LinearLayout) view.findViewById(R.id.comment_id);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang xác thực");
    }
}

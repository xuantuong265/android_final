package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class YourCommentHolder extends RecyclerView.ViewHolder {

    public ImageView imgPro, imgCmt;
    public TextView txtStatus, txtContent, txtDate, txtNameProduct;
    public AppCompatRatingBar appCompatRatingBar;

    public YourCommentHolder(@NonNull View itemView) {
        super(itemView);

        imgCmt = (ImageView) itemView.findViewById(R.id.img_cmt_id);
        imgPro = (ImageView) itemView.findViewById(R.id.img_pro_id);
        txtContent = (TextView) itemView.findViewById(R.id.content_id);
        txtStatus = (TextView) itemView.findViewById(R.id.trangThai_id);
        txtDate = (TextView) itemView.findViewById(R.id.date_id);
        txtNameProduct = (TextView) itemView.findViewById(R.id.name_products_id);
        appCompatRatingBar = (AppCompatRatingBar) itemView.findViewById(R.id.rating_bar_id);
    }
}

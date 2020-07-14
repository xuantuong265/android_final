package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class CommentHolder extends RecyclerView.ViewHolder {

    public TextView txtName, txtDate, txtContent;
    public ImageView imgCommment;
    public AppCompatRatingBar appCompatRatingBar;

    public CommentHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        txtName = (TextView) itemView.findViewById(R.id.name_id);
        txtContent = (TextView) itemView.findViewById(R.id.content_id);
        txtDate = (TextView) itemView.findViewById(R.id.date_id);
        imgCommment = (ImageView) itemView.findViewById(R.id.img_id);
        appCompatRatingBar = (AppCompatRatingBar) itemView.findViewById(R.id.rating_bar_id);

    }
}

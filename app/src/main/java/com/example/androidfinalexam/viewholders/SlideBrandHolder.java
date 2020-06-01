package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SlideBrandHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public CircleImageView circleImageView;
    public TextView txtNameBrand;

    public SlideBrandHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        imageView = (ImageView) itemView.findViewById(R.id.img_band_id);
        txtNameBrand = (TextView) itemView.findViewById(R.id.name_brand_id);

    }
}

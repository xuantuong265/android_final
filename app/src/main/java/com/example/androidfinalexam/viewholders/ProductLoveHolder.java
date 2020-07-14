package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class ProductLoveHolder extends RecyclerView.ViewHolder {

    public ImageView imgAvt;
    public TextView txtNamePro, txtPricePro;

    public ProductLoveHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        imgAvt = (ImageView) itemView.findViewById(R.id.img_avt_products_id);
        txtNamePro = (TextView) itemView.findViewById(R.id.name_products_id);
        txtPricePro = (TextView) itemView.findViewById(R.id.price_products_id);

    }
}

package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class DetailOrderHolder extends RecyclerView.ViewHolder {

    public ImageView imgPro;
    public TextView txtName, txtPrice;

    public DetailOrderHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        imgPro = (ImageView) itemView.findViewById(R.id.img_products_id);
        txtName = (TextView) itemView.findViewById(R.id.name_id);
        txtPrice = (TextView) itemView.findViewById(R.id.price_id);

    }
}

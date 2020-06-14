package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class CartHolder extends RecyclerView.ViewHolder {

    public ImageView imgPro, imgClear;
    public TextView txtNamePro, txtPricePro, txtAmount;
    public ImageButton imbAdd, imbRemove;

    public CartHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        imgPro = (ImageView) itemView.findViewById(R.id.img_products_id);
        imgClear = (ImageView) itemView.findViewById(R.id.img_clear_id);
        txtNamePro = (TextView) itemView.findViewById(R.id.name_products_id);
        txtPricePro = (TextView) itemView.findViewById(R.id.price_products_id);
        txtAmount = (TextView) itemView.findViewById(R.id.amounts_id);
        imbAdd = (ImageButton) itemView.findViewById(R.id.add_id);
        imbRemove = (ImageButton) itemView.findViewById(R.id.remove_id);

    }
}

package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.adapters.OrderAdapter;

public class OrderHolder extends RecyclerView.ViewHolder {

    public TextView txtName, txtTotal, txtDate;

    public OrderHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        txtName = (TextView) itemView.findViewById(R.id.name_id);
        txtTotal = (TextView) itemView.findViewById(R.id.total_id);
        txtDate = (TextView) itemView.findViewById(R.id.date_id);



    }
}

package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.ClickItemSearch;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.activities.ChiTietDonHang;
import com.example.androidfinalexam.models.Orders;
import com.example.androidfinalexam.viewholders.OrderHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    private Context context;
    private ArrayList<Orders> mData;
    public static ClickItemSearch clickItemSearch;

    public OrderAdapter(Context context, ArrayList<Orders> mData, ClickItemSearch clickItemSearch) {
        this.context = context;
        this.mData = mData;
        this.clickItemSearch = clickItemSearch;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_donhang_choxuly_layout, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, final int position) {
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtTotal.setText(decimalFormat.format(mData.get(position).getTotal()) + "đ");
        holder.txtDate.setText((CharSequence) mData.get(position).getDate());

        // set OnClick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ChiTietDonHang.class);
                intent.putExtra("data", mData.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickItemSearch.onLongClickItem(mData.get(position).getId());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

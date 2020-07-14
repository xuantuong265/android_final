package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.models.DetailOrders;
import com.example.androidfinalexam.viewholders.DetailOrderHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailOrderHolder> {

    private Context context;
    private ArrayList<DetailOrders> mData;

    public DetailAdapter(Context context, ArrayList<DetailOrders> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public DetailOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chitiet_donhang_layout, parent, false);
        return new DetailOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderHolder holder, int position) {
        holder.txtName.setText(mData.get(position).getName_products());
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImage()).into(holder.imgPro);
        Log.d("haha", UrlApi.getImage + mData.get(position).getImage());
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtPrice.setText(decimalFormat.format(mData.get(position).getPrice()) + " đ" + " X " + mData.get(position).getAmounts());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

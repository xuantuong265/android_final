package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.DetailProActivity;
import com.example.androidfinalexam.models.Products;
import com.example.androidfinalexam.viewholders.ProductBrandHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ComputerAdapter extends RecyclerView.Adapter<ProductBrandHolder> {

    private Context context;
    private ArrayList<Products> mData;

    public ComputerAdapter(Context context, ArrayList<Products> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProductBrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout_products_brand, parent, false);
        return new ProductBrandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBrandHolder holder, final int position) {
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImage()).into(holder.imgAvt);
        holder.txtNamePro.setMaxLines(2); // set hiển thị chỉ 2 dòng
        holder.txtNamePro.setEllipsize(TextUtils.TruncateAt.END); // ... ở cuối dòng
        holder.txtNamePro.setText(mData.get(position).getName());
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtPricePro.setText(decimalFormat.format(mData.get(position).getPrice()) + "đ");

        // setOnclick
        holder.imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, DetailProActivity.class);
                intent.putExtra("data", mData.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

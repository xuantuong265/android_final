package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.DetailProActivity;
import com.example.androidfinalexam.models.Products;
import com.example.androidfinalexam.viewholders.ProductBrandHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context context;
    private ArrayList<Products> mData;

    public ProductAdapter(Context context, ArrayList<Products> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_products_layout, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImage()).into(holder.imgAvt);
//        holder.txtNamePro.setMaxLines(2); // set hiển thị chỉ 2 dòng
//        holder.txtNamePro.setEllipsize(TextUtils.TruncateAt.END); // ... ở cuối dòng
        holder.txtNamePro.setText(mData.get(position).getName());
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtPricePro.setText(decimalFormat.format(mData.get(position).getPrice()) + "đ");

        // setOnclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public class ProductHolder extends RecyclerView.ViewHolder {

        public ImageView imgAvt;
        public TextView txtNamePro, txtPricePro;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            // ánh xạ
            imgAvt = (ImageView) itemView.findViewById(R.id.img_avt_products_id);
            txtNamePro = (TextView) itemView.findViewById(R.id.name_products_id);
            txtPricePro = (TextView) itemView.findViewById(R.id.price_products_id);
        }
    }
}

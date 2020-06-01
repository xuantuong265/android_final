package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.activities.ListProductsBrand;
import com.example.androidfinalexam.models.ItemBrand;
import com.example.androidfinalexam.viewholders.SlideBrandHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlideBrandAdapter extends RecyclerView.Adapter<SlideBrandHolder> {

    private Context context;
    private ArrayList<ItemBrand> mData;

    public SlideBrandAdapter(Context context, ArrayList<ItemBrand> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SlideBrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout_brand, parent, false);
        return new SlideBrandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideBrandHolder holder, final int position) {
        Picasso.get().load(mData.get(position).getImage()).into(holder.imageView);
        holder.txtNameBrand.setText(mData.get(position).getName());

        // set onclick
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ListProductsBrand.class);
                intent.putExtra("id", mData.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

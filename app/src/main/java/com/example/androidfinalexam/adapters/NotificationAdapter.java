package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.models.Notification;
import com.example.androidfinalexam.viewholders.NotificationHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private Context context;
    private ArrayList<Notification> mData;

    public NotificationAdapter(Context context, ArrayList<Notification> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notification_layout, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.txtTitle.setText(mData.get(position).getTitle());
        holder.txtContent.setText(mData.get(position).getContent());
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImg()).into(holder.imgAvt);
        holder.txtDate.setText(mData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

package com.example.androidfinalexam.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;

public class NotificationHolder extends RecyclerView.ViewHolder {

    public ImageView imgAvt;
    public TextView txtTitle, txtContent, txtDate;

    public NotificationHolder(@NonNull View itemView) {
        super(itemView);

        // ánh xạ
        imgAvt = (ImageView) itemView.findViewById(R.id.img_notification);
        txtContent = (TextView) itemView.findViewById(R.id.content);
        txtDate = (TextView) itemView.findViewById(R.id.date);
        txtTitle = (TextView) itemView.findViewById(R.id.title);

    }
}

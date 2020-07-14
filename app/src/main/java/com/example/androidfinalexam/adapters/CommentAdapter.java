package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.models.Comment;
import com.example.androidfinalexam.viewholders.CommentHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    private Context context;
    private ArrayList<Comment> mData;

    public CommentAdapter(Context context, ArrayList<Comment> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment_layout, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.txtName.setText(mData.get(position).getEmail());
        holder.txtDate.setText(mData.get(position).getDate());
        holder.txtContent.setText(mData.get(position).getContent());

        String image = mData.get(position).getImage();
        if ( image.length() > 0 ){
            holder.imgCommment.setVisibility(View.VISIBLE);
            Picasso.get().load(UrlApi.getImage + image).into(holder.imgCommment);
        }else {
            holder.imgCommment.setVisibility(View.GONE);
        }

        holder.appCompatRatingBar.setRating(mData.get(position).getStar());
        holder.appCompatRatingBar.setOnClickListener(null);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

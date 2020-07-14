package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.models.Comment;
import com.example.androidfinalexam.viewholders.YourCommentHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YourCommentAdapter extends RecyclerView.Adapter<YourCommentHolder> {

    private Context context;
    private ArrayList<Comment> mData;

    public YourCommentAdapter(Context context, ArrayList<Comment> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public YourCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_your_comment_layout, parent, false);
        return new YourCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourCommentHolder holder, int position) {
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImage_products()).into(holder.imgPro);
        holder.appCompatRatingBar.setRating(Float.parseFloat(mData.get(position).getStar() + ""));
        holder.txtContent.setMaxLines(4); // set hiển thị chỉ 2 dòng
        holder.txtContent.setEllipsize(TextUtils.TruncateAt.END); // ... ở cuối dòng
        holder.txtContent.setText(mData.get(position).getContent());
        holder.txtNameProduct.setMaxLines(2); // set hiển thị chỉ 2 dòng
        holder.txtNameProduct.setEllipsize(TextUtils.TruncateAt.END); // ... ở cuối dòng
        holder.txtNameProduct.setText(mData.get(position).getName_products());

        switch ( mData.get(position).getStar() ){

            case 1:
                holder.txtStatus.setText( "Rất không hài lòng" );
                break;
            case 2:
                holder.txtStatus.setText("Không hài lòng");
                break;
            case 3:
                holder.txtStatus.setText("Bình thường");
                break;
            case 4:
                holder.txtStatus.setText("Hài lòng");
                break;
            case 5:
                holder.txtStatus.setText("Cực kỳ hài lòng");
                break;

        }

        holder.txtDate.setText("Ngày " + mData.get(position).getDate());

        // kiểm tra ảnh bình luận
        String image_comment = mData.get(position).getImage();
        if (image_comment.length() > 0){
            holder.imgCmt.setVisibility(View.VISIBLE);
            Picasso.get().load(UrlApi.getImage + mData.get(position).getImage()).into(holder.imgCmt);
        }else {
            holder.imgCmt.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

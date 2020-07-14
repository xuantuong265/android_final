package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.ClickItemSearch;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.models.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context context;
    private ArrayList<History> mData;
    private ClickItemSearch clickItemSearch;

    public HistoryAdapter(Context context, ArrayList<History> mData, ClickItemSearch clickItemSearch) {
        this.context = (Context) context;
        this.mData = mData;
        this.clickItemSearch = clickItemSearch;
    }


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history_layout, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder,  int position) {
        holder.txtNameSearch.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class HistoryHolder extends RecyclerView.ViewHolder{

        public TextView txtNameSearch;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSearch = (TextView) itemView.findViewById(R.id.name_search_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemSearch.onClickItem(getAdapterPosition());
                }
            });
        }
    }
}

package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalexam.viewholders.PersonHolder;

public class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

    private Context context;
    private PersonAdapter personAdapter;

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

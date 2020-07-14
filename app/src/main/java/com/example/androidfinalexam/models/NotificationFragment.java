package com.example.androidfinalexam.models;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.CartActivity;
import com.example.androidfinalexam.activities.HomeActivity;
import com.example.androidfinalexam.adapters.NotificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private ArrayList<Notification> mData = new ArrayList<>();
    private ImageView imgBack, imgCart;
    private View view;
    private String url = UrlApi.getNotification;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notifications_layout, container, false);

        // functions
        initView();
        setOnClick();
        getNotification();

        return  view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        imgBack = (ImageView) view.findViewById(R.id.img_back_id);
        imgCart = (ImageView) view.findViewById(R.id.imgCart_id);
    }

    private void setOnClick() {
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), CartActivity.class ));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), HomeActivity.class) );
            }
        });
    }

    private void setUp(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        notificationAdapter = new NotificationAdapter(getActivity(), mData);
        recyclerView.setAdapter(notificationAdapter);

    }

    private void getNotification(){

        // get data api
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                       for ( int i = 0; i < response.length(); i++ ){

                           try {
                               JSONObject jsonObject = response.getJSONObject(i);
                               mData.add( new Notification(
                                       jsonObject.getInt("id"),
                                       jsonObject.getString("title"),
                                       jsonObject.getString("content"),
                                       jsonObject.getString("img"),
                                       jsonObject.getString("date")
                               ) );
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }
                       notificationAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());

            }
        });
        queue.add(jsonArrayRequest);
        setUp();

    }

}

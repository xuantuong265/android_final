package com.example.androidfinalexam.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.ClickItemSearch;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.HomeActivity;
import com.example.androidfinalexam.activities.ListProductsBrand;
import com.example.androidfinalexam.adapters.HistoryAdapter;
import com.example.androidfinalexam.adapters.MobileProAdapter;
import com.example.androidfinalexam.adapters.ProductAdapter;
import com.example.androidfinalexam.adapters.SlideBrandAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment implements ClickItemSearch {

    private static final int CODE = 1000;
    private View view;
    private ImageView imgSearch, imgBack, mic_search;
    private EditText edtSearch;
    private String url = UrlApi.search;
    private RecyclerView recyclerView, historyRecy;
    private ProductAdapter productAdapter;
    private ArrayList<Products> productsArrayList = new ArrayList<>();
    private ArrayList<Products> productList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Boolean isCheck = false;
    private TextView txtNoPro, txtXoa;
    private LinearLayout linearLayout, layout_history;
    private ArrayList<History> listHistory = new ArrayList<>();
    private SharedPreferences sharedHistory;
    private SharedPreferences.Editor edt;
    private HistoryAdapter historyAdapter;
    private boolean isFlag = false;
    private String name_products;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.search_layout, container, false);

        initView();
        CheckData();
        setOnClick();

        return view;

    }

    private void setOnClick() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đang tìm kiếm");
                progressDialog.show();
                productsArrayList.clear();
                name_products = edtSearch.getText().toString().toLowerCase();
                search(name_products);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), HomeActivity.class) );
            }
        });

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xóa hết lịch sử trong tìm kiếm
                sharedHistory = getActivity().getSharedPreferences("sharedHistory", Context.MODE_PRIVATE);
                edt = sharedHistory.edit();
                edt.clear();
                edt.apply();
                getSearch();
            }
        });

        mic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsArrayList.clear();
                micSearch();
            }
        });

    }

    private void search(String name_products) {


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")){
                        JSONArray mang = jsonObject.getJSONArray("data");

                        if ( mang.length() > 0 ){
                            for ( int i = 0; i < mang.length(); i++ ){
                                JSONObject object = (JSONObject) mang.get(i);
                                productsArrayList.add( new Products(
                                        object.getInt("id"),
                                        object.getInt("id_brand"),
                                        object.getInt("id_categories"),
                                        object.getString("name"),
                                        object.getString("image"),
                                        object.getInt("amounts"),
                                        object.getDouble("price"),
                                        object.getString("products_desc"),
                                        object.getDouble("star")
                                ) );
                            }
                            productAdapter.notifyDataSetChanged();
                            setUpRecyclerview();
                            CheckData(); // kiểm tra mảng
                            String name_products = edtSearch.getText().toString().toLowerCase();
                            checkSeach(name_products); // thêm vào lịch sử
                            progressDialog.dismiss();

                        }else {
                            Toast.makeText(getActivity(), "Không có gì cả", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi nè " + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                // get data from editext
                parrams.put("name_products", name_products);
                return parrams;

            }
        };
        queue.add(stringRequest);
        setUpRecyclerview();


    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        imgBack = (ImageView) view.findViewById(R.id.img_back_id);
        mic_search = (ImageView) view.findViewById(R.id.mic_search);
        progressDialog = new ProgressDialog(getActivity());
        txtNoPro = (TextView) view.findViewById(R.id.noPro);
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        layout_history = (LinearLayout) view.findViewById(R.id.layout_history);
        txtXoa = (TextView) view.findViewById(R.id.xoa_id);

        // lịch sử tìm kiếm
        historyRecy = (RecyclerView) view.findViewById(R.id.history_id);
        historyRecy.setHasFixedSize(true);
        historyRecy.setLayoutManager( new LinearLayoutManager(getActivity()) );
        historyAdapter = new HistoryAdapter(getActivity(), listHistory, this);
        historyRecy.setAdapter(historyAdapter);

    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productAdapter = new ProductAdapter(getActivity(), productsArrayList);
        recyclerView.setAdapter(productAdapter);
    }

    private void getSearch(){

        // dọn sạch mảng
        listHistory.clear();

        // lấy name search
        sharedHistory  = getActivity().getSharedPreferences("sharedHistory", Context.MODE_PRIVATE);
        String json = sharedHistory.getString("ListSearch", "");

        if ( json.length() > 0 ){

            try {
                JSONArray jsonArray = new JSONArray(json);
                for ( int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    listHistory.add( new History( object.getString("name") ) );
                }
                historyAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
           layout_history.setVisibility(View.GONE);
        }

    }

    private void CheckData(){
        if ( productsArrayList.size() > 0 ){
            txtNoPro.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            layout_history.setVisibility(View.GONE);
        }else{
            //txtNoPro.setVisibility(View.VISIBLE);
            layout_history.setVisibility(View.VISIBLE);
            getSearch();
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void checkSeach(String name_products){
        if ( listHistory.size() > 0 ) {
            // kiểm tra xem từ khóa đã có trong lịch sử chưa
            sharedHistory = getActivity().getSharedPreferences("sharedHistory", Context.MODE_PRIVATE);
            edt = sharedHistory.edit();
            for (int i = 0; i < listHistory.size(); i++) {
                if (listHistory.get(i).getName().equalsIgnoreCase(name_products)) {
                    listHistory.get(i).setName(name_products);
                    Gson gson = new Gson();
                    String json = gson.toJson(listHistory);
                    edt.putString("ListSearch", json);
                    edt.apply();
                    isFlag = true;
                }
            }

            if (isFlag == false) {
                // thêm vào lịch sử
                // thêm vào mảng listHistory
                listHistory.add(new History(name_products));

                sharedHistory = getActivity().getSharedPreferences("sharedHistory", Context.MODE_PRIVATE);
                edt = sharedHistory.edit();
                Gson gson = new Gson();
                String json = gson.toJson(listHistory);
                edt.putString("ListSearch", json);
                edt.apply();

            }

        }else {
            listHistory.add( new History(name_products) );

            sharedHistory  = getActivity().getSharedPreferences("sharedHistory", Context.MODE_PRIVATE);
            edt = sharedHistory.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listHistory);
            edt.putString("ListSearch", json);
            edt.apply();

        }
    }

    private void micSearch(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe...");

        try {
            startActivityForResult(intent, CODE);
        }catch (Exception e){
            Toast.makeText(getActivity(), "Lỗi nè " + e, Toast.LENGTH_SHORT).show();
        }

    }

    // nhận dữ liệu


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case CODE:{
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    name_products = list.get(0).toLowerCase().toString();
                    edtSearch.setText(name_products);
                    search(name_products);
                }
                break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onClickItem(int position) {
        String name = listHistory.get(position).getName();
        edtSearch.setText(name);
        search(name);
    }

    @Override
    public void onLongClickItem(int positon) {

    }
}

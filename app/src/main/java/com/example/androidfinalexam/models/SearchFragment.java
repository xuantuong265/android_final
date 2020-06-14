package com.example.androidfinalexam.models;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.ListProductsBrand;
import com.example.androidfinalexam.adapters.MobileProAdapter;
import com.example.androidfinalexam.adapters.SlideBrandAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private View view;
    private ImageView imgSearch;
    private EditText edtSearch;
    private String url = UrlApi.getProduct;
    private RecyclerView recyclerView;
    private MobileProAdapter mobileProAdapter;
    private ArrayList<Products> productsArrayList = new ArrayList<>();
    private ArrayList<Products> productList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Boolean isCheck = false;
    private TextView txtNoPro;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.search_layout, container, false);
        
        initView();
        setOnClick();

        return view;

    }

    private void setOnClick() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đang tìm kiếm");
                progressDialog.show();
                getProduct();
                setUpRecyclerview();
                mobileProAdapter.notifyDataSetChanged();
            }
        });
    }


    private void filtered() {

        // get data from edt
        String name_pro = edtSearch.getText().toString().toLowerCase();
        String namePro = "";

        if ( name_pro.equals("") ){
            productList.clear();
            txtNoPro.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "ko có datas" + productList, Toast.LENGTH_SHORT).show();
        }else {
            //productList.clear();
            txtNoPro.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if ( productList == null ){
                Toast.makeText(getActivity(), "null nè", Toast.LENGTH_SHORT).show();
            }
                for ( int i = 0; i < productsArrayList.size(); i++ ){
                    namePro = productsArrayList.get(i).getName().toLowerCase();

                    if ( namePro.contains(name_pro) ){
                        Toast.makeText(getActivity(), "vô đây ròi", Toast.LENGTH_SHORT).show();
                        int id = productsArrayList.get(i).getId();
                        int id_brand = productsArrayList.get(i).getId_b();
                        int id_categories = productsArrayList.get(i).getId_categories();
                        String name = productsArrayList.get(i).getName();
                        String image = productsArrayList.get(i).getImage();
                        int amounts = productsArrayList.get(i).getAmounts();
                        double price = productsArrayList.get(i).getPrice();
                        String products_desc = productsArrayList.get(i).getProducts_desc();
                        double star = productsArrayList.get(i).getStar();

                        productList.add( new Products( id, id_brand, id_categories, name, image, amounts, price, products_desc, star ) );
                    }

                }
            mobileProAdapter.notifyDataSetChanged();
            }


        mobileProAdapter.notifyDataSetChanged();
        progressDialog.dismiss();


    }

    private void getProduct() {

        // get data api
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for ( int i = 0; i < response.length(); i++ ){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                                int id = jsonObject.getInt("id");
                                int id_brand = jsonObject.getInt("id_brand");
                                int id_categories = jsonObject.getInt("id_categories");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                int amounts = jsonObject.getInt("amounts");
                                double price = jsonObject.getDouble("price");
                                String products_desc = jsonObject.getString("products_desc");
                                double star = jsonObject.getDouble("star");

                                productsArrayList.add( new Products(id, id_brand, id_categories, name, image, amounts, price, products_desc, star) );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        filtered(); // tìm kiếm sản phẩm

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());

            }
        });
        queue.add(jsonArrayRequest);

    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        progressDialog = new ProgressDialog(getActivity());
        txtNoPro = (TextView) view.findViewById(R.id.noPro);
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mobileProAdapter = new MobileProAdapter(getActivity(), productList);
        recyclerView.setAdapter(mobileProAdapter);
        mobileProAdapter.notifyDataSetChanged();
    }


}

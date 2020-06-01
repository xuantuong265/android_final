package com.example.androidfinalexam.models;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.example.androidfinalexam.adapters.ProductBrandAdapter;
import com.example.androidfinalexam.adapters.SlideBrandAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;
    private EditText edtSearch;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView, recyFeaturedProducts;
    private SlideBrandAdapter slideBrandAdapter;
    private ProductBrandAdapter productBrandAdapter;
    private ArrayList<Products> productsArrayList;
    private ArrayList<ItemBrand> mData;



    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.home_layout_fragments, container, false);
        initView();
        //searchChange();
        slide();
        setUpRecyclerviewSlideBrand();
        featuredProduct();
        return view;
    }


    private void featuredProduct() {
        String url = "http://192.168.0.27/Laravel/clonetiki/api/featuredProduct";
        recyFeaturedProducts.setHasFixedSize(true);
        recyFeaturedProducts.setLayoutManager( new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false) );
        productsArrayList = new ArrayList<Products>();

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
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                int amounts = jsonObject.getInt("amounts");
                                double price = jsonObject.getDouble("price");
                                String products_desc = jsonObject.getString("products_desc");
                                double star = jsonObject.getDouble("star");

                                productsArrayList.add( new Products(id, id_brand, name, image, amounts, price, products_desc, star) );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        productBrandAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());

            }
        });
        queue.add(jsonArrayRequest);

        productBrandAdapter = new ProductBrandAdapter(getActivity(), productsArrayList);
        recyFeaturedProducts.setAdapter(productBrandAdapter);

    }


    private void setUpRecyclerviewSlideBrand() {
        String url = "http://192.168.0.27/Laravel/clonetiki/api/get-data-brand";

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager
                ( getContext(), RecyclerView.HORIZONTAL, false );
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager( new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false) );
        mData = new ArrayList<ItemBrand>();

        // get data from api
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for ( int i = 0; i < response.length(); i++ ){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mData.add( new ItemBrand(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("image")

                        ) );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                slideBrandAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "fail" + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);


        slideBrandAdapter = new SlideBrandAdapter(  getContext(), mData );
        recyclerView.setAdapter(slideBrandAdapter);
    }




    private void initView() {
        edtSearch = (EditText) view.findViewById(R.id.search_id);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.slide_id);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_slide_brand_id);
        recyFeaturedProducts = (RecyclerView) view.findViewById(R.id.recyclerview_slide_featured_products);
    }

    private void slide() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.anh1);
        arrayList.add(R.drawable.anh2);
        arrayList.add(R.drawable.anh3);

        for ( int i = 0; i < arrayList.size(); i++ ){
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(arrayList.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }




}

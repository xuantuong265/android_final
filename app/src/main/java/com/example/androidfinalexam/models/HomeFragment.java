package com.example.androidfinalexam.models;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.CartActivity;
import com.example.androidfinalexam.activities.DetailProActivity;
import com.example.androidfinalexam.activities.HomeActivity;
import com.example.androidfinalexam.activities.SanPhamDaXem;
import com.example.androidfinalexam.activities.XemThemDienThoai;
import com.example.androidfinalexam.adapters.ComputerAdapter;
import com.example.androidfinalexam.adapters.DaXemAdapter;
import com.example.androidfinalexam.adapters.MobileProAdapter;

import com.example.androidfinalexam.adapters.SlideBrandAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private View view;
    private EditText edtSearch;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView, recyMobilePro, recynewProducts, goi_y_hom_nay_id, sanPhamDaXem;
    private SlideBrandAdapter slideBrandAdapter;
    private MobileProAdapter mobileProAdapter;
    private DaXemAdapter daXemAdapter;
    public static ArrayList<Products> productsArrayList;
    private ArrayList<Products> listSanPhamDaXem = new ArrayList<>();
    private ArrayList<ItemBrand> mData;
    private ArrayList<Products> computerList;
    private ComputerAdapter computerAdapter;
    private ImageView imgCart;
    private TextView txtDienThoai, txtMayTinh, txtDaxem;


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
        mobileProduct();
        computerProduct();
        setOnClick();
        xemSanPham();
        return view;
    }

    private void computerProduct() {

        // url
        String urlNewPro = UrlApi.computerProduct;

        recynewProducts.setHasFixedSize(true);
        recynewProducts.setLayoutManager( new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false) );
        computerList = new ArrayList<Products>();

        // get data api
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlNewPro, null,
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

                                computerList.add( new Products(id, id_brand, id_categories, name, image, amounts, price, products_desc, star) );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mobileProAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());

            }
        });
        queue.add(jsonArrayRequest);

        computerAdapter = new ComputerAdapter(getActivity(), computerList);
        recynewProducts.setAdapter(computerAdapter);

    }

    private void setOnClick() {

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framContainer, new SearchFragment()).commit();
            }
        });

        txtDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get id_cate
                int id_cate = productsArrayList.get(0).getId_categories();

                Intent intent = new Intent( getActivity(), XemThemDienThoai.class );
                intent.putExtra("id_categories", id_cate);
                startActivity(intent);
            }
        });

        txtMayTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get id_cate
                int id_cate = computerList.get(0).getId_categories();

                Intent intent = new Intent( getActivity(), XemThemDienThoai.class );
                intent.putExtra("id_categories", id_cate);
                startActivity(intent);
            }
        });

        txtDaxem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = HomeActivity.sharedLogin.getString("id", "");
                Intent intent = new Intent(getActivity(), SanPhamDaXem.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }

    private void mobileProduct() {
        //String url = "http://192.168.0.23/Laravel/clonetiki/api/featuredProduct";
        String url = UrlApi.mobileProduct;
        recyMobilePro.setHasFixedSize(true);
        recyMobilePro.setLayoutManager( new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false) );
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
                        mobileProAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi", error.toString());

            }
        });
        queue.add(jsonArrayRequest);

        mobileProAdapter = new MobileProAdapter(getActivity(), productsArrayList);
        recyMobilePro.setAdapter(mobileProAdapter);

    }

    private void setUpRecyclerviewSlideBrand() {
        String url = UrlApi.slideBrand;

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
        imgCart = (ImageView) view.findViewById(R.id.imgCart_id);
        edtSearch = (EditText) view.findViewById(R.id.search_id);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.slide_id);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_slide_brand_id);
        recyMobilePro = (RecyclerView) view.findViewById(R.id.recyclerview_slide_featured_products);
        recynewProducts = (RecyclerView) view.findViewById(R.id.recyclerview_newPro);
        txtDienThoai = (TextView) view.findViewById(R.id.dienThoai_id);
        txtMayTinh = (TextView) view.findViewById(R.id.matTinh_id);
        goi_y_hom_nay_id = (RecyclerView) view.findViewById(R.id.goi_y_hom_nay_id);
        sanPhamDaXem = (RecyclerView) view.findViewById(R.id.san_pham_da_xem_id);
        txtDaxem = (TextView) view.findViewById(R.id.xem_them_sp);
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

    private void xemSanPham(){


        // check có đăng nhập hay chưa
        // đăng nhập mới tính xem
        String taikhoan = HomeActivity.sharedLogin.getString("taikhoan", "");
        String id = HomeActivity.sharedLogin.getString("id", "");

        if ( ( taikhoan != "" ) ){

            // set up recyclerview
            sanPhamDaXem.setHasFixedSize(true);
            sanPhamDaXem.setLayoutManager( new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false) );
            daXemAdapter = new DaXemAdapter(getActivity(), listSanPhamDaXem);
            sanPhamDaXem.setAdapter(daXemAdapter);

            String xemSanPhamDaXem = UrlApi.xemSanPhamDaXem; // url


            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, xemSanPhamDaXem, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for ( int i = 0; i < jsonArray.length(); i++ ){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            listSanPhamDaXem.add( new Products(
                                    jsonObject.getInt("id"),
                                    jsonObject.getInt("id_brand"),
                                    jsonObject.getInt("id_categories"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("image"),
                                    jsonObject.getInt("amounts"),
                                    jsonObject.getDouble("price"),
                                    jsonObject.getString("products_desc"),
                                    jsonObject.getDouble("star")
                            ) );
                        }

                        daXemAdapter    .notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parrams = new HashMap<>();
                    parrams.put("id_users", id);
                    return parrams;
                }
            };
            queue.add(stringRequest);

        }
    }


}

package com.example.androidfinalexam.adapters;

import android.app.AlertDialog;
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
import com.example.androidfinalexam.activities.CartActivity;
import com.example.androidfinalexam.activities.HomeActivity;
import com.example.androidfinalexam.models.Cart;
import com.example.androidfinalexam.viewholders.CartHolder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private Context context;
    private ArrayList<Cart> mData;
    private Gson gson = new Gson();

    public CartAdapter(Context context, ArrayList<Cart> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart_layout, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, final int position) {
        Picasso.get().load(UrlApi.getImage + mData.get(position).getImgPro()).into(holder.imgPro);
        holder.txtNamePro.setMaxLines(2);
        holder.txtNamePro.setEllipsize(TextUtils.TruncateAt.END); // ... ở cuối dòng
        holder.txtNamePro.setText(mData.get(position).getNamePro());

        String pattern = "###,###.###";
        final DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtPricePro.setText("Giá: " + decimalFormat.format(mData.get(position).getPrice()) + "đ");
        holder.txtAmount.setText(String.valueOf(mData.get(position).getAmount()));

        // set onclick
        holder.imbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongHienTai = HomeActivity.cartArrayList.get(position).getAmount();
                double giaHienTai = HomeActivity.cartArrayList.get(position).getPrice();

                if ( soLuongHienTai < 10 ){
                    int soLuongMoi = Integer.parseInt(holder.txtAmount.getText().toString()) + 1;
                    // set số lượng lên tv và cập nhật lại số lượng vào mảng
                    holder.txtAmount.setText(String.valueOf(soLuongMoi));
                    HomeActivity.cartArrayList.get(position).setAmount(soLuongMoi);

                    // tính giá tiền sau khi thêm
                    double giaMoi = ( giaHienTai * soLuongMoi ) / soLuongHienTai;
                    // gán lên tv và cập nhật lại mảng
                    holder.txtPricePro.setText(String.valueOf("Giá: " + decimalFormat.format(giaMoi) + "đ"));
                    HomeActivity.cartArrayList.get(position).setPrice(giaMoi);

                    // cập nhật lại tổng tiền
                    CartActivity.setMoney();

                } else {
                    holder.txtAmount.setText(String.valueOf(soLuongHienTai));
                }

                // lưu vào
                Gson gson = new Gson();
                String json = gson.toJson(HomeActivity.cartArrayList);
                HomeActivity.editor.putString("CartList", json);
                HomeActivity.editor.apply();
            }
        });
        holder.imbRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongHienTai = HomeActivity.cartArrayList.get(position).getAmount();
                double giaHienTai = HomeActivity.cartArrayList.get(position).getPrice();

                if ( soLuongHienTai > 1 ){
                    int soLuongMoi = Integer.parseInt(holder.txtAmount.getText().toString()) - 1;
                    // set số lượng lên tv và cập nhật lại số lượng vào mảng
                    holder.txtAmount.setText(String.valueOf(soLuongMoi));
                    HomeActivity.cartArrayList.get(position).setAmount(soLuongMoi);

                    // tính giá tiền sau khi thêm
                    double giaMoi = ( giaHienTai * soLuongMoi ) / soLuongHienTai;
                    // gán lên tv và cập nhật lại mảng
                    holder.txtPricePro.setText(String.valueOf("Giá: " + decimalFormat.format(giaMoi) + "đ"));
                    HomeActivity.cartArrayList.get(position).setPrice(giaMoi);

                    // cập nhật lại tổng tiền
                    CartActivity.setMoney();

                    String json = gson.toJson(HomeActivity.cartArrayList);
                    HomeActivity.editor.putString("CartList", json);
                    HomeActivity.editor.apply();


                } else {
                    holder.txtAmount.setText(String.valueOf(soLuongHienTai));
                }

                // lưu vào
                Gson gson = new Gson();
                String json = gson.toJson(HomeActivity.cartArrayList);
                HomeActivity.editor.putString("CartList", json);
                HomeActivity.editor.apply();

            }
        });

        // xóa sp khỏi giỏ hàng
        holder.imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
                CartActivity.setMoney();
                CartActivity.checkData();

                // lưu vào
                Gson gson = new Gson();
                String json = gson.toJson(HomeActivity.cartArrayList);
                HomeActivity.editor.putString("CartList", json);
                HomeActivity.editor.apply();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}

package com.example.androidfinalexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.androidfinalexam.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    private int images[] = {
        R.drawable.p1,
        R.drawable.p1,
        R.drawable.p1,
    };

    private String title[] = {
            "Learn",
            "Create",
            "Enjoy",
    };

    private String desc[] = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
            "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
    };

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_pager, container, false);

        // init view
        ImageView imageView = v.findViewById(R.id.imageViewViewPager);
        TextView txtTitle = v.findViewById(R.id.textViewTitle);
        TextView txtDesc = v.findViewById(R.id.textViewViewPager);

        imageView.setImageResource(images[position]);
        txtTitle.setText(title[position]);
        txtDesc.setText(desc[position]);

        container.addView(v);
        return v;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

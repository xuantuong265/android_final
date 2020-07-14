package com.example.androidfinalexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidfinalexam.R;
import com.example.androidfinalexam.adapters.ViewPagerAdapter;

public class OnboardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnLeft, btnRight;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout linearLayout;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        initView();
        setOnClick();

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        linearLayout = (LinearLayout) findViewById(R.id.dotslayout);
        viewPagerAdapter = new ViewPagerAdapter(this);

        addDots(0);
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(viewPagerAdapter);

    }

    private void setOnClick(){

        btnRight.setOnClickListener(v->{
            if ( btnRight.getText().toString().equals("Next") ){
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }else {
                startActivity( new Intent( OnboardActivity.this, HomeActivity.class));
                finish();
            }
        });

        btnLeft.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 2);
        });

    }

    private void addDots(int postion){

        linearLayout.removeAllViews();
        dots = new TextView[3];
        for ( int i = 0; i < dots.length; i++ ){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            linearLayout.addView(dots[i]);
        }

        if ( dots.length > 0 ){
            dots[postion].setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if ( position == 0 ){
                btnLeft.setVisibility(View.VISIBLE);
                btnLeft.setEnabled(true);
                btnRight.setText("Next");
            }else if( position == 1 ){
                btnLeft.setVisibility(View.GONE);
                btnLeft.setEnabled(false);
                btnRight.setText("Next");
            }else {
                btnLeft.setVisibility(View.GONE);
                btnLeft.setEnabled(false);
                btnRight.setText("Finish");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}

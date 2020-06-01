package com.example.tabsold;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.tabsold.ui.main.SectionsPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class MainActivity extends AppCompatActivity {

    static SmartTabLayout viewPagerTab;
//    Toolbar toolbar;
    boolean tabIsDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = findViewById(R.id.toolbar);
//        setActionBar(toolbar);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Log.d("DaTuM", "onPageScrolled: page Changed & position is "+position );
                if (position == 1){
                    if (!tabIsDown){
                        viewPagerTab.animate().translationY(0).setDuration(500);
                        tabIsDown=true;
                    }
                }else if(position == 0){
                    if (tabIsDown){
                        viewPagerTab.animate().translationY(-100).setDuration(500);
                        tabIsDown=false;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public static void showTabLayout() {
        viewPagerTab.setVisibility(View.VISIBLE);
    }

    public static void hideTabLayout() {
        viewPagerTab.setVisibility(View.GONE);
    }

    public interface FragmentLifecycle {

        public void onPauseFragment();
        public void onResumeFragment();

    }
}
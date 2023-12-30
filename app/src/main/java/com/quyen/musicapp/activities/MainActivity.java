package com.quyen.musicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.quyen.musicapp.R;
import com.quyen.musicapp.adapters.ViewPagerAdapterMain;
import com.quyen.musicapp.fragments.AccountFragmment;
import com.quyen.musicapp.fragments.TimKiemFragment;
import com.quyen.musicapp.fragments.TranngChuFragment;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private static final int TIME_INTERVAL = 2000; // time between two back presses
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
            Toast.makeText(this, "Nhấn lần nữa để thoát!", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();

    }

    public void mapping() {
        tabLayout = (TabLayout) findViewById(R.id.MainActivity_TabLayout);
        viewPager = (ViewPager) findViewById(R.id.MainActivity_ViewPager);
    }

    public void init() {
        ViewPagerAdapterMain mainViewPagerAdapter = new ViewPagerAdapterMain(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new TranngChuFragment(), "Trang chủ");
        mainViewPagerAdapter.addFragment(new TimKiemFragment(), "Tìm Kiếm");
        mainViewPagerAdapter.addFragment(new AccountFragmment(), "Tài Khoản");
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_post_add_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_red);
        tabLayout.setBackgroundColor(Color.rgb(177, 177, 177));
    }
}
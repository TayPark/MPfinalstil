package com.example.mp_final_stil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Stil extends AppCompatActivity {
    private Context mContext;
    private TabLayout tabs;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stil_main);
        mContext = getApplicationContext();

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ArrayList<Integer> frag = new ArrayList<>();
        frag.add(R.drawable.my_on);
        frag.add(R.drawable.stil_on);
        frag.add(R.drawable.bookmark_off);

        for (int i = 0; i < 3; i++) {
            tabs.getTabAt(i).setIcon(frag.get(i));
        }
    }
}

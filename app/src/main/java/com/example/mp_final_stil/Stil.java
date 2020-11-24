package com.example.mp_final_stil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Stil extends AppCompatActivity {
    private TabLayout tabs;
    private final int NUMBER_OF_TABS = 3;
    ViewPager viewPager;
    ListView shareList, bookmarkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stil_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        // lists setting
        shareList = findViewById(R.id.stilList);
        bookmarkList = findViewById(R.id.bookmarkList);

        /**
         * TODO: 뷰를 어떻게 넣을 것인지 결정
         * 1. Scroll View 에 LinearLayout 넣고 View.onClickListener 로 확장
         * 2. Expandable Scroll View 에 넣고 onClickListener
         */

        // images in tabs
        ArrayList<Integer> frag = new ArrayList<>();
        frag.add(R.drawable.my_on);
        frag.add(R.drawable.stil_on);
        frag.add(R.drawable.bookmark_on);

        for (int i = 0; i < NUMBER_OF_TABS; i++) {
            tabs.getTabAt(i).setIcon(frag.get(i));
        }
    }
}

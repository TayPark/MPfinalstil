package com.example.mp_final_stil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.nio.channels.AlreadyBoundException;
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

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**
         * Set dialog for deployment and inflate.
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share TIL");

        final View dialogLayout = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(dialogLayout);

        /**
         * Set buttons and action here.
         */
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText title = dialogLayout.findViewById(R.id.userTitle);
                EditText summary = dialogLayout.findViewById(R.id.userSummary);

                /**
                 * POST HTTP request and get response.
                 * Then parse a response, make Toast message as response given.
                 */

//                Toast.makeText(Stil.this, title.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

        return true;
    }
}

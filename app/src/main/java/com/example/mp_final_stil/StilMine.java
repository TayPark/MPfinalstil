package com.example.mp_final_stil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class StilMine extends ListFragment {
    private ArrayList<String> items = new ArrayList<>();

    ListViewAdapter adapter;

    public StilMine() {
        // Required empty public constructor
    }

    public static StilMine newInstance(String param1, String param2) {
        StilMine fragment = new StilMine();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ListViewAdapter();
        setListAdapter(adapter);

        /**
         * 서버로부터 데이터를 받아와서 처리 코드 필요
         */

        adapter.addItem("Titleasdf3", "Summary1", "contents");
        adapter.addItem("Title2", "Summary1", "contents");
        adapter.addItem("Title3", "Summary5", "contents");
        adapter.addItem("123", "Summary4", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("1", "Summary1", "contents");
        adapter.addItem(" ef2", "Summary1", "contents");
        adapter.addItem("aw", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("12", "Summary1", "contents");
        adapter.addItem("61347", "Summary1", "contents");


        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.mine_tab, container, false);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        boolean isOpened = item.getOpenness();

        TextView titleTextView = v.findViewById(R.id.titleTextView);
        TextView summaryTextView = v.findViewById(R.id.summaryTextView);
        TextView contentTextView = v.findViewById(R.id.contentTextView);

        Button bookmarkBtn = v.findViewById(R.id.bookmarkBtn);
        Button closeBtn = v.findViewById(R.id.closeBtn);
        Button deleteBtn = v.findViewById(R.id.deleteBtn);

        if (isOpened) {
            item.setClose();

            titleTextView.setVisibility(View.VISIBLE);
            summaryTextView.setVisibility(View.VISIBLE);

            contentTextView.setVisibility(View.GONE);
            bookmarkBtn.setVisibility(View.GONE);
            closeBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);

        } else {
            item.setOpen();

//            titleTextView.setVisibility(View.GONE);
            summaryTextView.setVisibility(View.GONE);

            contentTextView.setVisibility(View.VISIBLE);
            bookmarkBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);


            deleteBtn.setBackgroundColor(Color.RED);
            deleteBtn.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();;
    }

    public void addItem(String title, String summary, String content) {
        adapter.addItem(title, summary, content);
    }

    public void removeItem(int position) {

    }

}
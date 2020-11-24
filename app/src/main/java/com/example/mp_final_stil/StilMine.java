package com.example.mp_final_stil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
         * 서버로부터 데이터를 받아와서 처리
         */

        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title2", "Summary1", "contents");
        adapter.addItem("Title3", "Summary5", "contents");
        adapter.addItem("Title4", "Summary4", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");
        adapter.addItem("Title", "Summary1", "contents");


        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.mine_tab, container, false);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        String title = item.getTitle();
//        Toast.makeText(v.getContext(), title, Toast.LENGTH_SHORT).show(); // it works!


//        v.findViewById(R.id.bookmarkBtn).setVisibility(View.VISIBLE);
//        v.findViewById(R.id.contentTextView).setVisibility(View.VISIBLE);
    }

    public void addItem(String title, String summary, String content) {
        adapter.addItem(title, summary, content);
    }

    public void removeItem(int position) {

    }

}
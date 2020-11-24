package com.example.mp_final_stil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StilMine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StilMine extends ListFragment {
    private ArrayList<String> items = new ArrayList<>();

    ListViewAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StilMine() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StilMine newInstance(String param1, String param2) {
        StilMine fragment = new StilMine();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ListViewAdapter();
        setListAdapter(adapter);

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
}
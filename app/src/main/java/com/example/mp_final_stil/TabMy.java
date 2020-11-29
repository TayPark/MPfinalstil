package com.example.mp_final_stil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.telephony.CellIdentity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TabMy extends ListFragment {
    private ArrayList<String> items = new ArrayList<>();
    CheckBoxAdapter adapter;
//    LinearLayout myTabLayout;

    public TabMy() {
        // Required empty public constructor
    }

    public TabMy(JSONArray items) {
        try {
            ArrayList<String> contents = (ArrayList<String>) items.get(0);
            Log.d("Tab-my", contents.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static TabMy newInstance() {
        TabMy fragment = new TabMy();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new CheckBoxAdapter();
        setListAdapter(adapter);

        adapter.addItem("Hello");
        adapter.addItem("Hello1");
        adapter.addItem("Hello2");
        adapter.addItem("Hello3");


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {

    }

    public void removeItem(int position) {

    }

}
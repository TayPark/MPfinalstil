package com.example.mp_final_stil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.telephony.CellIdentity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TabMy extends ListFragment {
    private ArrayList<String> _items = new ArrayList<>();
    CheckBoxAdapter adapter;
    CheckBox checkBox;

    public TabMy() {
        // Required empty public constructor
    }

    public TabMy(JSONArray items) {
        try {
            for (int i = 0; i < items.length(); i++) {
                this._items.add(items.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static TabMy newInstance(JSONArray items) {
        TabMy fragment = new TabMy(items);
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

        for (int i = 0; i < _items.size(); i++) {
            adapter.addItem(_items.get(i));
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
//
//    @Override
//    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
//
//    }
}
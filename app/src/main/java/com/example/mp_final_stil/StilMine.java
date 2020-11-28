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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StilMine extends ListFragment {
    private ArrayList<String> items = new ArrayList<>();
    ListViewAdapter adapter;

    public StilMine() {
        // Required empty public constructor
    }

    public static StilMine newInstance() {
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
         * HTTP request and response with Volley
         */
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://15.164.96.105:8080/stil?type=my&email=" + "worker@naver.com";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUG/MyTIL", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DEBUG/MyTIL", error.toString());
                Toast.makeText(getContext(), String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        boolean isOpened = item.getOpenness();
        Log.d("DEBUG", String.valueOf(isOpened));

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

            summaryTextView.setVisibility(View.GONE);
            contentTextView.setVisibility(View.VISIBLE);

            bookmarkBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);

            bookmarkBtn.setBackgroundColor(Color.parseColor("#ffb347"));
            deleteBtn.setBackgroundColor(Color.parseColor("#e65a1e"));
            closeBtn.setBackgroundColor(Color.parseColor("#21b2de"));
        }

        adapter.notifyDataSetChanged();
//        Toast.makeText(getContext(), String.valueOf(position) + "를 눌렀단다", Toast.LENGTH_SHORT).show();
    }

    public void addItem(String title, String summary, String content) {
        adapter.addItem(title, summary, content);
    }

    public void removeItem(int position) {

    }

}
package com.example.mp_final_stil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TabBookmark extends ListFragment {
    private ArrayList<String> items = new ArrayList<>();
    ListViewAdapter adapter;
    Button bookmarkBtn, closeBtn, deleteBtn;
    TextView titleTextView, summaryTextView, contentTextView;
    public TabBookmark() {
        // Required empty public constructor
    }

    public static TabBookmark newInstance() {
        TabBookmark fragment = new TabBookmark();
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
         * 서버에서 데이터를 받아와서 처리해야 함
         */
        adapter.addItem("1245", "Summary1", "contents");
        adapter.addItem("Title2", "Summary1", "contents");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        boolean isOpened = item.getOpenness();
        Log.d("DEBUG", String.valueOf(isOpened));

        /**
         * View/Button getters
         */
        titleTextView = v.findViewById(R.id.titleTextView);
        summaryTextView = v.findViewById(R.id.summaryTextView);
        contentTextView = v.findViewById(R.id.contentTextView);

        bookmarkBtn = v.findViewById(R.id.bookmarkBtn);
        closeBtn = v.findViewById(R.id.closeBtn);
        deleteBtn = v.findViewById(R.id.deleteBtn);

        /**
         * Open content
         */
        summaryTextView.setVisibility(View.GONE);
        contentTextView.setVisibility(View.VISIBLE);

        closeBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);

        deleteBtn.setBackgroundColor(Color.parseColor("#e65a1e"));
        closeBtn.setBackgroundColor(Color.parseColor("#21b2de"));

        /**
         * Button click listeners
         */
        closeBtn.setOnClickListener(contentCloser());
        deleteBtn.setOnClickListener(deleteBookmark());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titleTextView.setVisibility(View.VISIBLE);
                summaryTextView.setVisibility(View.VISIBLE);

                contentTextView.setVisibility(View.GONE);
                bookmarkBtn.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
            }
        });

        adapter.notifyDataSetChanged();


//        Toast.makeText(getContext(), String.valueOf(position) + "를 눌렀단다", Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener contentCloser () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTextView.setVisibility(View.VISIBLE);
                summaryTextView.setVisibility(View.VISIBLE);

                contentTextView.setVisibility(View.GONE);
                bookmarkBtn.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
            }
        };
    }

    /**
     * Delete content if it is user's
     * @return
     */
    private View.OnClickListener deleteBookmark() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 통신 코드 필요
                 */
            }
        };
    }
}
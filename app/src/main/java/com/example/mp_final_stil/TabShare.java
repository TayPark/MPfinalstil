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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabShare extends ListFragment {
    private ArrayList<JSONObject> items = new ArrayList<>();
    ListViewAdapter adapter;
    TextView titleTextView;
    TextView summaryTextView;
    TextView contentTextView;

    Button bookmarkBtn;
    Button closeBtn;
    Button deleteBtn;

    public TabShare() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ListViewAdapter();
        setListAdapter(adapter);

        /**
         * 서버로부터 데이터를 받아와서 처리 코드 필요
         */

        adapter.addItem("1245", "Summary1", "contents");
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
    }

    /**
     * Open a content.
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
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

        bookmarkBtn.setVisibility(View.VISIBLE);
        closeBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);

        bookmarkBtn.setBackgroundColor(Color.parseColor("#ffb347"));
        deleteBtn.setBackgroundColor(Color.parseColor("#e65a1e"));
        closeBtn.setBackgroundColor(Color.parseColor("#21b2de"));

        /**
         * Button click listeners
         */
        closeBtn.setOnClickListener(contentCloser());
        bookmarkBtn.setOnClickListener(addBookmark());
        deleteBtn.setOnClickListener(deleteContent());

        adapter.notifyDataSetChanged();
//        Toast.makeText(getContext(), String.valueOf(position) + "를 눌렀단다", Toast.LENGTH_SHORT).show();
    }

    /**
     * Close opened content.
     * @return Button's OnClickListener that close content
     */
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
     * Bookmark this STIL.
     * @return Button's OnClickListener that add bookmark for current user's email
     */
    private View.OnClickListener addBookmark() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 통신 코드 필요
                 */
            }
        };
    }

    /**
     * Delete content if it is user's
     * @return
     */
    private View.OnClickListener deleteContent() {
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
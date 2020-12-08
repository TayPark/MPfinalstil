package com.example.mp_final_stil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public ListViewAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Render items when user select TabShare, TabBookmark.
     *
     * @param position    - Position on parent view
     * @param convertView - Item view to be made
     * @param parent      - Parent view group(layout)
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* To render item onto parent view group, inflate view with inflater service in parent context */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView summaryTextView = convertView.findViewById(R.id.summaryTextView);
        TextView contentTextView = convertView.findViewById(R.id.contentTextView);
        TextView author = convertView.findViewById(R.id.itemAuthor);
        TextView idHolder = convertView.findViewById(R.id.itemId);

        ListViewItem listViewItem = listViewItemList.get(position);

        String contentForEndUser = "";
        try {
            JSONArray contents = new JSONArray(listViewItem.getContent());
            for (int i = 0; i < contents.length(); i++) {
                contentForEndUser += "- " + contents.get(i) + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        contentTextView.setText(contentForEndUser);
        titleTextView.setText(listViewItem.getTitle());
        summaryTextView.setText(listViewItem.getSummary());
        author.setText(listViewItem.getAuthor());
        idHolder.setText(listViewItem.getId());

        return convertView;
    }

    public void addItem(String title, String summary, String content, String author, String id) {
        ListViewItem item = new ListViewItem(title, summary, content, author, id);

        listViewItemList.add(item);
    }

    public void removeItem(int position) {
        listViewItemList.remove(position);
    }

}

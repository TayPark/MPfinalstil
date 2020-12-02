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
     * 뷰가 렌더링 될 때 각 아이템을 렌더링하는 메소드입니다.
     *
     * @param position    - 부모 뷰에서의 위치
     * @param convertView - 만들어질 아이템 뷰
     * @param parent      - 부모 뷰
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 뷰를 inflate 하기 위해 서비스와 인플레이터를 호출 함  */
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

        titleTextView.setText(listViewItem.getTitle());
        summaryTextView.setText(listViewItem.getSummary());

        String contentForEndUser = "";

        try {
            JSONArray contents = new JSONArray(listViewItem.getContent());
            for (int i = 0; i < contents.length(); i++) {
                contentForEndUser += i + ". " + contents.get(i) + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        contentTextView.setText(contentForEndUser);
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

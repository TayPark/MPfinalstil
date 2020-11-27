package com.example.mp_final_stil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();
    private boolean _isOpened = false;

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
     * Stil-Share 탭과 Stil-Bookmark 에서 컨텐츠 뷰를 위한 메소드입니다.
     * 아래의 코드는 뷰를 불러오는 방식을 기록합니다.
     *
     * @param position - position of contents
     * @param convertView - Direct view
     * @param parent - Parent view
     * @return View
     *
     * author: taypark@github.com
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /**
         * 뷰를 inflate 하기 위해 서비스와 인플레이터를 호출 함
         */
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView summaryTextView = convertView.findViewById(R.id.summaryTextView);
        TextView contentTextView = convertView.findViewById(R.id.contentTextView);

        ListViewItem listViewItem = listViewItemList.get(position);


        titleTextView.setText(listViewItem.getTitle());
        summaryTextView.setText(listViewItem.getSummary());
        contentTextView.setText(listViewItem.getContent());

        Log.d("Debug", String.valueOf(position));

        return convertView;
    }

    public void addItem(String title, String summary, String content) {
        ListViewItem item = new ListViewItem(title, summary, content);

        listViewItemList.add(item);
    }

    public void removeItem(int position) {
        listViewItemList.remove(position);
    }
}

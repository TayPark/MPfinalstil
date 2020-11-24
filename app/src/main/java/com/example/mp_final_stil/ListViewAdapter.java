package com.example.mp_final_stil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {
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

        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView summaryTextView = (TextView) convertView.findViewById(R.id.summaryTextView);

        ListViewItem listViewItem = listViewItemList.get(position);


        titleTextView.setText(listViewItem.getTitle());
        summaryTextView.setText(listViewItem.getSummary());

        return convertView;
    }

    public void addItem(String title, String summary, String content) {
        ListViewItem item = new ListViewItem(title, summary, content);

        listViewItemList.add(item);
    }

    public void removeItem(int position) {
        listViewItemList.remove(position);
    }

    /**
     * 카드를 눌렀을 때 세부 정보를 확인하기 위해 레이아웃을 변경하는 onClick method 입니다.
     *
     * @param v - 실제 반응하는 뷰
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "작동은 하니?", Toast.LENGTH_SHORT).show();

        TextView titleTextView = v.findViewById(R.id.titleTextView);
        TextView summaryTextView = v.findViewById(R.id.summaryTextView);
        TextView contentTextView = v.findViewById(R.id.contentTextView);

        Button bookmarkBtn = v.findViewById(R.id.bookmarkBtn);
        Button closeBtn = v.findViewById(R.id.closeBtn);
        Button deleteBtn = v.findViewById(R.id.deleteBtn);

        if (this._isOpened == false) {
            this._isOpened = true;

            titleTextView.setVisibility(View.GONE);
            summaryTextView.setVisibility(View.GONE);
            contentTextView.setVisibility(View.VISIBLE);

            bookmarkBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);

            /**
             * 본인인지 확인해서 deleteBtn 활성화 여부
             * */

        } else {
            this._isOpened = true;

            titleTextView.setVisibility(View.VISIBLE);
            summaryTextView.setVisibility(View.VISIBLE);
            contentTextView.setVisibility(View.GONE);

            bookmarkBtn.setVisibility(View.GONE);
            closeBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
    }
}

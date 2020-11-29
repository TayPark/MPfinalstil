package com.example.mp_final_stil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;


public class CheckBoxAdapter extends BaseAdapter {
    private ArrayList<CheckBoxItem> checkBoxItems = new ArrayList<>();

    public CheckBoxAdapter() { }

    /**
     * Constructor for recall
     * @param items - Items to be made for tab
     */
    public CheckBoxAdapter(ArrayList<String> items) {
        for (String each : items) {
            CheckBoxItem tempCheckBox = new CheckBoxItem(each);
            checkBoxItems.add(tempCheckBox);
        }
    }

    @Override
    public int getCount() {
        return checkBoxItems.size();
    }

    @Override
    public Object getItem(int position) {
        return checkBoxItems.get(position);
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
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 뷰를 inflate 하기 위해 서비스와 인플레이터를 호출 함 */
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkbox_item, parent, false);
        }

        /* Get data and set */
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        CheckBoxItem checkBoxItem = checkBoxItems.get(position);
        checkBox.setText(checkBoxItem.getContent());

        return convertView;
    }

    public void addItem(String content) {
        CheckBoxItem item = new CheckBoxItem(content);

        checkBoxItems.add(item);
    }

    public void removeItem(int position) {
        checkBoxItems.remove(position);
    }

}

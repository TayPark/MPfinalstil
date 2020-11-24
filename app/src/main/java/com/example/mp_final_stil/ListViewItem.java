package com.example.mp_final_stil;

public class ListViewItem {
    private String _title;
    private String _summary;
    private String _content;

    public ListViewItem(String title, String summary, String content) {
        this._title = title;
        this._summary = summary;
        this._content = content;
    }

    public String[] getItem() {
        String[] item = {this._title, this._summary, this._content};
        return item;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public void setSummary(String summary) {
        this._summary = summary;
    }

    public void setContent(String content) {
        this._content = content;
    }

    public String getTitle() {
        return this._title;
    }

    public String getSummary() {
        return this._summary;
    }

    public String getContent() {
        return this._content;
    }
}

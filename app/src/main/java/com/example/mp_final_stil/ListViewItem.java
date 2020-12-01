package com.example.mp_final_stil;

public class ListViewItem {
    private String _id;
    private String _title;
    private String _summary;
    private String _content;
    private String _author;
    private boolean _openness;

    public ListViewItem(String title, String summary, String content, String author, String id) {
        this._title = title;
        this._summary = summary;
        this._content = content;
        this._author = author;
        this._id = id;
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

    public void setAuthor(String author) {
        this._author = author;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setOpen() {
        this._openness = true;
    }

    public void setClose() {
        this._openness = false;
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

    public Boolean getOpenness() {
        return this._openness;
    }

    public String getId() {
        return this._id;
    }

    public String getAuthor() {
        return this._author;
    }
}

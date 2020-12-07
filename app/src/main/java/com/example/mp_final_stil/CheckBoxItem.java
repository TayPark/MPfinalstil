package com.example.mp_final_stil;

public class CheckBoxItem {
    private String _content;
    private String _id;
    private Boolean _checked;

    public CheckBoxItem(String content, String id, Boolean checked) {
        this._content = content;
        this._id = id;
        this._checked = checked;
    }

    public void setContent(String content) {
        this._content = content;
    }

    public String getId() {
        return this._id;
    }

    public boolean isChecked() {
        return this._checked;
    }

    public String getContent() {
        return this._content;
    }
}
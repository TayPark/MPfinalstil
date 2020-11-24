package com.example.mp_final_stil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    private boolean isChecked = false;
    CheckBox _checkBox = findViewById(R.id.checkBox1);

    public CheckableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean isChecked() {
        return _checkBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        if(_checkBox.isChecked() != checked) {
            _checkBox.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
        setChecked(_checkBox.isChecked() ? false : true);
    }
}

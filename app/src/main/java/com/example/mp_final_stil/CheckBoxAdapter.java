package com.example.mp_final_stil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CheckBoxAdapter extends BaseAdapter {
    private ArrayList<CheckBoxItem> checkBoxItems = new ArrayList<>();

    public CheckBoxAdapter() {
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

    public void addItem(JSONObject content) {
        CheckBoxItem item = null;
        try {
            item = new CheckBoxItem(content.getString("content"), content.getString("_id"), content.getBoolean("checked"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkBoxItems.add(item);
    }

    /**
     * Render items when user select TabMy.
     *
     * @param position    - Position on parent view
     * @param convertView - Item view to be made
     * @param parent      - Parent view group(layout)
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* To render item onto parent view group, inflate view with inflater service in parent context */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkbox_item, parent, false);
        }

        /* Initially set proper position on every CheckBoxItems */
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        CheckBoxItem checkBoxItem = checkBoxItems.get(position);
        checkBox.setText(checkBoxItem.getContent());
        if (checkBoxItem.isChecked()) {
            checkBox.setChecked(true);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            checkBox.setBackgroundColor(Color.parseColor("#c4c4c4"));
        }

        /* Mongoose ObjectId holder */
        TextView idHolder = convertView.findViewById(R.id.checkBoxId);
        idHolder.setText(checkBoxItem.getId());

        /* CheckBox check on/off */
        checkBox.setOnClickListener(v -> {
            JSONObject requestBody = new JSONObject();
            SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

            try {
                requestBody.put("author", userAccount.getString("email", null));
                requestBody.put("itemId", idHolder.getText().toString());

                if (checkBox.isChecked()) {
                    checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    checkBox.setBackgroundColor(Color.parseColor("#c4c4c4"));
                    requestBody.put("toggle", true);
                } else {
                    checkBox.setPaintFlags(0);
                    checkBox.setBackgroundColor(Color.WHITE);
                    requestBody.put("toggle", false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://15.164.96.105:8080/stil/check";
            queue.add(new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
                try {
                    if (response.getString("ok").equals("1")) {
                        Toast.makeText(context, "Checked successfully.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUG/Main-deploy", response.toString());
            }, error -> {
                Toast.makeText(context, "Unexpected error: " + error, Toast.LENGTH_SHORT).show();
                Log.e("DEBUG/Main-deploy", error.toString());
            }));
        });

        /* Edit/Delete feature on long click */
        checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Select action");

                /* Delete listener */
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                        deleteDialog.setTitle("Delete TIL");

                        deleteDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject requestBody = new JSONObject();
                                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                                try {
                                    requestBody.put("author", userAccount.getString("email", null));
                                    requestBody.put("itemId", idHolder.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = "http://15.164.96.105:8080/stil/pull";
                                queue.add(new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
                                    try {
                                        if (response.getString("ok").equals("1")) {
                                            TabMy frag = (TabMy) Main.viewpagerAdapter.getItem(0);
                                            frag.updateItem(response.getJSONArray("data"));
                                            Main.viewpagerAdapter.notifyDataSetChanged();
                                            Main.setIcons();
                                            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("DEBUG/Main-deploy", response.toString());
                                }, error -> {
                                    Toast.makeText(context, "Unexpected error: " + error, Toast.LENGTH_SHORT).show();
                                    Log.e("DEBUG/Main-deploy", error.toString());
                                }));
                            }
                        });
                        deleteDialog.setNegativeButton("Cancel", null);
                        deleteDialog.show();
                    }
                    /* Edit listener */
                }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder editDialog = new AlertDialog.Builder(context);
                        editDialog.setTitle("Edit TIL");
                        EditText editText = new EditText(context);
                        editText.setText(checkBox.getText());
                        editDialog.setView(editText);

                        editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject requestBody = new JSONObject();
                                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                                try {
                                    requestBody.put("author", userAccount.getString("email", null));
                                    requestBody.put("itemId", idHolder.getText().toString());
                                    requestBody.put("content", editText.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = "http://15.164.96.105:8080/stil/edit";
                                queue.add(new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
                                    try {
                                        if (response.getString("ok").equals("1")) {
                                            TabMy frag = (TabMy) Main.viewpagerAdapter.getItem(0);
                                            frag.updateItem((response.getJSONArray("data")));
                                            Main.viewpagerAdapter.notifyDataSetChanged();
                                            Main.setIcons();
                                            Toast.makeText(context, "Edited.", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("DEBUG/Main-deploy", response.toString());
                                }, error -> {
                                    Toast.makeText(context, "Unexpected error: " + error, Toast.LENGTH_SHORT).show();
                                    Log.e("DEBUG/Main-deploy", error.toString());
                                }));
                            }
                        });
                        editDialog.setNegativeButton("Cancel", null);
                        editDialog.show();
                    }
                });
                dialog.show();
                return true;
            }
        });
        return convertView;
    }


}

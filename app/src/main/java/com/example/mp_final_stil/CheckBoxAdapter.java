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

    /* 뷰가 렌더링 될 때 호출되는 코드 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 뷰를 inflate 하기 위해 서비스와 인플레이터를 호출 함 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkbox_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        CheckBoxItem checkBoxItem = checkBoxItems.get(position);
        checkBox.setText(checkBoxItem.getContent());
        if (checkBoxItem.isChecked()) {
            checkBox.setChecked(true);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            checkBox.setBackgroundColor(Color.parseColor("#c4c4c4"));
        }

        TextView idHolder = convertView.findViewById(R.id.checkBoxId);
        idHolder.setText(checkBoxItem.getId());

        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                checkBox.setBackgroundColor(Color.parseColor("#c4c4c4"));

                JSONObject requestBody = new JSONObject();
                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                try {
                    requestBody.put("author", userAccount.getString("email", null));
                    requestBody.put("toggle", true);
                    requestBody.put("itemId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("checkBoxId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://15.164.96.105:8080/stil/check";
                JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
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
                });
                queue.add(deleteRequest);

            } else {
                checkBox.setPaintFlags(0);
                checkBox.setBackgroundColor(Color.WHITE);

                JSONObject requestBody = new JSONObject();
                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                try {
                    requestBody.put("author", userAccount.getString("email", null));
                    requestBody.put("toggle", false);
                    requestBody.put("itemId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("checkBoxId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://15.164.96.105:8080/stil/check";
                JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
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
                });
                queue.add(deleteRequest);
            }
        });

        checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Select action");

                /* 삭제 이벤트리스너 */
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                        deleteDialog.setTitle("Delete TIL");

                        /* 체크박스 뷰어 홀더 getter */
                        ViewGroup viewGroup = (ViewGroup) checkBox.getParent().getParent();

                        deleteDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject requestBody = new JSONObject();
                                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                                try {
                                    requestBody.put("author", userAccount.getString("email", null));
                                    requestBody.put("itemId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                /* 데이터 삭제 요청 */
                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = "http://15.164.96.105:8080/stil/pull";
                                JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
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
                                });
                                queue.add(deleteRequest);
                            }
                        });
                        deleteDialog.setNegativeButton("Cancel", null);
                        deleteDialog.show();
                    }
                    /* On Edit action */
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
                                checkBox.setText(editText.getText().toString());

                                JSONObject requestBody = new JSONObject();
                                SharedPreferences userAccount = context.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                                try {
                                    requestBody.put("author", userAccount.getString("email", null));
                                    requestBody.put("itemId", ((TextView) ((ViewGroup) checkBox.getParent()).getChildAt(0)).getText().toString());
                                    requestBody.put("content", checkBox.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                /* 수정 요청 */
                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = "http://15.164.96.105:8080/stil/edit";
                                JsonObjectRequest deployRequest = new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
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
                                });
                                queue.add(deployRequest);
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

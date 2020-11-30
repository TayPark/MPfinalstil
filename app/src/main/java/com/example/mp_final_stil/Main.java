package com.example.mp_final_stil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    private TabLayout tabs;
    private final int NUMBER_OF_TABS = 3;
    ViewPager viewPager;
    LinearLayout myList;
    ListView shareList, bookmarkList;
    String url;
    SharedPreferences userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stil_main);

        userAccount = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        // set adapter on viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        // lists by tab
        myList = findViewById(R.id.myTabLayout);
        shareList = findViewById(R.id.shareList);
        bookmarkList = findViewById(R.id.bookmarkList);

        // images in tabs
        ArrayList<Integer> headerIcons = new ArrayList<>();
        headerIcons.add(R.drawable.my_on);
        headerIcons.add(R.drawable.stil_on);
        headerIcons.add(R.drawable.bookmark_on);
        for (int i = 0; i < NUMBER_OF_TABS; i++) {
            tabs.getTabAt(i).setIcon(headerIcons.get(i));
        }

        /* Initial fetch from server (my tab) */
        url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                TabMy newerMyTab = new TabMy(response);
                adapter.updateItem(0, newerMyTab);
                Log.d("Stil-my-init", response.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
        }, error -> {
            Log.d("Stil-my-init", error.toString());
            Toast.makeText(Main.this, error.toString(), Toast.LENGTH_SHORT).show();
        }));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs) {
            @Override
            public void onPageSelected(int position) {
                int tabPosition = position;
                if (tabPosition == 0) {
                    url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
                } else if (tabPosition == 1) {
                    url = "http://15.164.96.105:8080/stil?type=share";
                } else if (tabPosition == 2) {
                    url = "http://15.164.96.105:8080/stil?type=bookmark&email=" + userAccount.getString("email", null);
                } else {
                    Toast.makeText(Main.this, "Wrong access on tab: " + tabs.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                }
                queue.add(new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                    try {
                        if (tabPosition == 0) {
                            TabMy newMyTab = new TabMy(response);
                            adapter.updateItem(0, newMyTab);
                        } else if (tabPosition == 1) {
                            TabShare newShareTab = new TabShare(response);
                            adapter.updateItem(1, newShareTab);
                        } else if (tabPosition == 2) {
                            TabBookmark newBookmarkTab = new TabBookmark(response);
                            adapter.updateItem(2, newBookmarkTab);
                        }
                        Log.d("Stil-tab-" + tabs.getSelectedTabPosition(), response.toString(2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.d("Stil-tab-" + tabs.getSelectedTabPosition(), error.toString())));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveMenu) {
            saveTilListener();
        } else if (item.getItemId() == R.id.deployMenu) {
            deployTilListener();
        }
        return true;
    }

    private void saveTilListener() {
        PagerAdapter adapter = viewPager.getAdapter();
        final int counts = adapter.getCount();
        Log.d("Number-of-pager", String.valueOf(counts));
        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();

        CheckBox eachCheckBox = null;
        String content = null;
        for (int i = 0; i < counts; i++) {
//            eachCheckBox = (CheckBox) myLayout.getChildAt(i);
//            content = eachCheckBox.getText().toString();
//            contents.put(content);
        }

        try {
            requestBody.put("author", userAccount.getString("email", null));
            requestBody.put("content", contents);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(Main.this);
        String url = "http://15.164.96.105:8080/stil";
        JsonObjectRequest deployRequest = new JsonObjectRequest(Request.Method.PATCH, url, requestBody, response -> {
            Log.d("DEBUG/Main-save", response.toString());
            Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
        }, error -> {
            Log.d("DEBUG/Main-save", error.toString());
            Toast.makeText(Main.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
        });
    }

    private void deployTilListener() {
        /* Set dialog for deployment and inflate. */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share TIL");

        final View dialogLayout = getLayoutInflater().inflate(R.layout.deploy_dialog, null);
        builder.setView(dialogLayout);

        /* Set buttons and action here. */
        builder.setPositiveButton("OK", (dialog, which) -> {
            EditText title = dialogLayout.findViewById(R.id.userTitle);
            EditText summary = dialogLayout.findViewById(R.id.userSummary);

            if (title.getText().toString() != null && summary.getText().toString() != null) {
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("title", title.getText().toString());
                    requestBody.put("summary", summary.getText().toString());
                    JSONArray contentArray = new JSONArray();
                    contentArray.put("hello1");
                    contentArray.put("hello2");
                    requestBody.put("content", contentArray);

                    requestBody.put("author", userAccount.getString("email", null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(Main.this);
                String url = "http://15.164.96.105:8080/stil";
                JsonObjectRequest deployRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, response -> {
                    Log.d("DEBUG/Main-deploy", response.toString());
                    Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
                }, error -> {
                    Log.d("DEBUG/Main-deploy", error.toString());
                    Toast.makeText(Main.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                });

                queue.add(deployRequest);
            } else {
                Toast.makeText(this, "Title and summary cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}

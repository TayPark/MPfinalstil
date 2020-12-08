package com.example.mp_final_stil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    static TabLayout tabs;
    static ViewpagerAdapter viewpagerAdapter;
    static ArrayList<Integer> headerIcons;
    ViewPager viewPager;
    String url;
    SharedPreferences userAccount;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userAccount = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        /* Set icons */
        headerIcons = new ArrayList<>();
        headerIcons.add(R.drawable.my_on);
        headerIcons.add(R.drawable.stil_on);
        headerIcons.add(R.drawable.bookmark_on);

        /* Initial fetch from server (my tab) */
        url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
        queue = Volley.newRequestQueue(this);
        queue.add(new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            // Set adapter on viewpager to initiate.
            viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), response);
            viewPager.setAdapter(viewpagerAdapter);
            tabs.setupWithViewPager(viewPager);

            setIcons();
        }, error -> {
            Toast.makeText(Main.this, error.toString(), Toast.LENGTH_SHORT).show();
        }));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs) {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
                } else if (position == 1) {
                    url = "http://15.164.96.105:8080/stil?type=share";
                } else if (position == 2) {
                    url = "http://15.164.96.105:8080/stil?type=bookmark&email=" + userAccount.getString("email", null);
                } else {
                    Toast.makeText(Main.this, "Wrong access on tab: " + tabs.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                }

                queue.add(new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                    if (position == 0) {
                        TabMy frag = (TabMy) viewpagerAdapter.getItem(position);
                        frag.updateItem(response);
                    } else if (position == 1) {
                        TabShare frag = (TabShare) viewpagerAdapter.getItem(position);
                        frag.updateItem(response);
                    } else if (position == 2) {
                        TabBookmark frag = (TabBookmark) viewpagerAdapter.getItem(position);
                        frag.updateItem(response);
                    }

                    viewpagerAdapter.notifyDataSetChanged();
                    setIcons();
                }, error -> {
                    Log.d("Stil-tab-" + tabs.getSelectedTabPosition(), error.toString());
                }
                ));
            }
        });
    }

    public static void setIcons() {
        for (int i = 0; i < 3; i++) {
            tabs.getTabAt(i).setIcon(headerIcons.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deployMenu) {
            deployTilListener();
        } else if (item.getItemId() == R.id.addTil) {
            addTilListener();
        }
        return true;
    }

    private void addTilListener() {
        /* Set dialog for deployment and inflate. */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add TIL");

        final View dialogLayout = getLayoutInflater().inflate(R.layout.dialog_add_til, null);
        builder.setView(dialogLayout);

        /* Set buttons and action here. */
        builder.setPositiveButton("OK", (dialog, which) -> {
            EditText tilContent = dialogLayout.findViewById(R.id.til_content);

            if (!tilContent.getText().toString().trim().equals("")) {
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("author", userAccount.getString("email", null));
                    requestBody.put("content", tilContent.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(Main.this);
                String url = "http://15.164.96.105:8080/stil";
                queue.add(new JsonObjectRequest(Request.Method.POST, url, requestBody, response -> {
                    try {
                        if (response.getString("ok").equals("1")) {
                            JSONArray myTil = response.getJSONArray("data");
                            TabMy frag = (TabMy) viewpagerAdapter.getItem(0);
                            frag.updateItem(myTil);
                            viewpagerAdapter.notifyDataSetChanged();
                            setIcons();
                        } else {
                            Toast.makeText(Main.this, "Server goes wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Main.this, "Your TIL is added!", Toast.LENGTH_SHORT).show();
                }, error -> {
                    Toast.makeText(Main.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                }));
            } else {
                Toast.makeText(this, "Content cannot be an empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deployTilListener() {
        /* Set dialog for deployment and inflate. */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share TIL");

        final View dialogLayout = getLayoutInflater().inflate(R.layout.dialog_deploy, null);
        builder.setView(dialogLayout);

        /* Set buttons and action here. */
        builder.setPositiveButton("OK", (dialog, which) -> {
            EditText title = dialogLayout.findViewById(R.id.userTitle);
            EditText summary = dialogLayout.findViewById(R.id.userSummary);

            if (!(title.getText().toString().trim()).equals("") && !(summary.getText().toString().trim()).equals("")) {
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("title", title.getText().toString());
                    requestBody.put("summary", summary.getText().toString());
                    requestBody.put("author", userAccount.getString("email", null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(Main.this);
                String url = "http://15.164.96.105:8080/stil/deploy";
                JsonObjectRequest deployRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, response -> {
                    try {
                        if(response.getString("ok").equals("1")) {
                            TabMy frag = (TabMy) viewpagerAdapter.getItem(0);
                            frag.updateItem(new JSONArray());
                            viewpagerAdapter.notifyDataSetChanged();
                            setIcons();
                            Toast.makeText(Main.this, "Your TIL is deployed!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    if (error.toString().equals("com.android.volley.ClientError")) {
                        Toast.makeText(Main.this, "Write TIL first", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Main.this, "Unexpected error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(deployRequest);
            } else {
                Toast.makeText(this, "Title and summary cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }

}

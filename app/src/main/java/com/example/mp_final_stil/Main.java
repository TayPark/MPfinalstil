package com.example.mp_final_stil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stil_main);

        SharedPreferences userAccount = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        // set adapter on viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        // lists by tab
        myList = findViewById(R.id.myList);
        shareList = findViewById(R.id.shareList);
        bookmarkList = findViewById(R.id.bookmarkList);

        // images in tabs
        ArrayList<Integer> frag = new ArrayList<>();
        frag.add(R.drawable.my_on);
        frag.add(R.drawable.stil_on);
        frag.add(R.drawable.bookmark_on);
        for (int i = 0; i < NUMBER_OF_TABS; i++) {
            tabs.getTabAt(i).setIcon(frag.get(i));
        }

        /**
         * Initial fetch from server (my tab)
         */
        url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("Stil-my-init", response.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Stil-my-init", error.toString());
                Toast.makeText(Main.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int tabPosition = tab.getPosition();
                JsonArrayRequest request = null;

                if (tabPosition == 0) {
                    url = "http://15.164.96.105:8080/stil?type=my&email=" + userAccount.getString("email", null);
                } else if (tabPosition == 1) {
                    url = "http://15.164.96.105:8080/stil?type=share";
                } else if (tabPosition == 2) {
                    url = "http://15.164.96.105:8080/stil?type=bookmark&email=" + userAccount.getString("email", null);
                } else {
                    Toast.makeText(Main.this, "Wrong access on tab: " + tabs.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                }

                request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("Stil-tab-" + tabs.getSelectedTabPosition(), response.toString(2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Stil-tab-" + tabs.getSelectedTabPosition(), error.toString());
                    }
                });
                queue.add(request);
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
        /**
         * Set dialog for deployment and inflate.
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share TIL");

        final View dialogLayout = getLayoutInflater().inflate(R.layout.deploy_dialog, null);
        builder.setView(dialogLayout);

        /**
         * Set buttons and action here.
         */
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText title = dialogLayout.findViewById(R.id.userTitle);
                EditText summary = dialogLayout.findViewById(R.id.userSummary);

                RequestQueue queue = Volley.newRequestQueue(Main.this);
                String url = "http://15.164.96.105:8080/stil";
                JsonObjectRequest deployRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DEBUG/Main-button", response.toString());
                        Toast.makeText(Main.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DEBUG/Main-button", error.toString());
                        Toast.makeText(Main.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(deployRequest);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

        return true;
    }
}

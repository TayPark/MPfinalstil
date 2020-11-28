package com.example.mp_final_stil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Login extends AppCompatActivity {
    Button loginBtn, joinBtn;
    EditText emailEt, passwordEt;
    String autoEmail, autoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailEt = (EditText) findViewById(R.id.email);
        passwordEt = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);

        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        /**
         * editor.commit() : 확인
         * editor.clear() : 비우기
         */
        SharedPreferences.Editor editor = autoLogin.edit();
        autoEmail = autoLogin.getString("email", null);
        autoPassword = autoLogin.getString("password", null);

        JSONObject userData = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        String url = "http://15.164.96.105:8080/user/login";

        /**
         * Auto-login
         */
        if (autoEmail != null && autoPassword != null) {
            try {
                userData.put("email", autoEmail);
                userData.put("password", autoPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest autoLoginRequest = new JsonObjectRequest(
                    Request.Method.POST, url, userData
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("login-success", response.toString());
                    try {
                        if (response.get("ok").toString().equals("1")) {
                            Toast.makeText(Login.this, "Logined with " + userData.getString("email"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Stil.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("auto-login-error", error.toString());
                    Log.e("auto-login-error", "Requested params: " + autoEmail + " " + autoPassword);
                    if (error.toString().equals("com.android.volley.ClientError")) {
                        editor.clear();
                        Toast.makeText(Login.this, "Auto login failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Unexpected error: " + String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            queue.add(autoLoginRequest);
        }

        /**
         * Login process
         */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userData.put("email", emailEt.getText().toString());
                    userData.put("password", passwordEt.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest loginRequest = new JsonObjectRequest(
                        Request.Method.POST, url, userData
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("login-success", response.toString());
                        try {
                            if (response.get("ok").toString().equals("1")) {
                                Toast.makeText(Login.this, "Logined with " + userData.getString("email"), Toast.LENGTH_SHORT).show();
                                editor.putString("email", emailEt.getText().toString());
                                editor.putString("password", passwordEt.getText().toString());
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), Stil.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("login-error", error.toString());
                        if (error.toString().equals("com.android.volley.ClientError")) {
                            Toast.makeText(Login.this, "Check your ID and password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                queue.add(loginRequest);
            }
        });

        /**
         * Join process
         */
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

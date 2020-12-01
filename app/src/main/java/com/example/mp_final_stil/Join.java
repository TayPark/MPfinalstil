package com.example.mp_final_stil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Join extends AppCompatActivity {
    EditText email, password;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        joinBtn = findViewById(R.id.joinBtn);

        joinBtn.setOnClickListener(v -> {
            JSONObject joinAccount = new JSONObject();
            try {
                joinAccount.put("email", email.getText().toString());
                joinAccount.put("password", password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(Join.this);
            String url = "http://15.164.96.105:8080/user/join";
            JsonObjectRequest joinRequest = new JsonObjectRequest(
                    Request.Method.POST, url, joinAccount
                    , response -> {
                Log.d("join-success", response.toString());
                try {
                    if (response.get("ok").toString().equals("1")) {
                        Toast.makeText(Join.this, "Joined successfully. Please Login.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Join.this, "Join failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                Log.e("join-error", error.toString());
                if (error.toString().equals("com.android.volley.ClientError")) {
                    Toast.makeText(Join.this, "Duplicated email. Try other one", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Join.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(joinRequest);
        });
    }
}

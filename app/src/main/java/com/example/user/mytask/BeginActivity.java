package com.example.user.mytask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.mytask.ServerHandler.ServerHandler;


public class BeginActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_layout);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        final String user = preferences.getString("user", "null");
        final String password = preferences.getString("password", "null");
        Log.d("user", user);
        Log.d("pass", password);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (user.equals("null") && password.equals("null"))
                    intent = new Intent(BeginActivity.this, MainActivity.class);
                else {
                    User user1 = new ServerHandler().login(user, password);
                    intent = new Intent(BeginActivity.this, TasksActivity.class);
                    intent.putExtra("user", user1);
                }
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}

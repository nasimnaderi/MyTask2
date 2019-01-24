package com.example.user.mytask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mytask.ServerHandler.ServerHandler;


public class MainActivity extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button register = (Button)findViewById(R.id.register_btn);
        Button login = (Button)findViewById(R.id.login_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View defineView = li.inflate(R.layout.login_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText username = (EditText)findViewById(R.id.login_username);
                final EditText pass = (EditText)findViewById(R.id.login_pass);

                builder.setView(defineView);
                builder
                        .setCancelable(false)
                        .setPositiveButton("Login",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        User user = new ServerHandler().login(username.getText().toString(),pass.getText().toString());
                                        if(user == null){
                                            Toast.makeText(getApplicationContext(),"No user with this username",Toast.LENGTH_SHORT).show();
                                            return;
                                        } else{
                                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("user",user.getUserName());
                                            editor.putString("password",user.getPassword());
                                            editor.apply();
                                            Intent intent = new Intent(MainActivity.this, TasksActivity.class);
                                            intent.putExtra("user",user);
                                            startActivity(intent);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = builder.create();


                alertDialog.show();
            }
        });
    }
}

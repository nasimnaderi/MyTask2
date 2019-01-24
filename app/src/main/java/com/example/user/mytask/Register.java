package com.example.user.mytask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.user.mytask.ServerHandler.ServerHandler;


public class Register extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText name;
    EditText family;
    EditText password;
    String type;
    Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        final RadioButton golden = (RadioButton) findViewById(R.id.register_golden_btn);
        final RadioButton silver = (RadioButton) findViewById(R.id.register_silver_btn);
        final RadioButton regular = (RadioButton) findViewById(R.id.register_regular_btn);

        username = (EditText) findViewById(R.id.register_user_name);
        email = (EditText) findViewById(R.id.register_email);
        name = (EditText) findViewById(R.id.register_name);
        family = (EditText) findViewById(R.id.register_family);
        password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.user_register_btn);


        golden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silver.setChecked(false);
                regular.setChecked(false);
                type = "Golden";
                checkAll();
            }
        });

        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                golden.setChecked(false);
                regular.setChecked(false);
                type = "Silver";
                checkAll();
            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                golden.setChecked(false);
                silver.setChecked(false);
                type = "Regular";
                checkAll();
            }
        });


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAll();
            }
        });


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAll();
            }
        });


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAll();
            }
        });


        family.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAll();
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAll();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register.getCurrentTextColor() == Color.WHITE){
                    User user = new User(username.getText().toString(),email.getText().toString(),name.getText().toString(),family.getText().toString(),password.getText().toString(),type);
                    if(family.getText().toString().trim().equals(""))
                        user.setFamily("Not-Set");
                    new ServerHandler().createUser(user);
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("user",user.getUserName());
                    editor.putString("password",user.getPassword());
                    editor.apply();
                    Intent intent = new Intent(Register.this, TasksActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });


    }

    private boolean emptyChecker() {
        if (!username.getText().toString().trim().isEmpty() &&
                !email.getText().toString().trim().isEmpty() &&
                !name.getText().toString().trim().isEmpty() &&
                !password.getText().toString().trim().isEmpty() &&
                type != null) {
            return true;
        } else
            return false;
    }


    private boolean checkUsername() {
        String user = username.getText().toString();
        if (!Character.isDigit(user.charAt(0))) {
            for (int i = 1; i < user.length(); i++) {
                if (!Character.isDigit(user.charAt(i)) && !Character.isLetter(user.charAt(i)))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmail() {
        String mail = email.getText().toString();
        if (Character.isDigit(mail.charAt(0)))
            return false;
        boolean find1 = false, find2 = false;
        for (int i = 0; i < mail.length(); i++) {
            if (mail.charAt(i) == '@')
                find1 = true;
            if (find1 && mail.charAt(i) == '.')
                find2 = true;
        }
        if (find1 && find2)
            return true;
        else
            return false;
    }

    private boolean checkPassword() {
        boolean find1 = false, find2 = false, find3 = false;
        String pass = password.getText().toString();
        if (pass.length() < 8)
            return false;
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isDigit(pass.charAt(i)))
                find1 = true;
            else if (Character.toUpperCase(pass.charAt(i)) == pass.charAt(i))
                find2 = true;
            else
                find3 = true;

        }
        if (find1 && find2 && find3)
            return true;
        else
            return false;
    }

    private void checkAll() {
        if (emptyChecker() && checkUsername() && checkEmail() && checkPassword()) {
            register.setTextColor(Color.WHITE);
        } else {
            register.setTextColor(Color.GRAY);
        }
    }


}

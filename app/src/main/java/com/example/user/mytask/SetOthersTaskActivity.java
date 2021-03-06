package com.example.user.mytask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.mytask.ServerHandler.ServerHandler;
import com.example.user.mytask.ServerHandler.Task;
import com.example.user.mytask.ServerHandler.Type;
import com.example.user.mytask.ServerHandler.User;

import java.io.IOException;
import java.util.Date;

public class SetOthersTaskActivity extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_layout);
        final User user = (User) getIntent().getSerializableExtra("user");
        final TextInputEditText name = (TextInputEditText) findViewById(R.id.set_task_name);
        final TextInputEditText taskDate = (TextInputEditText) findViewById(R.id.set_task_date);
        final TextInputEditText taskTime = (TextInputEditText) findViewById(R.id.set_task_time);
        final Spinner typename = (Spinner) findViewById(R.id.set_task_type);
        final TextInputEditText description = (TextInputEditText) findViewById(R.id.set_task_description);
        final TextInputEditText username = (TextInputEditText) findViewById(R.id.set_task_username);
        Button back = (Button) findViewById(R.id.set_task_back);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.set_task_btn);
        String[] types = new String[user.getTypes().size()];
        for (int i = 0; i < types.length; i++) {
            types[i] = user.getTypes().get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, types);
        typename.setAdapter(adapter);

        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        taskDate.setText(view.getYear() + "-" + view.getMonth() + "-" + view.getDayOfMonth());
                    }
                }, 2019, 2, 30);
                dialog.show();
            }
        });

        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        taskTime.setText(view.getHour() + ":" + view.getMinute());
                    }
                }, 23, 59, true);
                dialog.show();
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
                if (name.getText().toString().trim().isEmpty()) {
                    name.setError("Task name can't stay empty");
                }else {
                    name.setError(null);
                }
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
                if (username.getText().toString().trim().isEmpty()) {
                    username.setError("you must enter username to define task.");
                } else{
                    username.setError(null);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetOthersTaskActivity.this, TasksActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().trim().isEmpty() && !username.getText().toString().trim().isEmpty()) {
                    Date date;
                    try {
                        String[] dateString = taskDate.getText().toString().split("-");
                        String[] str = taskTime.getText().toString().split(":");
                        date = new Date(Integer.valueOf(dateString[0]), Integer.valueOf(dateString[1]), Integer.valueOf(dateString[2]),
                                Integer.valueOf(str[0]), Integer.valueOf(str[1]), 0);
                        Log.d("date", date.toString());
                    } catch (Exception e) {
                        date = null;
                    }
                    String descrip = description.getText().toString();
                    if (description.getText().toString().trim().isEmpty())
                        descrip = null;
                    int pr = 0;
                    for (int i = 0; i < user.getTypes().size(); i++) {
                        if (user.getTypes().get(i).getName().equals(typename.getSelectedItem().toString()))
                            pr = user.getTypes().get(i).getPriority();
                    }
                    Task task = new Task(name.getText().toString(), date, new Type(typename.getSelectedItem().toString(), pr), descrip);
                    try {
                        String userType = new ServerHandler().getType(username.getText().toString());
                        if (userType != null) {
                            new ServerHandler().createTask(username.getText().toString(), userType, task);
                            new ServerHandler().defineType(username.getText().toString(), userType, task.getType());
                        } else {
                            Toast.makeText(context, "Not a vlid username", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(SetOthersTaskActivity.this, TasksActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });

    }
}

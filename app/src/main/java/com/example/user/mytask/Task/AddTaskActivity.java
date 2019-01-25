
package com.example.user.mytask.Task;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.example.user.mytask.R;
import com.example.user.mytask.ServerHandler.Task;
import com.example.user.mytask.ServerHandler.Type;
import com.example.user.mytask.ServerHandler.User;
import com.example.user.mytask.TasksActivity;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    final Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_layout);
        final User user = (User) getIntent().getSerializableExtra("user");
        final EditText name = (EditText) findViewById(R.id.create_task_name);
        final EditText taskDate = (EditText)findViewById(R.id.create_task_date);
        final EditText taskTime = (EditText)findViewById(R.id.create_task_time);
        final Spinner typename = (Spinner) findViewById(R.id.create_task_type);
        final EditText description = (EditText) findViewById(R.id.create_task_description);
        Button back = (Button)findViewById(R.id.create_task_back);
        FloatingActionButton add = (FloatingActionButton)findViewById(R.id.create_task_btn);
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
                } else{
                    name.setError(null);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this,TasksActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().trim().isEmpty()){
                    Date date;
                    try {
                        String[] dateString = taskDate.getText().toString().split("-");
                        String[] str = taskTime.getText().toString().split(":");
                        date = new Date(Integer.valueOf(dateString[0]),Integer.valueOf(dateString[1]),Integer.valueOf(dateString[2]),
                                Integer.valueOf(str[0]),Integer.valueOf(str[1]),0);
                        Log.d("date",date.toString());
                    }catch (Exception e){
                        date = null;
                    }
                    String descrip = description.getText().toString();
                    if(description.getText().toString().trim().isEmpty())
                        descrip = null;
                    int pr = 0;
                    for (int i = 0; i < user.getTypes().size(); i++) {
                        if(user.getTypes().get(i).getName().equals(typename.getSelectedItem().toString()))
                            pr = user.getTypes().get(i).getPriority();
                    }
                    Task task = new Task(name.getText().toString(),date,new Type(typename.getSelectedItem().toString(),pr),descrip);
                    user.addTask(task);
                    // new ServerHandler().createTask(user.getUserName(), user.getUserType(), task);
                    Intent intent = new Intent(AddTaskActivity.this,TasksActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
    }



}

package com.example.user.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mytask.ServerHandler.ServerHandler;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task_layout);
        final User user = (User) getIntent().getSerializableExtra("user");
        final EditText name = (EditText) findViewById(R.id.create_task_name);
        final EditText year = (EditText) findViewById(R.id.create_task_year);
        final EditText month = (EditText) findViewById(R.id.create_task_month);
        final EditText day = (EditText) findViewById(R.id.create_task_day);
        final EditText hour = (EditText) findViewById(R.id.create_task_hour);
        final EditText min = (EditText) findViewById(R.id.create_task_min);
        final EditText sec = (EditText) findViewById(R.id.create_task_sec);
        final EditText typename = (EditText) findViewById(R.id.create_task_type);
        final EditText description = (EditText) findViewById(R.id.create_task_description);
        Button back = (Button)findViewById(R.id.create_task_back);
        FloatingActionButton add = (FloatingActionButton)findViewById(R.id.create_task_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, TasksActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().trim().isEmpty() && !typename.getText().toString().trim().isEmpty()){
                    boolean find = false;
                    for (int i = 0; i < user.getTypes().size(); i++) {
                        if(user.getTypes().get(i).getName().equals(typename.getText().toString())){
                            find = true;
                            break;
                        }
                    }
                    if(!find){
                        Toast.makeText(getApplicationContext(),"Type name not set for this user.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Date date;
                    try {
                        date = new Date(Integer.valueOf(year.getText().toString()), Integer.valueOf(month.getText().toString()), Integer.valueOf(day.getText().toString()), Integer.valueOf(hour.getText().toString()), Integer.valueOf(min.getText().toString()), Integer.valueOf(sec.getText().toString()));
                    }catch (Exception e){
                        date = null;
                    }
                    String descrip = description.getText().toString();
                    if(description.getText().toString().trim().isEmpty())
                        descrip = null;
                    int pr = 0;
                    for (int i = 0; i < user.getTypes().size(); i++) {
                        if(user.getTypes().get(i).getName().equals(typename.getText().toString()))
                            pr = user.getTypes().get(i).getPriority();
                    }
                    Task task = new Task(name.getText().toString(),date,new Type(typename.getText().toString(),pr),descrip);
                    user.addTask(task);
                    new ServerHandler().createTask(user.getUserName(), user.getUserType(), task);

                }
            }
        });
    }



}

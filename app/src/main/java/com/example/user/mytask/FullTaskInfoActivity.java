package com.example.user.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FullTaskInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_task_layout);

        Task task = (Task) getIntent().getSerializableExtra("task");
        final User user = (User) getIntent().getSerializableExtra("user");
        TextView name = (TextView)findViewById(R.id.full_task_name);
        TextView year = (TextView)findViewById(R.id.full_task_year);
        TextView month = (TextView)findViewById(R.id.full_task_month);
        TextView day = (TextView)findViewById(R.id.full_task_day);
        TextView hour = (TextView)findViewById(R.id.full_task_hour);
        TextView min = (TextView)findViewById(R.id.full_task_min);
        TextView sec = (TextView)findViewById(R.id.full_task_sec);
        TextView typename = (TextView)findViewById(R.id.full_task_type);
        TextView description = (TextView)findViewById(R.id.full_task_description);
        Button back = (Button)findViewById(R.id.full_task_back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullTaskInfoActivity.this, TasksActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        name.setText(task.getName());
        typename.setText(task.getType().getName());
        typename.setText(task.getType().getName());
        if(task.getDeadline() != null){
            year.setText(task.getDeadline().getYear());
            month.setText(task.getDeadline().getMonth());
            day.setText(task.getDeadline().getDay());
            hour.setText(task.getDeadline().getHours());
            min.setText(task.getDeadline().getMinutes());
            sec.setText(task.getDeadline().getSeconds());
        } else{
            year.setText("Not Set");
            month.setText("Not Set");
            day.setText("Not Set");
            hour.setText("Not Set");
            min.setText("Not Set");
            sec.setText("Not Set");
        }

        if(description != null){
            description.setText(task.getDescription());
        } else{
            description.setText("No Description.");
        }

    }
}

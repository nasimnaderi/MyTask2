package com.example.user.mytask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mytask.ServerHandler.ServerHandler;
import com.example.user.mytask.ServerHandler.Type;
import com.example.user.mytask.ServerHandler.User;
import com.example.user.mytask.Task.TaskAdapter;

import java.io.IOException;

public class TasksActivity extends AppCompatActivity {
    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_task_layout);
        final User user = (User) getIntent().getSerializableExtra("user");
        final TaskAdapter taskAdapter = new TaskAdapter(user);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.user_tasks_recycler_view);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button define = (Button)findViewById(R.id.define_type_btn);
        Button set = (Button)findViewById(R.id.set_type_btn);
        Button addTask = (Button)findViewById(R.id.user_add_task);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this, AddTaskActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });



        if(user.getUserType().equals("Regular")) {
            define.setVisibility(View.GONE);
            set.setVisibility(View.GONE);

        } else if(user.getUserType() == "Silver"){
            set.setVisibility(View.GONE);
            define.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View defineView = li.inflate(R.layout.define_task_dialogue, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final EditText defTypeName = (EditText)findViewById(R.id.define_task_name);
                    final EditText defTypePriority = (EditText)findViewById(R.id.define_task_priority);

                    builder.setView(defineView);
                    builder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {

                                            new ServerHandler().defineTask(user.getUserName(),user.getUserType(),new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
                                            user.addType(new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
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
        } else {
            define.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View defineView = li.inflate(R.layout.define_task_dialogue, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final EditText defTypeName = (EditText)findViewById(R.id.define_task_name);
                    final EditText defTypePriority = (EditText)findViewById(R.id.define_task_priority);

                    builder.setView(defineView);
                    builder
                            .setCancelable(false)
                            .setPositiveButton("Define",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            new ServerHandler().defineTask(user.getUserName(),user.getUserType(),new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
                                            user.addType(new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
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

            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View defineView = li.inflate(R.layout.defin_task_lother_ayout, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final EditText setTypeName = (EditText)findViewById(R.id.set_type_task_name);
                    final EditText setTypePriority = (EditText)findViewById(R.id.set_type_priority);
                    final EditText userName = (EditText)findViewById(R.id.set_type_username);

                    builder.setView(defineView);
                    builder
                            .setCancelable(false)
                            .setPositiveButton("Set",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            try {
                                                String type = new ServerHandler().getType(userName.getText().toString());
                                                if(type.equals("")){
                                                    Toast.makeText(getApplicationContext(),"No user with this username",Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                new ServerHandler().defineTask(userName.getText().toString(),type,new Type(setTypeName.getText().toString(),Integer.valueOf(setTypePriority.getText().toString())));
                                            } catch (IOException e) {
                                                e.printStackTrace();
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
}


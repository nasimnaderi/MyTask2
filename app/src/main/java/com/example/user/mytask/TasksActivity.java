package com.example.user.mytask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mytask.ServerHandler.ServerHandler;
import com.example.user.mytask.ServerHandler.Type;
import com.example.user.mytask.ServerHandler.User;
import com.example.user.mytask.Task.AddTaskActivity;
import com.example.user.mytask.Task.CustomItemClickListener;
import com.example.user.mytask.Task.FullTaskInfoActivity;
import com.example.user.mytask.Task.TaskAdapter;

import java.io.IOException;

public class TasksActivity extends AppCompatActivity {
    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tasks_layout);
        final User user = (User) getIntent().getSerializableExtra("user");
        Log.d("USER", "User:" + user.getName());
        final TaskAdapter taskAdapter = new TaskAdapter(new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(context,"CLICKED :(((((.",Toast.LENGTH_LONG);

                Intent intent = new Intent(TasksActivity.this,FullTaskInfoActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("task",user.getTasks().get(position));
                startActivity(intent);
            }
        },user,this);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.user_tasks_recycler_view);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final Button define = (Button)findViewById(R.id.define_type_btn);
        Button setType = (Button)findViewById(R.id.set_type_btn);
        Button setTask = (Button)findViewById(R.id.set_task_btn);
        FloatingActionButton addTask = (FloatingActionButton) findViewById(R.id.user_add_task);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this,AddTaskActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });



        if(user.getUserType().equals("Regular")) {
            define.setVisibility(View.GONE);
            setType.setVisibility(View.GONE);
            setTask.setVisibility(View.GONE);

        } else if(user.getUserType() == "Silver"){
            setType.setVisibility(View.GONE);
            setTask.setVisibility(View.GONE);
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

                                            // new ServerHandler().defineType(user.getUserName(),user.getUserType(),new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
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
                    final EditText defTypeName = (EditText)defineView.findViewById(R.id.define_task_name);
                    final EditText defTypePriority = (EditText)defineView.findViewById(R.id.define_task_priority);

                    builder.setView(defineView);
                    builder
                            .setCancelable(false)
                            .setPositiveButton("Define",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // new ServerHandler().defineType(user.getUserName(),user.getUserType(),new Type(defTypeName.getText().toString(),Integer.valueOf(defTypePriority.getText().toString())));
                                            try {
                                                user.addType(new Type(defTypeName.getText().toString(), Integer.valueOf(defTypePriority.getText().toString())));
                                            }catch (Exception e){
                                                Toast.makeText(context,"Error while enter type to user.",Toast.LENGTH_SHORT);
                                                Log.d("type",defTypeName.getText().toString());
                                                Log.d("pr",defTypePriority.getText().toString());
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

            setType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View defineView = li.inflate(R.layout.defin_type_others_layout, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final EditText setTypeName = (EditText)defineView.findViewById(R.id.set_type_task_name);
                    final EditText setTypePriority = (EditText)defineView.findViewById(R.id.set_type_priority);
                    final EditText userName = (EditText)defineView.findViewById(R.id.set_type_username);

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
                                                new ServerHandler().defineType(userName.getText().toString(),type,new Type(setTypeName.getText().toString(),Integer.valueOf(setTypePriority.getText().toString())));
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

            setTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TasksActivity.this, SetOthersTaskActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(TasksActivity.this,MainActivity.class);
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user",null);
                editor.putString("password",null);
                editor.apply();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

package com.example.user.mytask.Task;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.mytask.R;
import com.example.user.mytask.ServerHandler.Task;
import com.example.user.mytask.ServerHandler.User;

import java.util.Vector;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private  CustomItemClickListener listener;
    private Vector<Task> tasks;
    private User user;
    private Context context;

    public Context getContext() {
        return context;
    }

    public CustomItemClickListener getListener() {
        return listener;
    }

    public void setListener(CustomItemClickListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Vector<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Vector<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskAdapter(User user,Context context) {
        this.tasks = user.getTasks();
        this.user = user;
        this.context = context;
        sort();
    }

    public TaskAdapter(CustomItemClickListener listener, User user, Context context) {
        this.listener = listener;
        this.tasks = user.getTasks();
        this.user = user;
        this.context = context;
        sort();
    }

    public void sort(){
        for (int i = 0; i < tasks.size(); i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if(tasks.get(j).getType().getPriority() > tasks.get(i).getType().getPriority()){

                    Task temp = tasks.get(i);
                    tasks.set(i,tasks.get(j));
                    tasks.set(j,temp);

                } else if(tasks.get(j).getType().getPriority() == tasks.get(i).getType().getPriority()
                        &&  tasks.get(j).getDeadline() != null &&  tasks.get(i).getDeadline() != null
                        && tasks.get(j).getDeadline().getTime() < tasks.get(i).getDeadline().getTime()){

                    Task temp = tasks.get(i);
                    tasks.set(i,tasks.get(j));
                    tasks.set(j,temp);

                }
            }
        }
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView taskName;
        private TextView taskType;
        private FloatingActionButton actionButton;




        public TextView getTaskName() {
            return taskName;
        }

        public void setTaskName(TextView taskName) {
            this.taskName = taskName;
        }

        public TextView getTaskType() {
            return taskType;
        }

        public void setTaskType(TextView taskType) {
            this.taskType = taskType;
        }

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.simple_task_name);
            taskType = (TextView) itemView.findViewById(R.id.simple_task_type);
            actionButton = (FloatingActionButton) itemView.findViewById(R.id.simple_task_check);
            actionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tasks.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context1 = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context1);
        View taskView = inflater.inflate(R.layout.simple_task_layout, viewGroup, false);
        final TaskHolder viewHolder = new TaskHolder(taskView);
        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskHolder taskHolder, final int i) {
        Task task = tasks.get(i);
        TextView taskName = taskHolder.getTaskName();
        TextView taskType = taskHolder.getTaskType();
        taskName.setText(task.getName());
        taskType.setText(task.getType().getName());

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

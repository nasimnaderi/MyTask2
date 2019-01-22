

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.mytask.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public class TaskHolder extends RecyclerView.ViewHolder{

        public TextView taskName;
        public TextView taskType;

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

        public TaskHolder(@NonNull View itemView){
            super(itemView);
            taskName = (TextView)itemView.findViewById(R.id.simple_task_name);
            taskType = (TextView)itemView.findViewById(R.id.simple_task_type);

        }
    }
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskView = inflater.inflate(R.layout.simple_task_layout,viewGroup,false);
        TaskHolder viewHolder = new TaskHolder(taskView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
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

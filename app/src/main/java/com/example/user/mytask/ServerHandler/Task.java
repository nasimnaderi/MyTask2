package com.example.user.mytask.ServerHandler;

import java.io.Serializable;
import java.util.Date;

;

public class Task implements Serializable {
    String name;
    Date deadline;
    Type type;
    String description;

    public Task(String name, Date deadline, Type type, String description) {
        this.name = name;
        this.deadline = deadline;
        this.type = type;
        this.description = description;
    }

    public Task(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean equals(Task task) {
        if(name.equals(task.getName()) && deadline.getTime() == task.getDeadline().getTime() &&
                type.equals(task.getType()) && description.equals(task.getDescription())){
            return true;
        } else{
            return false;
        }
    }
}

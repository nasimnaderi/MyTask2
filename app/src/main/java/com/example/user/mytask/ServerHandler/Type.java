package com.example.soroush.taskmanager.ServerHandler;

import java.io.Serializable;

public class Type implements Serializable {
    String name;
    int priority;

    public Type(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean equals(Type type){
        if(name.equals(type.getName()) && priority == type.getPriority()){
            return true;
        } else{
            return false;
        }

    }
}

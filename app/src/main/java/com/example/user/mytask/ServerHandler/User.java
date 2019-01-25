package com.example.soroush.taskmanager.ServerHandler;

import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable {
    String userName;
    String email;
    String name;
    String family;
    String password;
    String userType;
    Vector<com.example.soroush.taskmanager.ServerHandler.Type> types;
    Vector<com.example.soroush.taskmanager.ServerHandler.Task> tasks;

    public User(String userName, String email, String name, String family, String password, String userType, Vector<com.example.soroush.taskmanager.ServerHandler.Type> types, Vector<com.example.soroush.taskmanager.ServerHandler.Task> tasks) {
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.family = family;
        this.password = password;
        this.userType = userType;
        this.types = types;
        this.tasks = tasks;
    }

    public User(String userName, String email, String name, String family, String password, String userType) {
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.family = family;
        this.password = password;
        this.userType = userType;
        tasks = new Vector<>();
        types = new Vector<>();
        types.add(new com.example.soroush.taskmanager.ServerHandler.Type("Low",0));
        types.add(new com.example.soroush.taskmanager.ServerHandler.Type("Average",50));
        types.add(new com.example.soroush.taskmanager.ServerHandler.Type("High",100));
    }

    public User() {
    }

    public void addType(com.example.soroush.taskmanager.ServerHandler.Type type){
        types.add(type);
    }

    public void addTask(com.example.soroush.taskmanager.ServerHandler.Task task){
        tasks.add(task);
    }

    public void deleteTask(com.example.soroush.taskmanager.ServerHandler.Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).equals(task)){
                tasks.remove(i);
                return;
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Vector<com.example.soroush.taskmanager.ServerHandler.Type> getTypes() {
        return types;
    }

    public void setTypes(Vector<com.example.soroush.taskmanager.ServerHandler.Type> types) {
        this.types = types;
    }

    public Vector<com.example.soroush.taskmanager.ServerHandler.Task> getTasks() {
        return tasks;
    }

    public void setTasks(Vector<com.example.soroush.taskmanager.ServerHandler.Task> tasks) {
        this.tasks = tasks;
    }
}

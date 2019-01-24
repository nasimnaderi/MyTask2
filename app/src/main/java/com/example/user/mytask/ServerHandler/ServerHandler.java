package com.example.user.mytask.ServerHandler;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class ServerHandler {
    private Socket connection;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public String getType(String username) throws IOException {
        String string = "GET TYPE " + username;
        dataOutputStream.writeBytes(string);
        byte[] ans = new byte[16];
        int count = dataInputStream.read(ans);
        if(new String(ans,0,count).equals("NOK"))
            return "";
        else
            return new String(ans,0,count);
    }

    public ServerHandler() {
        try {
            InetAddress host = InetAddress.getByName("127.0.0.1");
            this.connection = new Socket(host,55555);
            dataInputStream = new DataInputStream(connection.getInputStream());
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            Log.d("Begin Handler", "IO ERROR in Constructor!");
        }

    }



    public boolean createUser(User user){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE USER " + user.getUserName() + " " + user.getEmail() + " " + user.getName() + " " + user.getFamily() + " " + user.getPassword() + " " + user.getUserType());
        boolean done = false;
        try {
            dataOutputStream.writeBytes(stringBuilder.toString());
            byte[] temp = new byte[1000];
            int count = dataInputStream.read(temp);
            String string = new String(temp,0,count);
            if(string.equals("OK"))
                done = true;
            else
                done = false;
        } catch (IOException e) {
            Log.d("CREATE USER", "Unable to send CREATE USER!");
        }
        return done;
    }



    public User login(String user, String password){
        String string = "LOGIN" + " " + user + " " + password;
        User userData = new User();
        byte[] temp = new byte[1000];
        try {
            dataOutputStream.writeBytes(string);
            int count = dataInputStream.read(temp);
            string = new String(temp,0,count);
            if(string.equals("null"))
                return null;
            String[] str = string.split(" ");
            userData = new User(str[0], str[1], str[2], str[3], str[4], str[5]);
            count = dataInputStream.read(temp);
            Log.d("Type",new String(temp,0,count));
            while (true){
                count = dataInputStream.read(temp);
                string = new String(temp,0,count);
                if(string.equals("endTypes"))
                    break;
                str = string.split(" ");
                Type type = new Type(str[0], Integer.valueOf(str[1]));
                userData.addType(type);
            }
            count = dataInputStream.read(temp);
            Log.d("Task",new String(temp,0,count));
            while (true){
                count = dataInputStream.read(temp);
                string = new String(temp,0,count);
                if(string.equals("endTasks"))
                    break;
                str = string.split(" ");
                StringBuilder builder = new StringBuilder();
                for (int i = 5; i < str.length; i++) {
                    builder.append(str[i]);
                    if(i != str.length - 1)
                        builder.append(" ");
                }
                Task task = new Task(str[0], new Date(Long.valueOf(str[1])),new Type(str[2], Integer.valueOf(str[3])), builder.toString());
                userData.addTask(task);

            }
        } catch (IOException e) {
            Log.e("LOGIN", "Login Messages Failure!");
        }
        return userData;
    }


    public boolean createTask(String user, String userType, Task task){
        String string = "CREATE TASK " + user + " " + userType + " " + task.getName() +
                " " + task.getDeadline().getTime() + " " + task.getType().getName() + task.getType().getPriority() +
                " " + task.getDescription();
        boolean done = false;
        try {
            dataOutputStream.writeBytes(string);
            byte[] temp = new byte[1000];
            int count = dataInputStream.read(temp);
            String answer = new String(temp,0,count);
            if(answer.equals("OK"))
                done = true;
            else
                done = false;
        } catch (IOException e) {
            Log.e("CREATE TASK","CREATE TASK FAILED!");
        }
        return done;
    }



    public boolean deleteTask(String user, String userType, Task task){
        String string = "DELETE TASK " + user + " " + userType + " " + task.getName() +
                " " + task.getDeadline().getTime() + " " + task.getType().getName() + task.getType().getPriority() +
                " " + task.getDescription();
        boolean done = false;
        try {
            dataOutputStream.writeBytes(string);
            byte[] temp = new byte[1000];
            int count = dataInputStream.read(temp);
            String answer = new String(temp,0,count);
            if(answer.equals("OK"))
                done = true;
            else
                done = false;
        } catch (IOException e) {
            Log.e("CREATE TASK","CREATE TASK FAILED!");
        }
        return done;
    }

    public boolean defineTask(String user, String userType, Type type){
        String string = "DEFINE TASK " + user + " " + type.getName() + " " + type.getPriority();
        boolean done = false;
        try {
            dataOutputStream.writeBytes(string);
            byte[] temp = new byte[1000];
            int count = dataInputStream.read(temp);
            String answer = new String(temp,0,count);
            if(answer.equals("OK"))
                done = true;
            else
                done = false;
        } catch (IOException e) {
            Log.e("DEFINE TASK", "Unable to define task!");
        }
        return done;
    }






}

package com.ibao.alanger.worktime.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User {
    private int id;
    private String user;
    private String name;
    private String password;
    private String token;


    public String toString(){
        Gson gson = new Gson();

        return gson.toJson(
                this,
                new TypeToken<User>() {}.getType());
    }
    public User() {

        user="";
        name="";
        password="";
        token="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

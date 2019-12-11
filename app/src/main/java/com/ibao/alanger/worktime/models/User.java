package com.ibao.alanger.worktime.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String user;
    private String name;
    private String password;
    private int idSupervisor;
    private String fundos;
    private List<Integer> idFundoList;
    private boolean permisoManual;

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
        fundos="";
        idFundoList = new ArrayList<>();
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

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getFundos() {
        return fundos;
    }

    public void setFundos(String fundos) {
        this.fundos = fundos;
        idFundoList = new ArrayList<>();
        String[] temp = fundos.split(",");
        for(String idF : temp){
            idFundoList.add(Integer.valueOf(idF));
        }
    }

    public List<Integer> getIdFundoList() {
        return idFundoList;
    }

    public boolean isPermisoManual() {
        return permisoManual;
    }

    public void setPermisoManual(boolean permisoManual) {
        this.permisoManual = permisoManual;
    }
}

package com.ibao.alanger.worktime.models.VO.external;

public class Cultivo {

    private int id;
    private String cod;
    private String name;


    public Cultivo() {
        this.id=0;
        this.cod="";
        this.name="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
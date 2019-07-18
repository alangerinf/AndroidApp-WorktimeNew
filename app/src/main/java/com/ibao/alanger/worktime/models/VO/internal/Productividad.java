package com.ibao.alanger.worktime.models.VO.internal;

public class Productividad {

    private int id;
    private int value;
    private String dateTime;
    private int idTareoTrabajador;


    public Productividad(){
        this.id=0;
        this.value=0;
        this.dateTime="";
        this.idTareoTrabajador=0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getIdTareoTrabajador() {
        return idTareoTrabajador;
    }

    public void setIdTareoTrabajador(int idTareoTrabajador) {
        this.idTareoTrabajador = idTareoTrabajador;
    }
}

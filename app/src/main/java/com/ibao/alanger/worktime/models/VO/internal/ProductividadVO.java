package com.ibao.alanger.worktime.models.VO.internal;

import java.io.Serializable;

public class ProductividadVO implements Serializable {

    private int id;
    private float value;
    private String dateTime;
    private int idTareoDetalle;

    public ProductividadVO(){
        this.id=0;
        this.value=0;
        this.dateTime="";
        this.idTareoDetalle =0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getIdTareoDetalle() {
        return idTareoDetalle;
    }

    public void setIdTareoDetalle(int idTareoDetalle) {
        this.idTareoDetalle = idTareoDetalle;
    }
}

package com.ibao.alanger.worktime.models.VO.external;

import java.io.Serializable;

public class SalidaVO implements Serializable {

    private int id;
    private String cod;
    private String name;
    private boolean status;


    public SalidaVO() {
        this.id=0;
        this.cod="";
        this.name="";
        this.status=true;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

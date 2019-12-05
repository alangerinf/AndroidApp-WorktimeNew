package com.ibao.alanger.worktime.models.VO.external;

import java.io.Serializable;

public class TrabajadorVO implements Serializable {

    private String dni;
    private String cod;
    private String name;

    public TrabajadorVO(){
        this.cod="";
        this.dni="";
        this.name="";
    }


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

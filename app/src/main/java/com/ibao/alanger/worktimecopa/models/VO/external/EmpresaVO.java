package com.ibao.alanger.worktimecopa.models.VO.external;

import java.io.Serializable;

public class EmpresaVO implements Serializable {

    private int id;
    private String cod;
    private String razon;
    private String ruc;
    private String name;
    private boolean status;

    public EmpresaVO(){
        this.id=0;
        this.name="";
        this.status=true;
        this.razon="";
        this.ruc="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

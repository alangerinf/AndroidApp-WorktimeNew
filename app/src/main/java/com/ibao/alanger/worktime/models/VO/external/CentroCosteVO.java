package com.ibao.alanger.worktime.models.VO.external;

public class CentroCosteVO {

    private int id;
    private String cod;
    private String name;
    private boolean status;
    private int idEmpresa;


    public CentroCosteVO(){
        this.id=0;
        this.cod="";
        this.name="";
        this.status=true;
        this.idEmpresa =0;
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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

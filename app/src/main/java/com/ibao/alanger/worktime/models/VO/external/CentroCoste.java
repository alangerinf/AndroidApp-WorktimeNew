package com.ibao.alanger.worktime.models.VO.external;

public class CentroCoste {

    private int id;
    private String cod;
    private String desc;
    private int idEmpresa;


    public CentroCoste(){
        this.id=0;
        this.cod="";
        this.desc="";
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}

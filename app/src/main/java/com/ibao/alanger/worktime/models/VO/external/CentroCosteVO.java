package com.ibao.alanger.worktime.models.VO.external;

public class CentroCosteVO {

    private int id;
    private String cod;
    private String name;
    private String desc;
    private int idEmpresa;


    public CentroCosteVO(){
        this.id=0;
        this.cod="";
        this.name="";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.ibao.alanger.worktime.models.VO.external;

public class ModuloVO {
    private int id;
    private String cod;
    private String name;
    private int idEmpresa;

    public ModuloVO(){
        this.id=0;
        this.cod="";
        this.name="";
        this.idEmpresa =0;
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}

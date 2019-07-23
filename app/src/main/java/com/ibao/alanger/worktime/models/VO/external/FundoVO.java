package com.ibao.alanger.worktime.models.VO.external;

public class FundoVO {
    private int id;
    private String name;
    private int idEmpresa;

    public FundoVO(){
        this.id=0;
        this.name="";
        this.idEmpresa=0;
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

}

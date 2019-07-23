package com.ibao.alanger.worktime.models.VO.external;

public class EmpresaVO {

    private int id;
    private String name;

    public EmpresaVO(){
        this.id=0;
        this.name="";
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


}

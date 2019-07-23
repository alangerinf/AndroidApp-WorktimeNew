package com.ibao.alanger.worktime.models.VO.external;

public class TrabajadorVO {

    private int id;
    private String cod;
    private String dni;
    private String name;
    private int idEmpresa;

    public TrabajadorVO(){
        this.id=0;
        this.cod="";
        this.dni="";
        this.name="";
        this.idEmpresa=0;
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


    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}

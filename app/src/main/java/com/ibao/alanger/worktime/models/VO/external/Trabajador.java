package com.ibao.alanger.worktime.models.VO.external;

public class Trabajador {

    private int id;
    private String cod;
    private String dni;
    private String name;
    private String apPat;
    private String apMat;
    private String cargo;
    private int idEmpresa;

    public Trabajador(){
        this.id=0;
        this.cod="";
        this.dni="";
        this.name="";
        this.apPat="";
        this.apMat="";
        this.cargo="";
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

    public String getApPat() {
        return apPat;
    }

    public void setApPat(String apPat) {
        this.apPat = apPat;
    }

    public String getApMat() {
        return apMat;
    }

    public void setApMat(String apMat) {
        this.apMat = apMat;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}

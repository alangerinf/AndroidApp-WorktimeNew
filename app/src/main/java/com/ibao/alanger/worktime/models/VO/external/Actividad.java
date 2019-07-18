package com.ibao.alanger.worktime.models.VO.external;


public class Actividad {

    private int id;
    private String cod;
    private String name;
    private String magnitud;//jabas por ejemplo
    private boolean isDirecto;//si es una actividad
    private float theoricalCost;
    private float theoricalHours;
    private boolean isTarea;
    private boolean isAsistencia;
    private int idAgrupadorActividad;

    public Actividad(){
        this.id=0;
        this.cod="";
        this.name="";
        this.isDirecto=false;
        this.theoricalCost=0.0f;
        this.theoricalHours=0.0f;
        this.isTarea=false;
        this.isAsistencia=false;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirecto() {
        return isDirecto;
    }

    public void setDirecto(boolean directo) {
        isDirecto = directo;
    }

    public float getTheoricalCost() {
        return theoricalCost;
    }

    public void setTheoricalCost(float theoricalCost) {
        this.theoricalCost = theoricalCost;
    }

    public float getTheoricalHours() {
        return theoricalHours;
    }

    public void setTheoricalHours(float theoricalHours) {
        this.theoricalHours = theoricalHours;
    }

    public boolean isTarea() {
        return isTarea;
    }

    public void setTarea(boolean tarea) {
        isTarea = tarea;
    }

    public boolean isAsistencia() {
        return isAsistencia;
    }

    public void setAsistencia(boolean asistencia) {
        isAsistencia = asistencia;
    }

    public int getIdAgrupadorActividad() {
        return idAgrupadorActividad;
    }

    public void setIdAgrupadorActividad(int idAgrupadorActividad) {
        this.idAgrupadorActividad = idAgrupadorActividad;
    }
}

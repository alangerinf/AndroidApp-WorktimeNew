package com.ibao.alanger.worktimecopa.models.VO.external;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LaborVO implements Serializable {

    private int id;
    private String cod;
    private String name;
    private String magnitud;//jabas por ejemplo
    private boolean isDirecto;//si es una actividad
    private float theoricalCost;
    private float theoricalHours;
    private boolean isTarea; //segun cuanto dura para tomar tiempos
    private boolean isAsistencia;
    private int idActividad;
    private List<Integer> listIdCultivos;
    private boolean status;

    public LaborVO(){
        this.id=0;
        this.cod="";
        this.name="";
        this.isDirecto=false;
        this.theoricalCost=0.0f;
        this.theoricalHours=0.0f;
        this.isTarea=false;
        this.isAsistencia=false;
        this.magnitud="";
        this.idActividad=0;
        this.listIdCultivos = new ArrayList<>();
        this.status=true;
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

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
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

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public List<Integer> getListIdCultivos() {
        return listIdCultivos;
    }

    public void setListIdCultivos(List<Integer> listIdCultivos) {
        this.listIdCultivos = listIdCultivos;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

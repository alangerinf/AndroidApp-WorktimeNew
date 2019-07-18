package com.ibao.alanger.worktime.models.VO.internal;

import com.ibao.alanger.worktime.models.VO.external.Actividad;
import com.ibao.alanger.worktime.models.VO.external.CentroCoste;
import com.ibao.alanger.worktime.models.VO.external.Cultivo;
import com.ibao.alanger.worktime.models.VO.external.Fundo;
import com.ibao.alanger.worktime.models.VO.external.Modulo;

import java.util.ArrayList;
import java.util.List;

public class Tareo {

    private int id;
    private Actividad actividad;
    private Fundo fundo;
    private Cultivo cultivo;
    private String dateTimeStart;
    private String dateTimeEnd;
    private CentroCoste centroCoste;
    private Modulo modulo;
    private int nHorasTareadas;
    private int nTrabajadores;
    private int idFundoCultivo;
    private boolean isActive;

    private List<TrabajadorTareo> trabajadorTareoList;

    public Tareo(){
        this.id=0;
        this.actividad = null;
        this.fundo = null;
        this.cultivo = null;
        this.dateTimeStart ="";
        this.dateTimeEnd ="";
        this.centroCoste=null;
        this.modulo=null;
        this.nHorasTareadas=0;
        this.nTrabajadores=0;
        this.idFundoCultivo=0;
        this.isActive=false;
        this.trabajadorTareoList=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Fundo getFundo() {
        return fundo;
    }

    public void setFundo(Fundo fundo) {
        this.fundo = fundo;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public CentroCoste getCentroCoste() {
        return centroCoste;
    }

    public void setCentroCoste(CentroCoste centroCoste) {
        this.centroCoste = centroCoste;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public int getnHorasTareadas() {
        return nHorasTareadas;
    }

    public void setnHorasTareadas(int nHorasTareadas) {
        this.nHorasTareadas = nHorasTareadas;
    }

    public int getnTrabajadores() {
        return nTrabajadores;
    }

    public void setnTrabajadores(int nTrabajadores) {
        this.nTrabajadores = nTrabajadores;
    }

    public int getIdFundoCultivo() {
        return idFundoCultivo;
    }

    public void setIdFundoCultivo(int idFundoCultivo) {
        this.idFundoCultivo = idFundoCultivo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<TrabajadorTareo> getTrabajadorTareoList() {
        return trabajadorTareoList;
    }

    public void setTrabajadorTareoList(List<TrabajadorTareo> trabajadorTareoList) {
        this.trabajadorTareoList = trabajadorTareoList;
    }
}

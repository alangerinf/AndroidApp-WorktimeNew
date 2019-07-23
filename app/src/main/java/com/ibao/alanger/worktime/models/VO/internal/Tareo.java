package com.ibao.alanger.worktime.models.VO.internal;

import com.ibao.alanger.worktime.models.VO.external.Actividad;
import com.ibao.alanger.worktime.models.VO.external.CentroCosteVO;
import com.ibao.alanger.worktime.models.VO.external.Cultivo;
import com.ibao.alanger.worktime.models.VO.external.FundoVO;
import com.ibao.alanger.worktime.models.VO.external.Modulo;

import java.util.ArrayList;
import java.util.List;

public class Tareo {

    private int id;
    private Actividad actividad;
    private FundoVO fundo;
    private Cultivo cultivo;
    private String dateTimeStart;
    private String dateTimeEnd;
    private CentroCosteVO centroCosteVO;
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
        this.centroCosteVO =null;
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

    public FundoVO getFundo() {
        return fundo;
    }

    public void setFundo(FundoVO fundo) {
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

    public CentroCosteVO getCentroCosteVO() {
        return centroCosteVO;
    }

    public void setCentroCosteVO(CentroCosteVO centroCosteVO) {
        this.centroCosteVO = centroCosteVO;
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

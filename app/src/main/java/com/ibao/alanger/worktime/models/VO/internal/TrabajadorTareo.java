package com.ibao.alanger.worktime.models.VO.internal;

import com.ibao.alanger.worktime.models.VO.external.Trabajador;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorTareo {

    private int id;
    private int idTareo;
    private Trabajador trabajador;
    private List<Productividad> productividadList;

    public TrabajadorTareo(){
        this.id=0;
        this.idTareo=0;
        this.trabajador=null;
        this.productividadList=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTareo() {
        return idTareo;
    }

    public void setIdTareo(int idTareo) {
        this.idTareo = idTareo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public List<Productividad> getProductividadList() {
        return productividadList;
    }

    public void setProductividadList(List<Productividad> productividadList) {
        this.productividadList = productividadList;
    }
}

package com.ibao.alanger.worktime.models.VO.internal;

import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorTareo {

    private int id;
    private int idTareo;
    private TrabajadorVO trabajadorVO;
    private List<Productividad> productividadList;

    public TrabajadorTareo(){
        this.id=0;
        this.idTareo=0;
        this.trabajadorVO =null;
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

    public TrabajadorVO getTrabajadorVO() {
        return trabajadorVO;
    }

    public void setTrabajadorVO(TrabajadorVO trabajadorVO) {
        this.trabajadorVO = trabajadorVO;
    }

    public List<Productividad> getProductividadList() {
        return productividadList;
    }

    public void setProductividadList(List<Productividad> productividadList) {
        this.productividadList = productividadList;
    }
}

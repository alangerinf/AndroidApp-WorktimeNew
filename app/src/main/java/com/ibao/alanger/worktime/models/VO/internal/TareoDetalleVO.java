package com.ibao.alanger.worktime.models.VO.internal;

import com.ibao.alanger.worktime.models.VO.external.SalidaVO;
import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;

import java.util.ArrayList;
import java.util.List;

public class TareoDetalleVO {

    private int id;
    private int idTareo;
    private String timeStart;
    private String timeEnd;
    private TrabajadorVO trabajadorVO;
    private List<ProductividadVO> productividadVOList;
    private SalidaVO salidaVO;
    private float productividad;
    public TareoDetalleVO(){
        this.id=0;
        this.idTareo=0;
        this.timeStart="";
        this.productividad=0f;
        this.timeEnd="";
        this.trabajadorVO =null;
        this.productividadVOList =new ArrayList<>();
        this.salidaVO=null;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public TrabajadorVO getTrabajadorVO() {
        return trabajadorVO;
    }

    public void setTrabajadorVO(TrabajadorVO trabajadorVO) {
        this.trabajadorVO = trabajadorVO;
    }

    public List<ProductividadVO> getProductividadVOList() {
        return productividadVOList;
    }

    public void setProductividadVOList(List<ProductividadVO> productividadVOList) {
        this.productividadVOList = productividadVOList;
    }

    public SalidaVO getSalidaVO() {
        return salidaVO;
    }

    public void setSalidaVO(SalidaVO salidaVO) {
        this.salidaVO = salidaVO;
    }

    public float getProductividad() {
        return productividad;
    }

    public void setProductividad(float productividad) {
        this.productividad = productividad;
    }
}

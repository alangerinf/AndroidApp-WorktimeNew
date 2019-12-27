package com.ibao.alanger.worktimecopa.models.VO.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibao.alanger.worktimecopa.models.VO.external.EmpresaVO;
import com.ibao.alanger.worktimecopa.models.VO.external.LaborVO;
import com.ibao.alanger.worktimecopa.models.VO.external.CentroCosteVO;
import com.ibao.alanger.worktimecopa.models.VO.external.CultivoVO;
import com.ibao.alanger.worktimecopa.models.VO.external.FundoVO;
import com.ibao.alanger.worktimecopa.models.VO.external.LoteVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TareoVO implements Serializable {

    private int id;
    private int idWeb;
    private LaborVO laborVO;
    private CentroCosteVO centroCosteVO;

    private LoteVO loteVO;
    private FundoVO fundoVO;
    private EmpresaVO empresaVO;

    private CultivoVO cultivoVO;
    private float productividad;
    private String dateTimeStart;
    private String dateTimeEnd;
    private float nHorasTareadas;
    private boolean isActive;
    private boolean isAsistencia;


    private List<TareoDetalleVO> tareoDetalleVOList;

    public TareoVO(){
        this.id=0;
        this.idWeb=0;
        this.laborVO = null;
        this.loteVO = null;
        this.empresaVO = null;
        this.centroCosteVO = null;
        this.fundoVO =null;
        this.cultivoVO =null;
        this.productividad=0f;
        this.dateTimeStart="";
        this.dateTimeEnd="";
        this.nHorasTareadas=0f;
        this.isActive=true;
        this.tareoDetalleVOList = new ArrayList<>();
        this.isAsistencia = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(int idWeb) {
        this.idWeb = idWeb;
    }

    public LaborVO getLaborVO() {
        return laborVO;
    }

    public void setLaborVO(LaborVO laborVO) {
        this.laborVO = laborVO;
    }

    public LoteVO getLoteVO() {
        return loteVO;
    }

    public void setLoteVO(LoteVO loteVO) {
        this.loteVO = loteVO;
    }

    public CentroCosteVO getCentroCosteVO() {
        return centroCosteVO;
    }

    public void setCentroCosteVO(CentroCosteVO centroCosteVO) {
        this.centroCosteVO = centroCosteVO;
    }

    public FundoVO getFundoVO() {
        return fundoVO;
    }

    public void setFundoVO(FundoVO fundoVO) {
        this.fundoVO = fundoVO;
    }

    public CultivoVO getCultivoVO() {
        return cultivoVO;
    }

    public void setCultivoVO(CultivoVO cultivoVO) {
        this.cultivoVO = cultivoVO;
    }

    public float getProductividad() {
        return productividad;
    }

    public void setProductividad(float productividad) {
        this.productividad = productividad;
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

    public float getnHorasTareadas() {
        return nHorasTareadas;
    }

    public void setnHorasTareadas(float nHorasTareadas) {
        this.nHorasTareadas = nHorasTareadas;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<TareoDetalleVO> getTareoDetalleVOList() {
        return tareoDetalleVOList;
    }

    public void setTareoDetalleVOList(List<TareoDetalleVO> tareoDetalleVOList) {
        this.tareoDetalleVOList = tareoDetalleVOList;
    }

    public EmpresaVO getEmpresaVO() {
        return empresaVO;
    }

    public void setEmpresaVO(EmpresaVO empresaVO) {
        this.empresaVO = empresaVO;
    }

    public boolean isAsistencia() {
        return isAsistencia;
    }

    public void setAsistencia(boolean asistencia) {
        isAsistencia = asistencia;
    }

    public String toString(){

        new Gson().toJson(
                this,
                new TypeToken<TareoVO>() {}.getType());
        return new Gson().toJson(this);
    }

}

package com.ibao.alanger.worktime.views.tareo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibao.alanger.worktime.models.VO.internal.ProductividadVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

public class TareoLiveData extends ViewModel {

    // Create a LiveData with a String
    private static MutableLiveData<TareoVO> _tareoVO;
    private static TareoVO tareoVO;

    public static LiveData<TareoVO> getTareoVO() {
        if (_tareoVO == null) {
            tareoVO = new TareoVO();
            _tareoVO = new MutableLiveData<TareoVO>();
        }
        return _tareoVO;
    }

    public static void setTareoVO(TareoVO tareo){
        tareoVO = tareo;
        _tareoVO.setValue(tareoVO);
    }

    public void addTareoDetalle(TareoDetalleVO tareoDetalleVO){
        tareoVO.getTareoDetalleVOList().add(tareoDetalleVO);
        _tareoVO.setValue(tareoVO);
    }

    public void deleteTareoDetalle(TareoDetalleVO tareoDetalleVO){
        tareoVO.getTareoDetalleVOList().remove(tareoDetalleVO);
        _tareoVO.setValue(tareoVO);
    }

    public static void init(){
        tareoVO = new TareoVO();
        _tareoVO = new MutableLiveData<TareoVO>();
    }

    public void modifyTareoDetalle(TareoDetalleVO tar) {
        for(TareoDetalleVO t : tareoVO.getTareoDetalleVOList()){
            if(t.getId()==tar.getId()){
                t.setId(tar.getId());
                t.setProductividad(tareoVO.getProductividad());
                t.setProductividadVOList(tar.getProductividadVOList());
                t.setIdTareo(tar.getIdTareo());
                t.setTrabajadorVO(tar.getTrabajadorVO());
                t.setSalidaVO(tar.getSalidaVO());
                t.setTimeStart(tar.getTimeStart());
                t.setTimeEnd(tar.getTimeEnd());
                break;
            }
        }
        _tareoVO.setValue(tareoVO);

    }
}

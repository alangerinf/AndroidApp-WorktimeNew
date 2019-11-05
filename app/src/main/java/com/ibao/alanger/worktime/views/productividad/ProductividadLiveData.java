package com.ibao.alanger.worktime.views.productividad;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibao.alanger.worktime.models.VO.internal.ProductividadVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;

public class ProductividadLiveData extends ViewModel {

    // Create a LiveData with a String
    private static MutableLiveData<TareoDetalleVO> _tareoDetalleVO;
    private static TareoDetalleVO tareoDetalleVO;

    public static LiveData<TareoDetalleVO> getTareoDetalleVO() {
        if (_tareoDetalleVO == null) {
            tareoDetalleVO = new TareoDetalleVO();
            _tareoDetalleVO = new MutableLiveData<TareoDetalleVO>();
        }
        return _tareoDetalleVO;
    }

    public static void setTareoDetalleVO(TareoDetalleVO tareoDetalle){
        tareoDetalleVO = tareoDetalle;
        _tareoDetalleVO.setValue(tareoDetalleVO);
    }

    public void addProductividad(ProductividadVO productividadVO){
        tareoDetalleVO.getProductividadVOList().add(productividadVO);
        _tareoDetalleVO.setValue(tareoDetalleVO);
    }

    public void deleteProductividad(ProductividadVO productividadVO){
        tareoDetalleVO.getProductividadVOList().remove(productividadVO);
        _tareoDetalleVO.setValue(tareoDetalleVO);
    }

    public static void init(){
        tareoDetalleVO = new TareoDetalleVO();
        _tareoDetalleVO = new MutableLiveData<TareoDetalleVO>();
    }

    public void modifyProductividad(ProductividadVO pro) {
        for(ProductividadVO p : tareoDetalleVO.getProductividadVOList()){
            if(p.getId()==pro.getId()){
                p.setId(pro.getId());
                p.setValue(pro.getValue());
                p.setDateTime(pro.getDateTime());
                p.setIdTareoDetalle(pro.getIdTareoDetalle());
                break;
            }
        }
        _tareoDetalleVO.setValue(tareoDetalleVO);

    }
}

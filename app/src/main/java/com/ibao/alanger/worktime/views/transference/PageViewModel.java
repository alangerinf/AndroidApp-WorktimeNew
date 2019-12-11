package com.ibao.alanger.worktime.views.transference;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private static MutableLiveData<List<TareoDetalleVO>> listMutableLiveData;
    private LiveData<List<TareoDetalleVO>> listLiveData = Transformations.map(listMutableLiveData, input -> input);

    public static void set(List<TareoDetalleVO> index) {
        listMutableLiveData.setValue(index);
    }
    public static void addTrabajador(int index,TareoDetalleVO tareoDetalleVO){
        listMutableLiveData.getValue().add(index,tareoDetalleVO);
        listMutableLiveData.setValue(listMutableLiveData.getValue());
    }

    public static void addTrabajador(TareoDetalleVO tareoDetalleVO){
        listMutableLiveData.getValue().add(tareoDetalleVO);
        listMutableLiveData.setValue(listMutableLiveData.getValue());
    }
    public static void removeTrabajador(TareoDetalleVO tareoDetalleVO){

        listMutableLiveData.getValue().remove(tareoDetalleVO);
        listMutableLiveData.setValue(listMutableLiveData.getValue());
    }
    public static TareoDetalleVO removeTrabajador(int i){
        TareoDetalleVO tareoDetalleVO = listMutableLiveData.getValue().remove(i);
        listMutableLiveData.setValue(listMutableLiveData.getValue());
        return tareoDetalleVO;
    }

    public LiveData<List<TareoDetalleVO>> get() {
        return listLiveData;
    }

    public static List<TareoDetalleVO> getMutable(){

        return  listMutableLiveData.getValue();
    }


    public static void init(){
        listMutableLiveData = new MutableLiveData<>();
    }

}
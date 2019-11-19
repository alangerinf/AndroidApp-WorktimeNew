package com.ibao.alanger.worktime.views.transference.helpers;

import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.List;

public class VerifyPersonal {


    public static boolean verify(TareoVO tareoVO, List<TareoDetalleVO> list,String mode,String DNI,String hour) throws Exception
    {
        boolean flag = true;

        if(DNI.length() != 8){
            new NullPointerException("DNI no válido");
        }
        if(tareoVO == null){
            new NullPointerException("Tareo es nulo");
        }
        if(list == null){
            new NullPointerException("lista es nula");
        }
        if(!mode.equals(TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR) && !mode.equals(TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR)){
            new NullPointerException("modo no válido");
        }
        if (hour.isEmpty()){
            new NullPointerException("Hour no válido");
        }

        switch (mode){//verificar todos los pusibles casos negativos
            case TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR:

                //todo; verificar si ya esta en el tareo para ver si ya tiene hora de salida
                TareoDetalleVO temp1  = getTareoDetalleFormTareo(tareoVO.getTareoDetalleVOList(),DNI);
                if(temp1!=null){ // verificar si ya existe en la lista del tareo
                    flag = false;
                    break;
                }

                //todo: verificar si ya esta en la lista final
                //verificar si ya esta en la lista actual
                if(getTareoDetalleFormTareo(list,DNI)!=null){
                    flag = false;
                    break;
                }
                //todo: verificar si no esta en otras labores
                //falta


                break;
            case TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR:

                //todo; verificar si ya esta en el tareo para ver si ya tiene hora de salida
                TareoDetalleVO temp  = getTareoDetalleFormTareo(tareoVO.getTareoDetalleVOList(),DNI);
                if(temp==null){ // verificar que sea distinto es nulo
                    flag = false;
                    break;
                }else {// si ha sido encontrado , verificamos que si ya tiene hora de salida
                    if(! temp.getTimeEnd().isEmpty()){ // si ya tiene hora de salida
                        flag = false;
                        break;
                    }
                    //caso contrario es true
                }
                //todo: verificar si ya esta en la lista final
                //verificar que este en la lista actual
                if(getTareoDetalleFormTareo(list,DNI)==null){
                    flag = false;
                    break;
                }
                break;
        }
        return flag;
    }




    private static TareoDetalleVO getTareoDetalleFormTareo(List<TareoDetalleVO> tareoDetalleVOList,String DNI){
        TareoDetalleVO tareoDetalleVO = null;

        for (TareoDetalleVO td: tareoDetalleVOList){
            if(td.getTrabajadorVO().getDni().equals(DNI)){
                tareoDetalleVO = td;
            }

        }
        return tareoDetalleVO;
    }

}

package com.ibao.alanger.worktime.views.transference.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.DAO.TrabajadorDAO;
import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VerifyPersonal {

    private static String TAG = VerifyPersonal.class.getSimpleName();



    public static boolean verify(Context ctx, boolean catchError, TareoVO tareoDestino, List<TareoDetalleVO> tareoDetalleVOList, String mode, String DNI, String hour) throws NullPointerException, ParseException {
        Log.d(VerifyPersonal.class.getSimpleName(),mode);
        boolean flag = true;

        if(DNI.length() != 8){
            throw new NullPointerException("DNI no tiene 8 digitos");
        }
        if(tareoDestino == null){
            throw new NullPointerException("Tareo es nulo");
        }
        if(tareoDetalleVOList == null){
            throw new NullPointerException("lista es nula");
        }
        if(!mode.equals(TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR) && !mode.equals(TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR)){
            throw new NullPointerException("modo no válido <"+mode+">");
        }
        if (hour.isEmpty()){
            throw new NullPointerException("Hour no válido");
        }

        switch (mode){//verificar todos los pusibles casos negativos
            case TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR:

                TrabajadorVO trabajadorVO = new TrabajadorDAO(ctx).selectByDNI(DNI);
                if (trabajadorVO!=null){
                    if(!trabajadorVO.getSuspencion().isEmpty()){
                        flag = false;
                        if(catchError){
                            throw new NullPointerException("Suspención: "+trabajadorVO.getSuspencion());
                        }
                        break;
                    }
                }


                //todo; verificar si ya esta en el tareo para ver si ya tiene hora de salida
                TareoDetalleVO trabajadorEnTareoActual  = getTareoDetalleFormTareo_DNI(tareoDestino.getTareoDetalleVOList(),DNI);
                if(trabajadorEnTareoActual!=null){ // verificar si ya existe en la lista del tareo
                    flag = false;
                    if(catchError){
                        throw new NullPointerException("Trabajador ya agregado a la Labor");
                    }
                    break;
                }

                //todo: verificar si ya esta en la lista final
                //verificar si ya esta en la lista actual
                TareoDetalleVO trabajadorEnLista =getTareoDetalleFormTareo_DNI(tareoDetalleVOList,DNI);
                if(trabajadorEnLista!=null){
                    flag = false;
                    if(catchError){
                        throw new NullPointerException("Trabajador ya agregado a la lista actual");
                    }
                    break;
                }
                //todo: verificar si no esta en otras labores
                //falta

                Log.d(TAG,"listando mismo dni");
                List<TareoVO> tareoDAOS = new TareoDAO(ctx).listTareo_same_dni(tareoDestino.getId(),DNI);
                Log.d(TAG,"finalizado listado mismo dni");
                if(tareoDAOS.size()>0){
                    Log.d(TAG,"encontro mismo dni");
                    flag = false;
                    if(catchError){
                        throw new NullPointerException("Trabajador en otra Labor");
                    }
                    break;
                }

                break;
            case TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR:
                //todo; verificar si ya esta en el tareo para ver si ya tiene hora de salida
                TareoDetalleVO temp  = getTareoDetalleFormTareo_DNI(tareoDestino.getTareoDetalleVOList(),DNI);
                if(temp==null){ // verificar que sea distinto es nulo
                    flag = false;
                    if(catchError){
                        throw new NullPointerException("Trabajador no encontrado en la Labor");
                    }
                    break;
                }else {// si ha sido encontrado , verificamos que si ya tiene hora de salida
                    //comparar si la hora de salida es mayor a la hora de entrada

                    Log.d("HORAHORA",temp.getTimeStart());

                    if(! temp.getTimeEnd().isEmpty()){ // si ya tiene hora de salida
                        flag = false;
                        if(catchError){
                            throw new NullPointerException("Trabajador ya marco su salida");
                        }
                        break;
                    }
                    //caso contrario es true
                        if(!isValidDateEnd(temp.getTimeStart(),hour)){
                            if(catchError){
                                throw new NullPointerException("Hora de salida invalida");
                            }
                            break;
                        }
                }
                //todo: verificar si ya esta en la lista final
                //verificar que este en la lista actual
                if(getTareoDetalleFormTareo_DNI(tareoDetalleVOList,DNI)!=null){
                    flag = false;
                    if(catchError){
                        throw new NullPointerException("Trabajador ya agregado a la lista actual");
                    }
                    break;
                }
                break;
        }
        return flag;
    }




    private static boolean isValidDateEnd(String dateStart,String dateEnd) throws ParseException {

        Log.d(TAG,"DATESTART:"+dateStart+"  DATEEND:"+dateEnd);

        boolean isValid = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        Date date_dateStart = format.parse(dateStart);

        Date date_dateEnd = format.parse(dateEnd);

        if (date_dateStart.compareTo(date_dateEnd) <= 0) {
            isValid = true;
        }
        return isValid;

    }



    private static TareoDetalleVO getTareoDetalleFormTareo_DNI(List<TareoDetalleVO> tareoDetalleVOList, String DNI){

        TareoDetalleVO tareoDetalleVO = null;

        for (TareoDetalleVO td: tareoDetalleVOList){
            if(td.getTrabajadorVO().getDni().equals(DNI)){
                tareoDetalleVO = td;
            }

        }
        return tareoDetalleVO;
    }

}

package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_PRODUCTIVIDAD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_PRODUCTIVIDAD_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_PRODUCTIVIDAD_IDTAREODETALLE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_DATEEND;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_DATESTART;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_DNI;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_IDSALIDA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_IDTAREO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREODETALLE_PRODUCTIVIDAD;
import static com.ibao.alanger.worktime.database.DataBaseDesign._AND;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;
import static com.ibao.alanger.worktime.database.DataBaseDesign._n;


public class TareoDetalleDAO {

    private static String TAG = TareoDetalleDAO.class.getSimpleName();

    private Context ctx;

    public TareoDetalleDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_TAREODETALLE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public long insert(int idTareo, String DNI ,String dateStart){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_TAREODETALLE_IDTAREO,idTareo);
        values.put(TAB_TAREODETALLE_DNI,DNI);
        values.put(TAB_TAREODETALLE_DATESTART,dateStart);
        long temp = db.insert(TAB_TAREODETALLE,TAB_TAREODETALLE_ID,values);
        db.close();
        conn.close();
        return temp;
    }

    public TareoDetalleVO selectById(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        TareoDetalleVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_TAREODETALLE +" as E "+
                        _WHERE+
                            "E."+TAB_TAREODETALLE_ID+" = "+ id
            ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectById "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectById "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public TareoDetalleVO selectByIdProductividad(int idProductividad){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TareoDetalleVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "TD."+TAB_TAREODETALLE_ID+ _n +
                            "TD."+TAB_TAREODETALLE_IDTAREO+ _n +
                            "TD."+TAB_TAREODETALLE_DNI+ _n +
                            "TD."+TAB_TAREODETALLE_PRODUCTIVIDAD+ _n +
                            "TD."+TAB_TAREODETALLE_DATESTART+ _n +
                            "TD."+TAB_TAREODETALLE_DATEEND+_n+
                            "TD."+TAB_TAREODETALLE_IDSALIDA+
                            _FROM+
                            TAB_TAREODETALLE +" as TD "+ _n +
                            TAB_PRODUCTIVIDAD   +" as P "+
                            _WHERE+
                            "TD."+TAB_PRODUCTIVIDAD_ID+" = "+ idProductividad +
                            _AND+
                            "TD."+TAB_PRODUCTIVIDAD_IDTAREODETALLE+" = "+"E."+TAB_TAREODETALLE_ID
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByidFundo "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByidFundo "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

 

    public List<TareoDetalleVO> listByIdTareo(int idTareo){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<TareoDetalleVO> tareoDetalleVOList = new ArrayList<>();
        String[] args = {
                String.valueOf(idTareo)
        };

        try{
            Cursor cursor= db.query(TAB_TAREODETALLE,null,TAB_TAREODETALLE_IDTAREO+"=?",args,null,null,null);

            while(cursor.moveToNext()){
                TareoDetalleVO temp = getAtributtes(cursor);
                tareoDetalleVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return tareoDetalleVOList;
    }

    private TareoDetalleVO getAtributtes(Cursor cursor){
        TareoDetalleVO tareoDetalleVO = new TareoDetalleVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
           switch (name){
               case TAB_TAREODETALLE_ID:
                   tareoDetalleVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREODETALLE_IDTAREO:
                   tareoDetalleVO.setIdTareo(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREODETALLE_PRODUCTIVIDAD:
                   tareoDetalleVO.setProductividad(cursor.getFloat(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREODETALLE_DNI:
                   TrabajadorVO trabajadorVO = new TrabajadorVO();
                   trabajadorVO.setDni(cursor.getString(cursor.getColumnIndex(name)));
                   trabajadorVO.setName("S/N");
                   tareoDetalleVO.setTrabajadorVO(trabajadorVO);
                   break;
               case TAB_TAREODETALLE_DATESTART:
                   tareoDetalleVO.setTimeStart(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREODETALLE_DATEEND:
                   tareoDetalleVO.setTimeEnd(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREODETALLE_IDSALIDA:
                   if(cursor.getInt(cursor.getColumnIndex(name))>0){
                       tareoDetalleVO.setSalidaVO(new SalidaDAO(ctx).selectById(cursor.getInt(cursor.getColumnIndex(name))));
                   }
                   break;
               default:
                   Toast.makeText(ctx,"getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                   break;
           }
        }
        return tareoDetalleVO;
    }

}

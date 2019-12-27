package com.ibao.alanger.worktimecopa.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.models.VO.external.ActividadVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_ACTIVIDAD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_ACTIVIDAD_COD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_ACTIVIDAD_ID;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_ACTIVIDAD_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_ACTIVIDAD_STATUS;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._WHERE;

public class ActividadDAO {

    private Context ctx;
    private static String TAG = ActividadDAO.class.getSimpleName();
    public ActividadDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_ACTIVIDAD,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insert(int id, String cod ,String name,boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_ACTIVIDAD_ID,id);
        values.put(TAB_ACTIVIDAD_COD,cod);
        values.put(TAB_ACTIVIDAD_NAME,name);
        values.put(TAB_ACTIVIDAD_STATUS,status);
        long temp = db.insert(TAB_ACTIVIDAD,TAB_ACTIVIDAD_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public ActividadVO selectById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ActividadVO temp = null;
        try{
            
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_ACTIVIDAD+" as C"+
                        _WHERE+
                            "C."+TAB_ACTIVIDAD_ID+" = "+ id
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp=getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByDNI "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByDNI "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public List<ActividadVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<ActividadVO> salidaVOList = new ArrayList<>();
        try{
          //  String[] campos = {TAB_ACTIVIDAD_ID,TAB_ACTIVIDAD_COD,TAB_ACTIVIDAD_NAME,TAB_ACTIVIDAD_STATUS};
            String[] args = {
                    "1"
            };
            Cursor cursor= db.query(TAB_ACTIVIDAD,null,TAB_ACTIVIDAD_STATUS+"=?",args,null,null,TAB_ACTIVIDAD_NAME+_STRASC);
            while(cursor.moveToNext()){
                ActividadVO temp = getAtributtes(cursor);
                salidaVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return salidaVOList;
    }

    private ActividadVO getAtributtes(Cursor cursor){
        ActividadVO salidaVO = new ActividadVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_ACTIVIDAD_ID:
                    salidaVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_ACTIVIDAD_COD:
                    salidaVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_ACTIVIDAD_NAME:
                    salidaVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_ACTIVIDAD_STATUS:
                    salidaVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                default:
                    Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                    Log.d(TAG," getAtributes error no se encuentra campo "+name);
                    break;
            }
        }
        return salidaVO;
    }


}

package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.EmpresaVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign._;
import static com.ibao.alanger.worktime.database.DataBaseDesign._AND;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class EmpresaDAO {

    private static String TAG =EmpresaDAO.class.getSimpleName();

    private Context ctx;

    public EmpresaDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_EMPRESA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insert(int id, String name){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_EMPRESA_ID,id);
        values.put(TAB_EMPRESA_NAME,name);
        long temp = db.insert(TAB_EMPRESA,TAB_EMPRESA_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public EmpresaVO selectById(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_EMPRESA +" as E "+
                        _WHERE+
                            "E."+TAB_EMPRESA_ID+" = "+ id
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


    public EmpresaVO selectByIdFundo(int idFundo){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "E."+TAB_EMPRESA_ID+ _ +
                            "E."+TAB_EMPRESA_NAME+
                        _FROM+
                            TAB_EMPRESA +" as E "+ _ +
                            TAB_FUNDO   +" as F "+
                        _WHERE+
                            "F."+TAB_FUNDO_ID+" = "+ idFundo +
                            _AND+
                            "F."+TAB_FUNDO_IDEMPRESA+" = "+"E."+TAB_EMPRESA_ID
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


    public EmpresaVO selectByIdSede(int idSede){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "E."+TAB_EMPRESA_ID+ _ +
                            "E."+TAB_EMPRESA_NAME+
                        _FROM+
                            TAB_EMPRESA +" as E "+ _ +
                            TAB_SEDE    +" as S "+
                        _WHERE+
                            "S."+TAB_SEDE_ID+" = "+ idSede +
                            _AND+
                            "S."+TAB_SEDE_IDEMPRESA+" = "+"E."+TAB_EMPRESA_ID
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByIdSede "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByIdSede "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public EmpresaVO selectByIdTrabajador(int idTrabajador){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "E."+TAB_EMPRESA_ID+ _ +
                            "E."+TAB_EMPRESA_NAME+
                        _FROM+
                            TAB_EMPRESA     +" as E "+ _ +
                            TAB_TRABAJADOR  +" as T "+
                        _WHERE+
                            "T."+TAB_TRABAJADOR_ID+" = "+ idTrabajador +
                            _AND+
                            "T."+TAB_TRABAJADOR_IDEMPRESA+" = "+"E."+TAB_EMPRESA_ID
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByIdTrabajador "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByIdTrabajador "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }


    public EmpresaVO selectByIdCCoste(int idCCoste){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "E."+TAB_EMPRESA_ID+ _ +
                            "E."+TAB_EMPRESA_NAME+
                            _FROM+
                            TAB_EMPRESA +" as E "+ _ +
                            TAB_CCOSTE +" as CC "+
                            _WHERE+
                            "CC."+ TAB_CCOSTE_ID +" = "+ idCCoste +
                            _AND+
                            "CC."+ TAB_CCOSTE_IDEMPRESA +" = "+"E."+TAB_EMPRESA_ID
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByIdCCoste "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByIdCCoste "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public List<EmpresaVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EmpresaVO> empresaVOS = new ArrayList<>();
        try{
            String[] campos = {TAB_EMPRESA_ID,TAB_EMPRESA_NAME};
            Cursor cursor= db.query(TAB_EMPRESA,campos,null,null,null,null,null);
            while(cursor.moveToNext()){
                EmpresaVO temp = getAtributtes(cursor);
                empresaVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return empresaVOS;
    }

    private EmpresaVO getAtributtes(Cursor cursor){
        EmpresaVO empresaVO = new EmpresaVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
           switch (name){
               case TAB_EMPRESA_ID:
                   empresaVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                   break;
               case TAB_EMPRESA_NAME:
                   empresaVO.setName(cursor.getString(cursor.getColumnIndex(TAB_EMPRESA_NAME)));
                   break;
           }
        }
        return empresaVO;
    }

}

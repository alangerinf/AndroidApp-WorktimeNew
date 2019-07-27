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
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_RAZON;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_RUC;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._n;
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

    public boolean insert(int id, String cod ,String ruc,String name,String razon,boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_EMPRESA_ID,id);
        values.put(TAB_EMPRESA_COD,cod);
        values.put(TAB_EMPRESA_RUC,ruc);
        values.put(TAB_EMPRESA_NAME,name);
        values.put(TAB_EMPRESA_RAZON,razon);
        values.put(TAB_EMPRESA_STATUS,status);
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
                            "E."+TAB_EMPRESA_ID+" = "+ id+
                            _AND+
                            "E."+TAB_EMPRESA_STATUS+" = 1"
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
                            "E."+TAB_EMPRESA_ID+ _n +
                            "E."+TAB_EMPRESA_COD+ _n +
                            "E."+TAB_EMPRESA_RUC+ _n +
                            "E."+TAB_EMPRESA_RAZON+ _n +
                            "E."+TAB_EMPRESA_NAME+_n+
                            "E."+TAB_EMPRESA_STATUS+
                        _FROM+
                            TAB_EMPRESA +" as E "+ _n +
                            TAB_FUNDO   +" as F "+
                        _WHERE+
                            "F."+TAB_FUNDO_ID+" = "+ idFundo +
                            _AND+
                            "F."+TAB_FUNDO_IDEMPRESA+" = "+"E."+TAB_EMPRESA_ID+
                            _AND+
                            "F."+TAB_FUNDO_STATUS+"=1"+
                            _AND+
                            "E."+TAB_EMPRESA_STATUS+"=1"
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
/*
    public EmpresaVO selectByIdCCoste(int idCCoste){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "E."+TAB_EMPRESA_ID+ _n +
                            "E."+TAB_EMPRESA_NAME+
                            _FROM+
                            TAB_EMPRESA +" as E "+ _n +
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
*/
    public List<EmpresaVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EmpresaVO> empresaVOS = new ArrayList<>();
        String[] args = {
                "1"
        };

        try{
            Cursor cursor= db.query(TAB_EMPRESA,null,TAB_EMPRESA_STATUS+"=?",args,null,null,TAB_EMPRESA_NAME+_STRASC);
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
                   empresaVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_EMPRESA_COD:
                   empresaVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_EMPRESA_RAZON:
                   empresaVO.setRazon(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_EMPRESA_RUC:
                   empresaVO.setRuc(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_EMPRESA_NAME:
                   empresaVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_EMPRESA_STATUS:
                   empresaVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                   break;
               default:
                   Toast.makeText(ctx,"getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                   break;
           }
        }
        return empresaVO;
    }

}

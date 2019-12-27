package com.ibao.alanger.worktimecopa.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.models.VO.internal.ProductividadVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_PRODUCTIVIDAD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_PRODUCTIVIDAD_ID;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_PRODUCTIVIDAD_IDTAREODETALLE;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_PRODUCTIVIDAD_VALUE;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_PRODUCTIVIDAD_DATETIME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._WHERE;


public class ProductividadDAO {

    private static String TAG = ProductividadDAO.class.getSimpleName();

    private Context ctx;

    public ProductividadDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_PRODUCTIVIDAD,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public long insert(int idTareoDetalle ,String value,String dateTime){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_PRODUCTIVIDAD_IDTAREODETALLE,idTareoDetalle);
        values.put(TAB_PRODUCTIVIDAD_VALUE,value);
        values.put(TAB_PRODUCTIVIDAD_DATETIME,dateTime);
        long temp = db.insert(TAB_PRODUCTIVIDAD,TAB_PRODUCTIVIDAD_ID,values);
        db.close();
        conn.close();
        return temp;
    }

    public boolean insert(ProductividadVO pro){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_PRODUCTIVIDAD_ID,pro.getId());
        values.put(TAB_PRODUCTIVIDAD_IDTAREODETALLE,pro.getIdTareoDetalle());
        values.put(TAB_PRODUCTIVIDAD_VALUE,pro.getValue());
        values.put(TAB_PRODUCTIVIDAD_DATETIME,pro.getDateTime());
        long temp = db.insert(TAB_PRODUCTIVIDAD,TAB_PRODUCTIVIDAD_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public ProductividadVO selectById(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        ProductividadVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_PRODUCTIVIDAD +" as E "+
                        _WHERE+
                            "E."+TAB_PRODUCTIVIDAD_ID+" = "+ id
            ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
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


    public List<ProductividadVO> listByIdTareoDetalle(int idTareoDatelle){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<ProductividadVO> empresaVOS = new ArrayList<>();
        String[] args = {
                String.valueOf(idTareoDatelle)
        };
        try{
            Cursor cursor= db.query(TAB_PRODUCTIVIDAD,null,TAB_PRODUCTIVIDAD_IDTAREODETALLE+"=?",args,null,null,null);
            while(cursor.moveToNext()){
                ProductividadVO temp = getAtributtes(cursor);
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

    public int deleteById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        String[] args = {
                String.valueOf(id)
        };
        int i =    db.delete(TAB_PRODUCTIVIDAD,TAB_PRODUCTIVIDAD_ID+"=?",args);
        db.close();
        c.close();
        return i;
    }

    public int deleteByIdTareoDetalle(int idTareoDetalle){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        String[] args = {
                String.valueOf(idTareoDetalle)
        };
        int i =    db.delete(TAB_PRODUCTIVIDAD,TAB_PRODUCTIVIDAD_IDTAREODETALLE+"=?",args);
        db.close();
        c.close();
        return i;
    }

    private ProductividadVO getAtributtes(Cursor cursor){
        ProductividadVO empresaVO = new ProductividadVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
           switch (name){
               case TAB_PRODUCTIVIDAD_ID:
                   empresaVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_PRODUCTIVIDAD_IDTAREODETALLE:
                   empresaVO.setIdTareoDetalle(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_PRODUCTIVIDAD_VALUE:
                   empresaVO.setValue(cursor.getFloat(cursor.getColumnIndex(name)));
                   break;
               case TAB_PRODUCTIVIDAD_DATETIME:
                   empresaVO.setDateTime(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               default:
                   Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                   Log.d(TAG," getAtributes error no se encuentra campo "+name);
                   break;
           }
        }
        return empresaVO;
    }

}

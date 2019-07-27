package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.CultivoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO_HASLABOR;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CULTIVO_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;

public class CultivoDAO {

    private Context ctx;
    private static String TAG = CultivoDAO.class.getSimpleName();
    public CultivoDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_CULTIVO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insert(int id, String cod ,String name,boolean isLabor, boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_CULTIVO_ID,id);
        values.put(TAB_CULTIVO_COD,cod);
        values.put(TAB_CULTIVO_NAME,name);
        values.put(TAB_CULTIVO_HASLABOR,isLabor);
        values.put(TAB_CULTIVO_STATUS,status);
        long temp = db.insert(TAB_CULTIVO,TAB_CULTIVO_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public CultivoVO selectById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CultivoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_CULTIVO+" as C"+
                        _WHERE+
                            "C."+TAB_CULTIVO_ID+" = "+ id
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

    public List<CultivoVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<CultivoVO> cultivos = new ArrayList<>();
        try{
            //String[] campos = {TAB_CULTIVO_ID,TAB_CULTIVO_COD,TAB_CULTIVO_NAME};
            String[] args ={
                    "1"
            };
            Cursor cursor= db.query(TAB_CULTIVO,null,TAB_CULTIVO_STATUS+"=?",args,null,null,null);
            while(cursor.moveToNext()){
                CultivoVO temp = getAtributtes(cursor);
                cultivos.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return cultivos;
    }

    private CultivoVO getAtributtes(Cursor cursor){
        CultivoVO cultivoVO = new CultivoVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_CULTIVO_ID:
                    cultivoVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_CULTIVO_COD:
                    cultivoVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_CULTIVO_NAME:
                    cultivoVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_CULTIVO_HASLABOR:
                    cultivoVO.setLabor(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                case TAB_CULTIVO_STATUS:
                    cultivoVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
            }
        }
        return cultivoVO;
    }


}

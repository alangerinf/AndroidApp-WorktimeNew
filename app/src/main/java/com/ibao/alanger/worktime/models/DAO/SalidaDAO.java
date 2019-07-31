package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.SalidaVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SALIDA_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;

public class SalidaDAO {

    private Context ctx;
    private static String TAG = SalidaDAO.class.getSimpleName();
    public SalidaDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_SALIDA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insert(int id, String cod ,String name, boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_SALIDA_ID,id);
        values.put(TAB_SALIDA_COD,cod);
        values.put(TAB_SALIDA_NAME,name);
        values.put(TAB_SALIDA_STATUS,status);
        long temp = db.insert(TAB_SALIDA,TAB_SALIDA_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public SalidaVO selectById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        SalidaVO temp = null;
        try{
            
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_SALIDA+" as S"+
                        _WHERE+
                            "S."+TAB_SALIDA_ID+" = "+ id
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

    public List<SalidaVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<SalidaVO> cultivos = new ArrayList<>();
        try{
           // String[] campos = {TAB_SALIDA_ID,TAB_SALIDA_COD,TAB_SALIDA_NAME,TAB_SALIDA_STATUS};
            String[] args = {
                    "1"
            };
            Cursor cursor= db.query(TAB_SALIDA,null,TAB_SALIDA_STATUS+"=?",args,TAB_SALIDA_NAME,null,TAB_SALIDA_NAME+_STRASC);
            while(cursor.moveToNext()){
                SalidaVO temp = getAtributtes(cursor);
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

    private SalidaVO getAtributtes(Cursor cursor){
        SalidaVO SalidaVO = new SalidaVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_SALIDA_ID:
                    SalidaVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_SALIDA_COD:
                    SalidaVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_SALIDA_NAME:
                    SalidaVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_SALIDA_STATUS:
                    SalidaVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                default:
                    Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                    Log.d(TAG," getAtributes error no se encuentra campo "+name);
                    break;
            }
        }
        return SalidaVO;
    }


}

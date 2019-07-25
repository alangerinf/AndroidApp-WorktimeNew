package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.ModuloVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_MODULO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_MODULO_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_MODULO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_MODULO_IDFUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_MODULO_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class ModuloDAO {

    private static String TAG = ModuloDAO.class.getSimpleName();
    private Context ctx;

    public ModuloDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_MODULO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insert(int id, String cod, String name, int idFundo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_MODULO_ID,id);
        values.put(TAB_MODULO_COD,cod);
        values.put(TAB_MODULO_NAME,name);
        values.put(TAB_MODULO_IDFUNDO,idFundo);
        long temp = db.insert(TAB_MODULO,TAB_MODULO_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }
    public ModuloVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ModuloVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_MODULO+" as F"+
                        _WHERE+
                            "F."+TAB_MODULO_ID+"="+ id
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

    public List<ModuloVO> listByIdFundo(int idFundo){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<ModuloVO> fundoVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_MODULO+" as F"+
                        _WHERE+
                            "F."+TAB_MODULO_IDFUNDO+"="+ idFundo +
                        _ORDERBY+
                            "F."+TAB_MODULO_NAME+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                ModuloVO temp = getAtributtes(cursor);
                fundoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listByIdFundo "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listByIdFundo "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return fundoVOS;
    }

    private ModuloVO getAtributtes(Cursor cursor){
        ModuloVO moduloVO = new ModuloVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_MODULO_ID:
                    moduloVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                    break;
                case TAB_MODULO_COD:
                    moduloVO.setCod(cursor.getString(cursor.getColumnIndex(TAB_MODULO_COD)));
                    break;
                case TAB_MODULO_NAME:
                    moduloVO.setName(cursor.getString(cursor.getColumnIndex(TAB_MODULO_NAME)));
                    break;
                case TAB_MODULO_IDFUNDO:
                    moduloVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(TAB_MODULO_IDFUNDO)));
                    break;
            }
        }
        return moduloVO;
    }

}

package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_DNI;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TRABAJADOR_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class TrabajadorDAO {

    private final static String TAG = TrabajadorDAO.class.getSimpleName();
    private Context ctx;

    public TrabajadorDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_TRABAJADOR,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insert(int id,String cod,String dni, String name, int idEmpresa){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(TAB_TRABAJADOR_ID,id);
            values.put(TAB_TRABAJADOR_COD,cod);
            values.put(TAB_TRABAJADOR_DNI,dni);
            values.put(TAB_TRABAJADOR_NAME,name);
            values.put(TAB_TRABAJADOR_IDEMPRESA,idEmpresa);
        long temp = db.insert(TAB_TRABAJADOR,TAB_TRABAJADOR_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public TrabajadorVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TrabajadorVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_TRABAJADOR+" as T"+
                        _WHERE+
                            "T."+TAB_TRABAJADOR_ID+"="+ id
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectById "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return temp;
    }

    public List<TrabajadorVO> listByIdEmpresa(int idEmpresa){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<TrabajadorVO> trabajadorVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_TRABAJADOR+" as T"+
                        _WHERE+
                            "T."+TAB_TRABAJADOR_IDEMPRESA+"="+ idEmpresa +
                        _ORDERBY+
                            "T."+TAB_TRABAJADOR_NAME+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                TrabajadorVO temp = getAtributtes(cursor);
                trabajadorVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listByIdEmpresa "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return trabajadorVOS;
    }
    
    private TrabajadorVO getAtributtes(Cursor cursor){
        TrabajadorVO trabajadorVO = new TrabajadorVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_TRABAJADOR_ID:
                    trabajadorVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                    break;
                case TAB_TRABAJADOR_COD:
                    trabajadorVO.setName(cursor.getString(cursor.getColumnIndex(TAB_TRABAJADOR_COD)));
                    break;
                case TAB_TRABAJADOR_DNI:
                    trabajadorVO.setName(cursor.getString(cursor.getColumnIndex(TAB_TRABAJADOR_DNI)));
                    break;
                case TAB_TRABAJADOR_NAME:
                    trabajadorVO.setName(cursor.getString(cursor.getColumnIndex(TAB_TRABAJADOR_NAME)));
                    break;
                case TAB_TRABAJADOR_IDEMPRESA:
                    trabajadorVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(TAB_TRABAJADOR_IDEMPRESA)));
                    break;
            }
        }
        return trabajadorVO;
    }

}

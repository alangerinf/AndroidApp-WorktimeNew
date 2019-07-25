package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.ActividadVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_ACTIVIDAD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_ACTIVIDAD_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_ACTIVIDAD_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_ACTIVIDAD_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;

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

    public boolean insert(int id, String cod ,String name){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_ACTIVIDAD_ID,id);
        values.put(TAB_ACTIVIDAD_COD,cod);
        values.put(TAB_ACTIVIDAD_NAME,name);
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
            temp = new ActividadVO();
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_ACTIVIDAD+" as C"+
                        _WHERE+
                            "C."+TAB_ACTIVIDAD_ID+" = "+ id +
                        _ORDERBY+
                            "C."+TAB_ACTIVIDAD_NAME+
                            _STRASC
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
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

    public List<ActividadVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<ActividadVO> cultivos = new ArrayList<>();
        try{
            String[] campos = {TAB_ACTIVIDAD_ID,TAB_ACTIVIDAD_COD,TAB_ACTIVIDAD_NAME};
            Cursor cursor= db.query(TAB_ACTIVIDAD,campos,null,null,null,null,null);
            while(cursor.moveToNext()){
                ActividadVO temp = getAtributtes(cursor);
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

    private ActividadVO getAtributtes(Cursor cursor){
        ActividadVO ActividadVO = new ActividadVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_ACTIVIDAD_ID:
                    ActividadVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_ACTIVIDAD_ID)));
                    break;
                case TAB_ACTIVIDAD_COD:
                    ActividadVO.setCod(cursor.getString(cursor.getColumnIndex(TAB_ACTIVIDAD_COD)));
                    break;
                case TAB_ACTIVIDAD_NAME:
                    ActividadVO.setName(cursor.getString(cursor.getColumnIndex(TAB_ACTIVIDAD_NAME)));
                    break;
            }
        }
        return ActividadVO;
    }


}

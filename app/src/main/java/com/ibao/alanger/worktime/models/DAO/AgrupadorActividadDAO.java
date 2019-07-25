package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.AgrupadorActividadVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_AGRUPADORACT;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_AGRUPADORACT_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_AGRUPADORACT_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_AGRUPADORACT_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;

public class AgrupadorActividadDAO {

    private Context ctx;
    private static String TAG = AgrupadorActividadDAO.class.getSimpleName();
    public AgrupadorActividadDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_AGRUPADORACT,null,null);
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
        values.put(TAB_AGRUPADORACT_ID,id);
        values.put(TAB_AGRUPADORACT_COD,cod);
        values.put(TAB_AGRUPADORACT_NAME,name);
        long temp = db.insert(TAB_AGRUPADORACT,TAB_AGRUPADORACT_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public AgrupadorActividadVO selectById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        AgrupadorActividadVO temp = null;
        try{
            temp = new AgrupadorActividadVO();
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_AGRUPADORACT+" as C"+
                        _WHERE+
                            "C."+TAB_AGRUPADORACT_ID+" = "+ id +
                        _ORDERBY+
                            "C."+TAB_AGRUPADORACT_NAME+
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

    public List<AgrupadorActividadVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<AgrupadorActividadVO> cultivos = new ArrayList<>();
        try{
            String[] campos = {TAB_AGRUPADORACT_ID,TAB_AGRUPADORACT_COD,TAB_AGRUPADORACT_NAME};
            Cursor cursor= db.query(TAB_AGRUPADORACT,campos,null,null,null,null,null);
            while(cursor.moveToNext()){
                AgrupadorActividadVO temp = getAtributtes(cursor);
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

    private AgrupadorActividadVO getAtributtes(Cursor cursor){
        AgrupadorActividadVO AgrupadorActividadVO = new AgrupadorActividadVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_AGRUPADORACT_ID:
                    AgrupadorActividadVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_AGRUPADORACT_ID)));
                    break;
                case TAB_AGRUPADORACT_COD:
                    AgrupadorActividadVO.setCod(cursor.getString(cursor.getColumnIndex(TAB_AGRUPADORACT_COD)));
                    break;
                case TAB_AGRUPADORACT_NAME:
                    AgrupadorActividadVO.setName(cursor.getString(cursor.getColumnIndex(TAB_AGRUPADORACT_NAME)));
                    break;
            }
        }
        return AgrupadorActividadVO;
    }


}

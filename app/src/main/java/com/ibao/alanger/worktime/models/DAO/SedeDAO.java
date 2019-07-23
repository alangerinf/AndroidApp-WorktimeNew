package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.FundoVO;
import com.ibao.alanger.worktime.models.VO.external.SedeVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_SEDE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class SedeDAO {

    private final static String TAG = SedeDAO.class.getSimpleName();
    private Context ctx;

    public SedeDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_SEDE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insert(int id, String name, int idEmpresa){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_SEDE_ID,id);
        values.put(TAB_SEDE_NAME,name);
        values.put(TAB_SEDE_IDEMPRESA,idEmpresa);
        long temp = db.insert(TAB_SEDE,TAB_SEDE_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public SedeVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        SedeVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "F."+TAB_SEDE_ID+_ +
                            "F."+TAB_SEDE_NAME+_ +
                            "F."+TAB_SEDE_IDEMPRESA+
                        _FROM+
                            TAB_SEDE+" as F"+
                        _WHERE+
                            "F."+TAB_SEDE_ID+"="+ id
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

    public List<SedeVO> listByIdEmpresa(int idEmpresa){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<SedeVO> sedeVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "F."+TAB_SEDE_ID+_ +
                            "F."+TAB_SEDE_NAME+_ +
                            "F."+TAB_SEDE_IDEMPRESA+
                        _FROM+
                            TAB_SEDE+" as F"+
                        _WHERE+
                            "F."+TAB_SEDE_IDEMPRESA+"="+ idEmpresa +
                        _ORDERBY+
                            "F."+TAB_SEDE_NAME+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                SedeVO temp = getAtributtes(cursor);
                sedeVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listByIdEmpresa "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return sedeVOS;
    }
    
    private SedeVO getAtributtes(Cursor cursor){
        SedeVO sedeVO = new SedeVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_SEDE_ID:
                    sedeVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                    break;
                case TAB_SEDE_NAME:
                    sedeVO.setName(cursor.getString(cursor.getColumnIndex(TAB_SEDE_NAME)));
                    break;
                case TAB_SEDE_IDEMPRESA:
                    sedeVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(TAB_SEDE_IDEMPRESA)));
                    break;
            }
        }
        return sedeVO;
    }

}

package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.FundoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class FundoDAO {

    private static String TAG = FundoDAO.class.getSimpleName();
    private Context ctx;

    public FundoDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_FUNDO,null,null);
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
        values.put(TAB_FUNDO_ID,id);
        values.put(TAB_FUNDO_NAME,name);
        values.put(TAB_FUNDO_IDEMPRESA,idEmpresa);
        long temp = db.insert(TAB_FUNDO,TAB_FUNDO_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }
    public FundoVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        FundoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_FUNDO+" as F"+
                        _WHERE+
                            "F."+TAB_FUNDO_ID+"="+ id
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

    public List<FundoVO> listByIdEmpresa(int idEmpresa){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<FundoVO> fundoVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_FUNDO+" as F"+
                        _WHERE+
                            "F."+TAB_FUNDO_IDEMPRESA+"="+ idEmpresa +
                        _ORDERBY+
                            "F."+TAB_FUNDO_NAME+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                FundoVO temp = getAtributtes(cursor);
                fundoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listByIdEmpresa "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return fundoVOS;
    }
    
    private FundoVO getAtributtes(Cursor cursor){
        FundoVO fundoVO = new FundoVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_FUNDO_ID:
                    fundoVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                    break;
                case TAB_FUNDO_NAME:
                    fundoVO.setName(cursor.getString(cursor.getColumnIndex(TAB_FUNDO_NAME)));
                    break;
                case TAB_FUNDO_IDEMPRESA:
                    fundoVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(TAB_FUNDO_IDEMPRESA)));
                    break;
            }
        }
        return fundoVO;
    }

}

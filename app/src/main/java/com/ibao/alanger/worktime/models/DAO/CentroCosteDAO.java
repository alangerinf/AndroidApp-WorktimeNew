package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.CentroCosteVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_CCOSTE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign._AND;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class CentroCosteDAO {

    private final static String TAG = CentroCosteDAO.class.getSimpleName();
    private Context ctx;

    public CentroCosteDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_CCOSTE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insert(int id,String cod ,String name , int idEmpresa,boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(TAB_CCOSTE_ID,id);
            values.put(TAB_CCOSTE_COD,cod);
            values.put(TAB_CCOSTE_NAME,name);
            values.put(TAB_CCOSTE_IDEMPRESA,idEmpresa);
            values.put(TAB_CCOSTE_STATUS,status);
        long temp = db.insert(TAB_CCOSTE,TAB_CCOSTE_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public CentroCosteVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CentroCosteVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_CCOSTE+" as F"+
                        _WHERE+
                            "F."+TAB_CCOSTE_ID+"="+ id+
                            _AND+
                            "F."+TAB_CCOSTE_STATUS+"=1"
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

    public List<CentroCosteVO> listByIdEmpresa(int idEmpresa){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<CentroCosteVO> centroCosteVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_CCOSTE+" as F"+
                        _WHERE+
                            "F."+TAB_CCOSTE_IDEMPRESA+"="+ idEmpresa +
                            _AND+
                            "F."+TAB_CCOSTE_STATUS+"=1"+
                        _ORDERBY+
                            "F."+TAB_CCOSTE_NAME+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                CentroCosteVO temp = getAtributtes(cursor);
                centroCosteVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listByIdEmpresa "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return centroCosteVOS;
    }

    private CentroCosteVO getAtributtes(Cursor cursor){
        CentroCosteVO centroCosteVO = new CentroCosteVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_CCOSTE_ID:
                    centroCosteVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_CCOSTE_COD:
                    centroCosteVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_CCOSTE_NAME:
                    centroCosteVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_CCOSTE_IDEMPRESA:
                    centroCosteVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_CCOSTE_STATUS:
                    centroCosteVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
            }
        }
        return centroCosteVO;
    }

}

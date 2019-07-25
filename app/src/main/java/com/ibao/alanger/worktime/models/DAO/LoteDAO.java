package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.LoteVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_IDCULTIVO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_IDFUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_NUM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class LoteDAO {

    private static String TAG = LoteDAO.class.getSimpleName();
    private Context ctx;

    public LoteDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_LOTE,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insert(int id, String cod, String num, int idFundo,int idCultivo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_LOTE_ID,id);
        values.put(TAB_LOTE_COD,cod);
        values.put(TAB_LOTE_NUM,num);
        values.put(TAB_LOTE_IDFUNDO,idFundo);
        values.put(TAB_LOTE_IDCULTIVO,idCultivo);
        long temp = db.insert(TAB_LOTE,TAB_LOTE_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }
    public LoteVO selectById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        LoteVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_LOTE+" as F"+
                        _WHERE+
                            "F."+TAB_LOTE_ID+"="+ id
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

    public List<LoteVO> listByIdFundo(int idFundo){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<LoteVO> fundoVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_LOTE+" as F"+
                        _WHERE+
                            "F."+TAB_LOTE_IDFUNDO+"="+ idFundo +
                        _ORDERBY+
                            "F."+TAB_LOTE_COD+
                            _STRASC
                    ,null);
            while (cursor.getCount()>0 && cursor.moveToNext()){
                LoteVO temp = getAtributtes(cursor);
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

    private LoteVO getAtributtes(Cursor cursor){
        LoteVO loteVO = new LoteVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_LOTE_ID:
                    loteVO.setId(cursor.getInt(cursor.getColumnIndex(TAB_EMPRESA_ID)));
                    break;
                case TAB_LOTE_COD:
                    loteVO.setCod(cursor.getString(cursor.getColumnIndex(TAB_LOTE_COD)));
                    break;
                case TAB_LOTE_NUM:
                    loteVO.setName(cursor.getString(cursor.getColumnIndex(TAB_LOTE_NUM)));
                    break;
                case TAB_LOTE_IDFUNDO:
                    loteVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(TAB_LOTE_IDFUNDO)));
                    break;
            }
        }
        return loteVO;
    }

}

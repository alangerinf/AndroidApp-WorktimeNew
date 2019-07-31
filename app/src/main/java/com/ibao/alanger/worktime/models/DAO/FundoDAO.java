package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.external.FundoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_IDEMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_FUNDO_STATUS;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_IDFUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign._AND;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._ORDERBY;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;
import static com.ibao.alanger.worktime.database.DataBaseDesign._n;


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


    public boolean insert(int id,String cod, String name, int idEmpresa,boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_FUNDO_ID,id);
        values.put(TAB_FUNDO_COD,cod);
        values.put(TAB_FUNDO_NAME,name);
        values.put(TAB_FUNDO_IDEMPRESA,idEmpresa);
        values.put(TAB_FUNDO_STATUS,status);
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
            Log.d(TAG," selectById "+e.toString());
        }
        db.close();
        c.close();
        return temp;
    }

    public FundoVO selectByIdLote(int idlote) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        FundoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "F."+TAB_FUNDO_ID+_n+
                            "F."+TAB_FUNDO_STATUS+_n+
                            "F."+TAB_FUNDO_COD+_n+
                            "F."+TAB_FUNDO_IDEMPRESA+_n+
                            "F."+TAB_FUNDO_NAME+
                            _FROM+
                            TAB_FUNDO+" as F"+_n+
                            TAB_LOTE+" as L"+
                            _WHERE+
                            "L."+TAB_LOTE_ID+"="+idlote+
                            _AND+
                            "L."+TAB_LOTE_IDFUNDO+"="+"F."+TAB_FUNDO_ID
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp = getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByIdLote "+e.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG," selectByIdLote "+e.toString());
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
                            _AND+
                            "F."+TAB_FUNDO_STATUS+"=1"+
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
            Log.d(TAG," listByIdEmpresa "+e.toString());
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
                    fundoVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_FUNDO_NAME:
                    fundoVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                case TAB_FUNDO_COD:
                    fundoVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_FUNDO_IDEMPRESA:
                    fundoVO.setIdEmpresa(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_FUNDO_STATUS:
                    fundoVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                default:
                    Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                    Log.d(TAG," getAtributes error no se encuentra campo "+name);
                        break;
            }
        }
        return fundoVO;
    }

}

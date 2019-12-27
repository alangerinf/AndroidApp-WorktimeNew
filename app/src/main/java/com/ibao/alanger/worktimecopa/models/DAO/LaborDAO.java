package com.ibao.alanger.worktimecopa.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.models.VO.external.LaborVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_COD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_ID;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_IDACTIVIDAD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_ISDIRECT;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_ISTAREA;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_LISTIDCULTIVO;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_MAGNITUD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_STATUS;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_THEORICALCOST;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_LABOR_THEORICALHOURS;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._AND;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._STRASC;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign._WHERE;

public class LaborDAO {

    private Context ctx;
    private static String TAG = LaborDAO.class.getSimpleName();
    public LaborDAO(Context ctx) {
        this.ctx=ctx;
    }

    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_LABOR,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insert(int id, String cod ,String name,boolean isDirect,float theoricalHours,float theoricalCosst, boolean isTarea, String magnitud,int idActividad,String listIdCultivos,boolean status){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(TAB_LABOR_ID,id);
            values.put(TAB_LABOR_COD,cod);
            values.put(TAB_LABOR_NAME,name);
            values.put(TAB_LABOR_ISDIRECT,isDirect);
            values.put(TAB_LABOR_THEORICALHOURS,theoricalHours);
            values.put(TAB_LABOR_THEORICALCOST,theoricalCosst);
            values.put(TAB_LABOR_ISTAREA,isTarea);
            values.put(TAB_LABOR_MAGNITUD,magnitud);
            values.put(TAB_LABOR_IDACTIVIDAD,idActividad);
            values.put(TAB_LABOR_LISTIDCULTIVO,listIdCultivos);
            values.put(TAB_LABOR_STATUS,status);
        long temp = db.insert(TAB_LABOR,TAB_LABOR_ID,values);
        db.close();
        conn.close();
        return temp > 0;
    }

    public LaborVO selectById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        LaborVO temp = null;
        try{
            
            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_LABOR+" as C"+
                        _WHERE+
                            "C."+TAB_LABOR_ID+" = "+ id
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp=getAtributtes(cursor);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" selectByDNI "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," selectByDNI "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return temp;
    }

    public List<LaborVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<LaborVO> laborVOList = new ArrayList<>();
        try{
            //  String[] campos = {TAB_LABOR_ID,TAB_LABOR_COD,TAB_LABOR_NAME,TAB_LABOR_STATUS};
            String[] args = {
                    "1"
            };
            Cursor cursor= db.query(TAB_LABOR,null,TAB_LABOR_STATUS+"=?",args,TAB_LABOR_NAME,null,TAB_LABOR_NAME+_STRASC);
            while(cursor.moveToNext()){
                LaborVO temp = getAtributtes(cursor);
                laborVOList.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return laborVOList;
    }


    public List<LaborVO> listByIdCultivoIdActividad_IsDirect(int idCultivo, int idActividad, boolean isDirect){
        ConexionSQLiteHelper c;

        Log.d(TAG,"listando labor "+idCultivo+ " "+idActividad+" :");

        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<LaborVO> laborVOList = new ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    _SELECT +
                            "*"+
                        _FROM+
                            TAB_LABOR+" as L"+
                        _WHERE+
                            "L."+TAB_LABOR_IDACTIVIDAD+" = "+ idActividad+
                            _AND+
                            "L."+TAB_LABOR_ISDIRECT+" = "+(isDirect?"1":0)+
                            _AND+
                            "L."+TAB_LABOR_STATUS+" = 1"
                    ,null);
            while(cursor.moveToNext()){
                LaborVO temp = getAtributtes(cursor);
                Log.d(TAG,"listando labor "+temp.getName());
                for(Integer id:temp.getListIdCultivos()){
                    if(id==idCultivo){
                        laborVOList.add(temp);
                        break;
                    }
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return laborVOList;
    }

    private LaborVO getAtributtes(Cursor cursor){
        LaborVO laborVO = new LaborVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
            switch (name){
                case TAB_LABOR_ID:
                    laborVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_COD:
                    laborVO.setCod(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_NAME:
                    laborVO.setName(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_ISDIRECT:
                    laborVO.setDirecto(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                case TAB_LABOR_THEORICALHOURS:
                    laborVO.setTheoricalHours(cursor.getFloat(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_THEORICALCOST:
                    laborVO.setTheoricalCost(cursor.getFloat(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_ISTAREA:
                    laborVO.setTarea(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                case TAB_LABOR_MAGNITUD:
                    laborVO.setMagnitud(cursor.getString(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_IDACTIVIDAD:
                    laborVO.setIdActividad(cursor.getInt(cursor.getColumnIndex(name)));
                    break;
                case TAB_LABOR_LISTIDCULTIVO:
                    String x = (cursor.getString(cursor.getColumnIndex(name)));
                    String [] list = x.split(",");
                    List<Integer> listIdCultivos = new ArrayList<>();
                    for(String id : list){
                        listIdCultivos.add(Integer.valueOf(id));
                    }
                    laborVO.setListIdCultivos(listIdCultivos);
                    break;
                case TAB_LABOR_STATUS:
                    laborVO.setStatus(cursor.getInt(cursor.getColumnIndex(name))>0);
                    break;
                default:
                    Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                    Log.d(TAG," getAtributes error no se encuentra campo "+name);
                    break;
            }
        }
        return laborVO;
    }


}

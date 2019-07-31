package com.ibao.alanger.worktime.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import java.util.ArrayList;
import java.util.List;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_PRODUCTIVIDAD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_PRODUCTIVIDAD_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_DATEEND;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_DATESTART;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_IDCCOSTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_IDFUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_IDLABOR;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_IDLOTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_ISACTIVE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_ISASISTENCIA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_TAREO_PRODUCTIVIDAD;
import static com.ibao.alanger.worktime.database.DataBaseDesign._FROM;
import static com.ibao.alanger.worktime.database.DataBaseDesign._SELECT;
import static com.ibao.alanger.worktime.database.DataBaseDesign._WHERE;


public class TareoDAO {

    private static String TAG = TareoDAO.class.getSimpleName();

    private Context ctx;

    public TareoDAO(Context ctx) {
        this.ctx=ctx;
    }


    public boolean dropTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TAB_TAREO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public long insert(int idLabor ,int idLote,int idCCoste,int idFundo, boolean isAsistencia){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAB_TAREO_IDLABOR,idLabor);
        values.put(TAB_TAREO_IDLOTE,idLote);
        values.put(TAB_TAREO_IDCCOSTE,idCCoste);
        values.put(TAB_TAREO_IDFUNDO,idFundo);
        values.put(TAB_TAREO_ISASISTENCIA,isAsistencia);
        long temp = db.insert(TAB_TAREO,TAB_TAREO_ID,values);
        db.close();
        conn.close();
        return temp;
    }

    public long insert(TareoVO tareoVO){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        int idLabor = tareoVO.getLaborVO()!=null?tareoVO.getLaborVO().getId():0;
        int idLote = tareoVO.getLoteVO()!=null?tareoVO.getLoteVO().getId():0;
        int idCCoste = tareoVO.getCentroCosteVO()!=null?tareoVO.getCentroCosteVO().getId():0;
        int idFundo = tareoVO.getFundoVO()!=null?tareoVO.getFundoVO().getId():0;

        values.put(TAB_TAREO_ID,tareoVO.getId());
        values.put(TAB_TAREO_IDLABOR,idLabor);
        values.put(TAB_TAREO_IDLOTE,idLote);
        values.put(TAB_TAREO_IDCCOSTE,idCCoste);
        values.put(TAB_TAREO_IDFUNDO,idFundo);
        values.put(TAB_TAREO_ISASISTENCIA,tareoVO.isAsistencia());
        values.put(TAB_TAREO_DATESTART,tareoVO.getDateTimeStart());
        values.put(TAB_TAREO_DATEEND,tareoVO.getDateTimeEnd());
        values.put(TAB_TAREO_PRODUCTIVIDAD,tareoVO.getProductividad());
        values.put(TAB_TAREO_ISACTIVE,tareoVO.isActive());

        long temp = db.insert(TAB_TAREO,TAB_TAREO_ID,values);


        if(temp>0){
            for(TareoDetalleVO td : tareoVO.getTareoDetalleVOList()){
                new TareoDetalleDAO(ctx).insert(td);
            }
        }

        db.close();
        conn.close();
        return temp;
    }

    public TareoVO selectById(int id){
        ConexionSQLiteHelper c=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );

        SQLiteDatabase db = c.getReadableDatabase();
        TareoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    _SELECT+
                            "*"+
                        _FROM+
                            TAB_TAREO +" as E "+
                        _WHERE+
                            "E."+TAB_TAREO_ID+" = "+ id
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

    public int deleteById(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        String[] args = {
                String.valueOf(id)
        };
        int i =    db.delete(TAB_TAREO,TAB_TAREO_ID+"=?",args);
        new TareoDetalleDAO(ctx).deleteByIdTareo(id);
        db.close();
        c.close();
        return i;
    }


    public List<TareoVO> listAll(){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<TareoVO> tareoVOS = new ArrayList<>();
        try{
            Cursor cursor= db.rawQuery(
                    _SELECT+
                        "*"+
                    _FROM+
                            TAB_TAREO,
                    null
            );
            Log.d(TAG,"cantidad de tareos "+cursor.getCount());
            while(cursor.moveToNext()){
                TareoVO temp = getAtributtes(cursor);
                tareoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,TAG+" listAll "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG," listAll "+e.toString());
        }finally {
            db.close();
            c.close();
        }
        return tareoVOS;
    }

    private TareoVO getAtributtes(Cursor cursor){
         TareoVO tareoVO = new TareoVO();
        String[] columnNames = cursor.getColumnNames();
        for(String name : columnNames){
           switch (name){
               case TAB_TAREO_ID:
                   tareoVO.setId(cursor.getInt(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREO_IDLABOR:
                   tareoVO.setLaborVO(new LaborDAO(ctx).selectById(cursor.getInt(cursor.getColumnIndex(name))));
                   break;
               case TAB_TAREO_IDLOTE:
                   tareoVO.setLoteVO(new LoteDAO(ctx).selectById(cursor.getInt(cursor.getColumnIndex(name))));
                   if(tareoVO.getLoteVO()!=null){
                       tareoVO.setFundoVO(new FundoDAO(ctx).selectByIdLote(tareoVO.getLoteVO().getId()));
                       tareoVO.setEmpresaVO(new EmpresaDAO(ctx).selectByIdLote(tareoVO.getLoteVO().getId()));
                       tareoVO.setCultivoVO(new CultivoDAO(ctx).selectByIdLote(tareoVO.getLoteVO().getId()));
                   }
                   break;
               case TAB_TAREO_IDCCOSTE:
                   tareoVO.setCentroCosteVO(new CentroCosteDAO(ctx).selectById(cursor.getInt(cursor.getColumnIndex(name))));
                   if(tareoVO.getCentroCosteVO()!=null){
                       tareoVO.setEmpresaVO(new EmpresaDAO(ctx).selectByIdCCoste(tareoVO.getCentroCosteVO().getId()));
                   }
                   break;
               case TAB_TAREO_IDFUNDO:
                   tareoVO.setFundoVO(new FundoDAO(ctx).selectById(cursor.getInt(cursor.getColumnIndex(name))));
                   if(tareoVO.getFundoVO()!=null){
                       tareoVO.setEmpresaVO(new EmpresaDAO(ctx).selectByIdFundo(tareoVO.getFundoVO().getId()));
                   }
                   break;
               case TAB_TAREO_DATESTART:
                   tareoVO.setDateTimeStart(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREO_DATEEND:
                   tareoVO.setDateTimeEnd(cursor.getString(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREO_PRODUCTIVIDAD:
                   tareoVO.setProductividad(cursor.getFloat(cursor.getColumnIndex(name)));
                   break;
               case TAB_TAREO_ISACTIVE:
                   tareoVO.setActive(cursor.getInt(cursor.getColumnIndex(name))>0);
                   break;
               case TAB_TAREO_ISASISTENCIA:
                   tareoVO.setAsistencia(cursor.getInt(cursor.getColumnIndex(name))>0);
                   break;
               default:
                   Toast.makeText(ctx,TAG+" getAtributes error no se encuentra campo "+name,Toast.LENGTH_LONG).show();
                   Log.d(TAG," getAtributes error no se encuentra campo "+name);
                   break;
           }
        }
        tareoVO.setTareoDetalleVOList(new TareoDetalleDAO(ctx).listByIdTareo(tareoVO.getId()));
        return tareoVO;
    }

}

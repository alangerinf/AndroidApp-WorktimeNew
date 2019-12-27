package com.ibao.alanger.worktimecopa.database.webserver.read;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ibao.alanger.worktimecopa.R;
import com.ibao.alanger.worktimecopa.app.AppController;
import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.database.webserver.ConectionConfig;
import com.ibao.alanger.worktimecopa.models.DAO.CultivoDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO_COD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO_HASLABOR;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO_ID;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_CULTIVO_STATUS;
import static com.ibao.alanger.worktimecopa.database.webserver.ConectionConfig.URL_DOWN_CULTUVOS;


public class DownloaderCultivos implements Downloader{

    Context ctx;

    public static int STATUS;

    public static String TAG = DownloaderCultivos.class.getSimpleName();

    public DownloaderCultivos(Context ctx){
        STATUS = ConectionConfig.STATUS_CREATED;
        this.ctx = ctx;
    }

    @Override
    public int getStatus() {
        return STATUS;
    }

    @Override
    public void download(){
        STATUS =ConectionConfig.STATUS_STARTED;

        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWN_CULTUVOS,
                response -> {
                    try {
                        JSONArray main = new JSONArray(response);
                        if(main.length()>0){
                            new CultivoDAO(ctx).dropTable();
                            STATUS = ConectionConfig.STATUS_PROCESSING;
                        }

                        final String SQLINSERT =  "INSERT INTO " +
                                TAB_CULTIVO+
                                "("+
                                TAB_CULTIVO_ID+","+
                                TAB_CULTIVO_COD+","+
                                TAB_CULTIVO_NAME+","+
                                TAB_CULTIVO_HASLABOR+", "+
                                TAB_CULTIVO_STATUS+" "+
                                ")"+
                                "VALUES ";

                        String insert = SQLINSERT;

                        for(int i=0;i<main.length();i++){
                            JSONObject data = new JSONObject(main.get(i).toString());
                            int id = data.getInt("id");
                            String cod = " ";
                            String name = data.getString("nombre");
                            int hasLabor = data.getInt("hasLabor");
                            int status = 1;

                            Log.d(TAG,"INSERTING :"+id+" "+cod+" "+name+" "+hasLabor+" "+status);

                            insert=insert +
                                    "("+
                                    id+","+
                                    "\""+cod+"\""+","+
                                    "\""+name+"\""+","+
                                    hasLabor+","+
                                    status+
                                    ")";

                            if(i%1000==0&& i>0){
                                try{
                                    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                    SQLiteDatabase db = conn.getWritableDatabase();
                                    db.execSQL(insert);
                                    db.close();
                                    conn.close();
                                    insert = SQLINSERT;

                                }catch (Exception e){
                                    Log.d(TAG,e.toString());
                                }
                            }else {
                                if(main.length()-1!=i ){
                                    insert=insert+",";
                                }
                            }
                        }
                        try{
                            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                            SQLiteDatabase db = conn.getWritableDatabase();
                            db.execSQL(insert);
                            db.close();
                            conn.close();
                        }catch (Exception e){
                            Log.e(TAG,e.toString());
                        }
                    STATUS =ConectionConfig.STATUS_FINISHED;
                    } catch (JSONException e) {
                        Log.e(TAG,e.toString());
                        STATUS =ConectionConfig.STATUS_ERROR_PARSE;
                    }
                },
                error -> {

                    Log.e(TAG,error.toString());

                    Toast.makeText(ctx,TAG+ ctx.getString(R.string.error_conexion_servidor), Toast.LENGTH_LONG).show();
                    STATUS =ConectionConfig.STATUS_ERROR_HTTP_ERROR;

                }){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);

    }

}

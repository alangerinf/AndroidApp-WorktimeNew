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
import com.ibao.alanger.worktimecopa.models.DAO.TrabajadorDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_TRABAJADOR;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_TRABAJADOR_COD;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_TRABAJADOR_DNI;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_TRABAJADOR_NAME;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.TAB_TRABAJADOR_SUSPENCION;
import static com.ibao.alanger.worktimecopa.database.webserver.ConectionConfig.URL_DOWN_TRABAJADORES;


public class DownloaderTrabajadores implements Downloader{

    Context ctx;

    public static int STATUS;

    public static String TAG = DownloaderTrabajadores.class.getSimpleName();

    public DownloaderTrabajadores(Context ctx){
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
                URL_DOWN_TRABAJADORES,
                response -> {
                    try {
                        JSONArray main = new JSONArray(response);
                        if(main.length()>0){
                            new TrabajadorDAO(ctx).dropTable();
                            STATUS = ConectionConfig.STATUS_PROCESSING;
                        }

                        final String SQLINSERT =  "INSERT INTO " +
                                TAB_TRABAJADOR+
                                "("+
                                TAB_TRABAJADOR_COD+","+
                                TAB_TRABAJADOR_DNI+","+
                                TAB_TRABAJADOR_NAME+", "+
                                TAB_TRABAJADOR_SUSPENCION+" "+
                                ")"+
                                "VALUES ";
                        String insert = SQLINSERT;

                        for(int i=0;i<main.length();i++){
                            JSONObject data = new JSONObject(main.get(i).toString());

                            /**
                             * ,"codigo":"48745924","nroDocumento":"48745924","nombre":"ACHARTI SIGUAS, MIGUEL ALFREDO"}
                             */
                            String codigo = data.getString("codigo");
                            String nroDocumento = data.getString("nroDocumento");
                            String nombre = data.getString("nombre");
                            String suspencion = data.getString("suspencion");

                            Log.d(TAG,"INSERTING :"+codigo+" "+nroDocumento+" "+nombre+" "+suspencion);

                            insert=insert +
                                    "("+
                                    "\""+codigo+"\""+","+
                                    "\""+nroDocumento+"\""+","+
                                    "\""+nombre+"\""+","+
                                    "\""+suspencion+"\""+
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
            /*
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                User temp = SharedPreferencesManager.getUser(ctx);

                params.put("id", String.valueOf(temp.getId()));
                params.put("idInspector", String.valueOf(temp.getToken()));

                return params;
            }

             */

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

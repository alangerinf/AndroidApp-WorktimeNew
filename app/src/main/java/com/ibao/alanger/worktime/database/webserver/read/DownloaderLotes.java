package com.ibao.alanger.worktime.database.webserver.read;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.app.AppController;
import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.models.DAO.LoteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_IDCULTIVO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_IDFUNDO;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_NUM;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_LOTE_STATUS;
import static com.ibao.alanger.worktime.database.webserver.ConectionConfig.URL_DOWN_LOTES;


public class DownloaderLotes implements Downloader{

    Context ctx;

    public static int STATUS;

    public static String TAG = DownloaderLotes.class.getSimpleName();

    public DownloaderLotes(Context ctx){
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
                URL_DOWN_LOTES,
                response -> {
                    try {
                        JSONArray main = new JSONArray(response);
                        if(main.length()>0){
                            new LoteDAO(ctx).dropTable();
                            STATUS = ConectionConfig.STATUS_PROCESSING;
                        }

                        final String SQLINSERT =  "INSERT INTO " +
                                TAB_LOTE+
                                "("+
                                TAB_LOTE_ID+","+
                                TAB_LOTE_IDFUNDO+","+
                                TAB_LOTE_IDCULTIVO+","+
                                TAB_LOTE_NUM+", "+
                                TAB_LOTE_COD+", "+
                                TAB_LOTE_STATUS+" "+
                                ")"+
                                "VALUES ";
                        String insert = SQLINSERT;

                        for(int i=0;i<main.length();i++){
                            JSONObject data = new JSONObject(main.get(i).toString());
                            int id = data.getInt("id");
                            int idFundo = data.getInt("idFundo");
                            int idCultivo = data.getInt("idCultivo");
                            String numero = data.getString("numeroLote");
                            String codigo = data.getString("codigo");
                            int status = 1;

                            Log.d(TAG,"INSERTING :"+id+" "+idFundo+" "+idCultivo+" "+numero+" "+codigo+" "+status);

                            insert=insert +
                                    "("+
                                    id+","+
                                    idFundo+","+
                                    idCultivo+","+
                                    "\""+numero+"\""+","+
                                    "\""+codigo+"\""+","+
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

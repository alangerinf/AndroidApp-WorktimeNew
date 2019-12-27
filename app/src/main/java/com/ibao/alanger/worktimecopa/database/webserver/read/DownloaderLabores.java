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
import com.ibao.alanger.worktimecopa.models.DAO.LaborDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
import static com.ibao.alanger.worktimecopa.database.webserver.ConectionConfig.URL_DOWN_LABOR;


public class DownloaderLabores implements Downloader{

    Context ctx;

    public static int STATUS;

    public static String TAG = DownloaderLabores.class.getSimpleName();

    public DownloaderLabores(Context ctx){
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
                URL_DOWN_LABOR,
                response -> {
                    try {
                        JSONArray main = new JSONArray(response);
                        if(main.length()>0){
                            new LaborDAO(ctx).dropTable();
                            STATUS = ConectionConfig.STATUS_PROCESSING;
                        }


                        final String SQLINSERT =  "INSERT INTO " +
                                TAB_LABOR+
                                "("+
                                TAB_LABOR_ID+","+
                                TAB_LABOR_COD+","+
                                TAB_LABOR_NAME+","+
                                TAB_LABOR_ISDIRECT+", "+
                                TAB_LABOR_THEORICALCOST+", "+
                                TAB_LABOR_THEORICALHOURS+", "+
                                TAB_LABOR_ISTAREA+", "+
                                TAB_LABOR_IDACTIVIDAD+", "+
                                TAB_LABOR_LISTIDCULTIVO+", "+
                                TAB_LABOR_MAGNITUD+", "+
                                TAB_LABOR_STATUS+" "+
                                ")"+
                                "VALUES ";
                        String insert = SQLINSERT;

                        for(int i=0;i<main.length();i++){
                            JSONObject data = new JSONObject(main.get(i).toString());
                            int id = data.getInt("id");
                            int idActividad = data.getInt("idActividad");
                            String listIdCultivo = data.getString("listIdCultivo");
                            String codigo = data.getString("codigo");
                            String nombre = data.getString("nombre");
                            int isDirect = data.getInt("isDirect");
                            int isTarea = data.getInt("isTarea");
                            double theoricalHours = data.getDouble("theoricalHours");
                            double theoricalCost = data.getDouble("theoricalCost");
                            String magnitud = data.getString("magnitud");
                            int status = 1;

                            Log.d(TAG,"INSERTING :"+id+" "+idActividad+" "+codigo+" "+nombre+" "+status);

                            insert=insert +
                                    "("+
                                    id+","+
                                    "\""+codigo+"\""+","+
                                    "\""+nombre+"\""+","+
                                    isDirect+","+
                                    theoricalCost+","+
                                    theoricalHours+","+
                                    isTarea+","+
                                    idActividad+","+
                                    "\""+listIdCultivo+"\""+","+
                                    "\""+magnitud+"\""+","+
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

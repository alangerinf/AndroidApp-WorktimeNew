package com.ibao.alanger.worktime.database.webserver.read;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.app.AppController;
import com.ibao.alanger.worktime.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktime.database.SharedPreferencesManager;
import com.ibao.alanger.worktime.models.DAO.EmpresaDAO;
import com.ibao.alanger.worktime.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ibao.alanger.worktime.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktime.database.DataBaseDesign.DATABASE_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_COD;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_ID;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_NAME;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_RAZON;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_RUC;
import static com.ibao.alanger.worktime.database.DataBaseDesign.TAB_EMPRESA_STATUS;
import static com.ibao.alanger.worktime.database.webserver.Config.URL_DOWN_EMPRESAS;


public class DownloaderEmpresas {

    Context ctx;

    public static int status;

    public static String TAG = DownloaderEmpresas.class.getSimpleName();

    public DownloaderEmpresas(Context ctx){
        status=0;
        this.ctx = ctx;
    }

    public void download(){
        status=1;

        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWN_EMPRESAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
     //                   progress.dismiss();
                        try {
                            JSONArray main = new JSONArray(response);
                            if(main.length()>0){
                                new EmpresaDAO(ctx).dropTable();
                                status=2;
                            }

                            String insert = "INSERT INTO " +
                                    TAB_EMPRESA+
                                    "("+
                                    TAB_EMPRESA_ID+","+
                                    TAB_EMPRESA_COD+","+
                                    TAB_EMPRESA_RAZON+","+
                                    TAB_EMPRESA_RUC+","+
                                    TAB_EMPRESA_NAME+","+
                                    TAB_EMPRESA_STATUS+" "+
                                    ")"+
                                    "VALUES ";

                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                String cod = data.getString("cod");
                                String razon = data.getString("razon");
                                String ruc = data.getString("ruc");
                                String name = data.getString("name");
                                boolean status = data.getBoolean("status");

                               // Log.d(TAG,"fila "+i+" : "+id+" "+nombre);
                                /*
                                if(new ZonaDAO(ctx).insertarZona(id,nombre)){

                                    Log.d("ZONADOWN","logro insertar"+id);
                                }
                                */

                                insert=insert +
                                        "("+
                                        id+","+
                                        "\""+cod+"\""+","+
                                        "\""+razon+"\""+","+
                                        "\""+ruc+"\""+","+
                                        "\""+name+"\""+","+
                                        status+
                                        ")";

                                if(i%1000==0&& i>0){
                                    try{
                                        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                        SQLiteDatabase db = conn.getWritableDatabase();
                                        db.execSQL(insert);
                                        db.close();
                                        conn.close();
                                        insert = "INSERT INTO " +
                                                TAB_EMPRESA+
                                                "("+
                                                TAB_EMPRESA_ID+","+
                                                TAB_EMPRESA_COD+","+
                                                TAB_EMPRESA_RAZON+","+
                                                TAB_EMPRESA_RUC+","+
                                                TAB_EMPRESA_NAME+","+
                                                TAB_EMPRESA_STATUS+" "+
                                                ")"+
                                                "VALUES ";
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
                                Log.d(TAG,e.toString());
                            }
                        status=3;
                        } catch (JSONException e) {
                            Log.d(TAG,e.toString());
                            status=-1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
       //                 progress.dismiss();
                        Log.d(TAG,error.toString());
                        Toast.makeText(ctx,TAG+ ctx.getString(R.string.error_conexion_servidor), Toast.LENGTH_LONG).show();
                        status=-2;
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                User temp = SharedPreferencesManager.getUser(ctx);

                params.put("id", String.valueOf(temp.getId()));
                params.put("idInspector", String.valueOf(temp.getToken()));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);

    }

}

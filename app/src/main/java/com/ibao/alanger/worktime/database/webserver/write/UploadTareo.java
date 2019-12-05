package com.ibao.alanger.worktime.database.webserver.write;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibao.alanger.worktime.app.AppController;
import com.ibao.alanger.worktime.database.SharedPreferencesManager;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;

import static com.ibao.alanger.worktime.database.webserver.ConectionConfig.URL_UP_TAREO;

public class UploadTareo {


    private String TAG = UploadTareo.class.getSimpleName();
    private Context ctx;
    private static int STATUS;

    public UploadTareo(Context ctx)
    {
        STATUS = ConectionConfig.STATUS_CREATED;
        this.ctx = ctx;
    }

    public static int getStatus(){
        return STATUS;
    }

    public void upload(final List<TareoVO> tareoVOS){
        Log.d(TAG,"started");

        STATUS = ConectionConfig.STATUS_STARTED;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_UP_TAREO,
                response -> {
                  //  progress.dismiss();
                    Log.d(TAG,"resp: "+response);
                    STATUS =ConectionConfig.STATUS_PROCESSING;

                    try {
                        JSONObject main = new JSONObject(response);

                        if(main.getInt("success")==1){
                            JSONArray arrayTareos = main.getJSONArray("data");
                            for (int i=0; i<arrayTareos.length();i++){

                                String temp = arrayTareos.getString(i);
                                String[] IDS = temp.split(",");

                                int idInternal = Integer.valueOf(IDS[0]);
                                int idWeb = Integer.valueOf(IDS[1]);

                                //updateIDWEB
                                new TareoDAO(ctx).updateIdWeb(idInternal,idWeb);

                            }
                        }


                        for (TareoVO t : tareoVOS) {
                            Log.d(TAG, "tareo: " + t.toString());

                            boolean isEnd = false;

                            if (t.getDateTimeEnd() != null) {
                                Log.d(TAG, "dateEnd not is null");
                                if (!t.getDateTimeEnd().isEmpty()) {
                                    isEnd = true;
                                }
                            }

                            if (isEnd || !t.isActive()) { // si ya esta marcado como salida o esta boorado
                                new TareoDAO(ctx).deleteById(t.getId());
                            }
                        }
                    }catch (JSONException e) {
                        STATUS = ConectionConfig.STATUS_ERROR_PARSE;
                        Log.e(TAG,e.toString());
                        e.printStackTrace();
                    }
                    STATUS = ConectionConfig.STATUS_FINISHED;
                },
                error -> {

                    Log.d(TAG,error.toString());
                    Toast.makeText(ctx,"Error al conectarse, verifique su conexion con el servidor", Toast.LENGTH_LONG).show();
                    STATUS = ConectionConfig.STATUS_ERROR_HTTP_ERROR;

                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                //informacion del usuario
                Gson gson = new Gson();
                String usuarioJson = gson.toJson(SharedPreferencesManager.getUser(ctx));
                params.put("usuario",usuarioJson);

                //visitas
                gson = new Gson();

                String dataJson = gson.toJson(
                        tareoVOS,
                        new TypeToken<ArrayList<TareoVO>>() {}.getType());
                params.put("data",dataJson);

                Log.d(TAG,"usuarioJson:"+usuarioJson);
                Log.d(TAG,"dataJson:"+dataJson);
                return params;
            }

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
package com.ibao.alanger.worktime.login.repository;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.ibao.alanger.worktime.database.SharedPreferencesManager;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.app.AppController;
import com.ibao.alanger.worktime.login.interactor.LoginInteractor;
import com.ibao.alanger.worktime.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

public class LoginRepositoryImpl implements LoginRepository {

    private LoginInteractor interactor;


    public static String POST_USER = "usuario";
    public static String POST_PASS = "password";


    String TAG = LoginRepositoryImpl.class.getSimpleName();
    public LoginRepositoryImpl(LoginInteractor interactor) {

        this.interactor = interactor;
    }

    @Override
    public void signIn(String user, String password) {
        Log.d(TAG, "sign in " + user + " " + password);


        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                ConectionConfig.POST_LOGIN,
                response -> {
                    Log.d(TAG, "resp:"+  response);

                    /**
                     * {"success":1,"login":[{"id":2,"user":"21825288","idSupervisor":"66","idFundo":"2,3","name":"RAMOS AGUILAR, ANGEL
                     * JOSE"}]}
                     */
                    try {
                        JSONObject data = new JSONObject(response);
                        int success = data.getInt("success");
                        if (success == 1) {
                            Log.d(TAG,"success1");
                            Log.d(TAG,user);
                            Log.d(TAG,password);

                            JSONArray main = data.getJSONArray("login");
                            if(main.length()>0){
                                JSONObject usuario = main.getJSONObject(0);//traerse el primer usuario
                                User userTemp = new User();
                                userTemp.setId(Integer.parseInt(usuario.getString("id")));
                                userTemp.setUser(user);
                                userTemp.setPassword(password);
                                userTemp.setName(usuario.getString("name"));
                                userTemp.setFundos(usuario.getString("idFundo"));
                                userTemp.setIdSupervisor(usuario.getInt("idSupervisor"));
                                userTemp.setPermisoManual(usuario.getInt("permisoManual")>0);
                                interactor.signSuccess(userTemp);
                            }else {
                                interactor.signError("Lista de usuarios vacia");
                            }


                        }else {
                            interactor.signError("Verfique sus Credenciales");
                        }

                    } catch (JSONException e) {
                        interactor.signError(e.toString());
                        e.printStackTrace();
                    }
                }, error -> {

                    Log.e(TAG,error.toString());
                    interactor.signError(error.toString());
                    error.printStackTrace();
                }

        ){

            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(POST_USER, user);
                params.put(POST_PASS, password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };


        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}

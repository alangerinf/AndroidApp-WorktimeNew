package com.ibao.alanger.worktimecopa.login.repository;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ibao.alanger.worktimecopa.database.webserver.ConectionConfig;
import com.ibao.alanger.worktimecopa.app.AppController;
import com.ibao.alanger.worktimecopa.login.interactor.LoginInteractor;
import com.ibao.alanger.worktimecopa.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

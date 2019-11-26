package com.ibao.alanger.worktime.login.repository;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.app.AppController;
import com.ibao.alanger.worktime.login.interactor.LoginInteractor;
import com.ibao.alanger.worktime.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import static java.net.HttpURLConnection.HTTP_OK;

public class LoginRepositoryImpl implements LoginRepository {

    private LoginInteractor interactor;

    String TAG = LoginRepositoryImpl.class.getSimpleName();
    public LoginRepositoryImpl(LoginInteractor interactor) {

        this.interactor = interactor;
    }

    @Override
    public void signIn(String user, String password) {
        Log.d(TAG, "sign in " + user + " " + password);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", user);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConectionConfig.POST_LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            int codigoRespuesta = response.getInt("codigoRespuesta");
                            if(codigoRespuesta==HTTP_OK) {
                                JSONObject datos = new JSONObject(String.valueOf(response.getJSONObject("datos")));
                                String token = datos.getString("token");
                                JSONObject usuario = datos.getJSONObject("usuario");

                                User userTemp = new User();
                                userTemp.setId(Integer.parseInt(usuario.getString("ID")));
                                userTemp.setUser(user);
                                userTemp.setPassword(password);
                                userTemp.setName(usuario.getString("NOMBRE"));
                                userTemp.setToken(token);

                                interactor.signSuccess(userTemp);
                            }else {
                                interactor.signError("Codigo "+codigoRespuesta+": "+response.getString("mensajeRespuesta"));
                            }

                        } catch (JSONException e) {
                            interactor.signError(e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        interactor.signError(error.toString());
                        error.printStackTrace();
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}

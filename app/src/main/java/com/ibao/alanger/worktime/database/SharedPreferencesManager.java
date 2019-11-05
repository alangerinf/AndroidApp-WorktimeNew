package com.ibao.alanger.worktime.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.ibao.alanger.worktime.models.User;

import java.util.Map;

public class SharedPreferencesManager {

    private static String TAG = SharedPreferencesManager.class.getSimpleName();
    private static String namePreferences_userdata = "data";

    //data user
    private static String user_id = "id";
    private static String user_user = "user";
    private static String user_name = "name";
    private static String user_password = "password";
    private static String user_token = "token";

    public static boolean saveUser(Context ctx, User user){
        boolean flag = false;
        try{
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(user_id,user.getId());
            editor.putString(user_user,user.getUser());
            editor.putString(user_password,user.getPassword());
            editor.putString(user_token,user.getToken());
            editor.putString(user_name,user.getName());
            flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"saveUser:"+e.toString());
            Toast.makeText(ctx,"saveUser:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static User getUser(Context ctx){
        User user = null;
            try {
                SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
                if(
                        preferences.contains(user_id)
                        &&
                        preferences.contains(user_user)
                        &&
                        preferences.contains(user_password)
                        &&
                        preferences.contains(user_token)
                        &&
                        preferences.contains(user_name)
                ){
                    //user.setId(preferences.getInt(user_id,0));
                    user = new User();
                    user.setId(12);
                    user.setUser(preferences.getString(user_user,""));
                    user.setPassword(preferences.getString(user_password,""));
                    user.setToken(preferences.getString(user_token,""));
                    user.setName(preferences.getString(user_name,""));
                    Log.d(TAG,"getUser token "+user.getToken() );
                }
            }catch (Exception e) {
                Log.d(TAG,"getUser:" + e.toString()) ;
                Toast.makeText(ctx, "getUser:" + e.toString(), Toast.LENGTH_LONG).show();
            }

        return user;
    }

    public static boolean deleteUser(Context ctx){
        boolean flag = false;
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            flag = editor.commit(); // commit changes
        }catch (Exception e) {
            Toast.makeText(ctx, "deleteUser:" + e.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG,"deleteUser: "+e.toString());
        }
        return flag;
    }

    public static Map<String,?> getMapUser(Context ctx){
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            Map<String,?> map = preferences.getAll();
            return  map;
        }catch (Exception e) {
            Toast.makeText(ctx, "getMapUser:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

}

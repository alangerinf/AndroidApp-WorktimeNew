package com.ibao.alanger.worktimecopa.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.ibao.alanger.worktimecopa.R;
import com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper;
import com.ibao.alanger.worktimecopa.login.view.LoginActivity;
import com.ibao.alanger.worktimecopa.database.SharedPreferencesManager;
import com.ibao.alanger.worktimecopa.views.main.MainActivity;
import com.ibao.alanger.worktimecopa.models.User;

import static com.ibao.alanger.worktimecopa.database.ConexionSQLiteHelper.VERSION_DB;
import static com.ibao.alanger.worktimecopa.database.DataBaseDesign.DATABASE_NAME;


public class ActivityPreloader extends Activity {

    static LottieAnimationView lAVbackground;
    static ImageView iViewLogoEmpresa;
    static TextView tViewlogo_p1;
    static TextView tViewlogo_p2;
    static Context ctx;

    String TAG = "TAG"+ActivityPreloader.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        declare();
        startAnimations();

        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

    }

    private void declare() {
        ctx = this;
        lAVbackground = findViewById(R.id.lottie);
        iViewLogoEmpresa = findViewById(R.id.iViewLogoEmpresa);
        tViewlogo_p1 = findViewById(R.id.tViewlogo_p1);
        tViewlogo_p2 = findViewById(R.id.tViewlogo_p2);




    }

    void startAnimations(){
        final Animation animLayout =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);

        Handler handler = new Handler();
        handler.post(
                () -> {
                    lAVbackground.startAnimation(animLayout);
                    lAVbackground.setVisibility(View.VISIBLE);
                }
        );

        final Animation anim_rightFadeIn1 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.right_fade_in);

        Handler handler1 = new Handler();
        handler1.post(
                () -> {
                    iViewLogoEmpresa.startAnimation(anim_rightFadeIn1);
                    iViewLogoEmpresa.setVisibility(View.VISIBLE);
                }
        );
        final Animation anim_rightFadeIn2 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.top_fade_in);
        Handler handler2 = new Handler();
        handler2.postDelayed(
                () -> {
                    tViewlogo_p1.startAnimation(anim_rightFadeIn2);
                    tViewlogo_p1.setVisibility(View.VISIBLE);
                },500
        );
        final Animation anim_rightFadeIn3 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.top_fade_in);
        Handler handler3 = new Handler();
        handler3.postDelayed(
                () -> {
                    tViewlogo_p2.startAnimation(anim_rightFadeIn3);
                    tViewlogo_p2.setVisibility(View.VISIBLE);
                },600
        );

        Handler handler4 = new Handler();
        handler4.postDelayed(()->
            {
                User user = SharedPreferencesManager.getUser(ctx);
                if(user!=null){
                    Log.d(TAG,"go to main");
                    startActivity(new Intent(this, MainActivity.class));
                }else {
                    Log.d(TAG,"go to login");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            },2000);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and STATUS bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


}

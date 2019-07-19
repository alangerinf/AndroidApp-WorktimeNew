package com.ibao.alanger.worktime.login.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.login.presenter.LoginPresenter;
import com.ibao.alanger.worktime.login.presenter.LoginPresenterImpl;
import com.ibao.alanger.worktime.views.MainActivity;


public class LoginActivity extends Activity implements LoginView{


    private LoginPresenter presenter;

    //contexto
    private Context ctx;
    private ProgressBar progressBar;
    static LottieAnimationView lAVbackground;
    //boton enter
    private Button btnLogin;

    //campos de acceso
    private EditText eTextUser, eTextPassword;
    //animacion  de boton
    private Animation animBtn;
    //boton de ver password
    private ImageView iViewPasswordSetVisible;

    private Animation animLayout;
    private ConstraintLayout constCombo;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx =  LoginActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        declaration();
        defineEvents();
        enterAnimation();

    }

    void declaration(){

        lAVbackground = findViewById(R.id.lottie);
        presenter = new LoginPresenterImpl(this);
        btnLogin    = findViewById(R.id.btnLogin);
        eTextUser   = findViewById(R.id.eTextEmail);
        eTextPassword   = findViewById(R.id.eTextPassword);
        iViewPasswordSetVisible = findViewById(R.id.iViewPassword);
        animBtn     = android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.press_btn);
        animLayout  = android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.left_in);
        constCombo  = findViewById(R.id.constCombo);
        progressBar = findViewById(R.id.progressBar);


        defaultAttributes();
    }

    void defaultAttributes(){
        handler.post(
                () -> {
                    constCombo.setVisibility(View.INVISIBLE);
                    hideProgressBar();
                }
        );

    }

    void defineEvents(){
        btnLogin.setOnClickListener(v -> {
            v.startAnimation(animBtn);
            handler.postDelayed(
                    () -> {
                        presenter.signIn(
                                eTextUser.getText().toString(),
                                eTextPassword.getText().toString()
                                );

                    },200
            );
        });

        iViewPasswordSetVisible.setOnClickListener(v -> {
            v.startAnimation(animBtn);
            if(eTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                eTextPassword.setInputType( InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else {
                eTextPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            }
            eTextPassword.setSelection(eTextPassword.getText().length());
        });



    }


    private void enterAnimation() {

        final Animation animLayout =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);

        Handler handler = new Handler();
        handler.post(
                () -> {
                    lAVbackground.startAnimation(animLayout);
                    lAVbackground.setVisibility(View.VISIBLE);
                }
        );


        handler.postDelayed(
                () -> {
                    constCombo.startAnimation(animLayout);
                    constCombo.setVisibility(View.VISIBLE);
                    enableInputs();
                },500
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        btnLogin.setClickable(true);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }


    @Override
    public void goRecoverPassword() {

    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        View viewTemp = findViewById(R.id.logo);
        ActivityOptions options = (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation
                (this,
                        Pair.create(viewTemp, viewTemp.getTransitionName())
                );
        startActivity(intent, options.toBundle());
    }

    @Override
    public void enableInputs() {
        handler.post(
                () -> {
                    btnLogin.setEnabled(true);
                    eTextUser.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    iViewPasswordSetVisible.setEnabled(true);
                    constCombo.setEnabled(true);
                }
        );
    }



    @Override
    public void disableInputs() {
        handler.post(
                () -> {
                    btnLogin.setEnabled(false);
                    eTextUser.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    iViewPasswordSetVisible.setEnabled(false);
                    constCombo.setEnabled(false);
                }
        );

    }

    @Override
    public void hideProgressBar() {
        handler.post(
                () -> {
                    progressBar.setVisibility(View.INVISIBLE);
                }
        );

    }

    @Override
    public void showProgressBar() {
        handler.post(
                () -> {
                    progressBar.setVisibility(View.VISIBLE);
                }
        );
    }

    @Override
    public void loginError(String error) {
        handler.post(
                () -> {
                    Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
                }
        );

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    onWindowFocusChanged(true);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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
                        // Hide the nav bar and status bar
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

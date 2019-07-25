package com.ibao.alanger.worktime.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.ibao.alanger.worktime.R;

import java.util.Objects;

public class CreateTareoActivity extends AppCompatActivity {



    private static String MY_CREATE_MODE = "";


    public final static String EXTRA_CREATE_MODE= "extra_mode";


    //modo de creacion segun quien lo llame
    public final static String CREATE_MODE_MAIN= "main";


    private static MaterialButton btnDone;
    private static LinearLayout selectorEmpresa;
    private static LinearLayout selectorFundo;
    private static LinearLayout selectorCultivo;
    private static LinearLayout selectorActividad;
    private static LinearLayout selectorAgrupadorActividad;
    private static LinearLayout selectorLote;
    private static LinearLayout selectorCentroCoste;


    private static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.createTareo);
        setContentView(R.layout.activity_create_tareo);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getBundle();

        declaration();
        events();
        openDialogSelectActivitdadType();
    }

    private void events() {
        btnDone.setOnClickListener(v->{

            goToTareo();
            btnDone.setFocusable(false);
            btnDone.setClickable(false);
            disableInputs();
        });
    }


    void enableInputs(){

        boolean flag = true;


        btnDone.setFocusable(flag);
        btnDone.setClickable(flag);

    }

    void disableInputs(){

        boolean flag = false;


        btnDone.setFocusable(flag);
        btnDone.setClickable(flag);

    }



    @Override
    protected void onResume() {
        super.onResume();
        enableInputs();
    }

    private void declaration() {
        ctx = this;


        selectorEmpresa = findViewById(R.id.ctareo_lLayoutEmpresa);
        selectorFundo = findViewById(R.id.ctareo_lLayoutFundo);
        selectorCultivo = findViewById(R.id.ctareo_lLayoutCultivo);
        selectorActividad = findViewById(R.id.ctareo_lLayoutActividad);
        selectorAgrupadorActividad = findViewById(R.id.ctareo_lLayoutAgrupadorActividad);
        selectorLote = findViewById(R.id.ctareo_lLayoutLote);
        selectorCentroCoste = findViewById(R.id.ctareo_lLayoutCentroCosto);


        btnDone = findViewById(R.id.ctareo_btnDone);

    }

    void getBundle(){
        Bundle b = getIntent().getExtras();
        assert b != null;
        MY_CREATE_MODE = b.getString(EXTRA_CREATE_MODE);

    }



    private int selectOnDialog =0; // used in openDialogSelectActivitdadType()
    protected void openDialogSelectActivitdadType(){


        String[] items = new String[2];
        items[0] = getString(R.string.directo);
        items[1] = getString(R.string.indirecto);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(R.string.seleccione_tipo_Actividad);

        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectOnDialog = which;
            }
        });
        builder.setPositiveButton(R.string.seleccionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selectOnDialog ==0){//direccto

                    selectorEmpresa.setVisibility(View.VISIBLE);
                    selectorFundo.setVisibility(View.VISIBLE);
                    selectorCultivo.setVisibility(View.VISIBLE);
                    selectorAgrupadorActividad.setVisibility(View.VISIBLE);
                    selectorActividad.setVisibility(View.VISIBLE);
                    selectorLote.setVisibility(View.VISIBLE);
                    selectorCentroCoste.setVisibility(View.GONE);

                }else {//indirecto

                    selectorEmpresa.setVisibility(View.VISIBLE);
                    selectorFundo.setVisibility(View.VISIBLE);
                    selectorCultivo.setVisibility(View.VISIBLE);
                    selectorAgrupadorActividad.setVisibility(View.VISIBLE);
                    selectorActividad.setVisibility(View.VISIBLE);
                    selectorLote.setVisibility(View.GONE);
                    selectorCentroCoste.setVisibility(View.VISIBLE);

                }
            }
        });
        builder.setNegativeButton(R.string.cancelar,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void goToTareo(){
        Intent i = new Intent(ctx, TareoActivity.class);
        startActivity(i);
        finish();
    }





}

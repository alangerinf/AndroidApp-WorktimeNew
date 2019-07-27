package com.ibao.alanger.worktime.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.Objects;

public class TareoActivity extends AppCompatActivity {


    private static Context ctx;

    private static FloatingActionButton fabAddTrabajador;
    private static FloatingActionButton fabOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        declaration();
        events();
    }



    int selectOnDialog=0;
    private void showOptions(){

        String[] items = new String[4];
        items[0] = getString(R.string.anhadir_productividad_grupal);
        items[1] = getString(R.string.transferencia);
        items[2] = getString(R.string.salida_personal);
        items[3] = getString(R.string.finalizar_labor);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(getString(R.string.seleccione_opcion));
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectOnDialog = which;
            }
        });
        builder.setPositiveButton(R.string.seleccionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (selectOnDialog){
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:

                        break;
                    case 3:
                        break;

                }
                enableInputs();
            }
        });
        builder.setNegativeButton(R.string.cancelar,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enableInputs();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

    }



    private void goToTransference(){
        Intent i = new Intent(ctx, TabbetActivity.class);
        startActivity(i);
    }


    private void events() {
        fabAddTrabajador.setOnClickListener(v->{
            goToTransference();
            disableInputs();
        });
        fabOptions.setOnClickListener(v->{
            showOptions();
            disableInputs();
        });
    }

    private void disableInputs() {
        boolean flag = false;
        fabAddTrabajador.setFocusable(flag);
        fabAddTrabajador.setClickable(flag);
    }


    @Override
    protected void onResume() {
        super.onResume();
        enableInputs();
    }

    private void enableInputs() {
        boolean flag = true;
        fabAddTrabajador.setFocusable(flag);
        fabAddTrabajador.setClickable(flag);
    }

    private void declaration() {
        ctx = this;
        fabAddTrabajador = findViewById(R.id.tareo_fabAdddTrabajador);
        fabOptions = findViewById(R.id.tareo_fabOptions);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

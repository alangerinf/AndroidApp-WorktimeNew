package com.ibao.alanger.worktime.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.adapters.RViewAdapterListTrabajadores;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.List;
import java.util.Objects;

public class TareoActivity extends AppCompatActivity {


    private static Context ctx;

    private static FloatingActionButton fabAddTrabajador;
    private static FloatingActionButton fabOptions;


    private static TextView tareo_Fundo;
    private static TextView tareo_Cultivo;
    private static TextView tareo_Labor;
    private static TextView tareo_dateTime;
    private static TextView tareo_costCenter;
    private static TextView tareo_nTrabajadores;
    private static TextView tareo_nHoras;

    private static RecyclerView tareo_rViewTActivos;
    private static RecyclerView tareo_rViewTInactivos;

    private static RViewAdapterListTrabajadores adapterActivo;
    private static RViewAdapterListTrabajadores adapterInactivo;

    public static final String EXTRA_TAREO = "tareo";

    private static TareoVO TAREOVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        declaration();
        events();

        cargarListas();
    }

    private void cargarListas() {
        adapterActivo = new RViewAdapterListTrabajadores(ctx,TAREOVO.getTareoDetalleVOList(),true);
        adapterInactivo = new RViewAdapterListTrabajadores(ctx,TAREOVO.getTareoDetalleVOList(),false);

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

        Bundle b = getIntent().getExtras();

        assert b != null;
        TAREOVO = (TareoVO) b.getSerializable(EXTRA_TAREO);

        tareo_Fundo = findViewById(R.id.tareo_Fundo);
        tareo_Cultivo = findViewById(R.id.tareo_Cultivo);
        tareo_Labor = findViewById(R.id.tareo_Labor);
        tareo_dateTime = findViewById(R.id.tareo_dateTime);
        tareo_costCenter = findViewById(R.id.tareo_costCenter);
        tareo_nTrabajadores = findViewById(R.id.tareo_nTrabajadores);
        tareo_nHoras = findViewById(R.id.tareo_nHoras);
        tareo_rViewTActivos = findViewById(R.id.tareo_rViewTActivos);
        tareo_rViewTInactivos = findViewById(R.id.tareo_rViewTInactivos);

        tareo_nTrabajadores.setText(""+TAREOVO.getTareoDetalleVOList().size());
        tareo_dateTime.setText(TAREOVO.getDateTimeStart());
        tareo_Fundo.setText(TAREOVO.getFundoVO().getName());
        if(TAREOVO.isAsistencia()){
            tareo_Labor.setText(getString(R.string.control_de_asistencia));
            tareo_Cultivo.setText(TAREOVO.getEmpresaVO().getName());
            tareo_costCenter.setText(getString(R.string.asistencia));
        }else {
            tareo_Labor.setText(TAREOVO.getLaborVO().getName());
            tareo_Fundo.setText(TAREOVO.getFundoVO().getName());
            tareo_Cultivo.setText(TAREOVO.getCultivoVO().getName());
            if(TAREOVO.getLaborVO().isDirecto()){
                tareo_costCenter.setText(TAREOVO.getLoteVO().getCod());
            }else {
                tareo_costCenter.setText(TAREOVO.getCentroCosteVO().getName());
            }
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

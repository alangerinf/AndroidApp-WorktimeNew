package com.ibao.alanger.worktime.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.adapters.RViewAdapterListTrabajadoresInactive;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.DAO.TareoDetalleDAO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.Objects;

import static com.ibao.alanger.worktime.views.ProductividadActivity.EXTRA_TAREODETALLE;
import static com.ibao.alanger.worktime.views.transference.TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR;

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

    private static RViewAdapterListTrabajadoresInactive adapterActivo;
    private static RViewAdapterListTrabajadoresInactive adapterInactivo;

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
        adapterActivo = new RViewAdapterListTrabajadoresInactive(ctx,TAREOVO.getTareoDetalleVOList(),true);
        adapterActivo.setOnClicListener(v->{

            int pos = tareo_rViewTActivos.getChildAdapterPosition(v);
            int i=0;
            for(TareoDetalleVO ta : TAREOVO.getTareoDetalleVOList()){
                if(ta.getTimeEnd().equals("")){ //si esta activo
                    if(i==pos){
                        goToProductividad(ta);
                        break;
                    }
                    i++;
                }
            }
        });
        tareo_rViewTActivos.setAdapter(adapterActivo);
        adapterInactivo = new RViewAdapterListTrabajadoresInactive(ctx,TAREOVO.getTareoDetalleVOList(),false);
        adapterInactivo.setOnClicListener(v->{
            int pos = tareo_rViewTInactivos.getChildAdapterPosition(v);
            int i=0;
            for(TareoDetalleVO ta : TAREOVO.getTareoDetalleVOList()){
                if(!ta.getTimeEnd().equals("")){ //si esta activo
                    if(i==pos){
                        goToProductividad(ta);
                        break;
                    }
                    i++;
                }
            }
        });
        tareo_rViewTInactivos.setAdapter(adapterInactivo);

    }

    private void goToProductividad(TareoDetalleVO ta) {
        Intent intent = new Intent(ctx, ProductividadActivity.class);
        intent.putExtra(EXTRA_TAREODETALLE,ta);
        startActivity(intent);

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


    private int REQUEST_CODE_ADD = 11;

    private void goToTransference(String EXTRA_MODE,TareoVO tareoVO){
        Intent i = new Intent(ctx, TabbetActivity.class);
        i.putExtra(TabbetActivity.EXTRA_MODE,TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR);
        i.putExtra(TabbetActivity.EXTRA_TAREOVO,tareoVO);
        startActivityForResult(i,EXTRA_MODE.equals(TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR)?REQUEST_CODE_ADD:0);
    }


    private void events() {
        fabAddTrabajador.setOnClickListener(v->{
            goToTransference(EXTRA_MODE_ADD_TRABAJADOR,TAREOVO);
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

    String TAG = TareoActivity.class.getSimpleName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD) {
            try {
                Bundle recibidos = (data.getExtras());
                if (recibidos != null) {
                    TAREOVO = (TareoVO) recibidos.getSerializable("TAREOVO");
                    adapterActivo.notifyDataSetChanged();
                    adapterInactivo.notifyDataSetChanged();

                    Log.d(  TAG,"tamaño "+TAREOVO.getTareoDetalleVOList().size());
                    adapterActivo = new RViewAdapterListTrabajadoresInactive(ctx,TAREOVO.getTareoDetalleVOList(),true);
                    adapterActivo.setOnClicListener(v->{
                        int pos = tareo_rViewTActivos.getChildAdapterPosition(v);
                        int i=0;
                        for(TareoDetalleVO ta : TAREOVO.getTareoDetalleVOList()){
                            if(ta.getTimeEnd().equals("")){ //si esta activo
                                if(i==pos){
                                    goToProductividad(ta);
                                    break;
                                }
                                i++;
                            }
                        }

                    });
                    tareo_rViewTActivos.setAdapter(adapterActivo);

                    adapterInactivo = new RViewAdapterListTrabajadoresInactive(ctx,TAREOVO.getTareoDetalleVOList(),false);
                    adapterInactivo.setOnClicListener(v->{
                        int pos = tareo_rViewTInactivos.getChildAdapterPosition(v);
                        int i=0;
                        for(TareoDetalleVO ta : TAREOVO.getTareoDetalleVOList()){
                            if(!ta.getTimeEnd().equals("")){ //si esta activo
                                if(i==pos){
                                    goToProductividad(ta);
                                    break;
                                }
                                i++;
                            }
                        }
                    });
                    tareo_rViewTInactivos.setAdapter(adapterInactivo);

                }
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult " +e.toString());
            }
        }
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final TareoDetalleVO item = TAREOVO.getTareoDetalleVOList().remove(viewHolder.getAdapterPosition());
            new TareoDAO(ctx).deleteById(item.getId());
            final int index = viewHolder.getAdapterPosition();
            adapterActivo.notifyDataSetChanged();
            adapterActivo.notifyDataSetChanged();
            //enviar(fp1_tietRefA.getText().toString(),fp1_tietRefB.getText().toString(),productList);

            Snackbar snackbar = Snackbar.make((View) findViewById((int)viewHolder.getItemId()),"Se Borró una Labor",Snackbar.LENGTH_LONG);
            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareoDetalleDAO(ctx).insert(item);
                    TAREOVO.getTareoDetalleVOList().add(index,item);

                    adapterActivo.notifyDataSetChanged();
                    adapterActivo.notifyDataSetChanged();
                }
            });
            snackbar.show();
        }
    };

}

package com.ibao.alanger.worktime.views.tareo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.adapters.RViewAdapterListTrabajadoresInactive;
import com.ibao.alanger.worktime.adapters.RViewAdapterProductividad;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.DAO.TareoDetalleDAO;
import com.ibao.alanger.worktime.models.VO.internal.ProductividadVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.productividad.ProductividadActivity;
import com.ibao.alanger.worktime.views.productividad.ProductividadLiveData;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.Objects;

import static com.ibao.alanger.worktime.views.productividad.ProductividadActivity.EXTRA_TAREODETALLE;
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


    private TareoLiveData model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        declaration();
        events();

    }

    private void cargarListas() {
        adapterActivo = new RViewAdapterListTrabajadoresInactive(ctx,model.getTareoVO().getValue().getTareoDetalleVOList(),true);
        adapterActivo.setOnClicListener(v->{

            int pos = tareo_rViewTActivos.getChildAdapterPosition(v);
            int i=0;
            for(TareoDetalleVO ta : model.getTareoVO().getValue().getTareoDetalleVOList()){
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
        adapterInactivo = new RViewAdapterListTrabajadoresInactive(ctx,model.getTareoVO().getValue().getTareoDetalleVOList(),false);
        adapterInactivo.setOnClicListener(v->{
            int pos = tareo_rViewTInactivos.getChildAdapterPosition(v);
            int i=0;
            for(TareoDetalleVO ta : model.getTareoVO().getValue().getTareoDetalleVOList()){
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
            goToTransference(EXTRA_MODE_ADD_TRABAJADOR,model.getTareoVO().getValue());
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

        tareo_Fundo = findViewById(R.id.tareo_Fundo);
        tareo_Cultivo = findViewById(R.id.tareo_Cultivo);
        tareo_Labor = findViewById(R.id.tareo_Labor);
        tareo_dateTime = findViewById(R.id.tareo_dateTime);
        tareo_costCenter = findViewById(R.id.tareo_costCenter);
        tareo_nTrabajadores = findViewById(R.id.tareo_nTrabajadores);
        tareo_nHoras = findViewById(R.id.tareo_nHoras);
        tareo_rViewTActivos = findViewById(R.id.tareo_rViewTActivos);
        tareo_rViewTInactivos = findViewById(R.id.tareo_rViewTInactivos);




        model = ViewModelProviders.of(this).get(TareoLiveData.class);
        // Create the observer which updates the UI.
        final Observer<TareoVO> nameObserver = new Observer<TareoVO>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable final TareoVO temp){
                Log.d(TAG,"actualizando");
                cargarListas();

                tareo_nTrabajadores.setText(""+temp.getTareoDetalleVOList().size());
                tareo_dateTime.setText(temp.getDateTimeStart());
                tareo_Fundo.setText(temp.getFundoVO().getName());
                if(temp.isAsistencia()){
                    tareo_Labor.setText(getString(R.string.control_de_asistencia));
                    tareo_Cultivo.setText(temp.getEmpresaVO().getName());
                    tareo_costCenter.setText(getString(R.string.asistencia));
                }else {
                    tareo_Labor.setText(temp.getLaborVO().getName());
                    tareo_Fundo.setText(temp.getFundoVO().getName());
                    tareo_Cultivo.setText(temp.getCultivoVO().getName());
                    if(temp.getLaborVO().isDirecto()){
                        tareo_costCenter.setText(temp.getLoteVO().getCod());
                    }else {
                        tareo_costCenter.setText(temp.getCentroCosteVO().getName());
                    }
                }



/*

                temp.setProductividad(0);
                //contando productividad
                for(ProductividadVO p: temp.getProductividadVOList()){
                    temp.setProductividad(temp.getProductividad()+p.getValue());
                }

                productividad_productividad.setText(""+temp.getProductividad());

                adapter.notifyDataSetChanged();
*/


            }
        };
        model.getTareoVO().observe(this, nameObserver);

        model.setTareoVO( (TareoVO) b.getSerializable(EXTRA_TAREO));


        cargarListas();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    String TAG = "TAG"+TareoActivity.class.getSimpleName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD) {
            try {
                Bundle recibidos = (data.getExtras());
                if (recibidos != null) {
                    model.setTareoVO( (TareoVO) recibidos.getSerializable(TabbetActivity.EXTRA_TAREOVO));

                    Log.d(  TAG,"tamaño "+model.getTareoVO().getValue().getTareoDetalleVOList().size());

                }else {
                    Toast.makeText(ctx,"nos e recibio nada",Toast.LENGTH_SHORT).show();
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

            final TareoDetalleVO item = model.getTareoVO().getValue().getTareoDetalleVOList().remove(viewHolder.getAdapterPosition());
            new TareoDAO(ctx).deleteById(item.getId());
            final int index = viewHolder.getAdapterPosition();
            adapterActivo.notifyDataSetChanged();
            adapterInactivo.notifyDataSetChanged();
            //enviar(fp1_tietRefA.getText().toString(),fp1_tietRefB.getText().toString(),productList);

            Snackbar snackbar = Snackbar.make((View) findViewById((int)viewHolder.getItemId()),"Se Borró una Labor",Snackbar.LENGTH_LONG);
/*
            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareoDetalleDAO(ctx).insert(item);
                    TAREOVO.getTareoDetalleVOList().add(index,item);

                    adapterActivo.notifyDataSetChanged();
                    adapterInactivo.notifyDataSetChanged();
                }
            });

 */
            snackbar.show();
        }
    };

}

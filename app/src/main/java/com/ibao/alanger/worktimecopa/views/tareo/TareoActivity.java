package com.ibao.alanger.worktimecopa.views.tareo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ibao.alanger.worktimecopa.R;
import com.ibao.alanger.worktimecopa.Utilities;
import com.ibao.alanger.worktimecopa.models.DAO.ProductividadDAO;
import com.ibao.alanger.worktimecopa.models.DAO.TareoDAO;
import com.ibao.alanger.worktimecopa.models.DAO.TareoDetalleDAO;
import com.ibao.alanger.worktimecopa.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktimecopa.models.VO.internal.TareoVO;
import com.ibao.alanger.worktimecopa.views.CreateTareoActivity;
import com.ibao.alanger.worktimecopa.views.productividad.ProductividadActivity;
import com.ibao.alanger.worktimecopa.views.transference.TabbetActivity;

import java.util.Objects;

import static com.ibao.alanger.worktimecopa.Utilities.getDateTime;
import static com.ibao.alanger.worktimecopa.Utilities.getHour;
import static com.ibao.alanger.worktimecopa.views.productividad.ProductividadActivity.EXTRA_TAREODETALLE;
import static com.ibao.alanger.worktimecopa.views.transference.TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR;
import static com.ibao.alanger.worktimecopa.views.transference.TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR;

public class TareoActivity extends AppCompatActivity {


    private static Context ctx;

    private static View root;

    private static FloatingActionButton fabAddTrabajador;
    private static FloatingActionButton fabRemoveTrabajador;
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


        adapterActivo = new RViewAdapterListTrabajadores(ctx,model.getTareoVO().getValue().getTareoDetalleVOList(),true);



        adapterActivo.setOnClicListener(v->{

            if(!model.getTareoVO().getValue().isAsistencia()){
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
            }
        });
        new ItemTouchHelper(itemTouchHelperCallBackActive).attachToRecyclerView(tareo_rViewTActivos);
        tareo_rViewTActivos.setAdapter(adapterActivo);

        adapterInactivo = new RViewAdapterListTrabajadores(ctx,model.getTareoVO().getValue().getTareoDetalleVOList(),false);

        adapterInactivo.setOnClicListener(v->{
            if(!model.getTareoVO().getValue().isAsistencia()){
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
            }


        });
        new ItemTouchHelper(itemTouchHelperCallBackInactive).attachToRecyclerView(tareo_rViewTInactivos);
        tareo_rViewTInactivos.setAdapter(adapterInactivo);

    }

    private void goToProductividad(TareoDetalleVO ta) {
        Intent intent = new Intent(ctx, ProductividadActivity.class);
        intent.putExtra(EXTRA_TAREODETALLE,ta);
        startActivity(intent);

    }




    private void marcarSalida(){

        if(model.getTareoVO().getValue().getTareoDetalleVOList().size()==0){
            Toast.makeText(ctx,"Lista Trabajadores vacía",Toast.LENGTH_SHORT).show();
        }



        // SI TIENES TRABAJADORES SIN HORA DE SALIDA NO PUEDE FINALIZAR
            //MOSTRAR UNA ALERTA


        boolean flagTodosTienenSalida = true;
        for(TareoDetalleVO ta :model.getTareoVO().getValue().getTareoDetalleVOList() ){
            if(ta.getTimeEnd().isEmpty()){
                flagTodosTienenSalida = false;
                break;
                //Log.d(TAG,"salida:"+ta.getIdTareo()+" "+ta.getTrabajadorVO().getDni()+" "+Utilities.getDateTime());
                //  new TareoDetalleDAO(ctx).updateSalidaby_dni_idTareo(ta.getIdTareo(),ta.getTrabajadorVO().getDni(),Utilities.getDateTime());
            }

        }
        if(flagTodosTienenSalida){ // si todos los trabajadores tienen hora de  salida
            new TareoDAO(ctx).updateFinishHourById(model.getTareoVO().getValue().getId(),Utilities.getDateTime());
            onBackPressed();
        }else {
            Snackbar snackbar = Snackbar.make(root,getString(R.string.existen_trabajadores_sin_salida),Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    //codigo pasado para obtener el resultado de la edicion de datos basicos
    static final int REQUEST_CODE_EDIT_BASICS =1234;
    private void goToBasics(){
        Intent i = new Intent(this, CreateTareoActivity.class);
        i.putExtra(CreateTareoActivity.EXTRA_MODE,CreateTareoActivity.MODE_EDIT);
        i.putExtra(CreateTareoActivity.EXTRA_TAREO,model.getTareoVO().getValue());//se le pasa el tareo como parametro
        startActivityForResult(i, REQUEST_CODE_EDIT_BASICS);
    }


    int selectOnDialog=0;
    private void showOptions(){

        String[] items = new String[3];
        items[0] = getString(R.string.editar_basicos);
        items[1] = getString(R.string.anhadir_productividad_grupal);
        items[2] = getString(R.string.finalizar_labor);

        if(model.getTareoVO().getValue().isAsistencia()){
            items = new String[2];
            items[0] = getString(R.string.editar_basicos);
            items[1] = getString(R.string.finalizar_labor);

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(getString(R.string.seleccione_opcion));
            builder.setSingleChoiceItems(items, 0, (dialog, which) -> selectOnDialog = which);
            builder.setPositiveButton(R.string.seleccionar, (dialog, which) -> {
                switch (selectOnDialog){
                    case 0:
                        goToBasics();

                        break;
                    case 1:
                        marcarSalida();
                        break;

                }
                enableInputs();
            });
            builder.setNegativeButton(R.string.cancelar, (dialog, which) -> enableInputs());
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();


        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(getString(R.string.seleccione_opcion));
            builder.setSingleChoiceItems(items, 0, (dialog, which) -> selectOnDialog = which);
            builder.setPositiveButton(R.string.seleccionar, (dialog, which) -> {
                switch (selectOnDialog){
                    case 0:
                        goToBasics();
                        break;
                    case 1:
                        showPopupAddProductividadGrupal();
                        break;
                    case 2:
                        marcarSalida();
                        break;
                }
                enableInputs();
            });
            builder.setNegativeButton(R.string.cancelar, (dialog, which) -> enableInputs());
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }

    }

    private final int REQUEST_CODE_ADD = 11;
    private final int REQUEST_CODE_REMOVE = 12;

    private void goToTransferenceActivity(String EXTRA_MODE, TareoVO tareoVO){
        Intent i = new Intent(ctx, TabbetActivity.class);
        i.putExtra(TabbetActivity.EXTRA_MODE,EXTRA_MODE);
        i.putExtra(TabbetActivity.EXTRA_TAREOVO,tareoVO);
        startActivityForResult(i,EXTRA_MODE.equals(TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR)?REQUEST_CODE_ADD:REQUEST_CODE_REMOVE);
    }


    private void events() {
        fabAddTrabajador.setOnClickListener(v->{
            goToTransferenceActivity(EXTRA_MODE_ADD_TRABAJADOR,model.getTareoVO().getValue());
            disableInputs();
        });
        fabRemoveTrabajador.setOnClickListener(v->{
            goToTransferenceActivity(EXTRA_MODE_REMOVE_TRABAJADOR,model.getTareoVO().getValue());
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

        fabRemoveTrabajador.setFocusable(flag);
        fabRemoveTrabajador.setClickable(flag);
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

        fabRemoveTrabajador.setFocusable(flag);
        fabRemoveTrabajador.setClickable(flag);
    }

    private void declaration() {
        ctx = this;
        fabAddTrabajador = findViewById(R.id.tareo_fabAddTrabajador);
        fabRemoveTrabajador = findViewById(R.id.tareo_fabRemoveTrabajador);

        fabOptions = findViewById(R.id.tareo_fabOptions);

        Bundle b = getIntent().getExtras();

        assert b != null;

        root = findViewById(R.id.root);
        tareo_Fundo = findViewById(R.id.tareo_tViewFundo);
        tareo_Cultivo = findViewById(R.id.tareo_tViewCultivo);
        tareo_Labor = findViewById(R.id.tareo_tViewLabor);
        tareo_dateTime = findViewById(R.id.tareo_tViewDateTime);
        tareo_costCenter = findViewById(R.id.tareo_tViewCostCenter);
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

                int countActivos = 0;
                for(TareoDetalleVO tad: temp.getTareoDetalleVOList()){
                    if(tad.getTimeEnd().isEmpty()){
                        countActivos++;
                    }
                }

                if(countActivos>0){
                        fabRemoveTrabajador.show();
                }else {
                        fabRemoveTrabajador.hide();
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

        switch(requestCode) {
            case REQUEST_CODE_EDIT_BASICS : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    model.setTareoVO((TareoVO) data.getSerializableExtra(CreateTareoActivity.EXTRA_TAREO));
                    Toast.makeText(ctx, R.string.datos_editados_correctamente,Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_ADD:
            case REQUEST_CODE_REMOVE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle recibidos = (data.getExtras());
                    if (recibidos != null) {
                        model.setTareoVO((TareoVO) recibidos.getSerializable(TabbetActivity.EXTRA_TAREOVO));
                        Log.d(TAG, "tamaño " + model.getTareoVO().getValue().getTareoDetalleVOList().size());

                    } else {
                        Toast.makeText(ctx, "no se recibio nada", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

        }

    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBackActive = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();
            final TareoDetalleVO item = adapterActivo.getTareoDetalle(index);
            model.deleteTareoDetalle(item);
            new TareoDetalleDAO(ctx).deleteById(item.getId());

            adapterActivo.notifyDataSetChanged();
            adapterInactivo.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(root,getString(R.string.se_elimino_trabajador),Snackbar.LENGTH_LONG);

            snackbar.setAction(getString(R.string.desahacer), v -> {
                new TareoDetalleDAO(ctx).insert(item);
                model.addTareoDetalle(index,item);
                adapterActivo.notifyDataSetChanged();
                adapterInactivo.notifyDataSetChanged();
            });

            snackbar.show();
        }
    };

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBackInactive = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();
            final TareoDetalleVO item = adapterInactivo.getTareoDetalle(index);
            model.deleteTareoDetalle(item);

            Log.d(TAG,"borrando:"+item.getId()+" ,resp: " +new TareoDetalleDAO(ctx).deleteById(item.getId()));

            adapterActivo.notifyDataSetChanged();
            adapterInactivo.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(root,getString(R.string.se_elimino_trabajador),Snackbar.LENGTH_LONG);

            snackbar.setAction("Deshacer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TareoDetalleDAO(ctx).insert(item);
                    model.addTareoDetalle(index,item);
                    adapterActivo.notifyDataSetChanged();
                    adapterInactivo.notifyDataSetChanged();
                }
            });

            snackbar.show();
        }
    };

    private void showPopupAddProductividadGrupal(){
        Dialog dialogClose;
        dialogClose = new Dialog(this);
        dialogClose.setContentView(R.layout.activity_productividad_dialog_add);
        MaterialButton prodadd_btnSave = (MaterialButton) dialogClose.findViewById(R.id.prodadd_btnSave);
        ImageView prodadd_iVienClose = (ImageView) dialogClose.findViewById(R.id.prodadd_iVienClose);
        TextView prodadd_tViewDateTime = dialogClose.findViewById(R.id.prodadd_tViewDateTime);
        EditText eTextCantidad= dialogClose.findViewById(R.id.prodadd_eTextCantidad);

        eTextCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if(eTextCantidad.getText().toString().equals("0.")){

                }else {
                    if(eTextCantidad.getText().toString().equals("")||(Float.valueOf(eTextCantidad.getText().toString())==0&&eTextCantidad.getText().toString().length()>1)){
                        Log.d(TAG,"flag1");
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                eTextCantidad.setText("0");
                                eTextCantidad.setSelection(eTextCantidad.getText().toString().length());
                            }
                        });

                    }
                    try{
                        Log.d(TAG,eTextCantidad.getText().toString().substring(0,2));
                    }catch (Exception e){
                        Log.d(TAG,e.toString());
                    }
                    if(eTextCantidad.getText().toString().length()>1&& eTextCantidad.getText().charAt(0)=='0' && !eTextCantidad.getText().toString().substring(0,2).equals("0.")){
                        eTextCantidad.setText(eTextCantidad.getText().toString().substring(1));
                        eTextCantidad.setSelection(eTextCantidad.getText().toString().length());
                    }
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        prodadd_iVienClose.setOnClickListener(v -> dialogClose.dismiss());
        prodadd_btnSave.setOnClickListener(v -> {

            if(eTextCantidad.getText().toString().equals("0")){
                Toast.makeText(ctx,"Ingrese un valor válido",Toast.LENGTH_LONG).show();
            }else {
                String valor = eTextCantidad.getText().toString();
                if(valor.charAt(valor.length()-1)=='.'){
                    eTextCantidad.setError("valor invalido");
                }else {
                    for(TareoDetalleVO td : model.getTareoVO().getValue().getTareoDetalleVOList()) {
                        long id = new ProductividadDAO(ctx).insert(td.getId(), valor,getDateTime());
                        if (id > 0) {
                            td.getProductividadVOList().add(new ProductividadDAO(ctx).selectById((int) id));
                        }
                    }
                    model.setTareoVO(new TareoDAO(ctx).selectById(model.getTareoVO().getValue().getId()));
                    dialogClose.dismiss();
                }
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();

        eTextCantidad.setSelection(eTextCantidad.getText().toString().length());

        Handler handlerHourUpdater = new Handler();

        Runnable runHourUpdater = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"Actualizando hora");
                prodadd_tViewDateTime.setText(getDateTime());
                handlerHourUpdater.postDelayed(this,1000);
            }
        };

        handlerHourUpdater.removeCallbacks(runHourUpdater);
        handlerHourUpdater.post(runHourUpdater);

    }
}

package com.ibao.alanger.worktimecopa.views.productividad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import com.ibao.alanger.worktimecopa.models.DAO.ProductividadDAO;
import com.ibao.alanger.worktimecopa.models.VO.internal.ProductividadVO;
import com.ibao.alanger.worktimecopa.models.VO.internal.TareoDetalleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.ibao.alanger.worktimecopa.Utilities.getDateTime;
import static com.ibao.alanger.worktimecopa.Utilities.getHour;

public class ProductividadActivity extends AppCompatActivity {

    private ProductividadLiveData model;

    public static final String EXTRA_TAREODETALLE = "productividad";

    String TAG = ProductividadActivity.class.getSimpleName();

    View root;
    RecyclerView productividad_rView;
    TextView productividad_hIni;
    TextView productividad_hFin;
    TextView productividad_productividad;
    TextView productividad_Name;
    TextView productividad_DNI;
    FloatingActionButton productividad_fabAdd;

    TareoDetalleVO TAREODETALLEVO;
    Bundle b;
    RViewAdapterProductividad adapter;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productividad);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ctx = this;
        b = getIntent().getExtras();
        assert b != null;
        TAREODETALLEVO = (TareoDetalleVO) b.getSerializable(EXTRA_TAREODETALLE);
        declare();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void declare() {
        root = findViewById(R.id.root);
        productividad_rView = findViewById(R.id.productividad_rView);
        productividad_hIni = findViewById(R.id.productividad_hIni);
        productividad_hFin = findViewById(R.id.productividad_hFin);
        productividad_productividad = findViewById(R.id.productividad_productividad);
        productividad_Name = findViewById(R.id.productividad_Name);
        productividad_DNI = findViewById(R.id.productividad_DNI);
        productividad_fabAdd = findViewById(R.id.productividad_fabAdd);

        Date dateIni = getHourFromDate(TAREODETALLEVO.getTimeStart());

        productividad_hIni.setText(""+(dateIni.getHours()<10?"0"+dateIni.getHours():dateIni.getHours())+":"+(dateIni.getMinutes()<10?"0"+dateIni.getMinutes():dateIni.getMinutes())+":"+(dateIni.getSeconds()<10?"0"+dateIni.getSeconds():dateIni.getSeconds()));
        if(!TAREODETALLEVO.getTimeEnd().equals("")){
            Date dateFin = getHourFromDate(TAREODETALLEVO.getTimeEnd());
            productividad_hFin.setText(""+(dateFin.getHours()<10?"0"+dateFin.getHours():dateFin.getHours())+":"+(dateFin.getMinutes()<10?"0"+dateFin.getMinutes():dateFin.getMinutes())+":"+(dateFin.getSeconds()<10?"0"+dateFin.getSeconds():dateFin.getSeconds()));

        }

        productividad_fabAdd.setOnClickListener(v->{
            showPopup(new ProductividadVO(),true);
        });

        model = ViewModelProviders.of(this).get(ProductividadLiveData.class);
        // Create the observer which updates the UI.
        final Observer<TareoDetalleVO> nameObserver = new Observer<TareoDetalleVO>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable final TareoDetalleVO temp){

                productividad_DNI.setText(temp.getTrabajadorVO().getDni());
                productividad_Name.setText(temp.getTrabajadorVO().getName().equals("")?"Sin Nombre":TAREODETALLEVO.getTrabajadorVO().getName());
                productividad_productividad.setText(""+temp.getProductividad());

                /*
                temp.setProductividad(0);
                //contando productividad
                for(ProductividadVO p: temp.getProductividadVOList()){
                    temp.setProductividad(temp.getProductividad()+p.getValue());
                }
*/
                productividad_productividad.setText(""+temp.getProductividad());

                adapter.notifyDataSetChanged();

                TextView tViewSinItems = findViewById(R.id.tViewSin);
                tViewSinItems.setVisibility(temp.getProductividadVOList().size()>0?View.INVISIBLE:View.VISIBLE);

            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getTareoDetalleVO().observe(this, nameObserver);

        model.setTareoDetalleVO(TAREODETALLEVO);

        adapter = new RViewAdapterProductividad(ctx, ProductividadLiveData.getTareoDetalleVO().getValue().getProductividadVOList());
        adapter.setOnClicListener(v->{
            int index = productividad_rView.getChildAdapterPosition(v);
            ProductividadVO temp = ProductividadLiveData.getTareoDetalleVO().getValue().getProductividadVOList().get(index);
            showPopup(temp,false);
        });

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(productividad_rView);
        productividad_rView.setAdapter(adapter);

    }

    private Date getHourFromDate(String dateString){

        Date date = null;
        try {
            date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void showPopup(ProductividadVO pro,boolean isNew){
        Dialog dialogClose;
        dialogClose = new Dialog(this);
        dialogClose.setContentView(R.layout.activity_productividad_dialog_add);
        MaterialButton prodadd_btnSave = (MaterialButton) dialogClose.findViewById(R.id.prodadd_btnSave);
        ImageView prodadd_iVienClose = (ImageView) dialogClose.findViewById(R.id.prodadd_iVienClose);
        TextView prodadd_tViewDateTime = dialogClose.findViewById(R.id.prodadd_tViewDateTime);
        prodadd_tViewDateTime.setText(""+pro.getDateTime());
        EditText eTextCantidad= dialogClose.findViewById(R.id.prodadd_eTextCantidad);

        if(!isNew){
            eTextCantidad.setText(String.valueOf(pro.getValue()));
        }

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
                String numero = eTextCantidad.getText().toString();
                if(numero.charAt(numero.length()-1)=='.'){
                    eTextCantidad.setError("valor invalido");
                }else {
                    pro.setValue(Float.valueOf(numero));
                    if(isNew){
                        long id = new ProductividadDAO(ctx).insert(TAREODETALLEVO.getId(),numero,getDateTime());
                        if(id>0){
                            pro.setId((int)id);
                            pro.setDateTime(getDateTime());
                            pro.setIdTareoDetalle( TAREODETALLEVO.getId());
                            model.addProductividad(pro);
                          //  Toast.makeText(ctx,"Se agregaron "+eTextCantidad.getText().toString()+"",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ctx,"error al insertar",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        model.modifyProductividad(pro);
                    }
                    dialogClose.dismiss();
                }
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();
        eTextCantidad.setSelection(eTextCantidad.getText().toString().length());

        if(isNew){
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

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();//esto tiene q ir arriba porq la programacion reaccitva elimina de inmediato en el recycler view sin actualizar
            final ProductividadVO item = ProductividadLiveData.getTareoDetalleVO().getValue().getProductividadVOList().get(index);
            model.deleteProductividad(item);

            new ProductividadDAO(ctx).deleteById(item.getId());

            Snackbar snackbar = Snackbar.make(root,"Se eliminó una productividad",Snackbar.LENGTH_LONG);

            snackbar.setAction("Deshacer", v -> {
                new ProductividadDAO(ctx).insert(item);
                model.addProductividad(index,item);
            });

            snackbar.show();
        }
    };

}


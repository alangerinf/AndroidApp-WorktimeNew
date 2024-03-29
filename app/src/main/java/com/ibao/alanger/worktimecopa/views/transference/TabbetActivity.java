package com.ibao.alanger.worktimecopa.views.transference;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;


import com.ibao.alanger.worktimecopa.R;
import com.ibao.alanger.worktimecopa.models.DAO.TareoDetalleDAO;
import com.ibao.alanger.worktimecopa.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktimecopa.models.VO.internal.TareoVO;
import com.ibao.alanger.worktimecopa.views.transference.ui.main.AddPersonalFragment;
import com.ibao.alanger.worktimecopa.views.transference.ui.main.ListPersonalAddedFragment;
import com.ibao.alanger.worktimecopa.views.transference.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  TabbetActivity extends AppCompatActivity
        implements
        AddPersonalFragment.OnFragmentInteractionListener,
        ListPersonalAddedFragment.OnFragmentInteractionListener {

    private static FloatingActionButton fab;
    private static CustomViewPager viewPager;
    private static TabLayout tabs;
    private static SectionsPagerAdapter sectionsPagerAdapter;
    ArrayList<String> tittles = new ArrayList<>();

    public static final String EXTRA_MODE= "extra_mode";

    public static final String EXTRA_MODE_ADD_TRABAJADOR="ADD TRABAJADOR";
    public static final String EXTRA_MODE_REMOVE_TRABAJADOR="REMOVE TRABAJADOR";
    public static final String EXTRA_TAREOVO="TAREOVO";

    public static String MY_EXTRA_MODE;
    public static TareoVO TAREO_RETURN;

    private static String TAG = TabbetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();

        MY_EXTRA_MODE = b.getString(EXTRA_MODE);


        //obtenemos el TAREO PARA HACERLE MODIFICACIONES, ESE MISMO ES Q SE DEVUELVE A LA ACTIVIDAD ANTERIOR PARA PARCHAR LOS CAMBIOS EN LA INTERFACE
        if(MY_EXTRA_MODE.equals(EXTRA_MODE_ADD_TRABAJADOR)){
            setTitle("Entrada Trabajadores");

            TAREO_RETURN = (TareoVO) b.getSerializable(EXTRA_TAREOVO);
            Log.d(TAG,""+ TAREO_RETURN);
        }

        if(MY_EXTRA_MODE.equals(EXTRA_MODE_REMOVE_TRABAJADOR)){
            setTitle("Salida Trabajadores");

            TAREO_RETURN = (TareoVO) b.getSerializable(EXTRA_TAREOVO);
            Log.d(TAG,""+ TAREO_RETURN);
        }

        declare();
        events();
    }

    private void events() {
        fab.setOnClickListener(v->{
            if(viewPager.getCurrentItem()!=1){//si esta en el segundo fragment
                viewPager.setCurrentItem(1);
            }else {
                Intent returnIntent = new Intent();

                switch (MY_EXTRA_MODE){
                    case TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR:
                        for(TareoDetalleVO tad :PageViewModel.getMutable()){
                            int i =(int) new TareoDetalleDAO(getBaseContext()).insert(TAREO_RETURN.getId(),tad.getTrabajadorVO().getDni(),tad.getTimeStart());
                            tad.setId(i);
                            tad.setIdTareo(TAREO_RETURN.getId());
                        }
                        TAREO_RETURN.getTareoDetalleVOList().addAll(PageViewModel.getMutable());
                        break;
                    case TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR:

                        for(TareoDetalleVO tad :PageViewModel.getMutable()){
                            //if is update
                            if(new TareoDetalleDAO(getBaseContext()).updateSalidaby_dni_idTareo(TAREO_RETURN.getId(),tad.getTrabajadorVO().getDni(),tad.getTimeEnd()) > 0){

                                Log.d(TAG,"actualizado "+tad.getTrabajadorVO().getDni());
                                //buscarlo en TAREO_RETURN
                                for(TareoDetalleVO TD: TAREO_RETURN.getTareoDetalleVOList()){
                                    if(TD.getTrabajadorVO().getDni().equals(tad.getTrabajadorVO().getDni())) {//si lo encuentra
                                        TD.setTimeEnd(tad.getTimeEnd());
                                        Log.d(TAG,TD.getTrabajadorVO().getDni());
                                        break;
                                    }
                                }
                            }else {
                                Log.d(TAG,"se mamo");
                                throw new NullPointerException(TAG+"se mamo");

                            }
                        }

                        break;
                }

                returnIntent.putExtra("TAREOVO", TAREO_RETURN);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void declare() {
        tittles = new ArrayList<>();
        tittles.add(getString(R.string.tab_text_1));
        tittles.add(getString(R.string.tab_text_2));
        PageViewModel.init();
        List <TareoDetalleVO> tareoDetalleVOList = new ArrayList<>();
        PageViewModel.set(tareoDetalleVOList);
        sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(),tittles,MY_EXTRA_MODE, TAREO_RETURN);

        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_return);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button dialog_positive = dialog.findViewById(R.id.dialog_positive);
        Button dialog_negative = dialog.findViewById(R.id.dialog_negative);

        dialog_positive.setOnClickListener(v->{
            dialog.dismiss();
            super.onBackPressed();
        });
        dialog_negative.setOnClickListener(v->{
            dialog.dismiss();
        });
        dialog.show();
    }


    @Override
    public void onFragmentInteraction_upd_eTextDNI(String mensaje) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
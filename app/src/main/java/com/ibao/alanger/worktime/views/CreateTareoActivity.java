package com.ibao.alanger.worktime.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.DAO.ActividadDAO;
import com.ibao.alanger.worktime.models.DAO.CentroCosteDAO;
import com.ibao.alanger.worktime.models.DAO.CultivoDAO;
import com.ibao.alanger.worktime.models.DAO.EmpresaDAO;
import com.ibao.alanger.worktime.models.DAO.FundoDAO;
import com.ibao.alanger.worktime.models.DAO.LaborDAO;
import com.ibao.alanger.worktime.models.DAO.LoteDAO;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.models.VO.external.ActividadVO;
import com.ibao.alanger.worktime.models.VO.external.CentroCosteVO;
import com.ibao.alanger.worktime.models.VO.external.CultivoVO;
import com.ibao.alanger.worktime.models.VO.external.EmpresaVO;
import com.ibao.alanger.worktime.models.VO.external.FundoVO;
import com.ibao.alanger.worktime.models.VO.external.LaborVO;
import com.ibao.alanger.worktime.models.VO.external.LoteVO;
import com.ibao.alanger.worktime.views.tareo.TareoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ibao.alanger.worktime.views.tareo.TareoActivity.EXTRA_TAREO;

public class CreateTareoActivity extends AppCompatActivity {


    String TAG = CreateTareoActivity.class.getSimpleName();


    private static String MY_CREATE_MODE = "";


    public final static String EXTRA_CREATE_MODE= "extra_mode";
    //modo de creacion segun quien lo llame
    public final static String CREATE_MODE_MAIN= "main";


    private static MaterialButton btnDone;

    private static LinearLayout selectorEmpresa;
    private static LinearLayout selectorFundo;
    private static LinearLayout selectorCultivo;
    private static LinearLayout selectorLabor;
    private static LinearLayout selectorActividad;
    private static LinearLayout selectorLote;
    private static LinearLayout selectorCentroCoste;

    private static Spinner spnEmpresa;
    private static Spinner spnFundo;
    private static Spinner spnCultivo;
    private static Spinner spnLabor;
    private static Spinner spnActividad;
    private static Spinner spnLote;
    private static Spinner spnCentroCoste;



    private static List<EmpresaVO> listEmpresas;
    private static List<String> listNombreEmpresas;

    private static List<FundoVO> listFundos;
    private static List<String> listNombreFundos;

    private static List<LoteVO> listLotes;
    private static List<String> listNombreLotes;


    private static List<CentroCosteVO> listCentroCoste;
    private static List<String> listNombreCentroCostes;


    private static List<CultivoVO> listCultivos;
    private static List<String> listNombreCultivos;

    private static List<ActividadVO> listActividades;
    private static List<String> listNombreActividades;

    private static List<LaborVO> listLabores;
    private static List<String> listNombreLabores;

    private static Context ctx;

    private static int CREATE_TYPE;
    private static final int TYPE_DIRECTA = 1 ;
    private static final int TYPE_INDIRECTA = 2 ;
    private static final int TYPE_ASISTENCIA = 3 ;

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
            if(verify()){
                disableInputs();
                if(MY_CREATE_MODE.equals(CREATE_MODE_MAIN)){ //si fue el modo normal de creación
                    int posLabor = spnLabor.getSelectedItemPosition();
                    int posLote = spnLote.getSelectedItemPosition();
                    int posCCoste = spnCentroCoste.getSelectedItemPosition();
                    int posFundo = spnFundo.getSelectedItemPosition();
                    try {

                        int idLabor =  posLabor==-1?0:listLabores.get(posLabor).getId();
                        Log.d(TAG,"idLabor:"+idLabor);
                        int idLote = posLote==-1?0:listLotes.get(posLote).getId();
                        Log.d(TAG,"idLote:"+idLote);
                        int idCCoste = posCCoste==-1?0:listCentroCoste.get(posCCoste).getId();
                        Log.d(TAG,"idCCoste:"+posCCoste+"    "+idCCoste);
                        int idFundo = listFundos.get(posFundo).getId();
                        Log.d(TAG," idFundo:"+idFundo);

                        long id =   new TareoDAO(ctx)
                                        .insert(
                                               idLabor,
                                                idLote,
                                                idCCoste,
                                                idFundo,
                                                CREATE_TYPE==TYPE_ASISTENCIA
                                        );
                        goToTareo(id);
                    }catch (Exception e){
                        Toast.makeText(ctx,TAG+" events"+e.toString(),Toast.LENGTH_LONG).show();
                        Log.d(TAG,"events "+e.toString());
                    }

                }else {
                    Toast.makeText(ctx,"Modo sin Eventos",Toast.LENGTH_SHORT).show();
                    enableInputs();
                }
            }else {
                Toast.makeText(ctx, ctx.getString(R.string.error_complete_configuraciones),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verify() {
        boolean flag =true;

        int posEmpresa = spnEmpresa.getSelectedItemPosition();
        int posFundo = spnFundo.getSelectedItemPosition();
        int posCultivo = spnCultivo.getSelectedItemPosition();
        int posActividad = spnActividad.getSelectedItemPosition();
        int posLabor = spnLabor.getSelectedItemPosition();
        int posLote = spnLote.getSelectedItemPosition();
        int posCCoste = spnCentroCoste.getSelectedItemPosition();

        if(CREATE_TYPE==TYPE_DIRECTA){
            try{
                listEmpresas.get(posEmpresa).getId();
                listFundos.get(posFundo).getId();
                listCultivos.get(posCultivo).getId();
                listActividades.get(posActividad).getId();
                listLabores.get(posLabor).getId();
                listLotes.get(posLote).getId();
            }catch (Exception e){
                Log.d(TAG,"verify "+e.toString());
                flag=false;
            }

        }
        if(CREATE_TYPE==TYPE_INDIRECTA){
            try{
                listEmpresas.get(posEmpresa).getId();
                listFundos.get(posFundo).getId();
                listCultivos.get(posCultivo).getId();
                listActividades.get(posActividad).getId();
                listLabores.get(posLabor).getId();
                listCentroCoste.get(posCCoste).getId();
            }catch (Exception e){
                Log.d(TAG,"verify "+e.toString());
                flag=false;
            }
        }

        if(CREATE_TYPE==TYPE_ASISTENCIA){
            try{
                listEmpresas.get(posEmpresa).getId();
                listFundos.get(posFundo).getId();
            }catch (Exception e){
                Log.d(TAG,"verify "+e.toString());
                flag=false;
            }
        }
        return flag;
    }


    void enable(View view){
        view.setFocusable(true);
        view.setClickable(true);
    }

    void disable(View view){
        view.setFocusable(false);
        view.setClickable(false);
    }


    void enableInputs(){
        enable(spnEmpresa);
        enable(spnFundo);
        enable(spnCultivo);
        enable(spnLabor);
        enable(spnActividad);
        enable(spnLote);
        enable(spnCentroCoste);
        enable(btnDone);
    }

    void disableInputs(){

        disable(spnEmpresa);
        disable(spnFundo);
        disable(spnCultivo);
        disable(spnLabor);
        disable(spnActividad);
        disable(spnLote);
        disable(spnCentroCoste);
        disable(btnDone);
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
        selectorLabor = findViewById(R.id.ctareo_lLayoutLabor);
        selectorActividad = findViewById(R.id.ctareo_lLayoutActividad);
        selectorLote = findViewById(R.id.ctareo_lLayoutLote);
        selectorCentroCoste = findViewById(R.id.ctareo_lLayoutCentroCosto);


        spnEmpresa = findViewById(R.id.ctareo_spnEmpresa);
        spnFundo = findViewById(R.id.ctareo_spnFundo);
        spnCultivo = findViewById(R.id.ctareo_spnCultivo);
        spnLabor = findViewById(R.id.ctareo_spnLabor);
        spnActividad = findViewById(R.id.ctareo_spnActividad);
        spnLote = findViewById(R.id.ctareo_spnLote);
        spnCentroCoste = findViewById(R.id.ctareo_spnCentroCosto);

        btnDone = findViewById(R.id.ctareo_btnDone);

    }

    void getBundle(){
        Bundle b = getIntent().getExtras();
        assert b != null;
        MY_CREATE_MODE = b.getString(EXTRA_CREATE_MODE);

    }



    private int selectOnDialog =0; // used in openDialogSelectActivitdadType()
    protected void openDialogSelectActivitdadType(){

        String[] items = new String[3];
        items[0] = getString(R.string.directo);
        items[1] = getString(R.string.indirecto);
        items[2] = getString(R.string.asistencia);

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

                switch (selectOnDialog){

                    case 0:
                        CREATE_TYPE=TYPE_DIRECTA;//DIRECTA
                        cargarDirecto();
                        break;
                    case 1:
                        CREATE_TYPE=TYPE_INDIRECTA;
                        cargarIndirecto();
                        break;
                    case 2:
                        CREATE_TYPE=TYPE_ASISTENCIA;
                        cargarAsistencia();
                        break;
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

    private void cargarDirecto(){
        selectorEmpresa.setVisibility(View.VISIBLE);
        selectorFundo.setVisibility(View.VISIBLE);
        selectorCultivo.setVisibility(View.VISIBLE);
        selectorActividad.setVisibility(View.VISIBLE);
        selectorLabor.setVisibility(View.VISIBLE);
        selectorLote.setVisibility(View.VISIBLE);
        selectorCentroCoste.setVisibility(View.GONE);

        loadSpnEmpresas();
        loadSpnCultivos();
        loadSpnActividades();


        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnLote.setAdapter(null);
                spnFundo.setAdapter(null);
                loadSpnFundos();
                spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spnLote.setAdapter(null);
                        loadSpnLote();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnLote.setAdapter(null);
                loadSpnLote();
                spnLabor.setAdapter(null);
                loadSpnLabor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnLabor.setAdapter(null);
                loadSpnLabor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void cargarIndirecto(){
        selectorEmpresa.setVisibility(View.VISIBLE);
        selectorFundo.setVisibility(View.VISIBLE);
        selectorCultivo.setVisibility(View.VISIBLE);
        selectorActividad.setVisibility(View.VISIBLE);
        selectorLabor.setVisibility(View.VISIBLE);
        selectorLote.setVisibility(View.GONE);
        selectorCentroCoste.setVisibility(View.VISIBLE);

        loadSpnEmpresas();
        loadSpnCultivos();
        loadSpnActividades();


        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnCentroCoste.setAdapter(null);
                loadSpnCentroCoste();

                spnFundo.setAdapter(null);
                loadSpnFundos();
                spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spnLote.setAdapter(null);
                        loadSpnLote();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnLabor.setAdapter(null);
                loadSpnLabor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnLabor.setAdapter(null);
                loadSpnLabor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private void cargarAsistencia(){
        selectorEmpresa.setVisibility(View.VISIBLE);
        selectorFundo.setVisibility(View.VISIBLE);
        selectorCultivo.setVisibility(View.GONE);
        selectorActividad.setVisibility(View.GONE);
        selectorLabor.setVisibility(View.GONE);
        selectorLote.setVisibility(View.GONE);
        selectorCentroCoste.setVisibility(View.GONE);

        loadSpnEmpresas();

        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnFundo.setAdapter(null);
                loadSpnFundos();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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

    void goToTareo(long id){
        Intent i = new Intent(ctx, TareoActivity.class);
        i.putExtra(EXTRA_TAREO,new TareoDAO(ctx).selectById((int)id));
        startActivity(i);
        finish();
    }


    // todo : Cargardores de empresas
    void loadSpnEmpresas(){
        loadListEmpresas();
        ArrayAdapter<String> adaptadorEmpresas = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreEmpresas);
        spnEmpresa.setAdapter(adaptadorEmpresas);

        if(listNombreEmpresas.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_empresas),Toast.LENGTH_SHORT).show();
        }
    }
    private void loadListEmpresas() {
        Log.d(TAG,"loadListEmpresas");
        EmpresaDAO empresaDAO = new EmpresaDAO(ctx);
        listEmpresas = empresaDAO.listAll();
        cargarNombreEmpresas();
    }
    private void cargarNombreEmpresas(){
        listNombreEmpresas = new ArrayList<>();
        //listNombreEmpresas.add(getString(R.string.cabezeraSpnEmpresa));
        for(EmpresaVO emp : listEmpresas){
            Log.d(TAG,"cargarNombreEmpresas "+emp.getName());
            listNombreEmpresas.add(emp.getName());
        }
    }

    // todo : Cargardores de Cultivos
    void loadSpnCultivos(){
        loadListCultivos();
        ArrayAdapter<String> adaptadorCultivos = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreCultivos);
        spnCultivo.setAdapter(adaptadorCultivos);
        if(listNombreCultivos.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_cultivos),Toast.LENGTH_SHORT).show();
        }
    }

    private void loadListCultivos() {
        Log.d(TAG,"loadListCultivos");
        CultivoDAO cultivoDAO = new CultivoDAO(ctx);
        listCultivos = cultivoDAO.listAll();
        cargarNombreCultivos();
    }
    private void cargarNombreCultivos(){
        listNombreCultivos = new ArrayList<>();
        //listNombreEmpresas.add(getString(R.string.cabezeraSpnEmpresa));
        for(CultivoVO cul : listCultivos){
            Log.d(TAG,"cargarNombreCultivos "+cul.getName());
            listNombreCultivos.add(cul.getName());
        }
    }


    // todo : Cargardores de Actividades
    void loadSpnActividades(){
        loadListActividades();
        ArrayAdapter<String> adaptadorActividades = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreActividades);
        spnActividad.setAdapter(adaptadorActividades);
        if(listNombreActividades.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_actividades),Toast.LENGTH_SHORT).show();
        }
    }

    private void loadListActividades() {
        Log.d(TAG,"loadListActividades");

        listActividades = new ActividadDAO(ctx).listAll();
        cargarNombreActividad();
    }
    private void cargarNombreActividad(){
        listNombreActividades = new ArrayList<>();
        for(ActividadVO act : listActividades){
            Log.d(TAG,"cargarNombreActividad"+act.getName());
            listNombreActividades.add(act.getName());
        }
    }


    // todo : Cargardores de Fundos
    void loadSpnFundos(){
        loadListFundos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreFundos);
        spnFundo.setAdapter(adapter);
        if(listNombreFundos.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_fundos),Toast.LENGTH_SHORT).show();
        }
    }


    private void loadListFundos() {
        FundoDAO fundoDAO = new FundoDAO(getBaseContext());
        int pos = spnEmpresa.getSelectedItemPosition();
        listFundos = fundoDAO.listByIdEmpresa(listEmpresas.get(pos/*-1*/).getId());
        loadNombreFundos();
    }

    private void loadNombreFundos(){

        listNombreFundos = new ArrayList<String>();
        //    listNombreFundos.add(getString(R.string.cabezeraSpnFundo));
        for(FundoVO fun : listFundos){
            listNombreFundos.add(fun.getName());
        }
    }


    // todo : Cargardores de Fundos
    void loadSpnCentroCoste(){
        loadListCentroCoste();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item, listNombreCentroCostes);
        spnCentroCoste.setAdapter(adapter);
        if(listNombreCentroCostes.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_centro_contes),Toast.LENGTH_SHORT).show();
        }
    }

    private void loadListCentroCoste() {
        CentroCosteDAO centroCosteDAO = new CentroCosteDAO(ctx);
        int pos = spnEmpresa.getSelectedItemPosition();
        listCentroCoste = centroCosteDAO.listByIdEmpresa(listEmpresas.get(pos/*-1*/).getId());
        loadNombreCentroCostes();
    }

    private void loadNombreCentroCostes(){

        listNombreCentroCostes = new ArrayList<>();
        //    listNombreFundos.add(getString(R.string.cabezeraSpnFundo));
        for(CentroCosteVO cen : listCentroCoste){
            listNombreCentroCostes.add(cen.getName());
        }
    }


    // todo : Cargardores de Fundos
    void loadSpnLote(){
        loadListLotes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreLotes);
        spnLote.setAdapter(adapter);
        if(listNombreLotes.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_lotes),Toast.LENGTH_SHORT).show();
        }
    }


    private void loadListLotes() {
        LoteDAO loteDAO = new LoteDAO(ctx);
        int posFundo = spnFundo.getSelectedItemPosition();
        int posCultivo = spnCultivo.getSelectedItemPosition();

        listLotes = loteDAO.listByIdFundoIdCultivo(
                listFundos.get(posFundo).getId(),
                listCultivos.get(posCultivo).getId());
        loadNombreLotes();
    }

    private void loadNombreLotes(){
        listNombreLotes = new ArrayList<>();
        for(LoteVO loteVO : listLotes){
            listNombreLotes.add(loteVO.getCod());
        }
    }

    // todo : Cargardores de Fundos
    void loadSpnLabor(){
        loadListLabor();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,listNombreLabores);
        spnLabor.setAdapter(adapter);
        if(listNombreLabores.size()==0){
            Toast.makeText(ctx, ctx.getString(R.string.sin_labores),Toast.LENGTH_SHORT).show();
        }
    }


    private void loadListLabor() {
        LaborDAO laborDAO = new LaborDAO(ctx);
        int posActividad = spnActividad.getSelectedItemPosition();
        int posCultivo = spnCultivo.getSelectedItemPosition();

        listLabores = laborDAO.listByIdCultivoIdActividad_IsDirect(
                listCultivos.get(posCultivo).getId(),
                listActividades.get(posActividad).getId(),
                CREATE_TYPE==TYPE_DIRECTA
        );
        loadNombreLabores();
    }

    private void loadNombreLabores(){
        listNombreLabores = new ArrayList<>();
        for(LaborVO laborVO : listLabores){
            listNombreLabores.add(laborVO.getName());
        }

    }
}

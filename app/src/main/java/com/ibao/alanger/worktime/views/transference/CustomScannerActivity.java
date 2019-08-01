package com.ibao.alanger.worktime.views.transference;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.external.TrabajadorVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.CAMERA;

/**
 * Custom Scannner Activity extending from Activity to display a custom layout form scanner view.
 */
public class CustomScannerActivity extends AppCompatActivity implements
        DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView barcodeScannerView;

    private String TAG = "QRScaner";

    private static FloatingActionButton fAButtonLinterna;
    private static boolean statusLight;

    private CaptureManager capture;

    public static final String EXTRA_HOUR = "hour";

    public static final int REQUEST_QR = 22;

    public static String HOUR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);

        statusLight=false;
        fAButtonLinterna = findViewById(R.id.fAButtonLinterna);

        Bundle b = getIntent().getExtras();
        HOUR = b.getString(EXTRA_HOUR,"");

        setTitle(HOUR.equals("")?"Agregar Trabajadores":"Agregar Trabajadores "+HOUR);

        tareoDetalleVOList = new ArrayList<>();
        validarPermisos();

        fAButtonLinterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusLight){
                    statusLight = false;
                 //   Toast.makeText(getBaseContext(),"apagando",Toast.LENGTH_SHORT).show();
                    fAButtonLinterna.setImageResource(R.drawable.ic_light_white_off);
                    barcodeScannerView.setTorchOff();

                }else {
                    statusLight = true;
                   // Toast.makeText(getBaseContext(), "encendiendo", Toast.LENGTH_SHORT).show();
                    fAButtonLinterna.setImageResource(R.drawable.ic_highlight_white_on);
                    barcodeScannerView.setTorchOn();
                }
            }
        });

        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39,BarcodeFormat.CODABAR);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeScannerView.initializeFromIntent(getIntent());
        barcodeScannerView.decodeContinuous(callback);

        beepManager = new BeepManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeScannerView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {

    }

    public void changeMaskColor(View view) {
        Random rnd = new Random();
        int color = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //viewfinderView.setMaskColor(color);
    }

    @Override
    public void onTorchOn() {
       // switchFlashlightButton.setText("Apagar Linterna");
    }

    @Override
    public void onTorchOff() {
       // switchFlashlightButton.setText("Encender Linterna");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public static Date removeSeconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    private BeepManager beepManager;
    private static List<TareoDetalleVO> tareoDetalleVOList;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            String resultado=result.getText().toString();
            Log.d(TAG,"tamaño "+resultado.length());

            if(resultado == null || verifiarDuplicado(resultado) || resultado.length()!=8) {//si s e rechazo
                // Prevent duplicate scans
                Log.d(TAG,resultado+"DUPLICADO");

                return;
            }else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                if(!HOUR.equals("")){
                    String time[] = HOUR.toString().split(":");
                    int hour = Integer.valueOf(time[0]);
                    int minutes = Integer.valueOf(time[1]);
                    date.setHours(hour);
                    date.setMinutes(minutes);
                    date = removeSeconds(date);
                }
                TareoDetalleVO tareoDetalleVO = new TareoDetalleVO();
                tareoDetalleVO.setTimeStart(formatter.format(date));
                TrabajadorVO trabajadorVO = new TrabajadorVO();
                trabajadorVO.setDni(resultado);
                tareoDetalleVO.setTrabajadorVO(trabajadorVO);

                tareoDetalleVOList.add(tareoDetalleVO);
                PageViewModel.addTrabajador(tareoDetalleVO);

                Log.d(TAG,resultado+"ingresado");

                //Toast.makeText(getBaseContext(),"Trabajador agregado: "+resultado/*+" "+muestraVO.getName()*/,Toast.LENGTH_SHORT).show();
                barcodeScannerView.setStatusText(result.getText());
                handler.post(runnable);
    /*
                Intent returnIntent = new Intent();
                returnIntent.putExtra("qr",resultado);
                setResult(RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
    */
            }
        }

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                beepManager.setVibrateEnabled(true);
                beepManager.playBeepSoundAndVibrate();
            }
        };


        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    boolean verifiarDuplicado(String newDNI){
        for(TareoDetalleVO td : tareoDetalleVOList){
            if( td.getTrabajadorVO().getDni().equals(newDNI)){
                return true;
            }
        }
        for(TareoDetalleVO td : PageViewModel.getMutable()){
            if( td.getTrabajadorVO().getDni().equals(newDNI)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private boolean validarPermisos(){
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CAMERA},100);
        }
        return false;

    }
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CustomScannerActivity.this);
        dialog.setTitle("Permisos Desactivados");
        dialog.setMessage("Debe aceptar todos los permisos para poder tomar fotos");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                solicitarPermisosManual();
            }
        });
        dialog.show();
    }
    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {
                "si",
                "no"
        };
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(CustomScannerActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?");
        alertOpciones.setItems(
                opciones,
                (dialog, i) -> {
                    if(opciones[i].equals("si")){
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                });
        alertOpciones.show();
    }
}

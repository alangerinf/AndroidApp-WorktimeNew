package com.ibao.alanger.worktime.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.database.webserver.write.UploadTareo;
import com.ibao.alanger.worktime.models.DAO.TareoDAO;
import com.ibao.alanger.worktime.views.main.MainActivity;


public class ActivityUpload extends AppCompatActivity {

    private static TextView porcentaje;
    private static TextView mensaje;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ctx = this;
        porcentaje = (TextView) findViewById(R.id.upload_tViewPorcentaje);
        mensaje = (TextView) findViewById(R.id.upload_tViewMensaje);
/*
        Toast.makeText(getBaseContext(),"Enviando Data...",
                Toast.LENGTH_SHORT).show();
*/
        UploadTareo up = new UploadTareo(ctx);
        up.upload(new TareoDAO(ctx).listAll_UPLOAD());
        r.run();
    }

    int i=0;

    Runnable r = new Runnable() {
        @Override
        public void run() {
        i++;
        Log.d("statusUpload",""+ UploadTareo.getStatus()+" "+i);
                if(UploadTareo.getStatus()== ConectionConfig.STATUS_STARTED){
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        mensaje.setText("Encontrando servidor");
                        porcentaje.setText("0%");
                        Log.d("porcentaje ",porcentaje.getText().toString());
                        r.run();
                    },1);

                    Log.d("qmierda",""+ UploadTareo.getStatus()+" "+i);
                }else {

                    Log.d("qmierda",""+i);
                    if (UploadTareo.getStatus() == ConectionConfig.STATUS_FINISHED) {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            mensaje.setText("Subiendo Archivos");
                            porcentaje.setText("100%");
                            Log.d("porcentaje ",porcentaje.getText().toString());

                        },500);
                        openMain();
                    }else{

                        if(UploadTareo.getStatus() == ConectionConfig.STATUS_PROCESSING){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    mensaje.setText("Subiendo Archivos");
                                    porcentaje.setText("50%");
                                    Log.d("porcentaje ",porcentaje.getText().toString());
                                    r.run();
                                }
                            },500);
                        }else{
                            if(UploadTareo.getStatus() == ConectionConfig.STATUS_ERROR_PARSE){
                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    mensaje.setText("Error de Conversion");
                                    porcentaje.setText("-1%");
                                    Log.d("porcentaje ",porcentaje.getText().toString());

                                },500);
                            }else{
                                if(UploadTareo.getStatus() == ConectionConfig.STATUS_ERROR_HTTP_ERROR) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            mensaje.setText("Error de Servidor");
                                            porcentaje.setText("-2%");
                                            Log.d("porcentaje ", porcentaje.getText().toString());

                                        }
                                    }, 500);
                                }else {
                                    Handler handler = new Handler();
                                    handler.postDelayed(() -> {
                                        mensaje.setText("Error de Fatal");
                                        porcentaje.setText("-3%");
                                        Log.d("porcentaje ", porcentaje.getText().toString());

                                    }, 500);
                                }
                            }
                            openMain();
                            finish();
                        }
                        Log.d("tamano",""+i);
                    }

                }
            }

    };

    void openMain(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        onBackPressed();
    }



}


package com.ibao.alanger.worktime.update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.database.webserver.ConectionConfig;
import com.ibao.alanger.worktime.database.webserver.read.Downloader;
import com.ibao.alanger.worktime.database.webserver.read.DownloaderActividades;
import com.ibao.alanger.worktime.database.webserver.read.DownloaderCentroCostes;
import com.ibao.alanger.worktime.database.webserver.read.DownloaderCultivos;
import com.ibao.alanger.worktime.database.webserver.read.DownloaderEmpresas;
import com.ibao.alanger.worktime.database.webserver.read.DownloaderFundos;

import java.util.ArrayList;
import java.util.List;


public class UpdateService extends IntentService {

    public static final String NOTIFICATION = "notification";
    public static final String RESULT = "RESULT";
    public static final String RESULT_PORCENT = "PORCENT";
    public static final String RESULT_MENSAJE = "MENSAJE";

    public UpdateService() {
        super("Download Service");
    }

    public static String TAG = UpdateService.class.getSimpleName();

    private static List<Downloader> DOWNLOADERS  = new ArrayList<>();

    void reiniciarDownloaders(){

        Log.d(TAG,"REINICIAR DESCARGADORES");
        DOWNLOADERS = new ArrayList<>();
        Context ctx = getBaseContext();
        //agregar aqui el donwloader y abajo en el metodo asincrono especificar el nombre
        DOWNLOADERS.add(new DownloaderActividades(ctx));
        DOWNLOADERS.add(new DownloaderCentroCostes(ctx));
        DOWNLOADERS.add(new DownloaderCultivos(ctx));
        DOWNLOADERS.add(new DownloaderEmpresas(ctx));
        DOWNLOADERS.add(new DownloaderFundos(ctx));
        Log.d(TAG,"TAMAÑAO DE DESCARGADORES "+DOWNLOADERS.size());

    }

    @SuppressLint("WrongThread")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        reiniciarDownloaders();

        actualizar ac = new actualizar(getBaseContext());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            ac.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        }
        else
        {
            ac.execute();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
/*
        String ANDROID_CHANNEL_ID="199232";
        int NOTIFICATION_ID=199232;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(this, ANDROID_CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Descargando Actualizacion")
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(NOTIFICATION_ID, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Descargando Actualizacion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(NOTIFICATION_ID, notification);
        }
  */
        r.run();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = getPackageName();
        String channelName = "Servicio de actualización";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_sync_black_24dp)
                .setContentTitle("Actualizando Data")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    private void publishResults(int result, String mensaje, int porcent) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        intent.putExtra(RESULT_PORCENT, porcent);
        intent.putExtra(RESULT_MENSAJE, mensaje);
        sendBroadcast(intent);
    }

    Runnable r = () -> {


        while (true){
            final int resultStatus = Activity.RESULT_OK;
            Log.d("holaasd", "empesando");
            List<Integer> statusList = new ArrayList<>();

            for(Downloader downloader:DOWNLOADERS){
                statusList.add(downloader.getStatus());
            }

            Log.d("holaasd", "111");
            String m0 = "Buscando Actulizacion";
            final String m1 = "Actualizando";
            final String m2 = "Buscando Actulizacion";
            final String m3 = "Error de Conversion";
            final String m4 = "Error de Conexion";

            int counterProcessing = 0;
            int counterFinished = 0;
            boolean act = false;
            Log.d("holaasd", "while");
            //verificando si todos las  descargas terminaron
            for (int n = 0; n < statusList.size(); n++) {

                String mensaje="";
                int porcentaje=0;
                switch (statusList.get(n)) {
                    case ConectionConfig.STATUS_FINISHED:
                        counterFinished++;
                        Log.d(TAG, "contFinished:" + counterFinished);
                        act = true;
                        break;
                    case ConectionConfig.STATUS_PROCESSING:
                        counterProcessing++;
                        Log.d(TAG, "contProsessing:" + counterFinished);
                        act = true;
                        break;

                    case ConectionConfig.STATUS_ERROR_PARSE:
                        Log.d(TAG, "error Parse " + n);
                        mensaje = m3;
                        porcentaje = ConectionConfig.STATUS_ERROR_PARSE;
                        publishResults(Activity.RESULT_CANCELED, mensaje, porcentaje);
                        return;

                    case ConectionConfig.STATUS_ERROR_HTTP_ERROR:
                        Log.d("holaasd", "qqqqqq2" + n);
                        mensaje = m4;
                        porcentaje = ConectionConfig.STATUS_ERROR_HTTP_ERROR;
                        publishResults(Activity.RESULT_CANCELED, mensaje, porcentaje);
                        return;
                }

            }

            Log.d(TAG,"finished "+counterFinished);
            Log.d(TAG,"inProceess "+counterProcessing);

            if (counterFinished == DOWNLOADERS.size()) {
                publishResults(Activity.RESULT_OK, "Actulizacion Terminada", 100);
                return;

            } else {
                Log.d(TAG, "" + DOWNLOADERS.size());
                Log.d(TAG, "" + counterFinished);
                final int porce = (int) ((float) counterFinished / (float) DOWNLOADERS.size() * 100);
                publishResults(Activity.RESULT_OK, m1+(INPROGESS_NAME.equals("")?"":" "+INPROGESS_NAME), porce);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.d("hola", e.toString());
                }


            }
        }
    };


    static String INPROGESS_NAME="";

    class actualizar extends AsyncTask<Void, Integer, Boolean> {



        Context ctx;

        actualizar(Context ctx){
            this.ctx=ctx;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG,"doInBackground");

            //   new UpdateDB(ctx);

            Log.d(TAG,"cantidad de descargadores: "+DOWNLOADERS.size());


            for (Downloader downloader : DOWNLOADERS){ // recorre cada uno de los downloaders
                downloader.download();
                while(downloader.getStatus()!=ConectionConfig.STATUS_FINISHED){ // no corre al siguiente si no acaba esto

                    if(downloader.getStatus()==ConectionConfig.STATUS_ERROR_HTTP_ERROR || downloader.getStatus()==ConectionConfig.STATUS_ERROR_PARSE){
                        Log.d(TAG,"ERROR "+downloader.getClass().getSimpleName()+" "+downloader.getStatus());
                        return null;
                    }else {
                        Log.d(TAG,"ESPERANDO A "+downloader.getClass().getSimpleName()+" "+downloader.getStatus());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    String downName = downloader.getClass().getSimpleName();

                    if(downName.equals(DownloaderActividades.class.getSimpleName())){
                        INPROGESS_NAME="Labores";
                        continue;
                    }

                    if(downName.equals(DownloaderEmpresas.class.getSimpleName())){
                        INPROGESS_NAME="Empresas";
                        continue;
                    }

                    if(downName.equals(DownloaderCentroCostes.class.getSimpleName())){
                        INPROGESS_NAME="Centros de Coste";
                        continue;
                    }

                    if(downName.equals(DownloaderCultivos.class.getSimpleName())){
                        INPROGESS_NAME="Cultivos";
                        continue;
                    }

                    if(downName.equals(DownloaderFundos.class.getSimpleName())){
                        INPROGESS_NAME="Fundos";
                        continue;
                    }

                    INPROGESS_NAME="";

                }

            }
            Log.d(TAG,"FINISHED");

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //          int progreso = values[0].intValue();

            //         pbarProgreso.setProgress(progreso);
            Log.d("segundo","doProgressUpdate");
        }

        @Override
        protected void onPreExecute() {
            Log.d("segundo","onPreExecute");
            try {
                //  mensaje.setText("Buscando Actualizacion");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //         pbarProgreso.setMax(100);
            //         pbarProgreso.setProgress(0);
        }


        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("segundo","onPostEXecute");
            super.onPostExecute(false);
            //         if(result)
            //             Toast.makeText(MainHilos.this, "Tarea finalizada!",
            //                     Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            Log.d("segundo","onCancelled");
            //        Toast.makeText(MainHilos.this, "Tarea cancelada!",
            //                Toast.LENGTH_SHORT).show();
        }
}


}




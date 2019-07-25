package com.ibao.alanger.worktime.views.transference.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.views.transference.CustomScannerActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String TAG = AddPersonalFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private static TextInputEditText ftp_tieTextHour;
    private static TextInputEditText ftp_tieTextDNI;
    private static FloatingActionButton ftp_fabSetTime;
    private static FloatingActionButton ftp_fabSetCancel;
    private static FloatingActionButton ftp_fabQR;
    private static FloatingActionButton ftp_fabRestart;









    public AddPersonalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPersonalFragment newInstance(String param1, String param2) {
        AddPersonalFragment fragment = new AddPersonalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_personal, container, false);
    }


    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction_upd_eTextDNI(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        declare();
        events();
    }


    private Handler handlerHourUpdater = new Handler();
    private Runnable runHourUpdater = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"Actualizando hora");
            ftp_tieTextHour.setText(getHour());
            handlerHourUpdater.postDelayed(this,1000);
        }
    };

    private void events() {
        Log.d(TAG,"events");

        startHourCounter();

        ftp_fabSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(ftp_tieTextHour);

            }
        });

        ftp_fabSetCancel.setOnClickListener(v->{
            startHourCounter();
        });


        ftp_fabRestart.setOnClickListener(v->{
            startHourCounter();
            ftp_tieTextDNI.setText("");
            ftp_tieTextDNI.requestFocus();
            ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showSoftInput(ftp_tieTextDNI, InputMethodManager.SHOW_FORCED);

        });

        ftp_fabQR.setOnClickListener(v->{
            IntentIntegrator intentIntegrator =new IntentIntegrator(getActivity());
            intentIntegrator
                    .setOrientationLocked(false)
                    .setCaptureActivity(CustomScannerActivity.class)
                   // .setRequestCode(REQUEST_QR_NPALLET)
                    .initiateScan();
        });

    }


    void startHourCounter(){
        ftp_fabSetTime.setAlpha(1f);
        ftp_fabSetTime.setClickable(true);
        ftp_fabSetTime.setFocusable(true);
        handlerHourUpdater.post(
                runHourUpdater
        );
        ftp_fabSetCancel.setAlpha(0f);
        ftp_fabSetCancel.setClickable(false);
        ftp_fabSetCancel.setFocusable(false);

    }


    void stopHourCounter(){//
        //Activar el bonn de  cancelar
        ftp_fabSetCancel.setAlpha(1f);
        ftp_fabSetCancel.setClickable(true);
        ftp_fabSetCancel.setFocusable(true);
        ftp_fabSetTime.setAlpha(0f);
        ftp_fabSetTime.setClickable(false);
        ftp_fabSetTime.setFocusable(false);
        handlerHourUpdater.removeCallbacks(runHourUpdater);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerHourUpdater.removeCallbacks(runHourUpdater);

    }

    private void declare() {
        Log.d(TAG,"declare");
        ftp_tieTextHour = getView().findViewById(R.id.ftp_tieTextHour);
        ftp_tieTextDNI = getView().findViewById(R.id.ftp_tieTextDNI);
        ftp_fabSetTime = getView().findViewById(R.id.ftp_fabSetTime);
        ftp_fabSetCancel = getView().findViewById(R.id.ftp_fabSetCancel);
        ftp_fabQR = getView().findViewById(R.id.ftp_fabQR);
        ftp_fabRestart = getView().findViewById(R.id.ftp_fabRestart);

    }







    String getHour(){

        Calendar calendar = new GregorianCalendar();

        int hour  = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String strHour = "";
        String strMinute = "";

        if(hour<10){
            strHour = "0"+hour;
        }else {
            strHour = ""+hour;
        }

        if(minute<10){
            strMinute = "0"+minute;
        }else {
            strMinute = ""+minute;
        }




        return strHour+":"+strMinute;

    }


    public void openTimePicker(final TextView tViewHora) {
        Dialog dialog;
        tViewHora.setError(null);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hourpicker);

        final TimePicker timePicker = dialog.findViewById(R.id.hour_datePicker1);
        timePicker.setIs24HourView(true);

        String strFecha= tViewHora.getText().toString();
        final String fecha = strFecha;
        int hour=0;
        int minute=0;

        try{
            String[] parts = fecha.split(":");
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }


        if(hour != 0 && minute != 0 ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                timePicker.setHour(hour);
                timePicker.setMinute(minute);
            }
        }

        final Button btn_aceptar = dialog.findViewById(R.id.hour_date_btn_aceptar);
        btn_aceptar.setOnClickListener(view -> {

            int hourTemp = 0;
            int minuteTemp =0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hourTemp = timePicker.getHour();
                minuteTemp = timePicker.getMinute();
            }


            String hour_ = String.valueOf(hourTemp);
            String minute_ = String.valueOf(minuteTemp);


            if(hour_.length()==1){
                hour_ = "0"+hour_;
            }

            if(minute_.length()==1){
                minute_ = "0"+minute_;
            }

            String fechaF = hour_+":"+minute_;


            tViewHora.setText(fechaF);

            dialog.dismiss();

            //BuscarServicios();
            stopHourCounter();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction_upd_eTextDNI(String mensaje);
    }
}

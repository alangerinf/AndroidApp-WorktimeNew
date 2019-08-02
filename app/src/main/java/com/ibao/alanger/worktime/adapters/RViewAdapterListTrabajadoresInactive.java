package com.ibao.alanger.worktime.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RViewAdapterListTrabajadoresInactive
        extends RecyclerView.Adapter<RViewAdapterListTrabajadoresInactive.ViewHolder>
        implements View.OnClickListener{

    private List<TareoDetalleVO> tareoDetalleVOList;

    private Context ctx;
    private boolean isActive;

    private View.OnClickListener onClickListener;

    private String TAG = RViewAdapterListTrabajadoresInactive.class.getSimpleName();

    public RViewAdapterListTrabajadoresInactive(Context ctx, List<TareoDetalleVO> tareoDetalleVOList,boolean isActive) {

        this.tareoDetalleVOList = new ArrayList<>();
                this.tareoDetalleVOList.addAll(tareoDetalleVOList);
        this.ctx = ctx;
        this.isActive=isActive;

        for(int i =0;i< this.tareoDetalleVOList.size();i++){

            /*
            if(this.tareoDetalleVOList.get(i).getTimeEnd().equals("")){
                if(!this.isActive){
                    this.tareoDetalleVOList.remove(i);
                    Log.d(TAG,"removiendo "+i);
                    i--;
                }
            }else {
                if(this.isActive){
                    this.tareoDetalleVOList.remove(i);
                    Log.d(TAG,"removiendo "+i);
                    i--;
                }
            }
            */

            if(this.isActive && !this.tareoDetalleVOList.get(i).getTimeEnd().equals("")){
                this.tareoDetalleVOList.remove(i);

                Log.d(TAG,"removiendo "+i);
                i--;
            }
            if(!this.isActive && this.tareoDetalleVOList.get(i).getTimeEnd().equals("")){
                this.tareoDetalleVOList.remove(i);
                Log.d(TAG,"removiendo "+i);
                i--;
            }


        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_tareo_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TareoDetalleVO item = tareoDetalleVOList.get(position);
        holder.tareo_item_DNI.setText(item.getTrabajadorVO().getDni());
        holder.tareo_item_Name.setText(item.getTrabajadorVO().getName().equals("")?"Sin Nombre":item.getTrabajadorVO().getName());
        holder.tareo_item_productividad.setText(""+item.getProductividad());

        Date dateIni = getHourFromDate(item.getTimeStart());

        holder.tareo_item_inicio.setText(""+(dateIni.getHours()<10?"0"+dateIni.getHours():dateIni.getHours())+":"+(dateIni.getMinutes()<10?"0"+dateIni.getMinutes():dateIni.getMinutes())+":"+(dateIni.getSeconds()<10?"0"+dateIni.getSeconds():dateIni.getSeconds()));
        if(!item.getTimeEnd().equals("")){
            Date dateFin = getHourFromDate(item.getTimeEnd());
            holder.tareo_item_hFin.setText(""+(dateFin.getHours()<10?"0"+dateFin.getHours():dateFin.getHours())+":"+(dateFin.getMinutes()<10?"0"+dateFin.getMinutes():dateFin.getMinutes())+":"+(dateFin.getSeconds()<10?"0"+dateFin.getSeconds():dateFin.getSeconds()));

        }
    }

    public void setOnClicListener(View.OnClickListener listener){
        this.onClickListener=listener;

    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    @Override
    public int getItemCount() {
        return tareoDetalleVOList.size();
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


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tareo_item_DNI;
        TextView tareo_item_Name;
        TextView tareo_item_productividad;
        TextView tareo_item_inicio;
        TextView tareo_item_hFin;

        FloatingActionButton tareo_item_fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tareo_item_DNI = itemView.findViewById(R.id.tareo_item_DNI);
            tareo_item_Name = itemView.findViewById(R.id.tareo_item_Name);
            tareo_item_productividad = itemView.findViewById(R.id.tareo_item_productividad);
            tareo_item_inicio = itemView.findViewById(R.id.tareo_item_inicio);
            tareo_item_hFin = itemView.findViewById(R.id.tareo_item_hFin);
            tareo_item_fab = itemView.findViewById(R.id.tareo_item_fab);

        }
    }
}

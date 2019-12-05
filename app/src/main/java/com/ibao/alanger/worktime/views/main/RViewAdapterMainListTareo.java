package com.ibao.alanger.worktime.views.main;

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
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import java.util.List;


public class RViewAdapterMainListTareo
        extends RecyclerView.Adapter<RViewAdapterMainListTareo.ViewHolder>
        implements View.OnClickListener{

    List<TareoVO> tareoVOList;
    Context ctx;

    private View.OnClickListener onClickListener;

    public RViewAdapterMainListTareo(Context ctx,List<TareoVO> tareoVOList) {
        this.tareoVOList = tareoVOList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_main_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TareoVO item = tareoVOList.get(position);

        holder.fmain_item_nTrabajadores.setText(""+item.getTareoDetalleVOList().size());
        holder.fmain_item_dateTime.setText(item.getDateTimeStart());
        holder.fmain_item_Fundo.setText(item.getFundoVO().getName());
        if(item.isAsistencia()){
            holder.fmain_item_Labor.setText(ctx.getString(R.string.control_de_asistencia));
            holder.fmain_item_Cultivo.setText(item.getEmpresaVO().getName());
            holder.fmain_item_costCenter.setText(ctx.getString(R.string.asistencia));
        }else {
            holder.fmain_item_Labor.setText(item.getLaborVO().getName());
            holder.fmain_item_Fundo.setText(item.getFundoVO().getName());
            holder.fmain_item_Cultivo.setText(item.getCultivoVO().getName());
            if(item.getLaborVO().isDirecto()){
                holder.fmain_item_costCenter.setText(item.getLoteVO().getCod());
            }else {
                holder.fmain_item_costCenter.setText(item.getCentroCosteVO().getName());
            }
        }

        if(item.getDateTimeEnd()==null||item.getDateTimeEnd().isEmpty()){
            holder.fmain_item_fabCheck.hide();
        }else {
            holder.fmain_item_fabCheck.show();
        }

    }


    public void setOnClicListener(View.OnClickListener listener){
        this.onClickListener=listener;
    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            Log.d("hola",""+v);
            onClickListener.onClick(v);
        }
    }

    @Override
    public int getItemCount() {
        return tareoVOList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fmain_item_Fundo;
        TextView fmain_item_Cultivo;
        TextView fmain_item_Labor;
        TextView fmain_item_dateTime;
        TextView fmain_item_costCenter;
        TextView fmain_item_nTrabajadores;
        TextView fmain_item_nHoras;
        FloatingActionButton fmain_item_fabCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fmain_item_Fundo = itemView.findViewById(R.id.fmain_item_Fundo);
            fmain_item_Cultivo = itemView.findViewById(R.id.fmain_item_Cultivo);
            fmain_item_Labor = itemView.findViewById(R.id.fmain_item_Labor);
            fmain_item_dateTime = itemView.findViewById(R.id.fmain_item_dateTime);
            fmain_item_costCenter = itemView.findViewById(R.id.fmain_item_costCenter);
            fmain_item_nTrabajadores = itemView.findViewById(R.id.fmain_item_nTrabajadores);
            fmain_item_nHoras = itemView.findViewById(R.id.fmain_item_nHoras);
            fmain_item_fabCheck = itemView.findViewById(R.id.fmain_item_fabCheck);

        }
    }
}

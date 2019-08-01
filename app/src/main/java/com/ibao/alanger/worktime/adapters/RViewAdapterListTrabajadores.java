package com.ibao.alanger.worktime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import java.util.List;


public class RViewAdapterListTrabajadores
        extends RecyclerView.Adapter<RViewAdapterListTrabajadores.ViewHolder>
        implements View.OnClickListener{

    List<TareoDetalleVO> tareoDetalleVOList;



    Context ctx;
    boolean isActive;

    private View.OnClickListener onClickListener;

    public RViewAdapterListTrabajadores(Context ctx, List<TareoDetalleVO> tareoDetalleVOList,boolean isActive) {

        this.tareoDetalleVOList = tareoDetalleVOList;
        this.ctx = ctx;
        this.isActive=isActive;
        for(int i =0;i< this.tareoDetalleVOList.size();i++){

            if(this.tareoDetalleVOList.get(i).getTimeEnd().equals("")){
                if(!isActive){
                    this.tareoDetalleVOList.remove(i);
                }
            }else {
                if (isActive){
                    this.tareoDetalleVOList.remove(i);
                }
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
        holder.tareo_item_Name.setText(item.getTrabajadorVO().getName());
        holder.tareo_item_productividad.setText(""+item.getProductividad());
        holder.tareo_item_inicio.setText(item.getTimeStart());
        if(!item.getTimeEnd().equals("")){
            holder.tareo_item_hFin.setText(item.getTimeEnd());
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

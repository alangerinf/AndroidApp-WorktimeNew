package com.ibao.alanger.worktime.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.internal.TareoDetalleVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;
import com.ibao.alanger.worktime.views.transference.TabbetActivity;

import java.util.List;


public class RViewAdapterTransferListTrabajadores
        extends RecyclerView.Adapter<RViewAdapterTransferListTrabajadores.ViewHolder>
        implements View.OnClickListener{

    List<TareoDetalleVO> tareoDetalleVOS;
    Context ctx;
    String MY_EXTRA_MODE;

    private View.OnClickListener onClickListener;

    public RViewAdapterTransferListTrabajadores(Context ctx, List<TareoDetalleVO> tareoDetalleVOS,String MY_EXTRA_MODE) {
        this.tareoDetalleVOS = tareoDetalleVOS;
        this.ctx = ctx;
        this.MY_EXTRA_MODE = MY_EXTRA_MODE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_personal_added_item,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TareoDetalleVO item = tareoDetalleVOS.get(position);
        holder.flpa_item_DNI.setText(""+item.getTrabajadorVO().getDni());
        holder.flpa_item_Name.setText(item.getTrabajadorVO().getName().equals("")?"Sin Nombre":item.getTrabajadorVO().getName());

        if(MY_EXTRA_MODE.equals(TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR)){
            holder.flpa_item_Hour.setText(""+item.getTimeStart());
            holder.flpa_item_Hour.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
        }else {
            holder.flpa_item_Hour.setText(""+item.getTimeEnd());
            holder.flpa_item_Hour.setTextColor(ContextCompat.getColor(ctx, R.color.red_pastel));
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
        return tareoDetalleVOS.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView flpa_item_Name;
        TextView flpa_item_Hour;
        TextView flpa_item_DNI;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flpa_item_Name = itemView.findViewById(R.id.flpa_item_Name);
            flpa_item_Hour = itemView.findViewById(R.id.flpa_item_Hour);
            flpa_item_DNI = itemView.findViewById(R.id.flpa_item_DNI);

        }
    }
}

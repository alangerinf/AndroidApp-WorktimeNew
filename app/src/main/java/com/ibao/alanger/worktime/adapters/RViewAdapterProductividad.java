package com.ibao.alanger.worktime.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.alanger.worktime.R;
import com.ibao.alanger.worktime.models.VO.internal.ProductividadVO;
import com.ibao.alanger.worktime.models.VO.internal.TareoVO;

import java.util.List;


public class RViewAdapterProductividad
        extends RecyclerView.Adapter<RViewAdapterProductividad.ViewHolder>
        implements View.OnClickListener{

    List<ProductividadVO> productividadVOList;
    Context ctx;

    private View.OnClickListener onClickListener;

    public RViewAdapterProductividad(Context ctx, List<ProductividadVO> productividadVOList) {
        this.productividadVOList = productividadVOList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_productividad_item_,null,false);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductividadVO item = productividadVOList.get(position);
        holder.productividad_item_DNI.setText(item.getDateTime());
        holder.productividad_item_productividad.setText(""+item.getValue());


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
        return productividadVOList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productividad_item_DNI;
        TextView productividad_item_productividad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productividad_item_DNI = itemView.findViewById(R.id.productividad_item_DNI);
            productividad_item_productividad = itemView.findViewById(R.id.productividad_item_productividad);

        }
    }
}

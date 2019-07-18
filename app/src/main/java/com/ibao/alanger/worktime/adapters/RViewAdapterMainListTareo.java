package com.ibao.alanger.worktime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibao.alanger.worktime.R;

import java.util.List;


public class RViewAdapterMainListTareo
        extends RecyclerView.Adapter<RViewAdapterMainListTareo.ViewHolder>{

    List<String> stringList;

    public RViewAdapterMainListTareo(List<String> stringList) {
        this.stringList = stringList;
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




    }



    @Override
    public int getItemCount() {
        return stringList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

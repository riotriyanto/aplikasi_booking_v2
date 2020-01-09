package com.example.aplikasi_booking_ruanganv2.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasi_booking_ruanganv2.Model.ModelDataDua;
import com.example.aplikasi_booking_ruanganv2.R;

import java.util.List;

public class AdapterDataDua extends RecyclerView.Adapter<AdapterDataDua.ViewHolder> {
    private List<ModelDataDua> mItem;
    private Context context;

    public AdapterDataDua(Context context, List<ModelDataDua> items){
        this.mItem=items;
        this.context=context;
        Log.d("c","data"+mItem.toString());
    }


    @NonNull
    @Override
    public AdapterDataDua.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_row_dua, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataDua.ViewHolder holder, int position) {
        final ModelDataDua modelData = mItem.get(position);
        holder.urai_ruangan.setText(modelData.getUrai_ruangan());
        Log.d("adapter", "data"+mItem.toString());
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urai_ruangan, tgl, jam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urai_ruangan = itemView.findViewById(R.id.ruangan);
        }
    }
}


package com.example.l_z0k.recl.ui.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l_z0k.recl.MainActivity;
import com.example.l_z0k.recl.R;

import java.util.ArrayList;
import java.util.List;


public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {
    public List<Datos> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,textView_carrera,textView_noctrl,img;
        public CardView cardView;
        public ViewHolder(View iteView) {
            super(iteView);
            cardView = itemView.findViewById(R.id.cv);
            textView= itemView.findViewById(R.id.textView);
            textView_carrera = itemView.findViewById(R.id.textView_carrera);
            textView_noctrl = itemView.findViewById(R.id.textView_noctrl);
            img = iteView.findViewById(R.id.Img);
        }
    }

    public DiseaseAdapter(MainActivity mainActivity) {
        mDataSet = new ArrayList<>();
        mContext = mainActivity;
    }
    public void setmDataSet(List<Datos> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public DiseaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_view, parent, false);
        ViewHolder vh = new ViewHolder(cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(mDataSet.get(position).name);
        holder.textView_carrera.setText(mDataSet.get(position).carrera);
        holder.textView_noctrl.setText(mDataSet.get(position).noctrl+"");
        holder.img.setText(mDataSet.get(position).photoId);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
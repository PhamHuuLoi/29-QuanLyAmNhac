package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    //Kieu du lieu
    Context context;
    ArrayList<XeMay> data = null;
    int layoutResourceId;

    public Adapter(Context context, ArrayList<XeMay> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(layoutResourceId, viewGroup, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txtMaXe.setText(data.get(i).getMaXe());
        myViewHolder.txtTenXe.setText(data.get(i).getTenXe());
        myViewHolder.txtHangSX.setText(data.get(i).getHangSX());
        myViewHolder.imgKq.setImageResource(data.get(i).getImgIcon());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgKq;
        TextView txtMaXe, txtTenXe, txtHangSX;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.txtMaXe = (TextView) itemView.findViewById(R.id.txtMaXe);
            this.txtTenXe = (TextView) itemView.findViewById(R.id.txtTenXe);
            this.txtHangSX = (TextView) itemView.findViewById(R.id.txtHangSX);
            this.imgKq = (ImageView) itemView.findViewById(R.id.imgIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }
    }

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

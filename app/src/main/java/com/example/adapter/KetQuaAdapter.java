package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appketquaxoso.R;
import com.example.model.ChiTietKetQua;

import java.util.ArrayList;

public class KetQuaAdapter extends RecyclerView.Adapter<KetQuaAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ChiTietKetQua> data;

    public KetQuaAdapter(Context context, ArrayList<ChiTietKetQua> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ket_qua,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ChiTietKetQua chiTietKetQua=data.get(i);
        viewHolder.txtTitle.setText(chiTietKetQua.getTitle());
        viewHolder.txtG1.setText(chiTietKetQua.getGiai1());
        viewHolder.txtG2.setText(chiTietKetQua.getGiai2());
        viewHolder.txtG3.setText(chiTietKetQua.getGiai3());
        viewHolder.txtG4.setText(chiTietKetQua.getGiai4());
        viewHolder.txtG5.setText(chiTietKetQua.getGiai5());
        viewHolder.txtG6.setText(chiTietKetQua.getGiai6());
        viewHolder.txtG7.setText(chiTietKetQua.getGiai7());
        viewHolder.txtG8.setText(chiTietKetQua.getGiai8());
        viewHolder.txtGDB.setText(chiTietKetQua.getGiaiDB());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public int getItemViewType(int position) {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtG1;
        TextView txtG2;
        TextView txtG3;
        TextView txtG4;
        TextView txtG5;
        TextView txtG6;
        TextView txtG7;
        TextView txtG8;
        TextView txtGDB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtG1=itemView.findViewById(R.id.txtG1);
            txtG2=itemView.findViewById(R.id.txtG2);
            txtG3=itemView.findViewById(R.id.txtG3);
            txtG4=itemView.findViewById(R.id.txtG4);
            txtG5=itemView.findViewById(R.id.txtG5);
            txtG6=itemView.findViewById(R.id.txtG6);
            txtG7=itemView.findViewById(R.id.txtG7);
            txtG8=itemView.findViewById(R.id.txtG8);
            txtGDB=itemView.findViewById(R.id.txtGDB);
        }

    }
}

package com.example.appketquaxoso;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.KetQuaAdapter;
import com.example.api.KetQuaXoSoService;
import com.example.model.ChiTietKetQua;
import com.example.model.JsonKQSX;
import com.example.model.KQSX;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.constant.Constant.MIEN_BAC_URL;

public class MienBacFragment extends Fragment {
    RecyclerView recyMienBac;
    KetQuaAdapter ketQuaAdapter;
    ArrayList<ChiTietKetQua> ketQuas;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_mien_bac, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
    }

    private void addControls() {
      /*  ketQuas= new ArrayList<>();
        recyMienBac= view.findViewById(R.id.recycle_mien_bac);
        recyMienBac.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ketQuaAdapter= new KetQuaAdapter(view.getContext(),ketQuas);
        recyMienBac.setAdapter(ketQuaAdapter);*/

        //getData(MIEN_BAC_URL);
        get gets= new get();
        gets.execute(MIEN_BAC_URL);
    }
    class get extends AsyncTask<String,Void,ArrayList<ChiTietKetQua>>{
        @Override
        protected void onPostExecute(ArrayList<ChiTietKetQua> chiTietKetQuas) {
            super.onPostExecute(chiTietKetQuas);
        }

        @Override
        protected ArrayList<ChiTietKetQua> doInBackground(String... strings) {
            final ArrayList<ChiTietKetQua> chiTietKetQuas= new ArrayList<>();

            KetQuaXoSoService.getInstance().getKetQua(new Callback<JsonKQSX>() {
                @Override
                public void onResponse(Call<JsonKQSX> call, Response<JsonKQSX> response) {
                    if (response.isSuccessful()){
                        JsonKQSX jsonKQSX=response.body();
                        String s=jsonKQSX.getStatus();
                        List<KQSX> arr=jsonKQSX.getListItem();
                        for (KQSX kqsx : arr){
                            chiTietKetQuas.add(convertData(kqsx.getDescription()));
                        }
                    }
                }
                @Override
                public void onFailure(Call<JsonKQSX> call, Throwable t) {

                }
            },strings[0]);
            return chiTietKetQuas;
        }
    }
    private void getData(String url){
        KetQuaXoSoService.getInstance().getKetQua(new Callback<JsonKQSX>() {
            @Override
            public void onResponse(Call<JsonKQSX> call, Response<JsonKQSX> response) {
                if (response.isSuccessful()){
                    ArrayList<ChiTietKetQua> chiTietKetQuas= new ArrayList<>();
                    JsonKQSX jsonKQSX=response.body();
                    String s=jsonKQSX.getStatus();
                   /* List<KQSX> arr=jsonKQSX.getListItem();
                    for (KQSX kqsx : arr){
                        chiTietKetQuas.add(convertData(kqsx.getDescription()));
                    }
                   *//* ketQuas.addAll(chiTietKetQuas);
                    ketQuaAdapter.notifyDataSetChanged();*/
                }
            }
            @Override
            public void onFailure(Call<JsonKQSX> call, Throwable t) {

            }
        },url);
    }
    private String doRegex(String s){
        String []kq=s.split("\n");
        return  kq[0];
    }
    private ChiTietKetQua convertData(String data) {
        ChiTietKetQua chiTiet= new ChiTietKetQua();
        chiTiet.setTitle("Test");
        String []items=data.split(":");
        chiTiet.setGiaiDB(doRegex(items[1].trim()));
        chiTiet.setGiai1(doRegex(items[2].trim()));
        chiTiet.setGiai2(doRegex(items[3].trim()));
        chiTiet.setGiai3(doRegex(items[4].trim()));
        chiTiet.setGiai4(doRegex(items[5].trim()));
        chiTiet.setGiai5(doRegex(items[6].trim()));
        chiTiet.setGiai6(doRegex(items[7].trim()));
        chiTiet.setGiai7(doRegex(items[8].trim()));
        chiTiet.setGiai8(doRegex(items[9].trim()));
        return chiTiet;
    }
}
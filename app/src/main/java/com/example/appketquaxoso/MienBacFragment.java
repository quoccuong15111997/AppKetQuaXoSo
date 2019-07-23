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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.KetQuaAdapter;
import com.example.api.KetQuaXoSoService;
import com.example.model.ChiTietKetQua;
import com.example.model.JsonKQSX;
import com.example.model.KQSX;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static com.example.constant.Constant.BASE_URL;
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
        ketQuas= new ArrayList<>();
        recyMienBac= view.findViewById(R.id.recycle_mien_bac);
        recyMienBac.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
        recyMienBac.addItemDecoration(new DividerItemDecoration(view.getContext(),RecyclerView.HORIZONTAL));
        ketQuaAdapter= new KetQuaAdapter(view.getContext(),ketQuas);
        recyMienBac.setAdapter(ketQuaAdapter);
        GetKetQuaTak tak= new GetKetQuaTak();
        tak.execute();
    }
    class GetKetQuaTak extends AsyncTask<Void,Void,ArrayList<KQSX>>{
        @Override
        protected void onPostExecute(ArrayList<KQSX> kqsxes) {
            super.onPostExecute(kqsxes);
            for (KQSX kqsx : kqsxes){
                ketQuas.add(convertData(kqsx.getDescription(),kqsx.getTitle()));
            }
            ketQuaAdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<KQSX> doInBackground(Void... voids) {
            ArrayList<KQSX> kqsxes= new ArrayList<>();
            try {
                URL url= new URL(BASE_URL+MIEN_BAC_URL);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject jsonObject= new JSONObject(builder.toString());
                JSONArray arrItem=jsonObject.getJSONArray("items");
                for (int i=0;i<arrItem.length();i++){
                    JSONObject item=arrItem.getJSONObject(i);
                    KQSX kqsx= new KQSX();
                    kqsx.setTitle(item.getString("title"));
                    kqsx.setPubDate(item.getString("pubDate"));
                    kqsx.setLink(item.getString("link"));
                    kqsx.setDescription(item.getString("description"));
                    kqsx.setContent(item.getString("content"));
                    kqsxes.add(kqsx);
                }
            }
            catch (Exception ex){

            }
            return kqsxes;
        }
    }
    private String doRegex(String s){
        String []kq=s.split("\n");
        return  kq[0];
    }
    private ChiTietKetQua convertData(String data,String title) {
        ChiTietKetQua chiTiet= new ChiTietKetQua();
        String []str=title.split("\\(");
        chiTiet.setTitle(str[0]+"\n"+str[1].replace(')',' '));
        String []items=data.split(":");
        chiTiet.setGiaiDB(doRegex(items[1].trim()));
        chiTiet.setGiai1(doRegex(items[2].trim()));
        chiTiet.setGiai2(doRegex(items[3].trim()));
        chiTiet.setGiai3(doRegex(items[4].trim()));
        chiTiet.setGiai4(doRegex(items[5].trim()));
        chiTiet.setGiai5(doRegex(items[6].trim()));
        chiTiet.setGiai6(doRegex(items[7].trim()));
        chiTiet.setGiai7(doRegex(items[8].trim()));
        if (items.length==10){
            chiTiet.setGiai8(doRegex(items[9].trim()));
        }
        else {
            chiTiet.setGiai8("");
        }

        return chiTiet;
    }
}
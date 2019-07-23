package com.example.appketquaxoso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.KetQuaAdapter;
import com.example.model.ChiTietKetQua;
import com.example.model.KQSX;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.constant.Constant.BASE_URL;
import static com.example.constant.Constant.MIEN_BAC_URL;
import static com.example.constant.Constant.MIEN_NAM_URL;


public class MienNamFragment extends Fragment {
    RecyclerView recyMienNam;
    KetQuaAdapter ketQuaAdapter;
    ArrayList<ChiTietKetQua> ketQuas;
    ArrayList<KQSX> kqsxArrayList;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_mien_nam, container, false);
        addControls();
        addEvents();
        return view;
    }
    private void addEvents() {
    }

    private void addControls() {
        ketQuas= new ArrayList<>();
        kqsxArrayList= new ArrayList<>();
        recyMienNam= view.findViewById(R.id.recycle_mien_nam);
        recyMienNam.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
        recyMienNam.addItemDecoration(new DividerItemDecoration(view.getContext(),RecyclerView.HORIZONTAL));
        ketQuaAdapter= new KetQuaAdapter(view.getContext(),ketQuas);
        recyMienNam.setAdapter(ketQuaAdapter);
        GetKetQuaTak tak= new GetKetQuaTak();
        tak.execute();
    }
    class GetKetQuaTak extends AsyncTask<Void,Void,ArrayList<KQSX>> {
        @Override
        protected void onPostExecute(ArrayList<KQSX> kqsxes) {
            super.onPostExecute(kqsxes);
            kqsxArrayList.addAll(kqsxes);
            getChiTiet(kqsxArrayList);
        }

        @Override
        protected ArrayList<KQSX> doInBackground(Void... voids) {
            ArrayList<KQSX> kqsxes= new ArrayList<>();
            try {
                URL url= new URL(BASE_URL+MIEN_NAM_URL);
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

    private void getChiTiet(ArrayList<KQSX> arrayList) {
        GetChiTietTask task= new GetChiTietTask();
        task.execute(arrayList);
    }

    private String doRegex(String s){
        String []kq=s.split("\n");
        return  kq[0];
    }
    private String doRegexTitle(String s){
        String []kq=s.split("\n");
        return  kq[1];
    }

    class GetChiTietTask extends AsyncTask<ArrayList<KQSX>,Void,ArrayList<ChiTietKetQua>>{
        @Override
        protected void onPostExecute(ArrayList<ChiTietKetQua> chiTietKetQuas) {
            super.onPostExecute(chiTietKetQuas);
            ketQuas.addAll(chiTietKetQuas);
            ketQuaAdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<ChiTietKetQua> doInBackground(ArrayList<KQSX>... arrayLists) {
            ArrayList<ChiTietKetQua> arrayList= new ArrayList<>();
            for (KQSX kqsx : arrayLists[0]){
                ChiTietKetQua chiTiet1= new ChiTietKetQua();
                String []str1=kqsx.getTitle().split("NGÀY");
                String []items1=kqsx.getDescription().split(":");
                int i=items1.length;
                String local1=doRegex(items1[0].trim()).replace('[',' ').replace(']',' ').trim();
                chiTiet1.setTitle("KẾT QUẢ XỔ SỐ "+local1.toUpperCase()+"\n"+str1[1]);
                chiTiet1.setGiaiDB(doRegex(items1[1].trim()));
                chiTiet1.setGiai1(doRegex(items1[2].trim()));
                chiTiet1.setGiai2(doRegex(items1[3].trim()));
                chiTiet1.setGiai3(doRegex(items1[4].trim()));
                chiTiet1.setGiai4(doRegex(items1[5].trim()));
                chiTiet1.setGiai5(doRegex(items1[6].trim()));
                chiTiet1.setGiai6(doRegex(items1[7].trim()));
                chiTiet1.setGiai7(doRegex(items1[8].trim()));
                chiTiet1.setGiai8(doRegex(items1[9].trim()));

                ChiTietKetQua chiTiet2= new ChiTietKetQua();
                String []str2=kqsx.getTitle().split("NGÀY");
                String []items2=kqsx.getDescription().split(":");
                String local2=doRegexTitle(items2[9].trim()).replace('[',' ').replace(']',' ').trim();
                chiTiet2.setTitle("KẾT QUẢ XỔ SỐ "+local2.toUpperCase()+"\n"+str2[1]);
                chiTiet2.setGiaiDB(doRegex(items2[10].trim()));
                chiTiet2.setGiai1(doRegex(items2[11].trim()));
                chiTiet2.setGiai2(doRegex(items2[12].trim()));
                chiTiet2.setGiai3(doRegex(items2[13].trim()));
                chiTiet2.setGiai4(doRegex(items2[14].trim()));
                chiTiet2.setGiai5(doRegex(items2[15].trim()));
                chiTiet2.setGiai6(doRegex(items2[16].trim()));
                chiTiet2.setGiai7(doRegex(items2[17].trim()));
                chiTiet2.setGiai8(doRegex(items2[18].trim()));

                ChiTietKetQua chiTiet3= new ChiTietKetQua();
                String []str3=kqsx.getTitle().split("NGÀY");
                String []items3=kqsx.getDescription().split(":");
                String local3=doRegexTitle(items3[18].trim()).replace('[',' ').replace(']',' ').trim();
                chiTiet3.setTitle("KẾT QUẢ XỔ SỐ "+local3.toUpperCase()+"\n"+str3[1]);
                chiTiet3.setGiaiDB(doRegex(items3[19].trim()));
                chiTiet3.setGiai1(doRegex(items3[20].trim()));
                chiTiet3.setGiai2(doRegex(items3[21].trim()));
                chiTiet3.setGiai3(doRegex(items3[22].trim()));
                chiTiet3.setGiai4(doRegex(items3[23].trim()));
                chiTiet3.setGiai5(doRegex(items3[24].trim()));
                chiTiet3.setGiai6(doRegex(items3[25].trim()));
                chiTiet3.setGiai7(doRegex(items3[26].trim()));
                chiTiet3.setGiai8(doRegex(items3[27].trim()));

                arrayList.add(chiTiet1);
                arrayList.add(chiTiet2);
                arrayList.add(chiTiet3);
            }
            return arrayList;
        }
    }
}

package com.example.appketquaxoso;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.model.Sys;
import com.example.util.WeatherAsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class ThoiTietFragment extends Fragment {
    NumberFormat format = new DecimalFormat("#0.0");
    View view;
    TabHost tabHost;
    LatLng locationMain;
    TextView txtCurrentAddressName;

    AutoCompleteTextView txtAddress;
    ListView lvTinhThanh;
    String []arrTinhThanh;
    ArrayAdapter<String> adapterTinhThanh;

    Button btnCheckWeather;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_thoi_tiet, container, false);
        addControls();
        addEvents();

        return view;
    }

    private void addEvents() {
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapterTinhThanh.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),WeatherByAddressActivity.class);
                intent.putExtra("ADDRESS",txtAddress.getText()+"");
                startActivity(intent);
            }
        });
        lvTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtAddress.setText(arrTinhThanh[position]);
            }
        });
    }

    private void addControls() {
        setupTasbHost();
        setupControl();
    }

    private void setupControl() {
        txtCurrentAddressName=view.findViewById(R.id.txtCurrentAddressName);
        new GETIP().execute();
        txtAddress=view.findViewById(R.id.txtAddressCheck);
        lvTinhThanh=view.findViewById(R.id.lvTinhThanh);
        arrTinhThanh=getResources().getStringArray(R.array.arrTinhThanh);
        adapterTinhThanh=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,arrTinhThanh);
        lvTinhThanh.setAdapter(adapterTinhThanh);
        btnCheckWeather=view.findViewById(R.id.btnCheckWeather);
        txtAddress.setAdapter(adapterTinhThanh);
    }

    private void setupTasbHost() {
        tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Vị trí hiện tại");
        tab1.setContent(R.id.tabHienTai);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Vị trí khác");
        tab2.setContent(R.id.tabNoiKhac);
        tabHost.addTab(tab2);

    }

    private void getCurrentLocation(ArrayList<Double> arrayList) {
        WeatherAsyncTask task = new WeatherAsyncTask(getActivity(), arrayList.get(0), arrayList.get(1));
        task.execute();

    }
    class GetLocation extends AsyncTask<String,Void, ArrayList<Double>>{
        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            super.onPostExecute(doubles);
            getCurrentLocation(doubles);
        }

        @Override
        protected ArrayList<Double> doInBackground(String... strings) {
            ArrayList<Double> arrayList= new ArrayList<>();
            try{
                URL url= new URL("http://api.ipstack.com/"+strings[0]+"?access_key=6a37ac662ea0b684020b4a6fe00c07a0&format=1");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

                InputStreamReader isr= new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                StringBuilder builder= new StringBuilder();
                String line=null;
                while ((line=br.readLine())!=null){
                    builder.append(line);
                }
                JSONObject jsonObject= new JSONObject(builder.toString());
                Double la=jsonObject.getDouble("latitude");
                Double log=jsonObject.getDouble("longitude");
                arrayList.add(la);
                arrayList.add(log);
            }
            catch (Exception ex){

            }
            return arrayList;
        }
    }
    class GetCiTyName extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtCurrentAddressName.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String name="";
            try{
                URL url= new URL("http://api.ipstack.com/"+strings[0]+"?access_key=6a37ac662ea0b684020b4a6fe00c07a0&format=1");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

                InputStreamReader isr= new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                StringBuilder builder= new StringBuilder();
                String line=null;
                while ((line=br.readLine())!=null){
                    builder.append(line);
                }
                JSONObject jsonObject= new JSONObject(builder.toString());
                name=jsonObject.getString("city");
            }
            catch (Exception ex){

            }
            return name;
        }
    }
    class GETIP extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getLocate(s);
            getCityName(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String systemipaddress = "";
            try
            {
                URL url_name = new URL("http://bot.whatismyipaddress.com");

                BufferedReader sc =
                        new BufferedReader(new InputStreamReader(url_name.openStream()));

                // reads system IPAddress
                systemipaddress = sc.readLine().trim();
            }
            catch (Exception e)
            {
                systemipaddress = "Cannot Execute Properly";
            }
            System.out.println("Public IP Address: " + systemipaddress +"\n");
            return systemipaddress;
        }
    }

    private void getCityName(String s) {
        new GetCiTyName().execute(s);
    }

    private void getLocate(String s) {
        new GetLocation().execute(s);
    }
}

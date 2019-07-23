package com.example.appketquaxoso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ThoiTietFragment extends Fragment {
    View view;
    TabHost tabHost;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_thoi_tiet, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
    }

    private void addControls() {
        setupTasbHost();
        setupControl();
    }

    private void setupControl() {
    }

    private void setupTasbHost() {
        tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Miền Bắc");
        tab1.setContent(R.id.tabMienBac);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Miền Trung");
        tab2.setContent(R.id.tabMienTrung);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Miền Nam");
        tab3.setContent(R.id.tabMienNam);
        tabHost.addTab(tab3);
    }
}

package com.example.appketquaxoso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ThoiTietFragment extends Fragment {
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_thoi_tiet, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
    }

    private void addControls() {
    }
}

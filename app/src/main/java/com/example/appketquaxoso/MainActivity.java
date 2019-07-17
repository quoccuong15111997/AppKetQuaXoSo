package com.example.appketquaxoso;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_mien_bac:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MienBacFragment()).commit();
                    return true;
                case R.id.navigation_mien_nam:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MienNamFragment()).commit();
                    return true;
                case R.id.navigation_mien_trung:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MienTrungFragment()).commit();
                    return true;
                case R.id.navigation_hoi_nhom:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new HoiNhomFragment()).commit();
                    return true;
                case R.id.navigation_thoi_tiet:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new ThoiTietFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

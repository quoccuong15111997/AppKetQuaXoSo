package com.example.appketquaxoso;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.model.Main;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigation bottomNavigation=(BottomNavigation)findViewById(R.id.bottom_navigation);
        bottomNavigation.setDefaultItem(0);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {
                switch (itemId){
                    case R.id.tab_home:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new MienBacFragment());
                        break;
                    case R.id.tab_images:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new MienTrungFragment());
                        break;
                    case R.id.tab_camera:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new MienNamFragment());
                        break;
                    case R.id.tab_products:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new HoiNhomFragment());
                        break;
                    case R.id.tab_more:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_fragment_containers,new ThoiTietFragment());
                        break;
                }
                transaction.commit();
            }
        });
        initPermission();

    }
    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.RECORD_AUDIO)) {
                    //Toast.makeText(BarActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Toast.makeText(BarActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(BarActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(BarActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

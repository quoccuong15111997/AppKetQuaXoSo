package com.example.appketquaxoso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sharedpreferences.SharedPreferencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class FistActivity extends FancyWalkthroughActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("ThongBao")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Đăng ký thành công";
                        if (!task.isSuccessful()) {
                            msg = "Đăng ký thất bại";
                        }
                        Log.d("FCM", msg);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        if (SharedPreferencesManager.isFirstTimeSetup()==false){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Tra Cứu Kết Quả Nhanh Nhất", "Kết quả xổ số được cập nhật nhanh nhất.",R.drawable.ic_fist);
        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Đầy Đủ Các Tỉnh Thành", "Cung cấp đầy đủ kết quả từ các nhà đài trên cả nước.",R.drawable.ic_fist_sub);
        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("Dự Báo Thời Tiết", "Có thể xem dự báo thời tiết của cả nước ngay trên ứng dụng.",R.drawable.ic_fist_weather);
        FancyWalkthroughCard fancywalkthroughCard4 = new FancyWalkthroughCard("Cùng Nhau Trò Chuyện", "Cùng nhau trò chuyện  và bàn bạc về những con số với cộng đồng ngay trên ứng dụng.",R.drawable.ic_fist_chat);

        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard4.setBackgroundColor(R.color.white);
        fancywalkthroughCard4.setIconLayoutParams(300,300,0,0,0,0);
        List<FancyWalkthroughCard> pages = new ArrayList<>();

        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);
        pages.add(fancywalkthroughCard4);

        for (FancyWalkthroughCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.black);
        }
        setFinishButtonTitle("Bắt đầu nào");
        showNavigationControls(true);
        setColorBackground(R.color.colorGreen);
        //setImageBackground(R.drawable.restaurant);
        setInactiveIndicatorColor(R.color.grey_600);
        setActiveIndicatorColor(R.color.colorGreen);
        setOnboardPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        SharedPreferencesManager.setFirstTimeSetup(false);
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

    }
}

package com.example.instant;

import com.example.api.KetQuaXoSoService;

import static com.example.constant.Constant.BASE_URL;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //KetQuaXoSoService.init(BASE_URL);
    }
}

package com.zhuanle.zhuanle;

import android.app.Application;



import com.zhuanle.zhuanle.service.StartService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       StartService.start(this);


    }

}

package com.zhuanle.zhuanle.service;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.tencent.smtt.sdk.QbSdk;


//用于初始化第三方sdk
public class StartService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.anly.githubapp.service.action.INIT";
    public StartService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, StartService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        try {
          /*  PushManager.getInstance().initialize(getApplicationContext());//个推
            // TODO: 2021/1/31个推dug
            PushManager.getInstance().setDebugLogger(getApplicationContext(), new IUserLoggerInterface() {
                @Override
                public void log(String s) {
                    KLog.e("s:"+s);
                }
            });*/


            QbSdk.PreInitCallback cb1 = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };
            QbSdk.setDownloadWithoutWifi(true);
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(), cb1);


        }catch (Exception ignored){}
    }
    //结束完后自动催费
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

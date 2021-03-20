package com.zhuanle.zhuanle;

import android.app.Application;


import com.tencent.smtt.sdk.QbSdk;
import com.zhuanle.zhuanle.service.StartService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
    private  int cpu_count=Runtime.getRuntime().availableProcessors();
    private int count=Math.max(2,Math.min(cpu_count-1,4));

    //如果不知道第三放方法结束了 但必须调用就用此方法
 // private CountDownLatch mCountdwnlach=   new CountDownLatch(1);

    @Override
    public void onCreate() {
        super.onCreate();
        //加快启动速度1
      //   StartService.start(this);

   //加快启动速度2
         ExecutorService service = Executors.newFixedThreadPool(count);//后面表示开启的线程数
          service.submit(new Runnable() {  //最后一个sdk放一个
              @Override
              public void run() {
                   //放第三方SDk1；
                  QbSdk.PreInitCallback cb1 = new QbSdk.PreInitCallback() {
                      @Override
                      public void onViewInitFinished(boolean arg0) {
                          //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                      }
                      @Override
                      public void onCoreInitFinished() {}
                  };
                  QbSdk.setDownloadWithoutWifi(true);
                  //x5内核初始化接口
                  QbSdk.initX5Environment(getApplicationContext(), cb1);
              }
          });
        service.submit(new Runnable() {
            @Override
            public void run() {
                //放第三方SDk2；
              //  mCountdwnlach.countDown(); //如果必须要此方法结束完，就打开，下面的wait就不等待了.
            }
        });

      /*  try {
             mCountdwnlach.wait();   //如果需要某第三方必须执行完的。就打开 ，如果发现已经了mcoulach,down.就不用等待了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

}

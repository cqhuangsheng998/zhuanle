package com.zhuanle.zhuanle.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.socks.library.KLog;
import com.zhuanle.zhuanle.bean.MessageSocket;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public abstract class BaseFrament extends Fragment {
    public Observable observable; //通知其他frament

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstLoad = true;//视图创建完成，将变量置为true
        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            loadDatas();
            isFirstLoad = false;
        }
    }

    public void loadDatas() {
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册监听
        observable= RxBus.getInstance().register(this.getClass().getSimpleName());//注册监听
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MessageSocket>() {
            @Override
            public void accept(MessageSocket messageSocket) throws Exception {
                rxBusCall(messageSocket);
            }
        });
    }
    //通知
    public void rxBusCall(MessageSocket message) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this.getClass().getSimpleName(),observable);
    }

    private boolean isFirstLoad = false;

/*    @Override  //onViewCreated后加载
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        KLog.e("加载一");
        if (isFirstLoad && hidden) {// 视图必须加载完成 .为可见.
            KLog.e("加载三");
            loadDatas();
            isFirstLoad = false;
        }
    }*/


}


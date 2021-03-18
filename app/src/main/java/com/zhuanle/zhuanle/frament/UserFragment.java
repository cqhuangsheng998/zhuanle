package com.zhuanle.zhuanle.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.socks.library.KLog;
import com.zhuanle.zhuanle.base.BaseFrament;
import com.zhuanle.zhuanle.databinding.FragmentHomeBinding;
import com.zhuanle.zhuanle.databinding.FragmentUserBinding;

public class UserFragment extends BaseFrament {

    private FragmentUserBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentUserBinding.inflate(inflater, container, false);
        ViewGroup p = (ViewGroup) mBinding.getRoot().getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mBinding.getRoot();
    }

    @Override
    public void loadDatas() {
        super.loadDatas();
        KLog.e("加载User;");
    }
}

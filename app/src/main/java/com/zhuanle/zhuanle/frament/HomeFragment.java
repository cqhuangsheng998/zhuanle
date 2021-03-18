package com.zhuanle.zhuanle.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.socks.library.KLog;
import com.zhuanle.zhuanle.R;
import com.zhuanle.zhuanle.base.BaseFrament;
import com.zhuanle.zhuanle.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFrament {

    private FragmentHomeBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        ViewGroup p = (ViewGroup) mBinding.getRoot().getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mBinding.getRoot();
    }

    @Override
    public void loadDatas() {
        super.loadDatas();
        KLog.e("加载homearament;");
    }
}

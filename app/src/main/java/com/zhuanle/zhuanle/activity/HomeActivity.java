package com.zhuanle.zhuanle.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.socks.library.KLog;
import com.zhuanle.zhuanle.R;
import com.zhuanle.zhuanle.frament.HomeFragment;
import com.zhuanle.zhuanle.frament.MsgFragment;
import com.zhuanle.zhuanle.frament.UserFragment;
import com.zhuanle.zhuanle.utils.StatusBar;
import com.zhuanle.zhuanle.view.LottieTabView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private HomeFragment homeFragment;
    private MsgFragment msgFragment;
    private UserFragment userFragment;
    private LottieTabView mLottieHomeTab;
    private LottieTabView mLottieMsgTab;
    private LottieTabView mLottieUserTab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentTabAdapter tabAdapter;
    private Activity activity;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //由于 启动时设置了 R.style.launcher 的windowBackground属性
        //势必要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme);
        activity = new WeakReference<>(this).get();
        super.onCreate(savedInstanceState);
        StatusBar.fitSystemBar(activity);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        msgFragment = new MsgFragment();
        userFragment = new UserFragment();
        fragments.clear();
        fragments.add(homeFragment);
        fragments.add(msgFragment);
        fragments.add(userFragment);


     tabAdapter = new FragmentTabAdapter(this, fragments, R.id.home_framentlayout);
        mLottieHomeTab = findViewById(R.id.tab_view_home);
        mLottieMsgTab = findViewById(R.id.tab_view_msg);
        mLottieUserTab = findViewById(R.id.tab_view_user);
        mLottieHomeTab.setOnClickListener(this);
       mLottieUserTab.setOnClickListener(this);
        mLottieMsgTab.setOnClickListener(this);

    }

    public class FragmentTabAdapter {
        private List<Fragment> fragments; // tab页面对应的Fragment
        private FragmentActivity fragmentActivity; // Fragment所在的Activity
        private int fragmentContentId; // Activity中所要被替换的区域的id
        private int currentTab; // 当前Tab页面索引

        public FragmentTabAdapter(FragmentActivity fragmentActivity,
                                  List<Fragment> fragments, int fragmentContentId) {
            this.fragments = fragments;
            this.fragmentActivity = fragmentActivity;
            this.fragmentContentId = fragmentContentId;
            // 默认显示第一页

            FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
                    .beginTransaction();
           ft.add(fragmentContentId, fragments.get(0));

            try {
             ft.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public int getCurrentTab() {
            return currentTab;
        }

        public Fragment getCurrentFragment() {
            return fragments.get(currentTab);
        }

        public void setCurrentFragment(int idx) {
            Fragment fragment = fragments.get(idx);
            FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
            getCurrentFragment().onPause(); // 暂停当前tab
            if (fragment.isAdded()) {  //如果添加过
                fragment.onResume(); // 启动目标tab的onResume()
            } else {
                ft.add(fragmentContentId, fragment);
            }
            showTab(idx); // 显示目标tab
            ft.commitAllowingStateLoss(); //这里也要提交一次，否则点击交换时没画面
        }

        private void showTab(int idx) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
                        .beginTransaction();
                if (idx == i) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
                ft.commitAllowingStateLoss();
            }
            currentTab = idx; // 更新目标tab为当前tab
        }


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_view_home:
                tabAdapter.setCurrentFragment(0);
                mLottieHomeTab.selected();
                mLottieMsgTab.unSelected();
                mLottieUserTab.unSelected();
                break;
            case R.id.tab_view_msg:
                tabAdapter.setCurrentFragment(1);
                mLottieMsgTab.selected();
                mLottieHomeTab.unSelected();
                mLottieUserTab.unSelected();
                break;
            case R.id.tab_view_user:
                tabAdapter.setCurrentFragment(2);
                mLottieUserTab.selected();
                mLottieMsgTab.unSelected();
                mLottieHomeTab.unSelected();
                break;
        }
    }
}
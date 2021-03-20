package com.zhuanle.zhuanle.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;
import com.socks.library.KLog;
import com.zhuanle.zhuanle.R;
import com.zhuanle.zhuanle.utils.SPUtils;
import com.zhuanle.zhuanle.utils.StatusBar;
import com.zhuanle.zhuanle.viewdata.AppConst;
import com.zhuanle.zhuanle.viewdata.Config;

import java.lang.ref.WeakReference;


public class SplashActivity extends AppCompatActivity {

    private String latidute;
    private Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        activity = new WeakReference<>(this).get();
        super.onCreate(savedInstanceState);
        boolean b = (boolean) SPUtils.get(AppConst.DATA, AppConst.showlog, false);
        if (!b) {
            Dialog dialog = new Dialog(this, R.style.jmui_default_dialog_style);
            LayoutInflater inflater = LayoutInflater.from(activity);
            //下面弹出
            View viewlog = inflater.inflate(R.layout.dilog_start, null);
            dialog.setContentView(viewlog);
            Window window1 = dialog.getWindow();
            window1.setWindowAnimations(R.style.mystyle); // 添加动画
            window1.setGravity(Gravity.CENTER);
            //全屏
            //   window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(false);
            TextView textView = viewlog.findViewById(R.id.tiaoli);

            SpannableStringBuilder spannable = new SpannableStringBuilder();
            //设置文字
            spannable.append(getString(R.string.tiaoli));

            int index = spannable.toString().indexOf(getString(R.string.yinsi_fuwu));
            int len = getString(R.string.yinsi_fuwu).length();

            //设置文字的前景色，2、4分别表示可以点击文字的起始和结束位置。
            spannable.setSpan(new ForegroundColorSpan(Color.RED), index, len + index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //这个一定要记得设置，不然点击不生效
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent intent1 = new Intent(activity, WebViewActivity.class);
                    intent1.putExtra("url", Config.HTTP_COMMON_CONTROLLER_URL + "/clause/agreement_cn.html");
                    intent1.putExtra("titel", getString(R.string.yinsi_fuwu));
                    startActivity(intent1);
                }
            }, index, len + index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spannable);

            TextView affitte = viewlog.findViewById(R.id.dialog_affitte);
            View clear = viewlog.findViewById(R.id.dialog_clear);
            affitte.setOnClickListener(view -> {
                dialog.dismiss();
                next();
                MobSDK.submitPolicyGrantResult(true, null);
                SPUtils.put(AppConst.DATA, AppConst.showlog, true);

            });
            clear.setOnClickListener(view -> {
                dialog.dismiss();
                MobSDK.submitPolicyGrantResult(false, null);
                activity.finish();
            });
            dialog.show();
        } else
            next();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void next() {
        // TODO: 2021/3/16
      //  SDKInitializer.initialize(getApplicationContext());//百度
        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
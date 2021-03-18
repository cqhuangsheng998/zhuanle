package com.zhuanle.zhuanle.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CommonUtils {
    private static String name;
    public static boolean isStringEmpty(String s) {
        return isStringEmpty(s, false);
    }
    public static boolean isStringEmpty(String s, boolean needTrim) {
        if (s != null) {
            if (needTrim) {
                if (!s.trim().equals("")) {
                    return false;
                }
            } else if (!s.equals("")) {
                return false;
            }
        }

        return true;
    }

    public static int getIntValue(Object ob) {
        try {
            return getIntValue(ob, 0);
        } catch (Exception var2) {
            System.err.println(var2.getMessage());
            return 0;
        }
    }
    private static int getIntValue(Object obj, int defaultVal) {
        String value = String.valueOf(obj);
        return obj != null && !isStringEmpty(value, true) ? Integer.parseInt(value) : defaultVal;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    /**
     * 判断网络是否可用
     * <p>需添加权限xml中 android.permission.ACCESS_NETWORK_STATE</p>
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
    //读取MD5
    public static String byteToHexStringForMD5(byte[] MD5Bytes) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] str = new char[32];
        int k = 0;

        for(int i = 0; i < 16; ++i) {
            byte byte0 = MD5Bytes[i];
            str[k++] = hexDigits[byte0 >>> 4 & 15];
            str[k++] = hexDigits[byte0 & 15];
        }

        String s = new String(str);
        return s;
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    //将px转换为dp
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //px转sp
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 分月存储，避免一个文件夹太多
     */
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat df = new SimpleDateFormat("yyMM");//设置日期格式
    private static String getDateString() {
        return df.format(System.currentTimeMillis());// new Date()为获取当前系统时间
    }
    public static Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

/*    public static String getImgName(File filepath,boolean isvideo) {
        String dateString = getDateString();
        if(isvideo)
            name = "mp4"; //后缀名
        else
            name = "png";
        final String fileMd5 = FileUtils.getInstence().getMd5Name(filepath);
        return  dateString+ UserManager.getUploadName()+(fileMd5!=null?fileMd5: UUID.randomUUID().toString())+"."+name;
    }*/
}

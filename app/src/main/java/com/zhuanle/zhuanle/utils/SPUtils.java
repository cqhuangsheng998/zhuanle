package com.zhuanle.zhuanle.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.zhuanle.zhuanle.help.AppGlobals;

import java.util.Set;
/**
 * 用来存缓存 和区缓存
 */
public class SPUtils {
    public static   Context context= AppGlobals.getApplication();
    public static void put(String sPname,String key, Object value) {
        putObject(sPname,key, value);
    }

    public static Object    get(String sPname,String key, Object object) {
        return getObject(sPname,key, object);
    }

    public static void putObject(String sPname,String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(sPname, 0);
        SharedPreferences.Editor editor = sp.edit();
        if(value instanceof String) {
            editor.putString(key, (String)value);
        } else if(value instanceof Integer) {
            editor.putInt(key, ((Integer)value).intValue());
        } else if(value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean)value).booleanValue());
        } else if(value instanceof Float) {
            editor.putFloat(key, ((Float)value).floatValue());
        } else if(value instanceof Long) {
            editor.putLong(key, ((Long)value).longValue());
        } else if(value instanceof Set) {
            if(Build.VERSION.SDK_INT >= 11) {
                editor.putStringSet(key, (Set)value);
            }
        } else if(value == null) {
            editor.remove(key);
        }

        editor.apply();
    }

    public static Object getObject(String sPname, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(sPname, 0);
        return object instanceof String?sp.getString(key, (String)object)
                :(object instanceof Integer?Integer.valueOf(sp.getInt(key, ((Integer)object).intValue()))
                :(object instanceof Boolean?Boolean.valueOf(sp.getBoolean(key, ((Boolean)object).booleanValue()))
                :(object instanceof Float?Float.valueOf(sp.getFloat(key, ((Float)object).floatValue()))
                :(object instanceof Long?Long.valueOf(sp.getLong(key, ((Long)object).longValue()))
                :(null == object?sp.getString(key, (String)null):null)))));
    }

    public static void clear(String sPname,String Key) {
        SharedPreferences preferences = context.getSharedPreferences(sPname, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Key);
        editor.apply();
    }



}
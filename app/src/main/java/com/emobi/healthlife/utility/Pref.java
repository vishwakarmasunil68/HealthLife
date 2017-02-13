package com.emobi.healthlife.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunil on 13-02-2017.
 */

public class Pref {
    private final static String DB_NAME="healthlift.txt";


    public static void SetStringPref(Context context,String KEY,String VALUE){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(KEY,VALUE);
        editor.commit();
    }
    public static String GetStringPref(Context context,String KEY,String defValue){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        return sp.getString(KEY,defValue);
    }

    public static void SetBooleanPref(Context context,String KEY,boolean VALUE){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(KEY,VALUE);
        editor.commit();
    }
    public static boolean GetBooleanPref(Context context,String KEY,boolean defValue){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(KEY,defValue);
    }
}

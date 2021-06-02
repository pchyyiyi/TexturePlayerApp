package com.pchyyiyi.textureplay.util;

import android.util.Log;

public class AndroidLog {
    private static final String TAG = "textureApp";

    public static boolean DEBUG = true;

    public static void d(String msg){
        if(DEBUG) {
            d(TAG, "");
        }
    }

    public static void d(String tag, String msg){
        if(DEBUG){
            Log.d(tag,msg);
        }
    }


    public static void e(String msg){
        if(DEBUG){
            e(TAG,"");
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG){
            Log.e(tag,msg);
        }
    }

    public static void i(String msg){
        if(DEBUG){
            i(TAG,"");
        }
    }

    public static void i(String tag, String msg){
        if(DEBUG){
            Log.i(tag,msg);
        }
    }

}

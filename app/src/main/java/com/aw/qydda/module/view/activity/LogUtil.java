package com.aw.qydda.module.view.activity;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "Young Lee";
    public static final boolean DEBUG = true;

    public static void Log(String msg) {
        if(DEBUG)
            Log.i(TAG, msg);
    }
}

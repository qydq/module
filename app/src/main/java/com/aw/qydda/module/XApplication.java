package com.aw.qydda.module;

import android.app.Application;

import org.xutils.x;

/**
 * Created by qydda on 2016/11/15.
 */

public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}

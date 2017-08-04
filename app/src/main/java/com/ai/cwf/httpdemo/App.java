package com.ai.cwf.httpdemo;

import android.app.Application;

/**
 * Created at 陈 on 2017/8/4.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OKHttpUtils.getInstance(this);
    }
}

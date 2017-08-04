package com.ai.cwf.httpdemo;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created at 陈 on 2017/8/4.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class OKHttpUtils {
    private OkHttpClient okHttpClient;
    private static OKHttpUtils instance;

    public static OKHttpUtils getInstance(Context app) {
        if (instance == null) {
            synchronized (OKHttpUtils.class) {
                instance = new OKHttpUtils(app);
            }
        }
        return instance;
    }

    public OKHttpUtils(Context app) {
        okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(app.getCacheDir().getAbsoluteFile(), 1024 * 1024 * 10))
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}

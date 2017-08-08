package com.ai.cwf.httpdemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created at 陈 on 2017/8/8.
 * 在服务器不支持缓存的情况下，添加这个拦截器，使OKHttp支持缓存，
 * 如果服务器支持，会在返回的response中添加返回头Cache-Control:max-age=xxx，只需要okHttpClient添加cache就可以了
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (null != response.header("Cache-Control")) {
            return response;
        }
        Response returnResponse = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for 5 * 60 second
                .addHeader("Cache-Control", "max-age=" + 5 * 60)
                .build();
        return returnResponse;
    }
}

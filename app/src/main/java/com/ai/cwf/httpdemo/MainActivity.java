package com.ai.cwf.httpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tip;

    private Handler UIHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tip = (TextView) findViewById(R.id.tip);
        tip.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String response = HttpClientUtil.getUseHttpClient("http://www.baidu.com");
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (null != response)
                                    tip.setText(response);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String response = HttpClientUtil.postUseHttpClient("http://www.baidu.com");
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (null != response)
                                    tip.setText(response);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String response = HttpURLConnectionUtil.postUseHttpURLConnection("http://www.baidu.com");
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (null != response)
                                    tip.setText(response);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn4:
                final Request request = new Request.Builder().url("http://www.baidu.com").build();
                Call call = OKHttpUtils.getInstance(this).getOkHttpClient().newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tip.setText(null != e.getMessage() ? e.getMessage() : "请求失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("response", null != response.cacheResponse() ? "cache--" : "network--" + response.toString());
                        final String ss = (null != response.cacheResponse() ? "cache--" : "network--")
                                + response.toString() + "\n" + response.body().string();
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tip.setText(ss);

                            }
                        });
                    }
                });
                break;
            case R.id.btn5:
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", "me")
                        .build();
                Request request1 = new Request.Builder().url(Math.random() * 10 > 5 ? "http://www.baidu.com/search" : "http://www.baidu.com").post(requestBody).build();
                Call call1 = OKHttpUtils.getInstance(this).getOkHttpClient().newCall(request1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tip.setText(null != e.getMessage() ? e.getMessage() : "请求失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("response", null != response.cacheResponse() ? "cache--" : "network--" + response.toString());
                        final String ss = (null != response.cacheResponse() ? "cache--" : "network--")
                                + response.toString() + "\n" + response.body().string();
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tip.setText(ss);

                            }
                        });
                    }
                });
                break;
            case R.id.btn6:
                String url = "http://ip.taobao.com/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        //添加string支持
                        .addConverterFactory(ScalarsConverterFactory.create())
                        //添加json支持
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                //实例化
                IpServiceApi ipServiceApi = retrofit.create(IpServiceApi.class);
                //创建call
                retrofit2.Call<String> call2 = ipServiceApi.getIpMsg("service", "110.110.110.110");
                //异步请求
                call2.enqueue(new retrofit2.Callback<String>() {
                    @Override
                    public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                        if (response.isSuccessful()) {
                            tip.setText(response.body());
                        } else {
                            tip.setText("数据出错：" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<String> call, Throwable t) {
                        tip.setText("请求出错：" + t.getMessage());
                    }
                });
                break;
        }
    }

    /**
     * 根据参数及附件得到一个多类型的构造器，用于构建请求体
     *
     * @param params 请求参数
     * @param files  要上传的附件
     * @return 多类型的构造器
     */
    public MultipartBody.Builder getMultipartBuilder(Map<String, String> params, List<File> files) {
        String mPostFileKey = "files";
        MediaType MEDIA_TYPE_ATTACH = MediaType.parse("application/octet-stream");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        Set<Map.Entry<String, String>> sets = params.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
                continue;
            builder.addFormDataPart(key, value);
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        for (File file : files) {
            if (!file.exists())
                continue;
            builder.addFormDataPart(mPostFileKey, file.getName(), RequestBody.create(MEDIA_TYPE_ATTACH, file));
        }
        return builder;
    }
}

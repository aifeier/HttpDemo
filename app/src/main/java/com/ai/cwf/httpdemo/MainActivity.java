package com.ai.cwf.httpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                        Log.d("response", null != response.cacheResponse() ? "cache--" : "newwork--" + response.toString());
                        final String ss = (null != response.cacheResponse() ? "cache--" : "newwork--")
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
                Request request1 = new Request.Builder().url("http://www.baidu.com").post(requestBody).build();
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
                        Log.d("response", null != response.cacheResponse() ? "cache--" : "newwork--" + response.toString());
                        final String ss = (null != response.cacheResponse() ? "cache--" : "newwork--")
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
        }
    }
}

package com.ai.cwf.httpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

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
        }
    }
}

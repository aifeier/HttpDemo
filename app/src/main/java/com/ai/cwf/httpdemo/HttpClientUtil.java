package com.ai.cwf.httpdemo;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created at 陈 on 2017/8/4.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class HttpClientUtil {
    //创建httpclient
    private static HttpClient createHttpClient() {
        HttpParams httpParams = new BasicHttpParams();
        //设置连接超时
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        //设置请求超时
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        //设置为tcp
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        //设置http版本
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        //设置http字符集
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(httpParams, true);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

    public static String getUseHttpClient(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient httpClient = createHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            int code = httpResponse.getStatusLine().getStatusCode();
            if (null != httpEntity) {
                InputStream inputStream = httpEntity.getContent();
                String response = converStreamToString(inputStream);
                Log.d("response", "请求状态码：" + code + "\n请求结果：\n" + response);
                inputStream.close();
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String converStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        String respose = sb.toString();
        return respose;
    }

    public static String postUseHttpClient(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient httpClient = createHttpClient();
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            nameValuePairList.add(new BasicNameValuePair("username", "me"));
            nameValuePairList.add(new BasicNameValuePair("school", "here"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            int code = httpResponse.getStatusLine().getStatusCode();
            if (null != httpEntity) {
                InputStream inputStream = httpEntity.getContent();
                String response = converStreamToString(inputStream);
                Log.d("response", "请求状态码：" + code + "\n请求结果：\n" + response);
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

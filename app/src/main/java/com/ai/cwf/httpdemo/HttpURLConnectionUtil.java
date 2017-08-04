package com.ai.cwf.httpdemo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created at 陈 on 2017/8/4.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class HttpURLConnectionUtil {

    public static HttpURLConnection getHttpUrlConnection(String strUrl, String requestMethod) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置链接超时时间
            httpURLConnection.setConnectTimeout(10000);
            //设置读取超时时间
            httpURLConnection.setReadTimeout(10000);
            //设置请求参数
            httpURLConnection.setRequestMethod(requestMethod);
            //添加Header
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            //接收输入流
            httpURLConnection.setDoInput(true);
            //传递参数时需要开启
            httpURLConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    public static void postParams(OutputStream outputStream, Map<String, String> params) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        if (null != outputStream) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            outputStream.close();
        }
    }

    public static String postUseHttpURLConnection(String url) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = getHttpUrlConnection(url, "POST");
        try {
            Map<String, String> params = new HashMap<>();
            params.put("username", "me");
            params.put("school", "here");
            postParams(httpURLConnection.getOutputStream(), params);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            int code = httpURLConnection.getResponseCode();
            String response = converStreamToString(inputStream);
            Log.d("response", "请求状态码:" + code + "\n请求结果:\n" + response);
            return response;
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
}

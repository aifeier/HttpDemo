package com.ai.cwf.httpdemo;

import android.text.TextUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created at 陈 on 2017/8/7.
 * retrofit api
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public interface IpServiceApi {
    //简单get请求 动态Url
    @GET("{path}/getIpInfo.php")
    Call<String> getIpMsg(@Path("path") String path,@Query("ip") String ip);

    //动态指定查询条件：@Query与@QueryMap
    @GET("getIpInfo.php")
    Call<String> getIpMsg1(@Query("ip") String ip);

    @GET("getIpInfo.php")
    Call<String> getIpMsg2(@QueryMap Map<String, String> options);


    //传输数据类型为键值对：@Field
    // @FormUrlEncoded注解来标明这是一个表单请求
    @FormUrlEncoded
    @POST("getIpInfo.php")
    Call<String> getIpMsg3(@Field("ip") String first);

    //传输数据类型Json字符串：@Body  用@Body这个注解标识参数对象即可，retrofit会将Ip对象转换为字符串。
    @POST("getIpInfo.php")
    Call<String> getIpMsg(@Body Object ip);

    //单个文件上传：@Part
    // Multipart注解表示允许多个@Part
    @Multipart
    @POST("user/photo")
    Call<String> updateUser(@Part MultipartBody.Part photo, @Part("description") RequestBody description);

    //多个文件上传：@PartMap
    @Multipart
    @POST("user/photo")
    Call<String> updateUser(@PartMap Map<String, RequestBody> photos, @Part("description") RequestBody description);

    //消息报头Header
    @GET("some/endpoint")
    @Headers("Accept-Encoding: application/json")
    Call<ResponseBody> getCarType();

    @GET("some/endpoint")
    @Headers({
            "Accept-Encoding: application/json",
            "User-Agent: MoonRetrofit"
    })
    Call<ResponseBody> getCarType1();

    //动态
    @GET("some/endpoint")
    Call<ResponseBody> getCarType(
            @Header("Location") String location);

}

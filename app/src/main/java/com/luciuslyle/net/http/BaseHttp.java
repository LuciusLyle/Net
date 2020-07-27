package com.luciuslyle.net.http;


import com.luciuslyle.net.http.converter.ExtConverterFactory;
import com.luciuslyle.net.http.inter.RequestCallFilter;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.CipherSuite;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseHttp {

    private static BaseHttp _instance;
    public HttpConfig config;
    private OkHttpClient.Builder okBuilder;
    private Retrofit.Builder retfitRetrofit;
    private Retrofit retrofit;
    //OKhttp反射
    private HttpField mHttpHelper;
    //返回数据过滤
    private RequestCallFilter mFilter;

    public static BaseHttp getInstance() {
        synchronized (BaseHttp.class) {
            if (_instance == null) {
                _instance = new BaseHttp();
            }
            return _instance;
        }
    }

    public void init(HttpConfig config) {
        this.config = config;
        if (okBuilder == null) {
            okBuilder = new OkHttpClient.Builder()
                    .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                    .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                    .writeTimeout(config.getConnectTimeout(), TimeUnit.SECONDS);

            if (config.getOkhttpConfiguration() != null) {
                config.getOkhttpConfiguration().configOkhttp(okBuilder);
            }
        }
        
        if (retfitRetrofit == null) {
            HttpUrl httpUrl = HttpUrl.get(config.getBaseUrl());
            retfitRetrofit = new Retrofit.Builder()
                    .baseUrl(config.getBaseUrl())
                    .client(okBuilder.build()) // 设置 OkHttp
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 RxJava
                    .addConverterFactory(GsonConverterFactory.create())//使用 Gson
            /**
             * Gson: com.squareup.retrofit2:converter-gson
             * Jackson: com.squareup.retrofit2:converter-jackson
             * Moshi: com.squareup.retrofit2:converter-moshi
             * Protobuf: com.squareup.retrofit2:converter-protobuf
             * Wire: com.squareup.retrofit2:converter-wire
             * Simple XML: com.squareup.retrofit2:converter-simplexml
             */
            ;

            if (config.getRetrofitConfiguration() != null) {
                config.getRetrofitConfiguration().configRetrofit(retfitRetrofit);
            }
        }

    }

    public RequestCallFilter getRequestCallFilter() {
        return mFilter;
    }

    public Retrofit setRequestCallFilter(RequestCallFilter filter) {
        mFilter = filter;
        return retrofit;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = retfitRetrofit.build();
        }
        return retrofit;
    }

    /**
     * 全局修改BaseRul
     *
     * @param host
     */
    public void setHost(String host) {
        if (mHttpHelper == null) {
            mHttpHelper = new HttpField(retrofit.baseUrl());
        }
        mHttpHelper.setBaseUil(host);
    }   
    
}

package com.luciuslyle.net;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Retrofit;

public class HttpConfig {
    
    private RetrofitConfiguration mRetrofitConfiguration;
    private OkhttpConfiguration mOkhttpConfiguration;
    private String baseUrl;
    private int connectTimeout=3000;//请求超时时长
    private int readTimeout=3000;//读取超时
    private int writeTimeout=3000; //写入超时


    public RetrofitConfiguration getRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    public HttpConfig setRetrofitConfiguration(RetrofitConfiguration retrofitConfiguration) {
        mRetrofitConfiguration = retrofitConfiguration;
        return this;
    }

    public OkhttpConfiguration getOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    public HttpConfig setOkhttpConfiguration(OkhttpConfiguration okhttpConfiguration) {
        mOkhttpConfiguration = okhttpConfiguration;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
        
    }

    public HttpConfig setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public HttpConfig setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    /**
    * {@link Retrofit} 自定义配置接口
    */
    public interface RetrofitConfiguration {
        void configRetrofit( Retrofit.Builder builder);
    }

     /**
     * {@link OkHttpClient} 自定义配置接口
     */
     public interface OkhttpConfiguration {
        void configOkhttp( OkHttpClient.Builder builder);
     }

     
     
     
        private ExecutorService mExecutorService;
        /**
         * 返回一个全局公用的线程池,适用于大多数异步需求。
         * 避免多个线程池创建带来的资源消耗。
         *
         * @return {@link Executor}
         */
        public ExecutorService provideExecutorService() {
            return mExecutorService == null ? new ThreadPoolExecutor(0  //要保留在池中的线程数
                    , Integer.MAX_VALUE //允许的最大线程数
                    , 60, TimeUnit.SECONDS, new SynchronousQueue<>()//存放待执行的线程，通过execute方法提交线程，就会保存在这个队列
                    , Util.threadFactory("Arms Executor", false))//创建线程池的工厂
                    : mExecutorService;
        }



}

package com.luciuslyle.net;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.luciuslyle.net.demo.UserEntity;
import com.luciuslyle.net.demo.UserService;
import com.luciuslyle.net.http.BaseHttp;
import com.luciuslyle.net.demo.HttpResult;
import com.luciuslyle.net.http.HttpConfig;
import com.luciuslyle.net.http.log.BaseHttpLogging;
import com.luciuslyle.net.http.log.HttpLoggingInterceptor;
import com.luciuslyle.net.http.RetrofitHelper;
import com.luciuslyle.net.http.RetrofitServiceManager;
import com.luciuslyle.net.http.call.RequestCallback;
import com.luciuslyle.net.http.inter.RequestCallFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;


import org.json.JSONObject;

import java.util.List;

import androidx.cardview.widget.CardView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //test();
                //组合/ 合并操作符 请求 
                //test2();
            }
        });
 

    }

    private void test2() {
        Observable<HttpResult<List<UserEntity>>> observable1 = RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class)
                .executeGetEntity("home/productCateList");
        Observable observable2 = RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class)
                .executeGet("home/content");
        Observable.merge(observable1,observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof ResponseBody) {
                            Log.e("xxx","合并请求解析1");

                        }else if (o instanceof HttpResult){
                            Log.e("xxx","合并请求解析2");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("xxx","合并请求,连个同时完成1次回调");
                    }
                });


    }


    private void test() {
        RetrofitHelper.qurest(RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class).executeGetEntity("home/productCateList"),
                new RequestCallback<HttpResult<List<UserEntity>>>() {
                    @Override
                    public void onFailure(Throwable e, String msg) {
                        Log.e("xxx","过滤解析错误分类："+msg);
                    }
                    @Override
                    public void onResponse(HttpResult<List<UserEntity>> response) {
                        super.onResponse(response);
                        Log.e("xxx","实体:消息："+response.getData().get(0).getName());
                    }
                });

        RetrofitHelper.qurest(RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class).executeGet("home/content"),  
                new RequestCallback<ResponseBody>() {
            @Override
            public void onFailure(Throwable e, String msg) {
                Log.e("xxx","过滤解析错误分类："+msg);
            }
            @Override
            public void onResponse(JSONObject response) {
                Log.e("xxx","Gson:消息："+response.optString("message"));
            }
        });
    }

    private void init() {
        HttpConfig config = new HttpConfig().setBaseUrl("https://interface.app.zuizhezhi.com/").setRetrofitConfiguration(new HttpConfig.RetrofitConfiguration() {
            @Override
            public void configRetrofit(@NonNull Retrofit.Builder builder) {
            }
        }).setOkhttpConfiguration(new HttpConfig.OkhttpConfiguration() {
            @Override
            public void configOkhttp(@NonNull OkHttpClient.Builder builder) {
                //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                // builder.addNetworkInterceptor(loggingInterceptor);
                builder.addNetworkInterceptor(new HttpLoggingInterceptor(new BaseHttpLogging()).setLevel(HttpLoggingInterceptor.Level.BODY));
//                builder.addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request original = chain.request();
//                        Request.Builder requestBuilder = original.newBuilder()
//                                .addHeader("user-agent", "android")
//                                .addHeader("content-type", "application/json;charset:utf-8")
//                                .addHeader("language", BaseSP.getInstance().getString("language"))
//                                .addHeader("token", BaseSP.getInstance().getString("token"))
//                                .addHeader("version-code", ""+AndroidUtil.getVersionCode(BaseInitData.applicationContext))
//                                .header("device", "device")//设备信息
//                                .header("version", "version")//app版本
//                                .header("apiVersion", "apiVersion")//api版本
//                                .header("channelId", "channelId")//渠道
//                                .header("networkType", "networkType");//网络类型
//                        Request request = requestBuilder.build();
//                        return chain.proceed(request);
//                    }
//                });
            }
        });
        BaseHttp.getInstance().init(config);
        /**
         * 请求返回过滤
         */
        BaseHttp.getInstance().setRequestCallFilter(new RequestCallFilter() {
            @Override
            public void onNext(Object o) {
                if (o instanceof ResponseBody) {

                } else if (o instanceof HttpResult) {
                    
                }
            }
        });

    }

}
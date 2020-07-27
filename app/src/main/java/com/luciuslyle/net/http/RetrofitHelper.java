package com.luciuslyle.net.http;


import com.luciuslyle.net.http.call.RequestCallback;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author
 * @version 1.0
 * @date 2020/7/16
 */
public class RetrofitHelper {
    
    public static void qurest(Observable observable, RequestCallback callback) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    public enum LoadType{
        /**
         * 只加载本地
         */
        LOCAL,
        /**
         * 加载网络
         */
        NETWORK,
        /**
         * 优先加载本地
         */
        PRIORITY_LOCAL,
        /**
         * 优先加载网络
         */
        PRIORITY_NETWORK,
    }
}

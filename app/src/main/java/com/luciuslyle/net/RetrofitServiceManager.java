package com.luciuslyle.net;


import java.util.WeakHashMap;

import retrofit2.Retrofit;

public class RetrofitServiceManager {
    
    private WeakHashMap<String, Object> mRetrofitServiceCache;
    private static RetrofitServiceManager _instance;
    
    //自定义  
    private ObtainServiceDelegate mObtainServiceDelegate;

    public static RetrofitServiceManager getInstance() {
        synchronized (RetrofitServiceManager.class) {
            if (_instance == null) {
                _instance = new RetrofitServiceManager();
            }
            return _instance;
        }
    }

    public void setObtainServiceDelegate(ObtainServiceDelegate mObtainServiceDelegate) {
        this.mObtainServiceDelegate = mObtainServiceDelegate;
    }


    public synchronized <T> T obtainRetrofitService( Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = new WeakHashMap<>();
        }
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
//            if (mObtainServiceDelegate != null) {
//                retrofitService = mObtainServiceDelegate.createRetrofitService(BaseHttp.getInstance().getRetrofit(), serviceClass);
//            }
            if (retrofitService == null) {
                retrofitService = BaseHttp.getInstance().getRetrofit().create(serviceClass);
            }
        }
        mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        return retrofitService;
    }


    public interface ObtainServiceDelegate {

        
        <T> T createRetrofitService(Retrofit retrofit, Class<T> serviceClass);
    }
}

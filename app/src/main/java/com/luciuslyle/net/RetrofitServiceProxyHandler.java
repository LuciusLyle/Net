//package com.luciuslyle.net.http;
//
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//
//import io.reactivex.Observable;
//import io.reactivex.Single;
//import retrofit2.Retrofit;
//
///**
// * 获取RetrofitService 代理模式
// */
//public class RetrofitServiceProxyHandler implements InvocationHandler {
//
//    private Retrofit mRetrofit;
//    private Class<?> mServiceClass;
//    private Object mRetrofitService;
//
//    public RetrofitServiceProxyHandler(Retrofit retrofit, Class<?> serviceClass) {
//        mRetrofit = retrofit;
//        mServiceClass = serviceClass;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {
// 
//        // 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
//        Log.e("xxx","后执行?");
//        if (method.getReturnType() == Observable.class) {
//
//            return Observable.defer(() -> {
//                return (Observable) method.invoke(getRetrofitService(), args);
//            });
//        } else if (method.getReturnType() == Single.class) {
//            return Single.defer(() -> {
//                return (Single) method.invoke(getRetrofitService(), args);
//            });
//        }
//
//        // 返回值不是 Observable 或 Single 的话不处理。
//        return method.invoke(getRetrofitService(), args);
//    }
//
//    private Object getRetrofitService() {
//        if (mRetrofitService == null) {
//            mRetrofitService = mRetrofit.create(mServiceClass);
//        }
//        return mRetrofitService;
//    }
//}

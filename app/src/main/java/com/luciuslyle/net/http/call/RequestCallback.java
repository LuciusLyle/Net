package com.luciuslyle.net.http.call;

import android.app.Dialog;
import android.net.ParseException;

import com.google.gson.JsonSyntaxException;
import com.luciuslyle.net.http.BaseHttp;
import com.luciuslyle.net.http.inter.RequestLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.HttpException;

public abstract class RequestCallback<T> implements Observer<T> {

    private RequestLoading mLoading;
    private Dialog mDialog;

    @Override
    public void onComplete() {
        onAfter();
        if (mLoading != null) {
            mLoading.onComplete();
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setLodingView(RequestLoading loding) {
        mLoading = loding;
    }

    public void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        onBefore(disposable);
        if (mLoading != null) {
            mLoading.onStart();
        }
        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void onNext(T result) {
        if (result instanceof ResponseBody) {
            try {
                String json = doJson((ResponseBody) result);
                onResponse(new JSONObject(json));
                if (BaseHttp.getInstance().getRequestCallFilter() != null) {
                    BaseHttp.getInstance().getRequestCallFilter().onNext(result);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                JSONObject object=new JSONObject();
                try {
                    object.put("code",507);
                    object.put("message","数据解析错误!");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                onResponse(object);
            }
        }else {
            onResponse(result);
        }

    }

    @Override
    public void onError(Throwable e) {
        //判断出错类型
        String errorMsg = "未知错误!";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用!";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时!";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() >= 500 && httpException.code() < 600) {
                errorMsg = "服务器处理请求出错!";
            } else if (httpException.code() >= 400 && httpException.code() < 500) {
                errorMsg = "服务器无法处理请求!";
            } else if (httpException.code() >= 300 && httpException.code() < 400) {
                errorMsg = "请求被重定向到其他页面!";
            } else {
                errorMsg = httpException.message();
            }
        } else if (e instanceof ParseException 
                || e instanceof java.text.ParseException 
                || e instanceof JSONException || e instanceof JsonSyntaxException) {
            errorMsg = "数据解析错误!";
        }
        onFailure(e, errorMsg);
        if (mLoading != null) {
            mLoading.onError(e, errorMsg);
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void onBefore(Disposable disposable) { }
    public void onAfter() { }
    public  void onFailure(Throwable e, String msg){}
    public  void onResponse(JSONObject response){}
    public  void onResponse(T response){}

    /**
     * ResponseBody 处理成 Json
     */
    private String doJson(ResponseBody responseBody) throws IOException {
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        // 拦截器，
        if (contentLength != 0) {
            result = buffer.clone().readString(charset);
            //            Log.e("MainActivity", " doJson====>:" + result);
        }
        return result;
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

}

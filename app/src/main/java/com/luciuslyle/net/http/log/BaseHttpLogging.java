package com.luciuslyle.net.http.log;

import android.util.Log;

import com.luciuslyle.net.http.log.HttpLoggingInterceptor;

/**
 * @author
 * @version 1.0
 * @date 2020/7/15
 */
public class BaseHttpLogging implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
       Log.d("okHttp", message);
    }
}

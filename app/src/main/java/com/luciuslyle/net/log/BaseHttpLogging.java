package com.luciuslyle.net.log;

import android.util.Log;

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

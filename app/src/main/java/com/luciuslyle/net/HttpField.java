package com.luciuslyle.net;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;

/**
 * @author 对OKHttp host 的反射修改
 * @version 1.0
 * @date 2020/7/10
 */
public class HttpField {

    private static final Field hostField;
    private final HttpUrl httpUrl;

    static {
        Field field = null;
        try {
            field = HttpUrl.class.getDeclaredField("host");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        hostField = field;
    }

    public HttpField(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setBaseUil(String host) {
        try {
            hostField.set(httpUrl, host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

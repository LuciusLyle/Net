package com.luciuslyle.net.demo;

/**
 * @author
 * @version 1.0
 * @date 2020/7/16
 */
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;
    private String page;
    private String number;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

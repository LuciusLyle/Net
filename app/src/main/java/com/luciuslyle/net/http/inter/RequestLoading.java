package com.luciuslyle.net.http.inter;

/**
 * @author
 * @version 1.0
 * @date 2020/7/16
 *  请求加载进度
 */
public interface RequestLoading {
    void onError(Throwable e,String msg);
    void onStart();
    void onComplete();

}

package com.luciuslyle.net.http.inter;

/**
 * @author 
 * @version 1.0
 * @date 2020/7/16
 * 
 * 统一请求回调后的过滤
 * 
 */
public interface RequestCallFilter {
    void onNext(Object o);
}

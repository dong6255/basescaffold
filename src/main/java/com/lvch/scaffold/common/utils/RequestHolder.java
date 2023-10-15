package com.lvch.scaffold.common.utils;


import com.lvch.scaffold.common.domain.dto.RequestInfo;

/**
 * Description: 请求上下文
 * Author: April
 * Date: 2023-04-05
 */
public class RequestHolder {

    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}

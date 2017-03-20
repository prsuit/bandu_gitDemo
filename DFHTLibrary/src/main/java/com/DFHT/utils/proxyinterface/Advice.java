package com.DFHT.utils.proxyinterface;

import java.lang.reflect.Method;

/**
 * author by Mckiera
 * time: 2016/1/7  13:29
 * description: 建议做的事
 * updateTime:
 * update description:
 */
public interface Advice {
    /**
     * 在方法之前添加
     * @param proxy   代理对象
     * @param method  方法
     * @param args    方法的参数
     */
    void methodBefore(Object proxy, Method method, Object[] args);
    /**
     * 在方法之后添加
     * @param proxy   代理对象
     * @param method  方法
     * @param args    方法的参数
     */
    void methodAfter(Object proxy, Method method, Object[] args);

    /**
     * 目标对象方法抛出异常以后
     * @param proxy    代理对象
     * @param method   方法
     * @param args     方法的参数
     * @param e        异常
     */
    void inThrowing(Object proxy, Method method, Object[] args, Exception e);
}

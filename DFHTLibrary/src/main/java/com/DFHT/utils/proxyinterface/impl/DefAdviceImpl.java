package com.DFHT.utils.proxyinterface.impl;

import com.DFHT.utils.proxyinterface.Advice;

import java.lang.reflect.Method;

/**
 * author by Mckiera
 * time: 2016/1/7  13:41
 * description:
 * updateTime:
 * update description:
 */
public class DefAdviceImpl implements Advice {
    @Override
    public void methodBefore(Object proxy, Method method, Object[] args) {

    }

    @Override
    public void methodAfter(Object proxy, Method method, Object[] args) {

    }

    @Override
    public void inThrowing(Object proxy, Method method, Object[] args, Exception e) {

    }
}

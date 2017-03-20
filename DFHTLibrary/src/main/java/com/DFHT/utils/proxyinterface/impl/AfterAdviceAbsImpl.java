package com.DFHT.utils.proxyinterface.impl;

import java.lang.reflect.Method;

/**
 * author by Mckiera
 * time: 2016/1/7  13:55
 * description:
 * updateTime:
 * update description:
 */
public abstract class AfterAdviceAbsImpl extends AbsAdviceImpl {
    @Override
    public void methodBefore(Object proxy, Method method, Object[] args) {

    }

    @Override
    public void inThrowing(Object proxy, Method method, Object[] args, Exception e) {

    }
}

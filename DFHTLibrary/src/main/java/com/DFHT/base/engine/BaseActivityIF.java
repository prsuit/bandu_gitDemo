package com.DFHT.base.engine;

/**
 * Created by jm on 2015/10/20.
 */
public interface BaseActivityIF {
    void successFromMid(Object ... obj);
    void failedFrom(Object ... obj);
    void onSuccess(Object result,int requestCode);
    void onFailed(int requestCode);
}

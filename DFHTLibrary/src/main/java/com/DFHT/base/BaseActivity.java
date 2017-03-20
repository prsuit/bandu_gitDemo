package com.DFHT.base;

import android.app.Activity;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.engine.NetCallback;


/**
 * Created by kiera1 on 15/9/22.
 */
public abstract class BaseActivity extends Activity implements NetCallback,BaseActivityIF {
    /**
     * 从中间层调用的方法
     * @param obj
     */
    public void successFromMid(Object ... obj){}
    /**
     * 从中间层调用的方法
     * @param obj
     */
    public void failedFrom(Object ... obj){}

    @Override
    public abstract void success(Object result, int requestCode);

    @Override
    public abstract void failed(int requestCode);

    @Override
    public void onSuccess(Object result, int requestCode) {

    }

    @Override
    public void onFailed(int requestCode) {

    }
}

package com.DFHT.base;


import android.content.Context;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.enenum.MethodType;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.net.utils.EasyNet;

import java.util.Map;

/**
 * Created by kiera1 on 15/9/22.
 * 中间层的基类
 */
public abstract class BaseMiddle implements NetCallback {

    protected BaseActivityIF activity;
    private int timeout = 5 * 1000;
    private Context mContext;


    public BaseMiddle() {
    }

    public BaseMiddle(BaseActivityIF activity) {
        this.activity = activity;
    }
    public BaseMiddle(BaseActivityIF activity, Context mContext) {
        this.activity = activity;
        this.mContext = mContext;
    }

    public  <E extends BaseMiddle> E setTimeout(int timeout) {
        this.timeout = timeout;
        return (E) this;
    }

    public <E extends BaseMiddle> E setContext(Context mContext){
        this.mContext = mContext;
        return (E) this;
    }

    public void send(int requestCode, EasyNetParam pm) {
        send(requestCode,pm,this);
    }

    public void send(int requestCode, EasyNetParam pm, NetCallback callback) {
        new EasyNet(requestCode, pm, callback).setTimeout(timeout).setProgressDialog(mContext).execute();
    }

    public void send(int requestCode, MethodType type, EasyNetParam pm) {
        send(requestCode, type, pm, this);
    }

    public void send(int requestCode, MethodType type, EasyNetParam pm, NetCallback callback) {
        new EasyNet(type, requestCode, pm, callback).setProgressDialog(mContext).setTimeout(timeout).execute();
    }

    public void send(String url, int requestCode, Map<String, String> data) {
        send(url,requestCode,data,this);
    }

    public void send(String url, int requestCode, Map<String, String> data, NetCallback callback) {
        new EasyNet(url, requestCode, data, callback).setTimeout(timeout).setProgressDialog(mContext).execute();
    }

    public void send(String url, int requestCode, Map<String, String> data, Object obj){
        send(url,requestCode,data,obj,this);
    }
    public void send(String url, int requestCode, Map<String, String> data, Object obj, boolean b){
        send(url,requestCode,data,obj,this, b);
    }
    public void send(String url, int requestCode, Map<String, String> data, Object obj, NetCallback netCallback){
        new EasyNet(url, requestCode, data, obj, netCallback).setTimeout(timeout).setProgressDialog(mContext).execute();
    }
    public void send(String url, int requestCode, Map<String, String> data, Object obj, NetCallback netCallback, boolean b){
        new EasyNet(url, requestCode, data, obj, netCallback, b).setTimeout(timeout).setProgressDialog(mContext).execute();
    }
    public void send(String url, int requestCode, Map<String, String> data, Object obj, NetCallback netCallback, boolean b,String s){
        new EasyNet(url, requestCode, data, obj, netCallback, b,s).setTimeout(timeout).setProgressDialog(mContext).execute();
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.onSuccess(result,requestCode);
    }

    @Override
    public void failed(int requestCode) {
        activity.onFailed(requestCode);
    }
}

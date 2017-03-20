package com.DFHT.net.utils;

import android.content.Context;

import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.enenum.MethodType;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.engine.impl.DFIhandler;
import com.DFHT.net.param.EasyNetParam;

import java.util.Map;

/**
 * Created by kiera1 on 15/9/18.
 * 调用网络的帮助类.
 */
public class EasyNet {

    private NetCallback callback;
    private EasyNetParam pm;
    private int requestCode;
    private MethodType type;
    private int timeout = 10 * 1000;
    private Context mContext;
    private boolean b =true;
    private String s=null;

    public EasyNet() {
        super();
    }

    public EasyNet(String url,int requestCode, Map<String, String> data, NetCallback callback){
        this(MethodType.POST, url, requestCode, data, callback);
    }
    public EasyNet(String url,int requestCode, Map<String, String> data, Object obj, NetCallback callback){
        this(MethodType.POST, url, requestCode, data, obj,callback);
    }
    public EasyNet(String url,int requestCode, Map<String, String> data, Object obj, NetCallback callback, boolean b){
        this(MethodType.POST, url, requestCode, data, obj,callback);
        this.b = b;
    }
    public EasyNet(String url,int requestCode, Map<String, String> data, Object obj, NetCallback callback, boolean b,String s){
        this(MethodType.POST, url, requestCode, data, obj,callback);
        this.s = s;
        this.b = b;
    }
    public EasyNet(MethodType type, String url,int requestCode, Map<String, String> data, NetCallback callback){
        this(type, requestCode, new EasyNetParam(url, data, new DFIhandler()), callback);
    }

    public EasyNet(MethodType type, String url,int requestCode, Map<String, String> data, Object obj,NetCallback callback){
        this(type, requestCode, new EasyNetParam(url, data, obj), callback);
    }

    public EasyNet(int requestCode, EasyNetParam pm,  NetCallback callback) {
        this(MethodType.POST, requestCode, pm, callback);
    }
    public EasyNet(MethodType type, int requestCode, EasyNetParam pm,  NetCallback callback) {
        this.requestCode = requestCode;
        this.callback = callback;
        this.pm = pm;
        this.type = type;
    }


    public EasyNet setCallback(NetCallback callback) {
        this.callback = callback;
        return this;
    }

    public EasyNet setPm(EasyNetParam pm) {
        this.pm = pm;
        return this;
    }

    public EasyNet setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public EasyNet setType(MethodType type) {
        this.type = type;
        return this;
    }

    public EasyNet setTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public EasyNet setProgressDialog(Context mContext){
        this.mContext = mContext;
        return this;
    }

    public void execute() {
        if (callback == null || pm == null )
            throw new IllegalArgumentException("参数异常:尚未初始化 NetCallback 或者 EasyNetParam");
        if(type == null)
            type = MethodType.POST;
        new EasyNetAsyncTask(type, requestCode, b,s,callback).setConnTimeout(timeout).setProgressDialog(mContext).execute(pm);
    }
}

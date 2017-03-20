package com.DFHT.net;


import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.DFHT.ENGlobalParams;
import com.DFHT.exception.NetException;
import com.DFHT.exception.error.ErrorHandler;
import com.DFHT.net.enenum.MethodType;
import com.DFHT.net.engine.Ihandler;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.engine.impl.DFIhandler;
import com.DFHT.net.param.EasyNetParam;

import java.util.Map;

/**
 * Created by kiera1 on 15/9/18.
 * 异步任务,访问网络
 */
public class EasyNetAsyncTask extends AsyncTask<EasyNetParam, Integer, Object> {

    private MethodType type;
    private NetCallback callback;
    private int connTimeout;
    private int requestCode;
    private boolean b = true;
    private String s=null;

    /**
     * 默认post请求
     * @param requestCode  请求码
     * @param callback     执行结束后的回调
     */
    public EasyNetAsyncTask(int requestCode, NetCallback callback) {
        this(MethodType.POST, requestCode, callback);
    }

    public EasyNetAsyncTask(MethodType type, int requestCode, NetCallback callback) {
        this.callback = callback;
        this.type = type;
        this.requestCode = requestCode;
    }
    public EasyNetAsyncTask(MethodType type, int requestCode, boolean b,NetCallback callback) {
        this.callback = callback;
        this.type = type;
        this.requestCode = requestCode;
        this.b = b;
    }
    public EasyNetAsyncTask(MethodType type, int requestCode,boolean b, String s,NetCallback callback) {
        this.callback = callback;
        this.type = type;
        this.requestCode = requestCode;
        this.b = b;
        this.s = s;
    }

    /**
     * @param params 参数包装类
     * @return 访问完服务器回来的数据
     */
    @Override
    protected Object doInBackground(EasyNetParam... params) {
        try {
            if (ENGlobalParams.applicationContext == null) {
                throw new IllegalArgumentException("参数异常 请在Application中初始化ENGlobalParams.applicationContext");
            }
            if (!NetworkUtil.checkNetwork(ENGlobalParams.applicationContext)) {
                throw new NetException();
            }
            Object result = null;
            Map<String, String> data = params[0].getData();
            String url = params[0].getUrl();
            Ihandler ihandler = params[0].getIhandler();
            Object obj = params[0].getObject();
            if (MethodType.GET.equals(type)) {
                if(obj != null){
                    result = NetworkUtil.getAndParse(url, data, obj.getClass(), connTimeout);
                }else if(ihandler != null){
                    result = NetworkUtil.getAndParse(url, data, ihandler, connTimeout);
                }else {
                    result = NetworkUtil.getAndParse(url, data, new DFIhandler(), connTimeout);
                }
            } else if (MethodType.POST.equals(type)) {
                if(obj != null){
                    result = NetworkUtil.postAndParse(url, data, obj.getClass(), connTimeout, b, s);
                }else if(ihandler != null){
                    result = NetworkUtil.postAndParse(url, data, ihandler, connTimeout);
                }else {
                    result = NetworkUtil.postAndParse(url, data, new DFIhandler(), connTimeout);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EasyNetAsyncTask setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (result == null || result instanceof Exception) {
            ErrorHandler.handle(ENGlobalParams.applicationContext, result);
            callback.failed(requestCode);
        } else {
            callback.success(result, requestCode);
        }
    }
    @Deprecated
    public EasyNetAsyncTask setProgressDialog(Context context){
        return this;
    }

}

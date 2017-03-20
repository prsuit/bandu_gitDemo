package com.DFHT.net.param;

import android.text.TextUtils;

import com.DFHT.ENGlobalParams;
import com.DFHT.net.engine.Ihandler;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.PhoneUtil;
import com.DFHT.utils.UIUtils;

import java.util.Map;

/**
 * Created by kiera1 on 15/9/18.
 */
public class EasyNetParam {
    private String url;
    private Map<String, String> data;
    private Object object;
    private Ihandler ihandler;

    public EasyNetParam() {
        super();
    }

    public EasyNetParam(String url, Map<String, String> data) {
        this.url = url;
        this.data = data;
        init();
    }


    /**
     * 构造函数
     *
     * @param url      请求地址
     * @param data     参数
     * @param ihandler 处理器
     */
    public EasyNetParam(String url, Map<String, String> data, Ihandler ihandler) {
        this.url = url;
        this.data = data;
        this.ihandler = ihandler;
        init();
    }

    /**
     * 构造函数
     *
     * @param url    请求地址
     * @param data   参数
     * @param object
     */
    public EasyNetParam(String url, Map<String, String> data, Object object) {
        this.url = url;
        this.data = data;
        this.object = object;
        init();
    }

    /**
     * 初始化参数
     */
    private void init() {
        //TODO 可以写一些必带的参数
        data.put("sign", TextUtils.isEmpty(ENGlobalParams.sign) ? PhoneUtil.getSign(UIUtils.getContext()) : ENGlobalParams.sign);
        data.put("os", "and");
    }

    public String getUrl() {
        return url;
    }

    public EasyNetParam setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getData() {
        return data;
    }

    public EasyNetParam setData(Map<String, String> data) {
        this.data = data;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public EasyNetParam setObject(Object object) {
        this.object = object;
        return this;
    }

    public Ihandler getIhandler() {
        return ihandler;
    }

    public EasyNetParam setIhandler(Ihandler ihandler) {
        this.ihandler = ihandler;
        return this;
    }
}

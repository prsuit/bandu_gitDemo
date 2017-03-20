package com.DFHT.exception;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

/**
 * Created by kiera1 on 15/9/18.
 */
public class NetException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NetException() {
        super("网络异常");
    }
    public NetException(String url) {
        super("网络异常");
        LogUtils.e("出错的URL = " + url);
        UIUtils.showToastSafe("网络异常,请检查网络连接");

    }
}

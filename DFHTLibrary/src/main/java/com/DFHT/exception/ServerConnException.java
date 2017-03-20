package com.DFHT.exception;


import com.DFHT.utils.LogUtils;

/**
 * Created by kiera1 on 15/9/18.
 */
public class ServerConnException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ServerConnException() {
        super("服务器链接错误");
    }

    public ServerConnException(String url) {
        super("服务器链接错误");
        LogUtils.e("服务器链接错误URL = " + url);

    }
}

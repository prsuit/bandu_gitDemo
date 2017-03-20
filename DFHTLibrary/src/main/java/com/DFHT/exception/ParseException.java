package com.DFHT.exception;


import com.DFHT.utils.LogUtils;

/**
 * Created by kiera1 on 15/9/18.
 * 解析异常
 */
public class ParseException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -299713641613973101L;

    public ParseException(){
        super("抱歉，解析异常!");
    }

    public ParseException(String url){
        super("抱歉，解析异常!");
        LogUtils.e("解析异常 = " + url);
    }
}

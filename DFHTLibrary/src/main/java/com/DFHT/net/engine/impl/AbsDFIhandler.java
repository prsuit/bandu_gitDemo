package com.DFHT.net.engine.impl;

import com.DFHT.exception.ParseException;
import com.DFHT.net.engine.Ihandler;
import com.DFHT.utils.LogUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kiera1 on 15/9/18.
 */
public abstract class AbsDFIhandler implements Ihandler {
    @Override
    public Object parseResponse(InputStream inputStream) throws ParseException {
        Object reponseResult = null;
        try {
            String responseStr = streamToString(inputStream);
            LogUtils.v("返回的结果:" + responseStr);
            reponseResult = parseResponse(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException();
        }
        return reponseResult;
    }


    abstract public Object parseResponse(String responseStr) throws Exception;


    private String streamToString(InputStream inputStream) throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }
}

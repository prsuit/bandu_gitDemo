package com.DFHT.base;

import java.io.Serializable;

/**
 * Created by kiera1 on 15/9/22.
 */
public class BaseBean implements Serializable{
    protected String msg;
    protected int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

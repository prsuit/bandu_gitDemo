package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;
import com.google.gson.internal.Streams;

/**
 * Created by fanYU on 2016/6/3.
 * 组合页更换小伙伴
 */
public class CombinaRecordChangeBean  extends BaseBean {
    private String uid;
    private String othername;
    private int otheravatar;

    public  CombinaRecordChangeBean(String uid,String othername,int otheravatar){
        this.uid=uid;
        this.othername=othername;
        this.otheravatar=otheravatar;

    }
    public int getOtheravatar() {
        return otheravatar;
    }

    public void setOtheravatar(int otheravatar) {
        this.otheravatar = otheravatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }



}

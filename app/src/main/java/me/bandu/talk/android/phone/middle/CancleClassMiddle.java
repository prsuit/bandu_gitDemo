package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.CancleClassBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.utils.UserUtil;

public class CancleClassMiddle extends BaseMiddle{
    private Context context;
    public static final int CANCLE_CLASS = 22;
    private LoginBean.DataEntity uerinfo;

    public CancleClassMiddle(BaseActivityIF baseActivityIF, Context context,LoginBean.DataEntity uerinfo){
        super(baseActivityIF, context);
        this.context = context;
        this.uerinfo = uerinfo;
    }


    public void setClassCancle(CancleClassBean obj){
        Map<String,String> data = new HashMap();
        data.put("cid",uerinfo.getCid());
        data.put("uid", UserUtil.getUid());
        send(ConstantValue.CANCLE_CLASS, CANCLE_CLASS, data,obj);
    }
}

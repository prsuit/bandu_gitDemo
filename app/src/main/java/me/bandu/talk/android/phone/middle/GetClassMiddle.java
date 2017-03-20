package me.bandu.talk.android.phone.middle;


import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.utils.UserUtil;

public class GetClassMiddle extends BaseMiddle {
    public static final int GET_CLASS = 1;
    public static final int GET_CLASS_ASY = 122;
    private Context context;
    public GetClassMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }

    public void getClassList(UserClassListBean obj) {
        Map<String, String> data = new HashMap();
        data.put("uid", String.valueOf(UserUtil.getUerInfo(context).getUid()));
        send(ConstantValue.GET_CLASSLIST, GET_CLASS, data, obj);
    }
    public void getClassList(UserClassListBean obj, int what) {
        Map<String, String> data = new HashMap();
        data.put("uid", String.valueOf(UserUtil.getUerInfo(context).getUid()));
        send(ConstantValue.GET_CLASSLIST, what, data, obj);
    }

}
package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.CheckWorkBean;
import me.bandu.talk.android.phone.bean.CheckWorkBean1;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：检查作业的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CheckWorkMiddle extends BaseMiddle {
    public static final int CHECK_WORK = 1;

    public CheckWorkMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void checkWork(String uid,String cid, String page, String size, CheckWorkBean1 obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("cid", cid);
        data.put("page", page);
        data.put("size", size);
        send(ConstantValue.CHECK_WORK, CHECK_WORK, data,obj);
    }

}

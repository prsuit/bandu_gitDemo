package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.SysMsgBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SysMsgMiddle extends BaseMiddle {
    public static final int SYS_MSG = 1;

    public SysMsgMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 系统消息列表
     * @param uid 用户id
     * @param page 页数
     * @param size 每页消息数量
     * @param obj
     */
    public void getSysMsg(String uid,String page,String size,SysMsgBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("page", page);
        data.put("size", size);
        send(ConstantValue.SYS_MSG, SYS_MSG, data,obj);
    }

}

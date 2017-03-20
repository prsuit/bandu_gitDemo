package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ReadMsgMiddle extends BaseMiddle {
    public static final int READ_MSG = 1;

    public ReadMsgMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 标记已读消息
     * @param uid 用户id
     * @param msgId 消息id
     * @param obj
     */
    public void readMsg(String uid,String msgId,BaseBean obj){
        Map data = new HashMap();
        ArrayList<String> msgIdList = new ArrayList<>();
        msgIdList.add(msgId);
        data.put("uid", uid);
        data.put("msg_ids",msgIdList);
        send(ConstantValue.READ_MSG, READ_MSG, data,obj);
    }

}

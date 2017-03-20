package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.SysMsgNumBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SysMsgNumMiddle extends BaseMiddle {
    public static final int SYS_MSG_NUM = 9;

    public SysMsgNumMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getSysMsgNum(String uid,SysMsgNumBean obj){
        Map<String,String> data = new HashMap<>();
        data.put("uid", uid);
        send(ConstantValue.NO_READ_MSG, SYS_MSG_NUM, data,obj);
    }

}

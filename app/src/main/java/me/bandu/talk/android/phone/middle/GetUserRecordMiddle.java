package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.MyRecordBean;

/**
 * Created by fanYu on 2016/6/8.
 * 玩一玩界面获取用户录音
 */
public class GetUserRecordMiddle extends BaseMiddle {
    public static final int GET_USERECORD = 2;

    public GetUserRecordMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getUserRecord(String quiz_id, String uid){
        Map<String,String> data = new HashMap();
        data.put("quiz_id", quiz_id);
        data.put("uid",uid);
        send(ConstantValue.GET_USERRECORD, GET_USERECORD, data,new MyRecordBean());
    }
}
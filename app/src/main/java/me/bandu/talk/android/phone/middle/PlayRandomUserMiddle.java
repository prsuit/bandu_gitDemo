package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.GetUserBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * Created by fanYu on 2016/6/6.
 */
public class PlayRandomUserMiddle extends BaseMiddle {

    public static final int GET_USER = 1;

    public PlayRandomUserMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getRandomUser(String quiz_id){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("quiz_id", quiz_id);
        send(ConstantValue.GET_RANDOMUSER, GET_USER, data,new GetUserBean());
    }
}
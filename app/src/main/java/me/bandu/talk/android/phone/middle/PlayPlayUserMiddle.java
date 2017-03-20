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
 * Created by fanYu on 2016/6/3.
 * 组合录音界面获取前八名用户的中间类
 */
public class PlayPlayUserMiddle extends BaseMiddle {

    public static final int GET_USER = 1;

    public PlayPlayUserMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getUser(String quiz_id){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("quiz_id", quiz_id);
        send(ConstantValue.GET_USER, GET_USER, data,new GetUserBean());
    }
}

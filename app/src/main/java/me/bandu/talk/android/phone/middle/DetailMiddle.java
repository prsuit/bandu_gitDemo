package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.engine.NetCallback;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.Detail;

/**
 * author by Mckiera
 * time: 2015/12/21  20:06
 * description:
 * updateTime:
 * update description:
 */
public class DetailMiddle extends BaseMiddle {

    public void detail(String jID, String qID, NetCallback callback){
        Map<String, String> data = new HashMap<>();
        data.put("stu_job_id",jID);
        data.put("hw_quiz_id",qID);
        data.put("uid", GlobalParams.userInfo.getUid()+"");
        send(ConstantValue.HOMEWORK_DETAIL,10,data, new Detail(),callback);
    }

}

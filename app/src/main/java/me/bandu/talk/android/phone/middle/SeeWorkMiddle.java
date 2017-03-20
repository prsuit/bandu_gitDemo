package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.SeeWorkBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：查看作业详情的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SeeWorkMiddle extends BaseMiddle {
    public static final int SEE_WORK = 1;

    public SeeWorkMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 查看作业
     * @param uid 用户id
     * @param jobId 作业id
     * @param obj
     */
    public void seeWork(String uid,String jobId, SeeWorkBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("job_id", jobId);
        send(ConstantValue.SEE_WORK, SEE_WORK, data,obj);
    }

}

package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.WorkStatisBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：删除作业的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkStatisMiddle extends BaseMiddle {
    public static final int WORK_STATIS = 2;

    public WorkStatisMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 获取分数信息
     * @param uid 用户id
     * @param jobId 作业id
     * @param obj
     */
    public void getWork(String uid,String jobId, WorkStatisBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("job_id", jobId);
        send(ConstantValue.WORK_STATIS, WORK_STATIS, data,obj);
    }

}

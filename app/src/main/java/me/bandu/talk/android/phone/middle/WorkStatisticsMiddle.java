package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.WorkStatisticsBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：王兰新
 * 时间：20166/6/2
 * 类描述：作业统计中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkStatisticsMiddle extends BaseMiddle {
    public static final int STATISTICS_CODE = 1;
        private Context mContext;
    public WorkStatisticsMiddle(BaseActivityIF activity, Context mContext) {
        super(activity, mContext);
        this.mContext = mContext;
    }


    //获取统计信息
    public void getStatisticsInfo(String jobId, WorkStatisticsBean obj){
        Map<String,String> data = new HashMap();
        data.put("job_id",jobId);
        String uid = UserUtil.getUerInfo(mContext).getUid();
        data.put("uid",uid);
        send(ConstantValue.STATISTICS_URL, STATISTICS_CODE, data,obj);
    }
}

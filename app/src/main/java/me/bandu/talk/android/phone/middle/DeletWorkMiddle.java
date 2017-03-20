package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：删除作业的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DeletWorkMiddle extends BaseMiddle {
    public static final int DELET_WORK = 2;

    public DeletWorkMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void deletWork(String uid,String cid, String jobId, BaseBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("cid", cid);
        data.put("job_id", jobId);
        send(ConstantValue.DELET_WORK, DELET_WORK, data,obj);
    }

}

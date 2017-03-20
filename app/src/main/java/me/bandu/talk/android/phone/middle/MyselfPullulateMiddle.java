package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.MyselfPullateSingleBean;
import me.bandu.talk.android.phone.bean.MyselfPullulateBean;
import me.bandu.talk.android.phone.utils.CacheUtils;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者:王兰新
 * 时间：2016/6/2
 * 类描述：个人成长曲线的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MyselfPullulateMiddle extends BaseMiddle {
    public static final int MYSELF_PULLULATE = 1;
    public static final int FIRST_MYSELF_PULLULATE = 2;
    Context mContext;

    public MyselfPullulateMiddle(BaseActivityIF activity, Context mContext) {
        super(activity, mContext);
        this.mContext = mContext;
    }
    //每次点击
    public void getMyselfPullulate(String uid,String job_id,MyselfPullateSingleBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid",uid);
        data.put("job_id", job_id);
        send(ConstantValue.MYSELF_PULLULATE_URL, MYSELF_PULLULATE, data,obj);
    }
    //第一次进入
    public void getFirstInfo(MyselfPullulateBean obj,String uid,String cid){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("cid", cid);
        send(ConstantValue.FIRST_MYSELF_PULLULATE_URL, FIRST_MYSELF_PULLULATE, data,obj);
    }


}

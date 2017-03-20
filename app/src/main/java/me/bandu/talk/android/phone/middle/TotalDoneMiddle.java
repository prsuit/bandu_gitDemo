package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.TotalDoneBean;

/**
 * Created by fanYu on 2016/6/10.
 */
public class TotalDoneMiddle  extends BaseMiddle {
    public static final int TOTAL_DONE = 1;

    public TotalDoneMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void TotalDone(String uid, String stu_job_id){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("stu_job_id",stu_job_id);
        send(ConstantValue.TOTAL_DONE, TOTAL_DONE, data,new TotalDoneBean());
    }
}
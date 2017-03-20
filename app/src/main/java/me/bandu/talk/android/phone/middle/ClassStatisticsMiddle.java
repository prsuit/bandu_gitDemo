package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.ClassStatisticsBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * Created by wanglanxin on 2016/6/5.
 */
public class ClassStatisticsMiddle extends BaseMiddle {
    private Context context;
    public static final int CLASS_STATICS_CODE = 1;

    public ClassStatisticsMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }

    public void getClassStatistisc(ClassStatisticsBean obj){
        Map<String,String> data = new HashMap();
        String uid = UserUtil.getUerInfo(context).getUid();
        data.put("uid",uid);
        send(ConstantValue.STATISTICS_CLASS, CLASS_STATICS_CODE, data,obj);
    }
}

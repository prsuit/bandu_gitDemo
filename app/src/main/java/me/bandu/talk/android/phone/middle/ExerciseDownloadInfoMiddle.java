package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadBean;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseDownloadInfoMiddle extends BaseMiddle {
    public static final int EXERCISE_DOWNLOAD_INFO = 9;

    public ExerciseDownloadInfoMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public ExerciseDownloadInfoMiddle(BaseActivityIF baseActivityIF){
        super(baseActivityIF);
    }

    public void getDownloadInfo(long unit_id){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("unit_id",unit_id + "");
        send(ConstantValue.EXERCISE_DOWNLOAD_INFO, EXERCISE_DOWNLOAD_INFO, data, new ExerciseDowanloadInfoBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result,requestCode);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}

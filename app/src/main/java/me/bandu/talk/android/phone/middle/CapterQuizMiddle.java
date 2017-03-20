package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.LessonInfoBean;
import me.bandu.talk.android.phone.bean.LessonInfoData;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CapterQuizMiddle extends BaseMiddle {
    public static final  int LESSON_INFO =20;
    public CapterQuizMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getLessonInfo(String lesson_id,LessonInfoBean info){
        Map<String,String> data = new HashMap();
        data.put("lesson_id",lesson_id);
        send(ConstantValue.QUIZE_LESSON,LESSON_INFO,data,info);
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        LessonInfoBean info = (LessonInfoBean) result;
        if (info.getStatus()==1){
            activity.successFromMid(result,requestCode);
        }else {
            activity.failedFrom(LESSON_INFO);
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(LESSON_INFO);
    }


}

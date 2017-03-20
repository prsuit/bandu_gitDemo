package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.GradeTextBookBean;
import me.bandu.talk.android.phone.bean.TextBookInfoBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：根据年级获取教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GradeTextBookMiddle extends BaseMiddle {
    public static final int GRADE_TEXTBOOK = 7;

    public GradeTextBookMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getGradeTextbook(String grade_id){
        Map<String,String> data = new HashMap();
        data.put("grade_id", grade_id);
        send(ConstantValue.GRADE_TEXTBOOK, GRADE_TEXTBOOK, data,new GradeTextBookBean());
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

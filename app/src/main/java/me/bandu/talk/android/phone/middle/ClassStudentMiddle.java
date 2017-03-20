package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ClassAllStudentBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：获取班级学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassStudentMiddle extends BaseMiddle {
    public static final int CLASS_STUDENT = 4;

    public ClassStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public ClassStudentMiddle(BaseActivityIF baseActivityIF){
        super(baseActivityIF);
    }

    public void getClassStudent(String cid){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("cid", cid);
        data.put("uid", UserUtil.getUid());
        send(ConstantValue.CLASS_STUDENT, CLASS_STUDENT, data,new ClassAllStudentBean());
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

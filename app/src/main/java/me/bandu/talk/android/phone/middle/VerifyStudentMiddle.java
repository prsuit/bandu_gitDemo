package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ClassStudentBean;
import me.bandu.talk.android.phone.bean.VerifyStudentsBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：获取班级学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class VerifyStudentMiddle extends BaseMiddle {
    public static final int VERIFY_STUDENTS = 1;

    public VerifyStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /*public void getVerifyStudents(String cid){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("cid", cid);
        send(ConstantValue.VERIFY_STUDENTS, VERIFY_STUDENTS, data, new VerifyStudentsBean());
    }*/

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}

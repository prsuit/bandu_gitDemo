package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.VerifyStudentsBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：通过学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AccessStudentMiddle extends BaseMiddle {
    public static final int ACCESS_STUDENTS = 1;

    public AccessStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

   /* public void accessStudents(String cid,List<String> uids){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("cid", cid);
        data.put("uid",JSONUtils.getJson(uids));

        send(ConstantValue.ACCESS_STUDENTS, ACCESS_STUDENTS, data, new BaseBean());
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
package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.utils.PreferencesUtils;

/**
 * 作者: FanYu
 * 创建时间: 2016/7/29 0029 14:25
 * 类描述: 删除学生的中间类
 */


public class DeleteStudentMiddle  extends BaseMiddle{
    public static final int DELETESTUDENT = 10;

    public DeleteStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void deleteStudent(String uid,String cid,long[] stu_uid){
        Map data = new HashMap();
        data.put("uid", uid);
        data.put("cid", cid);
        data.put("stu_uid", stu_uid);
        send(ConstantValue.DELETE_STUDENT, DELETESTUDENT, data,new BaseBean());
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

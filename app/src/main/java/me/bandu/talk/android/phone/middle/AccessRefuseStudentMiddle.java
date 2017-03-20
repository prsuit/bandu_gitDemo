package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ExerciseTextBookChoseBean;
import me.bandu.talk.android.phone.bean.VerifyStudentsBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：获取班级学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AccessRefuseStudentMiddle extends BaseMiddle {
    public static final int ACCESS_REFUSE_STUDENTS = 5;
    public static final int TYPE_ACCESS = 1;
    public static final int TYPE_REFUSEORDELETE = 0;

    public AccessRefuseStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void accessRefuseStudents(String cid,long[] stu_uid,int type){
        Map data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("uid", GlobalParams.userInfo.getUid());
        data.put("cid", cid);
        data.put("stu_uid", stu_uid);
        data.put("type",type);
        EasyNetParam param = new EasyNetParam(ConstantValue.ACCESS_REFUSE_STUDENTS,data, new BaseBean());
        new EasyNetAsyncTask(ACCESS_REFUSE_STUDENTS, this).execute(param);
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

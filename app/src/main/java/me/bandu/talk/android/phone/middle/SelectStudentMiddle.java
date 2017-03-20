package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.ClassStudentBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectStudentMiddle extends BaseMiddle {
    public static final int STUDENT_LIST = 40;
    public SelectStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getStudentList(String cid,String uid){
        Map<String,String> data = new HashMap();
        data.put("cid",cid);
        data.put("uid",uid);
        send(ConstantValue.CLASS_STUDENT,STUDENT_LIST,data,new ClassStudentBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        ClassStudentBean classStudentBean = (ClassStudentBean) result;
        if (classStudentBean.getStatus()==1){
            activity.successFromMid(classStudentBean,STUDENT_LIST);
        }else {
            activity.failedFrom(STUDENT_LIST);
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(STUDENT_LIST);
    }
}

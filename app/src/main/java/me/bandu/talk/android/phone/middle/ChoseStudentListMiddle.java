package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.ChoseStudentListBean;

/**
 * 创建者：高楠
 * 时间：on 2016/2/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentListMiddle extends BaseMiddle {
    public static final int CHOSE_STUDENT_LIST = 140;

    public ChoseStudentListMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getStudentList(String uid,String job_id){
        Map<String,String> data = new HashMap();
        data.put("uid",uid);
        data.put("job_id",job_id);
        send(ConstantValue.CHOSE_STUDENT_LIST, CHOSE_STUDENT_LIST, data,new ChoseStudentListBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        if (requestCode==CHOSE_STUDENT_LIST){
            ChoseStudentListBean bean = (ChoseStudentListBean) result;
            if (bean.getStatus()==1){
                activity.successFromMid(requestCode,bean);
            }else {
                activity.failedFrom(requestCode);
                UIUtils.showToastSafe(bean.getMsg());
            }
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        if (requestCode==CHOSE_STUDENT_LIST){
            UIUtils.showToastSafe("请保持网络畅通");
        }
    }
}

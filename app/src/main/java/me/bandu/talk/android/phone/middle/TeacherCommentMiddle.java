package me.bandu.talk.android.phone.middle;

import android.content.Context;
import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.bandu.talk.android.phone.ConstantValue;

/**
 * 创建者：高楠
 * 时间：on 2016/2/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherCommentMiddle extends BaseMiddle {
    public static final int TEACHER_COMMENT = 150;
    public TeacherCommentMiddle (Context context, BaseActivityIF activityIF){
        super(activityIF,context);
    }
    public void comment(String uid, String job_id, List stu_job_ids,List goods,List bads,String comment){
        Map data = new HashMap<>();
        data.put("uid",uid);
        data.put("job_id",job_id);
        data.put("stu_job_ids",stu_job_ids);
        data.put("goods",goods);
        data.put("bads",bads);
        data.put("comment",comment);
        send(ConstantValue.EVALUATE, TEACHER_COMMENT, data, new BaseBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        BaseBean baseBean = (BaseBean) result;
        if (baseBean.getStatus()==1){
            activity.successFromMid(requestCode,result);
        }else {
            activity.failedFrom(requestCode);
            UIUtils.showToastSafe(baseBean.getMsg());
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        UIUtils.showToastSafe("请保持网络畅通");
    }
}

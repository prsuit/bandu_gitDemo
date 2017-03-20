package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.ChoseStudentListBean;
import me.bandu.talk.android.phone.bean.CreatWorkContentBean;
import me.bandu.talk.android.phone.bean.CreatWorkContentsBean;
import me.bandu.talk.android.phone.bean.Quiz;

/**
 * 创建者：高楠
 * 时间：on 2016/2/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkContentMiddle extends BaseMiddle {
    public static final int CREAT_WORK_CONTENT = 140;

    public CreatWorkContentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getWorkSentenceList(List<String> quizList){
        Map data = new HashMap();
        data.put("quiz_ids",quizList);
        send(ConstantValue.WORKCONTENT_SENTENCE_QUIZ, CREAT_WORK_CONTENT, data,new CreatWorkContentsBean());
    }


    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        if (requestCode==CREAT_WORK_CONTENT){
            CreatWorkContentsBean bean = (CreatWorkContentsBean) result;
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
        if (requestCode==CREAT_WORK_CONTENT){
            UIUtils.showToastSafe("请保持网络畅通");
        }
    }
}

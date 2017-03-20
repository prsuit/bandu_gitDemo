package me.bandu.talk.android.phone.middle;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.param.EasyNetParam;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.activity.BaseAppCompatActivity;
import me.bandu.talk.android.phone.bean.ExerciseTextBookChoseBean;
import me.bandu.talk.android.phone.bean.ExerciseTextbookTypeBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：根据年级获取教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseTextbookChoseMiddle extends BaseMiddle {
    public static final int EXERCISE_TEXTBOOK_CHOSE = 35;
    public static final int STUDENT_TEXTBOOK_TYPES = 59;
    private BaseAppCompatActivity activity;

    public ExerciseTextbookChoseMiddle(BaseActivityIF baseActivityIF, BaseAppCompatActivity activity){
        super(baseActivityIF, activity);
        this.activity = activity;
    }

    public void getExerciseTextbook(int version,int subject,int[] grades,String order,int page,int size,int[] stars,String keyword,
                                    int[] cat_ids,int[] sub_cat_ids){
        Map data = new HashMap();
        if (version != -1)
            data.put("version",version + "");
        if (subject != -1)
            data.put("subject",subject + "");
        data.put("grades", grades);
        data.put("order",order + "");
        data.put("page",page + "");
        data.put("size",size + "");
        data.put("stars",stars);
        data.put("keyword",keyword);
        data.put("cat_ids",cat_ids);
        data.put("sub_cat_ids",sub_cat_ids);
        EasyNetParam param = new EasyNetParam(ConstantValue.EXERCISE_TEXTBOOK_CHOSE,data, new ExerciseTextBookChoseBean());
        if (activity != null)
            activity.showMyprogressDialog();
        new EasyNetAsyncTask(EXERCISE_TEXTBOOK_CHOSE, this).execute(param);
    }

    public void getExerciseTextbookTypes(){
        Map<String,String> data = new HashMap();
        send(ConstantValue.STUDENT_TEXTBOOK_TYPES,STUDENT_TEXTBOOK_TYPES,data,new ExerciseTextbookTypeBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result,requestCode);
        if (activity != null)
            activity.hideMyprogressDialog();
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
        if (activity != null)
            activity.hideMyprogressDialog();
    }
}

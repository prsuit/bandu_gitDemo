package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseDownloadMiddle extends BaseMiddle {
    public static final int EXERCISE_DOWNLOAD = 8;

    public ExerciseDownloadMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getDownloadList(String book_id){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("book_id",book_id);

        send(ConstantValue.EXERCISE_DOWNLOAD_LIST, EXERCISE_DOWNLOAD, data, new ExerciseDowanloadBean());
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

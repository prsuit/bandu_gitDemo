package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.TaskCatalogVideoBean;

/**
 * 创建者：wanglei
 * <p>时间：16/8/4  13:24
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskCatalogVideoMiMiddle extends BaseMiddle {
    public final static int GETVIDEOOFQUIZ = 21;

//    private BaseActivityIF activity;
//    private Context context;

    public TaskCatalogVideoMiMiddle(BaseActivityIF activity, Context context) {
        super(activity, context);
    }

    public void getvideoofquiz(int quiz_id) {
        Map<String, String> data = new HashMap();
        data.put("quiz_id", String.valueOf(quiz_id));
        send(ConstantValue.GETVIDEOOFQUIZ, GETVIDEOOFQUIZ, data, new TaskCatalogVideoBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        switch (requestCode) {
            case GETVIDEOOFQUIZ:
                TaskCatalogVideoBean tcvb = (TaskCatalogVideoBean) result;
                if (tcvb.getStatus() == 1)
                    activity.successFromMid(result, requestCode);
                else {
                    activity.failedFrom(requestCode);
                    UIUtils.showToastSafe(tcvb.getMsg());
                }
                break;
        }
    }
}

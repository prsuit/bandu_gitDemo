package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.TeacherHomeList;

/**
 * Created by Mckiera on 2015/12/14.
 */
public class TeacherHomeListMiddle extends BaseMiddle {
    public static final int LIST_OF_TEACHER_CODE = 33;

    public TeacherHomeListMiddle (Context context,BaseActivityIF activityIF){
        super(activityIF,context);
    }

    public void getStuList(int page){
        Map<String, String> data = new HashMap<>();
        data.put("uid", GlobalParams.userInfo.getUid()+"");
        data.put("page", page+"");
        data.put("size", "25");
        send(ConstantValue.LIST_OF_TEACHER, LIST_OF_TEACHER_CODE, data, new TeacherHomeList());
    }
}

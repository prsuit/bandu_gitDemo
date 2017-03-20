package me.bandu.talk.android.phone.middle;

import android.content.Context;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import java.util.HashMap;
import java.util.Map;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.DetailOfStudentBean;

public class DetailofStudentMiddle extends BaseMiddle {
    public static final int DETAIL_OF_STUDENT = 1;

    public DetailofStudentMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void workDetail(String uid, String job_id, String stu_job_id){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("job_id", job_id);
        data.put("stu_job_id", stu_job_id);
        send(ConstantValue.DETAIL_OF_STUDENT, DETAIL_OF_STUDENT, data,new DetailOfStudentBean());
    }

}

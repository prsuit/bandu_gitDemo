package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.SentenceBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;

/**
 * Created by fanyu on 2016/6/4.
 * 练习录音上传的中间类
 */
public class RecordUpdateMiddle extends BaseMiddle {
    public static final int UPDATE = 1;

    public RecordUpdateMiddle(BaseActivityIF baseActivityIF, Context context) {
        super(baseActivityIF, context);
    }

    public void RecordUpdate(String uid, String quiz_id, String score, List objs) {
        Map data = new HashMap();
        data.put("uid", uid);
        data.put("quiz_id", quiz_id);
        data.put("score", score);
        data.put("sentences", objs);
        send(ConstantValue.RECORD_UPDATE, UPDATE, data, new BaseBean(),this,true,"gaonan");
    }

}

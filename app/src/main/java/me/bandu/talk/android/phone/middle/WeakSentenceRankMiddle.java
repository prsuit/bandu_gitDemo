package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.WeakSentenceBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * Created by fanYu on 2016/6/13.
 */
public class WeakSentenceRankMiddle extends BaseMiddle{
    private Context context;
    public static final int Type_WEAKSENTENCERANK=5;

    public WeakSentenceRankMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }

    public void getWeakSentenceRank(String job_id,String sentence_id){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("job_id",job_id);
        data.put("sentence_id",sentence_id);
        send(ConstantValue.WEAK_RANK, Type_WEAKSENTENCERANK, data,new WeakSentenceBean());
    }


}

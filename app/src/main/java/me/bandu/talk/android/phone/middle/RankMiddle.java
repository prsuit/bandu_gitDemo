package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.RankBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：王兰新
 * 时间：2016/6/2
 * 类描述：排行榜的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RankMiddle extends BaseMiddle {
    private Context context;
    public static final int SCORE_RANK = 1;
    public static final int TOTAL_TIME_RANK = 2;

    public RankMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }


    public void getTotalTimeRankInfo(String job_id,RankBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid",UserUtil.getUid());
        data.put("job_id",job_id);
        data.put("order","total_time");
        send(ConstantValue.RANK_URL, TOTAL_TIME_RANK, data,obj);
    }
    public void getScoreRankInfo(String job_id,RankBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid",UserUtil.getUid());
        data.put("job_id",job_id);
        data.put("order","score");
        send(ConstantValue.RANK_URL, SCORE_RANK, data,obj);
    }
}

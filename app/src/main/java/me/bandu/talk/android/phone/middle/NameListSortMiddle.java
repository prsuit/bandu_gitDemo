package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.NameListSortBean;
import me.bandu.talk.android.phone.bean.RankBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * Created by fanYu on 2016/6/7.
 * 作业名单排序的中间类
 *
 */
public class NameListSortMiddle extends BaseMiddle {
    private Context context;
    public static final int Type_SCORE = 0;
    public static final int Type_TOTALTIME = 1;
    public static final int Type_UP = 2;
    public static final int Type_COMMITTIME = 3;

    public NameListSortMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }


    public void getCommitTimeInfo(String jobId){
        Map<String,String> data = new HashMap();
        data.put("uid",UserUtil.getUid());
        data.put("job_id",jobId);
        data.put("sort","commit_time");
        send(ConstantValue.NAMELIST_SORT, Type_COMMITTIME, data,new NameListSortBean());
    }
    public void getScoreInfo(String jobId){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("job_id",jobId);
        data.put("sort","score");
        send(ConstantValue.NAMELIST_SORT, Type_SCORE, data,new NameListSortBean());
    }
    public void getTotalTimeInfo(String jobId){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("job_id",jobId);
        data.put("sort","total_time");
        send(ConstantValue.NAMELIST_SORT, Type_TOTALTIME, data,new NameListSortBean());
    }
    public void getUpScoreInfo(String jobId){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("job_id",jobId);
        data.put("sort","up_score");
        send(ConstantValue.NAMELIST_SORT, Type_UP, data,new NameListSortBean());
    }
}

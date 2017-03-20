package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.FeedTagBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：反馈标签
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetTagMiddle extends BaseMiddle {
    public static final int FEED_TAG = 2;

    public GetTagMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getTag(FeedTagBean obj){
        Map<String,String> data = new HashMap();
        send(ConstantValue.FEED_BAVK_TAG, FEED_TAG, data,obj);
    }

}

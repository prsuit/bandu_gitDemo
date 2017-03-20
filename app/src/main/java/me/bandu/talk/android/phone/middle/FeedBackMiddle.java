package me.bandu.talk.android.phone.middle;

import android.content.Context;
import android.text.TextUtils;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.FeedTagBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：注册的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FeedBackMiddle extends BaseMiddle {
    public static final int FEEDBACK = 1;

    public FeedBackMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     *
     * @param content 反馈内容
     * @param source 硬件信息和应用版本
     * @param contact 联系方式
     * @param tags 标签
     * @param obj
     */
    public void feedBack(String content, String source, String contact, List<FeedTagBean.
            DataBean.ListBean> tags, BaseBean obj){
        Map data = new HashMap();
        ArrayList msgIdList = new ArrayList<>();
        for (FeedTagBean.DataBean.ListBean bean : tags) {
            if (bean.isSelect()) {
                msgIdList.add(bean.getTag_id());
            }
        }
        data.put("uid", TextUtils.isEmpty(UserUtil.getUid()) ? "0" : UserUtil.getUid());
        data.put("content", content);
        data.put("source", source);
        data.put("contact", contact);
        data.put("tag_ids",msgIdList);
        send(ConstantValue.FEEDBACK, FEEDBACK, data,obj);
    }
}

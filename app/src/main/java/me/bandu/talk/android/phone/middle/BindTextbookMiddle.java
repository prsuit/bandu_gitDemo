package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.JSONUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：绑定教材
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BindTextbookMiddle extends BaseMiddle {
    public static final int BIND_TEXTBOOK = 10;

    public BindTextbookMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void bindClassTextbook(String cid,String book_id){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("uid", GlobalParams.userInfo.getUid() + "");
        data.put("cid", cid);
        data.put("book_id",book_id);

        send(ConstantValue.BIND_TEXTBOOK, BIND_TEXTBOOK, data, new BaseBean());
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

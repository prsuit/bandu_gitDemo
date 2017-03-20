package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.TextBookInfoBean;

/**
 * 创建者：gaoye
 * 时间：2015/11/25 13:49
 * 类描述：获取绑定教材详情
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TextBookInfoMiddle extends BaseMiddle {
    public static final int TEXTBOOK_INFO = 8;

    public TextBookInfoMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getTextbookInfo(String bookid){
        Map<String,String> data = new HashMap();
        data.put("book_id", bookid);
        send(ConstantValue.TEXTBOOK_INFO, TEXTBOOK_INFO, data,new TextBookInfoBean());
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}

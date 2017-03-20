package me.bandu.talk.android.phone.middle;

import android.content.ContentValues;
import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.QueryClassBean;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ErrorMiddle extends BaseMiddle {
    public ErrorMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void sendError(String sentence_id, String msg, String param){
        Map<String,String> data = new HashMap();
        data.put("uid", UserUtil.getUid());
        data.put("msg",msg);
        data.put("param",param);
        data.put("sentence_id",sentence_id);
        data.put("os","0");
        send(ConstantValue.BOOK_ERRORSENTENCE, -50, data,new BaseBean());
    }
    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
    }
}

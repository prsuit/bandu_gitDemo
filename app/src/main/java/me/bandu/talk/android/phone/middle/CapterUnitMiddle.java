package me.bandu.talk.android.phone.middle;

import android.content.Context;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import java.util.HashMap;
import java.util.Map;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.BookInfoBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CapterUnitMiddle extends BaseMiddle {
    public static final  int BOOKINFO_CODE =20;
    public CapterUnitMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getBookInfo(String book_id,BookInfoBean info){
        Map<String,String> data = new HashMap();
        data.put("book_id",book_id);
        send(ConstantValue.UNIT_BOOK,BOOKINFO_CODE,data,info);
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        BookInfoBean info = (BookInfoBean) result;
        if (info.getStatus()==1){
            activity.successFromMid(info,BOOKINFO_CODE);
        }else {
            activity.failedFrom(BOOKINFO_CODE);
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(BOOKINFO_CODE);
    }
}

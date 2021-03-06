package me.bandu.talk.android.phone.middle;

import android.content.Context;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import java.util.HashMap;
import java.util.Map;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.UnitInfoBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CapterLessonMiddle extends BaseMiddle {
    public static final  int UNIT_INFO =20;
    public CapterLessonMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getUnitInfo(String unit_id,UnitInfoBean info){
        Map<String,String> data = new HashMap();
        data.put("unit_id",unit_id);
        send(ConstantValue.LESSON_UNIT,UNIT_INFO,data,info);
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        UnitInfoBean info = (UnitInfoBean) result;
        if (info.getStatus()==1){
            activity.successFromMid(info,UNIT_INFO);
        }else {
            activity.failedFrom(UNIT_INFO);
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(UNIT_INFO);
    }


}

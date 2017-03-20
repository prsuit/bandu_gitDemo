package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.GetGradesBean;
import me.bandu.talk.android.phone.bean.QueryClassBean;
import me.bandu.talk.android.phone.bean.UnitInfoData;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetGradesMiddle extends BaseMiddle {
    public static final  int GET_GRADES = 1;
    public GetGradesMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void getGrades(){
        Map<String,String> data = new HashMap();
        send(ConstantValue.GET_GRADES,GET_GRADES,data,new GetGradesBean());
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

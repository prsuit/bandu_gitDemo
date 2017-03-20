package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.SchoolBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：获取地区列表的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GetSchoolMiddle extends BaseMiddle {
    public static final int GET_SCHOOL = 1;

    public GetSchoolMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void getSchool(String parentId, SchoolBean obj){
        Map<String,String> data = new HashMap();
        data.put("district_id", parentId);
        send(ConstantValue.GET_SCHOOL, GET_SCHOOL, data,obj);
    }
}

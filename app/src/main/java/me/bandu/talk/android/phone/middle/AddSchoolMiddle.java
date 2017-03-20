package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.AddSchoolBean;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：创建学校的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AddSchoolMiddle extends BaseMiddle {
    public static final int ADD_SCHOOL = 1;

    public AddSchoolMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    public void createSchool(String uid,String districtId,String schoolName,AddSchoolBean obj){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("district_id", districtId);
        data.put("name", schoolName);
        send(ConstantValue.CREATE_SCHOOL, ADD_SCHOOL, data,obj);
    }

}

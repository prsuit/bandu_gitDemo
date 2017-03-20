package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.CreateClass;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 13:49
 * 类描述：创建班级的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreateClassMiddle extends BaseMiddle {
    public static final int CREATE_CLASS = 1;
    public static final int CLASS_MODIFY = 2;

    public CreateClassMiddle(BaseActivityIF baseActivityIF,Context context){
        super(baseActivityIF, context);
    }

    public void createClass(String className){
        Map<String,String> data = new HashMap();
        data.put("uid", GlobalParams.userInfo.getUid()+"");
        data.put("class_name", className);
        send(ConstantValue.CREATE_CLASS, CREATE_CLASS, data, new CreateClass());
    }

    public void modifyClass(String className, String cid){
        Map<String,String> data = new HashMap();
        data.put("uid", GlobalParams.userInfo.getUid()+"");
        data.put("cid", cid);
        data.put("class_name", className);
        send(ConstantValue.CLASS_MODIFY, CLASS_MODIFY, data, new CreateClass());
    }

    @Override
    public void success(Object result, int requestCode) {
        activity.successFromMid(result, requestCode);
    }

    @Override
    public void failed(int requestCode) {
        activity.failedFrom(requestCode);
    }
}

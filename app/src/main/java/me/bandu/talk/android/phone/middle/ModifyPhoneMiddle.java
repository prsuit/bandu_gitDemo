package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：修改手机的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyPhoneMiddle extends BaseMiddle {
    public static final int MODIFY_PHONE = 1;

    public ModifyPhoneMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 修改手机号
     * @param uid 用户id
     * @param phone 新手机号
     * @param checkCode 验证码
     * @param obj
     */
    public void modifyPhone(String uid,String phone,String checkCode,BaseBean obj){
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("uid", uid);
        data.put("phone", phone);
        data.put("checkcode", checkCode);
        send(ConstantValue.MODIFY_PHONE, MODIFY_PHONE, data,obj);
    }
}

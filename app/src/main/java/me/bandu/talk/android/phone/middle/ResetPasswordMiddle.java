package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;

/**
 * 创建者：taoge
 * 时间：2015/11/30 13:49
 * 类描述：注册的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ResetPasswordMiddle extends BaseMiddle {
    public static final int RESETPASSWORD = 1;

    public ResetPasswordMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }

    /**
     * 重置密码
     * @param phone 手机号
     * @param checkCode 验证码
     * @param password 密码
     * @param obj
     */
    public void reset(String phone,String checkCode,String password,BaseBean obj){
        Map<String,String> data = new HashMap();
        data.put("phone", phone);
        data.put("checkcode", checkCode);
        data.put("password", password);
        send(ConstantValue.RESET_PASSWORD, RESETPASSWORD, data,obj);
    }
}

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
 * 类描述：修改信息的中间类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyUserInfoMiddle extends BaseMiddle {
    public static final int MODIFY_NAME = 1;
    public static final int MODIFY_SCHOOL = 2;
    public static final int MODIFY_PASSWORD= 3;

    public ModifyUserInfoMiddle(BaseActivityIF baseActivityIF, Context context) {
        super(baseActivityIF, context);
    }

    /**
     *  修改姓名
     * @param uid 用户id
     * @param name 用户姓名
     */
    public void modifyName(String uid, String name) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("name", name);
        send(ConstantValue.MODIFY_USERINFO, MODIFY_NAME, data, new BaseBean());
    }

    /**
     *  修改学校
     * @param uid 用户id
     * @param school 学校名称
     */
    public void modifySchool(String uid, int school) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("school", school+"");
        send(ConstantValue.MODIFY_USERINFO, MODIFY_SCHOOL, data, new BaseBean());

    }

    /**
     *  修改密码
     * @param uid 用户id
     * @param password 新密码
     */
    public void modifyPass(String uid, String password) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("password", password);
        send(ConstantValue.MODIFY_USERINFO, MODIFY_PASSWORD, data, new BaseBean());

    }
}

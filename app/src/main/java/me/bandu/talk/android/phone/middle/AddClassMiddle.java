package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.QueryClassBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AddClassMiddle extends BaseMiddle {
    public static final  int QUERY_CLASS =40;
    public static final  int ADD_CLASS =50;
    public AddClassMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void queryClass(String cid,String phone){
        Map<String,String> data = new HashMap();
        data.put("cid",cid);
        data.put("phone",phone);
        send(ConstantValue.QUERY_CLASS,QUERY_CLASS,data,new QueryClassBean());
    }
    public void addClass(String cid,String uid){
        Map<String,String> data = new HashMap();
        data.put("cid",cid);
        data.put("uid",uid);
        send(ConstantValue.ADD_CLASS,ADD_CLASS,data,new BaseBean());
    }
    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        if (requestCode==QUERY_CLASS){
            QueryClassBean bean = (QueryClassBean) result;
            if (bean!=null&&bean.getStatus()==1){
                activity.successFromMid(result,requestCode);
            }else {
                activity.failedFrom(requestCode);
                UIUtils.showToastSafe(((QueryClassBean) result).getMsg());
            }
        }else if (requestCode==ADD_CLASS){
            BaseBean baseBean = (BaseBean) result;
            if (baseBean!=null&&baseBean.getStatus()==1){
                activity.successFromMid(result,requestCode);
            }else {
                activity.failedFrom(requestCode);
                UIUtils.showToastSafe(((BaseBean) result).getMsg());
            }
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(requestCode);
        UIUtils.showToastSafe("请保持您的网络畅通哟~");
    }
}

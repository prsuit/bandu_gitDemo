package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.WorkDataBean;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkMiddle extends BaseMiddle {
    public static final int CREATWORK = 30;

    public CreatWorkMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
    }
    public void creatWork(String uid, String cid, String book_id, ArrayList<String> selects, String title, String score_level, String description, String deadline,List<Map<String,String>> content){
        final Map data = new HashMap();
        data.put("uid",uid);
        data.put("cid",cid);
        data.put("book_id",book_id);
        data.put("title",title);
        data.put("score_level",score_level);
        data.put("description",description);
        data.put("deadline",deadline);
        data.put("content",content);
        if (selects==null){
            selects = new ArrayList<>();
        }
        data.put("uids",selects);
        send(ConstantValue.CREAT_WORK,CREATWORK,data,new BaseBean());
    }
    public void creatWork(WorkDataBean dataBean){
        if (dataBean==null){
            return;
        }
        String msg = dataBean.getUpdateMsg();
        if (msg==null){
            send(ConstantValue.CREAT_WORK,CREATWORK,dataBean.getDataMap(),new BaseBean());
        }else {
            UIUtils.showToastSafe(msg);
        }
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        BaseBean baseBean = (BaseBean) result;
        if (baseBean.getStatus()==1){
            activity.successFromMid(baseBean,requestCode);
        }else {
            UIUtils.showToastSafe(baseBean.getMsg());
            activity.failedFrom(requestCode);
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        UIUtils.showToastSafe("请保持网络畅通哟");
        activity.failedFrom(requestCode);
    }
}

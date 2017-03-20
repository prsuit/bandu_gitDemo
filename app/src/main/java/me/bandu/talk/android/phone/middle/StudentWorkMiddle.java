package me.bandu.talk.android.phone.middle;

import android.content.Context;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import java.util.HashMap;
import java.util.Map;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentWorkMiddle extends BaseMiddle {
    public static final int HOMEWORK = 59;
    public static final int HOMEWORK_OVER = 61;
//    public static final int HOMEWORK_CATLOG = 70;
    private Context context;

    public StudentWorkMiddle(BaseActivityIF baseActivityIF, Context context){
        super(baseActivityIF, context);
        this.context = context;
    }


    //38.学生作业列表
    public void getHomeWork(String uid,String page,String size){
        Map<String,String> data = new HashMap();
        data.put("uid", uid);
        data.put("cid", PreferencesUtils.getUserInfo().getCid());
        data.put("type","1");
        data.put("page",page);
        data.put("size",size);
        send(ConstantValue.LIST_HOMEWORK,HOMEWORK,data,new HomeWorkBean());
    }
    //38.学生作业列表
    public void getHomeWorkOver(String uid,String page,String size){
        Map<String,String> data = new HashMap();
        data.put("uid",uid);
        data.put("type","2");
        data.put("cid", PreferencesUtils.getUserInfo().getCid());
        data.put("page",page);
        data.put("size",size);
        send(ConstantValue.LIST_HOMEWORK,HOMEWORK_OVER,data,new HomeWorkBean());
    }
    //39.学生作业目录
//    public void getHomeWorkCatlog(String uid,String job_id,String stu_job_id){
//        Map<String,String> data = new HashMap();
//        data.put("uid",uid);
//        data.put("job_id",job_id);
//        data.put("stu_job_id",stu_job_id);
//        send(ConstantValue.HOMEWORK_CATLOG,HOMEWORK_CATLOG,data,new HomeWorkCatlogBean());
//    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        if (requestCode==HOMEWORK){
            HomeWorkBean b = (HomeWorkBean) result;
            if (b.getStatus()==1){
                activity.successFromMid(result,requestCode);
            }else {
                activity.failedFrom(requestCode);
            }
        }
//        else if (requestCode==HOMEWORK_CATLOG){
//            HomeWorkCatlogBean b = (HomeWorkCatlogBean) result;
//            if (b.getStatus()==1){
//                activity.successFromMid(result,requestCode);
//            }else {
//                activity.failedFrom(requestCode);
//            }
//        }
        else  if(requestCode == HOMEWORK_OVER){
            HomeWorkBean b = (HomeWorkBean) result;
            if (b.getStatus()==1){
                activity.successFromMid(result,requestCode);
            }else {
                activity.failedFrom(requestCode);
            }
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(requestCode);
    }
}

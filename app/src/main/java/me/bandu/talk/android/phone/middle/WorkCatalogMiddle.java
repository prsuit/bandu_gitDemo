package me.bandu.talk.android.phone.middle;

import android.content.Context;

import com.DFHT.base.BaseBean;
import com.DFHT.base.BaseMiddle;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ChooseengineBean;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.UnitAndLesson;
import me.bandu.talk.android.phone.bean.UploadBean;
import me.bandu.talk.android.phone.bean.WorkDoneBean;

/**
 * 创建者：高楠
 * 时间：on 2015/12/18
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCatalogMiddle extends BaseMiddle {
    public final static int CHOOSEENGINE = 60;
    public final static int WORK_CATALOG = 70;
    public final static int UNIT_LESSON = 80;
    public final static int EXERCISE_DOWNLOAD_INFO = 90;
    public final static int FINISH_WORK = 100;
    public final static int UPLOAD_TEXT = 110;
    public final static int UPLOAD_TEXT_V3 = 170;
    public final static int DETAIL = 180;


    public WorkCatalogMiddle(BaseActivityIF baseActivityIF, Context context) {
        super(baseActivityIF, context);
    }

    /*
    62. 驰声接口使用情况控制接口
接口地址：/app/ chooseengine
请求参数：无
响应格式：{status:1 ,data:{engine:0},msg:'成功'}
响应说明：data.engine 值为0 表示调用驰声
			data.engine 值为1 表示调用其他接口

     */
    public void getWorkCatalog(String uid, String job_id, long stu_job_id) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("job_id", job_id);
        data.put("stu_job_id", stu_job_id + "");
        send(ConstantValue.WORK_CATALOG, WORK_CATALOG, data, new HomeWorkCatlogBean());
        send(ConstantValue.CHOOSEENGINE, CHOOSEENGINE, new HashMap(), new ChooseengineBean());
    }

    public void getTaskDetail(String userID, long stu_job_id, long hw_quiz_id) {
        Map<String, String> data = new HashMap<>();
        data.put("stu_job_id", String.valueOf(stu_job_id));
        data.put("hw_quiz_id", String.valueOf(hw_quiz_id));
        data.put("uid", GlobalParams.userInfo.getUid() + "");
        send(ConstantValue.HOMEWORK_DETAIL, DETAIL, data, new Detail());
    }

    public void getUnitLesson(String job_id) {
        Map<String, String> data = new HashMap();
        data.put("job_id", job_id);
        send(ConstantValue.UNIT_LESSON, UNIT_LESSON, data, new UnitAndLesson());
    }

    public void getDownloadInfo(String unit_id) {
        Map<String, String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("unit_id", unit_id);
        send(ConstantValue.EXERCISE_DOWNLOAD_INFO, EXERCISE_DOWNLOAD_INFO, data, new ExerciseDowanloadInfoBean());
    }

    public void setFinishWork(String uid, String stu_job_id, String spend_time, String percent) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("stu_job_id", stu_job_id);
        data.put("spend_time", spend_time);
        data.put("percent", percent);
        send(ConstantValue.FINISH_WORK, FINISH_WORK, data, new WorkDoneBean());
    }

    public void setFinishWorkNew(String uid, String stu_job_id) {
        Map<String, String> data = new HashMap();
        data.put("uid", uid);
        data.put("stu_job_id", stu_job_id);
        send(ConstantValue.TOTAL_DONE, UPLOAD_TEXT_V3, data, new WorkDoneBean());
    }

    //    public void uploadText(Map data){
//        send(ConstantValue.UPLOAD_TEXT, UPLOAD_TEXT, data, new UploadBean(),this,true,"gaonan");
//    }
    //3.3作业提交方式
    public void uploadText(Map data) {
        send(ConstantValue.UPLOAD_TEXT_V3, UPLOAD_TEXT, data, new UploadBean(), this, true, "gaonan");
    }

    @Override
    public void success(Object result, int requestCode) {
        super.success(result, requestCode);
        if (requestCode == WORK_CATALOG) {
            HomeWorkCatlogBean bean = (HomeWorkCatlogBean) result;
            if (bean != null && bean.getStatus() == 1) {
                activity.successFromMid(result, requestCode);
            } else {
                activity.failedFrom(requestCode);//网络请求失败
                UIUtils.showToastSafe(bean.getMsg());
            }
        } else if (requestCode == UNIT_LESSON) {
            UnitAndLesson baseBean = (UnitAndLesson) result;
            if (baseBean.getStatus() == 1) {
                activity.successFromMid(result, requestCode);
            } else {
                activity.failedFrom(requestCode);
                UIUtils.showToastSafe(baseBean.getMsg());
            }
        } else if (requestCode == EXERCISE_DOWNLOAD_INFO) {
            ExerciseDowanloadInfoBean exerciseDowanloadInfoBean = (ExerciseDowanloadInfoBean) result;
            if (exerciseDowanloadInfoBean.getStatus() == 1) {
                activity.successFromMid(result, requestCode);
            } else {
                UIUtils.showToastSafe(exerciseDowanloadInfoBean.getMsg());
                activity.failedFrom(requestCode);
            }
        } else if (requestCode == FINISH_WORK) {
            WorkDoneBean bb = (WorkDoneBean) result;
            if (bb.getStatus() == 1) {
                activity.successFromMid(result, requestCode);
            } else {
                UIUtils.showToastSafe(bb.getMsg());
                activity.failedFrom(requestCode);
            }
        } else if (requestCode == UPLOAD_TEXT) {
            BaseBean bean = (UploadBean) result;
            if (bean.getStatus() == 1 || bean.getStatus() == 2) {
                activity.successFromMid(result, requestCode);
            } else {
                activity.failedFrom(requestCode);
            }
            UIUtils.showToastSafe(bean.getMsg());
        } else if (requestCode == DETAIL) {
            Detail bean = (Detail) result;
            if (bean.getStatus() == 1 || bean.getStatus() == 2) {
                activity.successFromMid(result, requestCode);
            } else {
                UIUtils.showToastSafe(bean.getMsg());
                activity.failedFrom(requestCode);
            }
        }
    }

    @Override
    public void failed(int requestCode) {
        super.failed(requestCode);
        activity.failedFrom(requestCode);
        UIUtils.showToastSafe("请保持网络畅通哟~");
    }
}

package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.DFHT.utils.UIUtils;

import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.async.UploadAsyncTask;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.dao.Sentence;
import me.bandu.talk.android.phone.impl.AsyncListener;
import me.bandu.talk.android.phone.impl.UploadListener;
import me.bandu.talk.android.phone.view.UpLoadDialog;

/**
 * 创建者：高楠
 * 时间：on 2016/3/3
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UpLoadUtils implements AsyncListener, DialogInterface.OnDismissListener {
    private List<Sentence> queues;
    private int error_count = 0 ;//错误的数量
    private int current_position = 0 ;//-1的时候表示无数据
    private int data_count = 0 ;//数据的数量
    private int state = UploadListener.NEVER;
    private Context context;
    private UpLoadDialog dialog;
    private UploadAsyncTask uploadAsyncTask;
    private UploadListener listener;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dialog!=null&&dialog.isShowing())
                dialog.dismiss();
            return false;
        }
    });

    public void setQueues(List<Sentence> queues, Context context, UploadListener listener) {
        this.queues = queues;
        this.context = context;
        this.listener = listener;
        dialog = new UpLoadDialog(context);
        dialog.setOnDismissListener(this);
        error_count = 0 ;
        if (queues==null||queues.size()==0){
            current_position = -1;//-1的时候表示无数据
            data_count = 0 ;
        }else {
            data_count = queues.size();
            current_position = 0;
        }
    }
    //开始上传
    public void startUpLoad(String msg){
        if (data_count>0){
            dialog.show();
            if (msg!=null&&!msg.equals("")){
                dialog.setNote(msg);
            }
            upload();
        }
    }
    //上传
    public void upload(){
        if (data_count>0){
            uploadAsyncTask = new UploadAsyncTask(context,queues.get(current_position),this);
            uploadAsyncTask.execute();
        }
    }

    //取消
    @Override
    public void onCancel() {
        state = UploadListener.CANCEL;
        //TODO  枚举
        dialog.setNote(state);
        if (dialog.isShowing())
            handler.sendEmptyMessageDelayed(0,1000);
    }
    //完成
    @Override
    public void onFinish() {
        state = UploadListener.FINISH;
        //更新数据库
        Sentence sentence = queues.get(current_position);
        sentence.setStu_done(false);
        DaoUtils.getInstance().updataSentence(sentence);

        current_position++;
        dialog.setProgress(current_position,data_count);
        if (current_position<data_count){
            upload();
        }else {
            dialog.setNote(state);
            if (dialog.isShowing()){
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    }
    //错误
    @Override
    public void onError() {
        state = UploadListener.ERROR;
        error_count++;
        if (error_count<=3){
            upload();
        }else {
            dialog.setNote(state,"音频提交失败，请检查网络环境");
            if (dialog.isShowing())
                handler.sendEmptyMessageDelayed(0,2000);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (uploadAsyncTask!=null){
            uploadAsyncTask.cancel(true);
        }
        if (listener!=null){
            listener.uploadResult(state,UploadListener.MORE,null);
        }
        int count = data_count-current_position;
        if (count>0){
            UIUtils.showToastSafe("当前已上传"+current_position+"个，还有"+count+"个需要上传，请继续上传!");
        }
    }
}

package me.bandu.talk.android.phone.utils;

/**
 * 创建者:taoge
 * 时间：2015/12/15
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/15
 * 修改备注：
 */
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.DFHT.net.NetworkUtil;
import com.DFHT.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.activity.WorkCatalogActivity;
import me.bandu.talk.android.phone.impl.UploadListener;
import me.bandu.talk.android.phone.view.UpLoadDialog;

/**
 *
 * 上传工具类
 * 支持上传文件和参数
 */
public class UploadUtilZip implements DialogInterface.OnDismissListener {
    private static UploadUtilZip utilZip;
    private UploadUtilZip(){}
    public static UploadUtilZip getInstance(){
        if (utilZip==null){
            return new UploadUtilZip();
        }
        return utilZip;
    }
    private File file;
    private HttpHandler httpHandler;
    private UpLoadDialog dialog;
    private UploadListener listener;
    private int state = UploadListener.NEVER;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dialog!=null&&dialog.isShowing())
                if (ScreenUtil.isForeground((Activity) context,context.getClass().getName()))
                dialog.dismiss();
            return false;
        }
    });
    private Context context;
    /**
     * 上传方法
     */
    public void  upload(String uid,String stu_job_id,Context context,File file,UploadListener listener){
        this.listener = listener;
        dialog = new UpLoadDialog(context);
        this.context = context;
        dialog.setOnDismissListener(this);
        if (ScreenUtil.isForeground((Activity) context, context.getClass().getName())){
            dialog.show();
            dialog.setProgress(0,1);
            dialog.setNote(context.getString(R.string.textsuccess_medianext));
            this.file = file;
            RequestParams params = new RequestParams();
            Map map = new HashMap();
            map.put("uid",uid);
            map.put("stu_job_id",stu_job_id);
            params.addBodyParameter("data", NetworkUtil.mapToDESStr(map, "gaonan"));
            params.addBodyParameter("zip", file);
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.configCurrentHttpCacheExpiry(1000 * 30);//设置当前网络请求的缓存超时时间
            httpUtils.configTimeout(1000 * 30);//设置连接超时时间
            httpUtils.configSoTimeout(1000 * 30);//设置连接超时时间
            httpHandler = httpUtils.send(HttpRequest.HttpMethod.POST, ConstantValue.UPLOAD_ZIP, params, new RequestCallBack<String>() {
                private int percent = 0;
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    state = UploadListener.FINISH;
                    dialog.setNote(state);
                    if (dialog.isShowing())
                        handler.sendEmptyMessageDelayed(0,1000);
                    LogUtils.e("结果："+responseInfo.toString());
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    if (error.getMessage().contains("SocketTimeoutException")&&percent==1){
                        state = UploadListener.FINISH;
                        dialog.setNote(state);
                    }else {
                        state = UploadListener.ERROR;
                        dialog.setNote(state,"音频提交失败，请检查网络环境");
                    }
                    if (dialog.isShowing())
                        handler.sendEmptyMessageDelayed(0,2000);
                    LogUtils.e("出错了："+error.getMessage());
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    if (isUploading){
                        dialog.setProgress(current,total);
                        percent = (int) (current/total)>=1?1:0;
                    }
                }
            });
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (httpHandler!=null){
            httpHandler.cancel();
        }
        if (listener!=null){
            listener.uploadResult(state,UploadListener.ONE,file);
        }
    }
}
//**
//     * 上传文件
//     * @param <T>
//     */
//    public static <T> Callback.Cancelable UpLoadFile(String uid,String stu_job_id,File file, String url, Callback.CommonCallback<T> callback){
//        RequestParams params=new RequestParams(url);
//        params.setMultipart(true);
//        params.addBodyParameter("zip",file);
//        Map map = new HashMap();
//        map.put("uid",uid);
//        map.put("stu_job_id",stu_job_id);
//        params.addBodyParameter("data", NetworkUtil.mapToDESStr(map, "gaonan"));
//        Callback.Cancelable cancelable = x.http().post(params, callback);
//        return cancelable;
//    }
//        UpLoadFile(uid,stu_job_id,file, ConstantValue.UPLOAD_ZIP, new Callback.ProgressCallback<Object>() {
//            @Override
//            public void onSuccess(Object result) {
//                state = UploadListener.FINISH;
//                dialog.setNote(state);
//                if (dialog.isShowing())
//                    handler.sendEmptyMessageDelayed(0,1000);
//                LogUtils.e("结果："+result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                state = UploadListener.ERROR;
//                dialog.setNote(state);
//                if (dialog.isShowing())
//                    handler.sendEmptyMessageDelayed(0,1000);
//                LogUtils.e("出错了："+ex.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                state = UploadListener.CANCEL;
//                dialog.setNote(state);
//                if (dialog.isShowing())
//                    handler.sendEmptyMessageDelayed(0,1000);
//                LogUtils.e("取消："+cex.getMessage());
//            }
//
//            @Override
//            public void onFinished() {
//            }
//
//            @Override
//            public void onWaiting() {
//            }
//
//            @Override
//            public void onStarted() {
//
//            }
//
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//                dialog.setProgress(current,total);
//                LogUtils.e("上传进度:"+current+"/"+total);
//            }
//        });

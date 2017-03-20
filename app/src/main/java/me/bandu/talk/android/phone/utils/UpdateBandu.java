package me.bandu.talk.android.phone.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import me.bandu.talk.android.phone.bean.AppUpdateBean;
import me.bandu.talk.android.phone.middle.AppUpdateMiddle;
import me.bandu.talk.android.phone.view.UpdateAppDialog;
import me.bandu.talk.android.phone.view.impl.UpdateAppDialogCallback;

/**
 * 创建者：wanglei
 * <p>时间：16/8/23  10:56
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class UpdateBandu implements BaseActivityIF, UpdateAppDialogCallback {

    private static UpdateBandu updateBandu;
    private Context context;
    private int versionCode;
    private int width;
    private int height;
    private AppUpdateBean.UpdateDate data1;
    private UpdateAppDialog appUpdateDialog;
    private SharedPreferences sp;
    private int netVersionCode;
    private boolean isShowOff;
    private int states;//1是个人中心的进入的,必须更新,2是其它页进入的,按照之前的逻辑

    private UpdateBandu() {
    }

    public static UpdateBandu getUpdateBandu() {
        if (updateBandu == null)
            updateBandu = new UpdateBandu();
        return updateBandu;
    }

    public void update(Context context, int versionCode, int states) {
        this.context = context;
        this.versionCode = versionCode;
        this.states = states;
        new AppUpdateMiddle(this, context).update(versionCode);
    }

    public void setWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;

    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        AppUpdateBean data = (AppUpdateBean) result;
        if (data != null && data.getStatus() == 1) {
            data1 = data.getData();
            if (data1 != null) {
                netVersionCode = data1.getVersion();
                Log.e("vc", "onSuccess: Version-"+data1.getVersion() );
                Log.e("vc", "onSuccess: Versionname-"+data1.getVersionname() );
                Log.e("vc", "onSuccess: Url-"+data1.getUrl() );
                if (netVersionCode == versionCode && states == 1) {
                    UIUtils.showToastSafe("当前已经是最新版本");
                    return;
                }
                if (netVersionCode - getOldNetVersionCode() == 1 || netVersionCode != 0 && netVersionCode > versionCode && (states == 1 ? true : !isUpdate())) {
                    appUpdateDialog = new UpdateAppDialog(context, this, width, height);
                    appUpdateDialog.forceUpdate(data1.isUpdate());
                    appUpdateDialog.showOftenDealog(data1.getDescription(), data1.getVersionname());
                }
            }
        }
    }

    @Override
    public void successFromMid(Object... obj) {
    }

    @Override
    public void failedFrom(Object... obj) {
    }

    @Override
    public void onFailed(int requestCode) {
    }

    public void myUpdateDialogDismiss() {
        if (appUpdateDialog != null)
            appUpdateDialog.dismiss();
    }

    @Override
    public void dialog_but_no() {//点击No就要判断是否记住状态下次根据状态提醒了
        recordVersionInfo(appUpdateDialog.notUpdate(), false);
    }

    @Override
    public void dialog_but_ok() {//点击ok就代表要更新,其它所有信息没必要存了,
        appUpdateDialog.determineUpdate();
        if (UIUtils.isRunInMainThread()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        downloadTo(data1.getUrl(), storagePath());
                    } catch (IOException e) {
                        UIUtils.showToastSafe("下载失败");
                        appUpdateDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void recordVersionInfo(boolean isRecordver, boolean isUpdateVersionCode) {
        if (null == sp)
            sp = context.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("appUpdateState", isRecordver);
        if (isUpdateVersionCode || isRecordver)
            editor.putInt("appVersionCode", netVersionCode);
        editor.apply();//更commit一样,apply()是异步调用执行磁盘I/O，commit()是同步的。所以避免调用commit()从UI线
    }

    private boolean isUpdate() {
        if (null == sp)
            sp = context.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        return sp.getBoolean("appUpdateState", false);
    }

    private int getOldNetVersionCode() {
        if (null == sp)
            sp = context.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        return sp.getInt("appVersionCode", 0);
    }

    /**
     * 存储路径
     *
     * @return 返回图片的地址
     */
    private File storagePath() {
        String folder;
        // 如果sd卡挂载了
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            folder = Environment.getExternalStorageDirectory() + File.separator;// 存SD卡上
        else
            folder = Environment.getRootDirectory() + File.separator;// 否则存机器上
        return new File(folder + "ban_du_123.apk");
    }

    private void downloadTo(final String netUrl, final File file) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        long downLoadFileSize = 0;
        if (file.exists())
            file.delete();
        if (!file.exists())
            file.createNewFile();
        // 构造URL
        URL url = new URL(netUrl);
        // 打开连接
        URLConnection con = url.openConnection();
        long fileSize = con.getContentLength();//文件的大小
        //设置超时时间
        con.setConnectTimeout(5000);
        is = con.getInputStream();
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        os = new FileOutputStream(file);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
            downLoadFileSize += len;
            update(fileSize, downLoadFileSize, file);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    private void update(final long fileSize, final long downLoadFileSize, final File file) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                float v = downLoadFileSize * 1.0f / fileSize * 1.0f;
                int i = (int) (v * 100);
                appUpdateDialog.updatePercentageSeek(i);

                if (i == 100) {
                    recordVersionInfo(false, true);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    context.startActivity(intent);
                }
            }
        });
    }

    public boolean isShowOff() {
        return isShowOff;
    }

    public void setShowOff(boolean showOff) {
        isShowOff = showOff;
    }
}

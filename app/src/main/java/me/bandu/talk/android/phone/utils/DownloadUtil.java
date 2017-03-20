package me.bandu.talk.android.phone.utils;

import android.os.Handler;
import android.os.Message;

import com.DFHT.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 创建者：gaoye
 * 时间：2015/12/15  15:24
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DownloadUtil {
    /**进度这里固定为100*/
    public static final int jd = 100;
    /**
     *
     * @param urlStr http://f.txt
     * @param path /mnt/sdcard/f.txt
     * @param handler what=0失败1成功2进度：arg1(1~100)
     */
    public static void to(final String urlStr,final String path,final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File(path);
                if (f.exists()) {
                    f.delete();
                }
                InputStream is = null;
                OutputStream os = null;
                try {
                    // 构造URL
                    URL url = new URL(urlStr);
                    // 打开连接
                    URLConnection con = url.openConnection();
                    //获得文件的长度
                    int contentLength = con.getContentLength();
                    int cd = 0;
                    if (contentLength>=jd) {
                        cd = contentLength/jd;//计算出文件长度的1/100用于进度
                    }
                    // 输入流
                    is = con.getInputStream();
                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    // 输出的文件流
                    os = new FileOutputStream(path);
                    // 开始读取
                    int i = 0;
                    int arg = 0;
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                        i+=len;
                        if (i>=cd) {
                            Message msg = Message.obtain();
                            msg.arg1 = ++arg;
                            msg.what = 2;
                            handler.sendMessage(msg);
                            i=0;
                        }
                    }
                    // 完毕，关闭所有链接
                    os.close();
                    is.close();
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    public static boolean stop;
    public static void downloadTo(final String urlStr,final String path,final String name,final DownloadState state){
        stop = false;
        if (state != null){
            state.stateStart();
        }
        File dir = new File(path);
        dir.mkdirs();
        File f = new File(dir,name);
        if (f.exists())
            f.delete();
                InputStream is = null;
                OutputStream os = null;
                try {
                    f.createNewFile();
                    // 构造URL
                    URL url = new URL(urlStr);
                    // 打开连接
                    URLConnection con = url.openConnection();
                    con.setConnectTimeout(8000);
                    //获得文件的长度
                    int contentLength = con.getContentLength();
                    int cd = 0;
                    if (contentLength>=jd) {
                        cd = contentLength/jd;//计算出文件长度的1/100用于进度
                    }
                    // 输入流
                    is = con.getInputStream();
                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    // 输出的文件流
                    os = new FileOutputStream(f);
                    // 开始读取
                    int i = 0;
                    int arg = 0;
                    while ((len = is.read(bs)) != -1) {
                        if (stop){
                            throw new Exception();
                        }
                        os.write(bs, 0, len);
                        i+=len;
                        if (i>=cd) {
                            if (state != null){
                                state.stateChange(++arg);
                            }
                            i=0;
                        }
                    }
                    // 完毕，关闭所有链接
                    os.close();
                    is.close();
                    if (state != null){
                        state.stateFinish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(e.toString());
                    if (state != null){
                        state.stateError();
                    }
                }
    }

    public interface DownloadState{
        public void stateChange(int percent);
        public void stateError();
        public void stateStart();
        public void stateFinish();
    }
}

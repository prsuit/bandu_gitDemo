package me.bandu.talk.android.phone.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.MediaPathUtils;

/**
 * 创建者：高楠
 * 时间：on 2016/1/7
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NewDownloadAsyncTask extends AsyncTask<Object, Object, Long> {

    private String url;
    private File mDownloadDir;
    private File mDownloadFile;
    private String curTimeL;
    public NewDownloadAsyncTask(String url){
        this.url = url;
    }

    @Override
    protected Long doInBackground(Object... params) {
//        url = (String) params[0];
        downLoadFile();
        return 1l;
    }

    private void downLoadFile() {
        InputStream inputStream = null;
        RandomAccessFile fileOutputStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                fileOutputStream = new RandomAccessFile(mDownloadFile,"rw");
                inputStream = conn.getInputStream();
                byte[] buff = new byte[1024];
                int len;
                long length = fileOutputStream.length();
                fileOutputStream.seek(length);
                while ((len = inputStream.read(buff)) > 0) {
                    fileOutputStream.write(buff, 0, len);
                }
                fileOutputStream.close();
            }
            Log.e("完成了：", mDownloadFile.getAbsolutePath() + "-----" + mDownloadFile.getName());
        } catch (Exception e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e2) {
                }
            }
        }
    }


    @Override
    protected void onPreExecute() {
        curTimeL = String.valueOf(System.currentTimeMillis());
        mDownloadDir = new File(FileUtil.DOWNLOAD_DIR);
        mDownloadFile = new File(FileUtil.DOWNLOAD_DIR+ File.separator+MediaPathUtils.getFileNameHash(url, curTimeL) );
        if (mDownloadDir != null && !mDownloadDir.exists()) {
            mDownloadDir.mkdirs();
        }
        if (mDownloadFile != null && !mDownloadFile.exists()) {
            try {
                mDownloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(Long result){
        super.onPostExecute(result);
        mDownloadFile.renameTo(new File(MediaPathUtils.DOWNLOAD_DIR+ File.separator+MediaPathUtils.getFileNameHash(url)));
        Log.e("完成了：", mDownloadFile.getAbsolutePath() + "-----" + mDownloadFile.getName());
    }
}
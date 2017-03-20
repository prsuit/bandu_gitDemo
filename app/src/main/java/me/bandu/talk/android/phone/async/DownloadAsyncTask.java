package me.bandu.talk.android.phone.async;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.util.Log;

import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.MediaPathUtils;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;

/**
 * Author Gaonan
 * 异步下载
 */
public class DownloadAsyncTask extends AsyncTask<Object, Object, Long> {

	private String mFileName;
	private boolean justdown;
	private String[] urls;
	private int mStartId;
	private File mDownloadDir;
	private File mDownloadFile;

	public DownloadAsyncTask(String fileName) {
		this.mFileName = fileName;
	}

	@Override
	protected Long doInBackground(Object... params) {
		getDataFromService(params);
		downLoadFile();
		return null;
	}

	private void downLoadFile() {
		if (urls != null && urls.length > 0 && mDownloadDir != null
				&& mDownloadFile != null) {
			for (String s : urls) {
				InputStream inputStream = null;
				RandomAccessFile fileOutputStream = null;
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(s)
							.openConnection();
					conn.setConnectTimeout(5 * 1000);
					conn.setRequestMethod("GET");
					conn.connect();
					if (conn.getResponseCode() == 200) {
						fileOutputStream = new RandomAccessFile(mDownloadFile,"rw");
						inputStream = conn.getInputStream();
						byte[] buff = new byte[1024];
						int len = 0;
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
		}
	}

	private void getDataFromService(Object... params) {
		urls = (String[]) params[0];
		mStartId = (Integer) params[1];
		justdown = (Boolean) params[2];
	}

	@Override
	protected void onPreExecute() {
		mDownloadDir = new File(FileUtil.DOWNLOAD_DIR);
		mDownloadFile = new File(mDownloadDir, mFileName);
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
		if (!justdown) {
			New_VoiceUtils.getInstance().startVoiceFile(mDownloadFile);
//			VoiceUtils.getInstance().startVoice(mDownloadFile,mStartId,VoiceUtils.getInstance().getListener());
		}
	}
}

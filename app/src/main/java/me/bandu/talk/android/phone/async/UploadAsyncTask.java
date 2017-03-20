package me.bandu.talk.android.phone.async;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.dao.Sentence;
import me.bandu.talk.android.phone.impl.AsyncListener;
import me.bandu.talk.android.phone.middle.UploadAvater;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * Author Gaonan
 * 异步上传
 */
public class UploadAsyncTask extends AsyncTask<Object, Object, Integer> {

	private AsyncListener listener;
	private Context context;
	private Sentence upload_data;


	public UploadAsyncTask(Context context, Sentence upload_data, AsyncListener listener){
		this.upload_data = upload_data;
		this.context = context;
		this.listener = listener;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(Object... params) {
		if (upload_data!=null){
			if (upload_data.getStu_mp3().contains("MusicDownload")){
				JSONObject str = UploadAvater.uploadWork(UserUtil.getUerInfo(context).getUid(), upload_data, upload_data.getStu_mp3());
				try {
					if (str==null||str.getString("status").equals("0")){
                        return AsyncListener.ERROR;
                    }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return AsyncListener.FINISH;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (listener!=null){
			if (result==AsyncListener.FINISH){
				listener.onFinish();
			}else if (result==AsyncListener.ERROR){
				listener.onError();
			}else if (result==AsyncListener.CANCEL){
				listener.onCancel();
			}
		}

	}
	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (listener!=null){
			listener.onCancel();
		}
	}
}

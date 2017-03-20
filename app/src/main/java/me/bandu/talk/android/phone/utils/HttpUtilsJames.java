package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.view.MyProgressDialog;

/**
 * http请求工具类
 * 
 * @author james
 * 
 */
public abstract class HttpUtilsJames {

	static MyProgressDialog pd;

	public static JSONObject sendPost(final Context activityContext, String url, Map<String, String> map) {
		LogUtils.i("联网地址:" + url);
		LogUtils.i("参数:" + map);
		if (!checkNetwork(UIUtils.getContext())) {
			UIUtils.showToastSafe("请检查网络是否畅通");
			return null;
		}
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				if (activityContext != null) {
					if (pd != null) {
						if (pd.isShowing()) {
							pd.dismiss();
						}
						pd = null;
					}
					pd = MyProgressDialog.createProgressDialog(activityContext, "");
					pd.setCancelable(true);
					pd.setCanceledOnTouchOutside(false);
					pd.show();
				}
			}
		});

		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		String result = "";
		JSONObject jsonObject = null;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList,"UTF-8");
			// URL使用基本URL即可，其中不需要加参数
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Accept-Encoding", "gzip");
			// 将请求体内容加入请求中
			httpPost.setEntity(requestHttpEntity);
			// 需要客户端对象来发送请求
			HttpClient httpClient = new DefaultHttpClient();
			// 发送请求
			HttpResponse response = httpClient.execute(httpPost);
			//连接超时
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);
			//读取超时
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);
			// 显示响应
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
			if (null == response) {
				return null;
			}
			int statusCode = response.getStatusLine().getStatusCode();
			LogUtils.i("状态码:" + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line = "";
				while (null != (line = reader.readLine())) {
					result += line;
				}
				LogUtils.i("访问的服务地址Url:" + url);
				LogUtils.i("json : " + result);
				jsonObject = new JSONObject(result);
			} else {
				LogUtils.e("访问的服务地址Url:" + url);
				LogUtils.e("服务器相应不是200,而是:" + statusCode);
				//ErrorUtil.saveMsgAndInterfaceUrl("服务器相应不是200,而是:" + statusCode + " 参数 :" + map.toString(), url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
		}
		try {
			String string = jsonObject == null ? "null" : jsonObject.toString();
			LogUtils.i("返回值:"+string);
		} catch (Exception e) {
		}
		
		return jsonObject;
	}
	
	/**
	 * 上传文件
	 */
	public static JSONObject postFile(String url, List<NameValuePair> nameValuePairs) {
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(url);
	    //连接超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);
		//读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,36000);
		String result = "";
		JSONObject jsonObject = null;
//	    try {
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

	        for(int index=0; index < nameValuePairs.size(); index++) {
	            if(nameValuePairs.get(index).getName().equals("avatar")||nameValuePairs.get(index).getName().equals("audio")) {
	                // If the key equals to "image", we use FileBody to transfer the data
	                entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new File(nameValuePairs.get(index).getValue())));
	            } else {
					try {
						entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
//						LogToFileUtil.saveCrashInfo2File(e);
					}
				}
	        }
	        httpPost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost, localContext);
		} catch (IOException e) {
			e.printStackTrace();
//			LogToFileUtil.saveCrashInfo2File(e);
		}
		UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
			if (null == response) {
				if (httpClient!=null){
					httpClient.getConnectionManager().shutdown();
				}
				return null;
			}
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = null;
				try {
					inputStream = httpEntity.getContent();
				} catch (IOException e) {
					e.printStackTrace();
//					LogToFileUtil.saveCrashInfo2File(e);
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				try {
					while (null != (line = reader.readLine())) {
                        result += line;
                    }
				} catch (IOException e) {
					e.printStackTrace();
//					LogToFileUtil.saveCrashInfo2File(e);
				}
				try {
					jsonObject = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
//					LogToFileUtil.saveCrashInfo2File(e);
				}
			} else {

			}
//	    } catch (Exception e) {
//	        e.printStackTrace();
//			UIUtils.showToastSafe("服务器异常！");
//	    }
		if (httpClient!=null){
			httpClient.getConnectionManager().shutdown();
		}
	    return jsonObject;
	}

	public static JSONObject sendPost(String url, Map<String, String> map) {
		return sendPost(null, url, map);
	}

	public static String sendPostGetStr(String url, Map<String, String> map) {
		return sendPostGetStr(url, map, null);
	}

	public static String sendPostGetStr(String url, Map<String, String> map, Context context) {
		JSONObject sendPost = sendPost(context, url, map);
		if (sendPost != null)
			return sendPost.toString();
		return null;
	}

	public static <T> T sendPost(String url, Map<String, String> map, Class<T> clz) {
		return sendPost(url, map, clz, null);
	}

	public static <T> T sendPost(String url, Map<String, String> map, Class<T> clz, Context context) {
		String result = sendPostGetStr(url, map, context);
		if (!TextUtils.isEmpty(result)) {
			return JSON.parseObject(result, clz);
		}
		return null;
	}

	public static boolean checkNetwork(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}

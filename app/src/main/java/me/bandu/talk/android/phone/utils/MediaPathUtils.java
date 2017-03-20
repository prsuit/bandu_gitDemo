package me.bandu.talk.android.phone.utils;

import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.util.Log;

import com.DFHT.utils.LogUtils;

import java.io.File;

import me.bandu.talk.android.phone.GlobalParams;

/**
 * Author Gaonan
 * SD卡
 */
public class MediaPathUtils {

    public static final String DOWNLOAD_DIR = FileUtil.DOWNLOAD_DIR;

    public static String getPath(){
        return FileUtil.DOWNLOAD_DIR + File.separator + System.currentTimeMillis() + "_" +(GlobalParams.userInfo == null ? "DFHT_ANDROID" : GlobalParams.userInfo.getUid()) + ".wav";
    }
    public static  String getHashOath(){
        String path = FileUtil.DOWNLOAD_DIR + File.separator + System.currentTimeMillis() + "_" +(GlobalParams.userInfo == null ? "DFHT_ANDROID" : GlobalParams.userInfo.getUid()) + ".wav";
        return FileUtil.DOWNLOAD_DIR + File.separator + getFileNameHash(path);

    }

    public static boolean idSDCardAviable(){
        boolean isaviable = false;
        String SDCardPath = "";
        StatFs statFs;
        if (Environment.getExternalStorageState ().equals (
                Environment.MEDIA_MOUNTED)) {
            SDCardPath = Environment.getExternalStorageDirectory ().getPath ();
            if (!SDCardPath.equals (null)) {
                statFs = new StatFs (SDCardPath);
                long SDFreeSize = (statFs.getAvailableBlocks () * statFs
                        .getBlockSize ()) / (1024 * 1024);
                if (SDFreeSize > 10) {
                    isaviable = true;
                }
            }
        }
        return isaviable;
    }
    public static String getFileName(String mDownloadUrl){
        Uri uri = Uri.parse (mDownloadUrl);
        return uri.getLastPathSegment ();
    }
    public static String getFileName(String[] mDownloadUrls){
    	if (mDownloadUrls==null||mDownloadUrls.length==0) {
			return "error.mp3";
		}
    	String filename = "";
    	String type = ".wav";
    	for(String s:mDownloadUrls){
    		String name = getFileName(s);
    		type = name.substring(name.lastIndexOf("."));
            filename = s.hashCode()+"";
//    		filename += name.substring(0, name.lastIndexOf("."));
    	}
    	filename+=type;
    	return filename;
    }
    public static String getFileNameHash(String mDownloadUrls){
    	if (mDownloadUrls==null||mDownloadUrls.length()==0) {
			return "error.mp3";
		}
        try {
            String name = getFileName(mDownloadUrls);
            String type = name.substring(name.lastIndexOf("."));
            String filename = mDownloadUrls.hashCode()+"";
            filename+=type;
            return filename;
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("地址出现问题:"+mDownloadUrls);
            return "error.mp3";
        }
    }
    public static String getFileNameHash(String mDownloadUrls,String curTimeL){
    	if (mDownloadUrls==null||mDownloadUrls.length()==0) {
			return "error.mp3";
		}
//        StringBuilder sb = new StringBuilder(getFileName(mDownloadUrls));
//        StringBuilder type = new StringBuilder(getFileName(mDownloadUrls));
    	String name = getFileName(mDownloadUrls);
        String type = name.substring(name.lastIndexOf("."));
        String filename = mDownloadUrls.hashCode()+"_"+curTimeL;
    	filename+=type;
    	return filename;
    }
}

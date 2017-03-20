package com.DFHT.db.local.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.DFHT.ENGlobalParams;
import com.DFHT.db.local.localenum.SaveType;
import com.DFHT.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jm on 2015/10/27.
 */
public class SDCardUtils {
    /**
     * 获取缓存路径
     * @param type 缓存的类型.
     * @return
     */
    public static File getCacheDir(SaveType type){
        String fileName = type.getType();
        if (Environment.getExternalStorageDirectory() != null) {
            File myRoot = new File(Environment.getExternalStorageDirectory(), "/Bandu/cache/" + fileName);
            if (!myRoot.exists())
                myRoot.mkdirs();
            return myRoot;
        }else {
            Toast.makeText(ENGlobalParams.applicationContext,"没有发现SD卡,将使用内部存储卡！",Toast.LENGTH_LONG).show();
            return new File(ENGlobalParams.applicationContext.getCacheDir(), "/Bandu/cache/" + fileName);
        }
    }
    /**
     * 获取本地缓存的Bean
     * @param interfaceUrl 接口地址:用于区别本地缓存文件.
     */
    public static <T> T getLocalEntity(String fileName,String interfaceUrl ,Class<T> entityClazz){
        File cacheDir = getCacheDir(SaveType.ENTITY);
        if(!cacheDir.exists())
            return null;
        File cacheFile = new File(cacheDir, TextUtils.isEmpty(interfaceUrl) ?  fileName : interfaceUrl.replaceAll(":", "%colon%").replaceAll("/", "%slash%")+"_"+ fileName);
        if(cacheDir.exists())
            return McJson.readFileToBean(entityClazz, cacheFile);
        return null;
    }

    /**
     * 更新本地缓存的Bean
     */
    public static void updateLocalEntity(String fileName, String interfaceUrl ,Object obj) {
        File cacheDir = getCacheDir(SaveType.ENTITY);
        if (obj == null || !cacheDir.exists())
            return;
        File cacheFile = new File(cacheDir, TextUtils.isEmpty(interfaceUrl) ?  fileName : interfaceUrl.replaceAll(":", "%colon%").replaceAll("/", "%slash%")+"_"+ fileName);
        if (cacheFile.exists())
            McJson.beanToFile(obj, cacheFile);
        return;
    }

    /**
     * 保存本地缓存的Bean
     */
    public static boolean saveEntity(String fileName, String interfaceUrl ,Object obj){
        File cacheDir = getCacheDir(SaveType.ENTITY);
        if (obj == null || !cacheDir.exists())
            return false;
        File cacheFile = new File(cacheDir, TextUtils.isEmpty(interfaceUrl) ?  fileName : interfaceUrl.replaceAll(":", "%colon%").replaceAll("/", "%slash%")+"_"+ fileName);
        if(cacheFile.exists())
            return McJson.beanToFile(obj, cacheFile);
        return false;
    }

    /**
     * 保存文件到本地
     */
    public static boolean saveFileToLocal(String fileName,String interfaceUrl, File file){
        File cacheDir = getCacheDir(SaveType.FILE);
        if(!cacheDir.exists())
            return false;
        File localFile = new File(cacheDir, TextUtils.isEmpty(interfaceUrl) ?  fileName : interfaceUrl.replaceAll(":", "%colon%").replaceAll("/", "%slash%")+"_"+ fileName);
        if(file != null && file.exists() && localFile != null && localFile.exists())
            try {
                copyFile(file, localFile);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return false;
    }

    /**
     * 获取本地文件
     * @param fileName
     * @param interfaceUrl
     * @return
     */
    public static File getLocalFile(String fileName, String interfaceUrl){
        File cacheDir = getCacheDir(SaveType.FILE);
        if(!cacheDir.exists()) {
            return null;
        }
        File localFile = new File(cacheDir,TextUtils.isEmpty(interfaceUrl) ?  fileName : interfaceUrl.replaceAll(":", "%colon%").replaceAll("/", "%slash%")+"_"+ fileName );
        if(localFile.exists())
            return localFile;
        return null;
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    /**
     * 位图保存到本地
     * @param bitmap
     * @param uri
     */
    public static void saveBitmapToLocal(Bitmap bitmap, String uri){
        //获取缓存目录
        File cacheDir = SDCardUtils.getCacheDir(SaveType.IMAGE);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File cacheFile = new File(cacheDir, uri.replaceAll(":", "%colon%").replaceAll("/", "%slash%"));
        if (cacheFile.exists()) {
            LogUtils.i("缓存文件已经存在！");
            return;
        }
        try {
            if (uri.endsWith("jpg") || uri.endsWith("jpeg"))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
            else
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(cacheFile));
            System.out.println("存储缓存文件:" + cacheFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CacheUtils {
    private  static  CacheUtils cacheUtils;
    private CacheUtils(){}
    public static CacheUtils getInstance(){
        if (cacheUtils==null){
            cacheUtils = new CacheUtils();
        }
        return  cacheUtils;
    }

    ACache cache;
    public void init(Context context){
        if (cache==null){
            try {
                cache = ACache.get(context);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void putStringCache(Context context,String data,String key){
        init(context);
        cache.put(key,data);
    }
    public String getStringCache(Context context, String key){
        init(context);
        return cache.getAsString(key);
    }
    public void putObjectCache(Context context, Serializable data, String key){
        init(context);
        cache.put(key,data);
    }
    public Object getObjectCache(Context context, String key){
        init(context);
        return cache.getAsObject(key);
    }
    public void putBitmapCache(Context context, Bitmap data, String key){
        init(context);
        cache.put(key,data);
    }
    public Bitmap getBitmapCache(Context context,String key){
        init(context);
        return cache.getAsBitmap(key);
    }
    public void putDrawableCache(Context context, Drawable data, String key){
        init(context);
        cache.put(key,data);
    }
    public Drawable getDrawableCache(Context context, String key){
        init(context);
        return cache.getAsDrawable(key);
    }
    public boolean removeCache(Context context,String key){
        init(context);
        return  cache.remove(key);
    }

}

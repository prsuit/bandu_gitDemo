package me.bandu.talk.android.phone.utils;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import me.bandu.talk.android.phone.R;

public class ImageLoaderOption {

    public static DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_avatar)
                // 正在加载
                .showImageForEmptyUri(R.mipmap.default_avatar)
                // 空图片
                .showImageOnFail(R.mipmap.default_avatar)
                // 错误图片
                .cacheInMemory(true)
                // 缓存在内存中
                .cacheOnDisk(true)
                // 缓存在磁盘
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(5000))// 设置圆角,单ImageView必须在xml中配置长宽，否则不显示
                .imageScaleType(ImageScaleType.EXACTLY)// 图像缩放类型。
                .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)// 使用RGB_565会比使用默认的ARGB_8888少消耗2倍的内存
                .build();
    }

    public static DisplayImageOptions getHeadOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_avatar)
                // 正在加载
                .showImageForEmptyUri(R.mipmap.default_avatar)
                // 空图片
                .showImageOnFail(R.mipmap.default_avatar)
                // 错误图片
                .cacheInMemory(true)
                // 缓存在内存中
                .cacheOnDisk(true)
                // 缓存在磁盘
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(5000))// 设置圆角,单ImageView必须在xml中配置长宽，否则不显示
                .imageScaleType(ImageScaleType.EXACTLY)// 图像缩放类型。
                .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)// 使用RGB_565会比使用默认的ARGB_8888少消耗2倍的内存
                .build();
    }

    public static DisplayImageOptions getOptions1() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_book)
                // 正在加载
                .showImageForEmptyUri(R.drawable.default_book)
                // 空图片
                .showImageOnFail(R.drawable.default_book)

                // 错误图片
                .cacheInMemory(true)
                // 缓存在内存中
                .cacheOnDisk(true)
                // 缓存在磁盘
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 图像缩放类型。
                .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)// 使用RGB_565会比使用默认的ARGB_8888少消耗2倍的内存
                .build();
    }

    public static DisplayImageOptions getOptions2() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                // 缓存在内存中
                .cacheOnDisk(true)
                // 缓存在磁盘
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))// 设置圆角,单ImageView必须在xml中配置长宽，否则不显示
                .imageScaleType(ImageScaleType.EXACTLY)// 图像缩放类型。
                .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)// 使用RGB_565会比使用默认的ARGB_8888少消耗2倍的内存
                .build();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
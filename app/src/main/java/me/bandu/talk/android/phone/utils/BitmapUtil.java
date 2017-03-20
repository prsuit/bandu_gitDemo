package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：操作bitmap的工具类
 * 修改人：gaonan
 * 修改时间：
 * 修改备注：
 */
public class BitmapUtil {
    public static Bitmap drawableToBitamp(Drawable drawable)
         {
                int w = drawable.getIntrinsicWidth();
             int h = drawable.getIntrinsicHeight();
                Bitmap.Config config =
                               drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                               : Bitmap.Config.RGB_565;
                 Bitmap bitmap = Bitmap.createBitmap(w,h,config);
             //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
               Canvas canvas = new Canvas(bitmap);
               drawable.setBounds(0, 0, w, h);
                drawable.draw(canvas);
             return bitmap;
            }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        if (bitmap==null)
            return null;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Context context,Bitmap bgimage, double newWidth, double newHeight) {
        if (newHeight==0||newWidth==0||context==null){
            return null;
        }
        if (bgimage==null){
            bgimage = BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_avatar);
        }
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight, width / 2, height / 2);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }
}

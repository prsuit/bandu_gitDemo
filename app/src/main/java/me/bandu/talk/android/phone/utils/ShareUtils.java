package me.bandu.talk.android.phone.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.wxapi.BaseUiListener;

/**
 * 创建者:taoge
 * 时间：2016/1/6
 * 类描述：
 * 修改人:taoge
 * 修改时间：2016/1/6
 * 修改备注：
 */
public class ShareUtils {

    private static IWXAPI wxApi;

    /**
     * 分享信息到朋友
     * @param context
     * @param url  下载地址
     * @param code 班级编号
     */
    public static void share(Context context,String url,String name, String code,String uid1,String uid2,String quid_id){
        Intent intent = new Intent(Intent.ACTION_SEND);
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        String phone = UserUtil.getUerInfo(context).getPhone();
        intent.setComponent(componentName);
        //intent.setType("text/plain");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT,"班级名称:"+name+"\n"+"班级编号:"+code+"\n"+"手机号后4位:"+(phone!=null?phone.substring(7):"")+"\n"+"下载地址:"+url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setPackage("com.tencent.mm");
        context.startActivity(Intent.createChooser(intent, "微信分享"));
    }

    /**
     * 微信分享
     * 教师端
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public static void wechatShare(Context context,int flag,String name, String code){
        //实例化
        wxApi = WXAPIFactory.createWXAPI(context, ConstantValue.WX_APP_ID,true);
        wxApi.registerApp(ConstantValue.WX_APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        String phone = UserUtil.getUerInfo(context).getPhone();//获取手机号
        webpage.webpageUrl = ConstantValue.BASE_SHARE_URL+"share/download";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "我用“伴读”创建了一个班级，快加入吧!";
        //拼接字符串并截取手机号后4位
        msg.description = "班级名称:"+name+"\n"+"班级编号:"+code+"\n"+"手机号后4位:"+
                (phone!=null?phone.substring(7):"");
        //这里替换一张自己工程里的图片资源,注意图片不能大于32KB
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bandu_icon1);
        msg.thumbData = Utils.bmpToByteArray(thumb, true);
        //msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
    /**
     * 微博分享
     */
    public static void tweetShare(Activity context, IWeiboShareAPI mWeiboShareAPI,String uid1,String uid2,String quiz_id) {
        TextObject textObject = new TextObject();

        textObject.text = "伴读-玩转对话练口语.我的对话练习，快来听听吧~~~"+ConstantValue.BASE_SHARE_URL+"share/share?uid1="+uid1+"&uid2="+uid2+"&quiz_id="+quiz_id;
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        weiboMessage.textObject = textObject;
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(context,request);
    }
    /**
     * 微信分享
     * 学生端
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public static void wechatShare(Context context,int flag,String uid1,String uid2,String quiz_id){
        //实例化
        wxApi = WXAPIFactory.createWXAPI(context, ConstantValue.WX_APP_ID,true);
        wxApi.registerApp(ConstantValue.WX_APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        String phone = UserUtil.getUerInfo(context).getPhone();//获取手机号
        webpage.webpageUrl = ConstantValue.BASE_SHARE_URL+"share/share?uid1="+uid1+"&uid2="+uid2+"&quiz_id="+quiz_id;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "伴读-玩转对话练口语";
        //拼接字符串并截取手机号后4位
        msg.description = "我的对话练习，快来听听吧~~~";
        //这里替换一张自己工程里的图片资源,注意图片不能大于32KB
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bandu_icon1);
        int total = thumb.getWidth()*thumb.getHeight();
        if(total > 32768) {
            thumb = BitmapUtil.zoomImage(context,thumb,64,64);
        }
        msg.thumbData = Utils.bmpToByteArray(thumb, true);
        //msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
    /**
     * QQ分享
     */
    public static void qqShare(Activity context, Tencent mTencent,String uid1,String uid2,String quiz_id){
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "伴读-玩转对话练口语");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我的对话练习，快来听听吧~~~");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, ConstantValue.BASE_SHARE_URL+"share/share?uid1="+uid1+"&uid2="+uid2+"&quiz_id="+quiz_id);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://8.pic.pc6.com/thumb/up/2016-1/2016130103023320420_128_128.jpg");
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(context, params,new BaseUiListener());
    }
    /**
     * 分享QQ空间
     */
    public static void qqZoneShare(Activity context, Tencent mTencent,String uid1,String uid2,String quiz_id){
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "伴读-玩转对话练口语.我的对话练习，快来听听吧~~~");
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, ConstantValue.BASE_SHARE_URL+"share/share?uid1="+uid1+"&uid2="+uid2+"&quiz_id="+quiz_id);
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add("http://8.pic.pc6.com/thumb/up/2016-1/2016130103023320420_128_128.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        mTencent.shareToQzone(context, params, new BaseUiListener());
    }
}
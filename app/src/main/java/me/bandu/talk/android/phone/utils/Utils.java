package me.bandu.talk.android.phone.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.LoginBean;

/**
 * 创建者:taoge
 * 时间：2015/12/9
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/9
 * 修改备注：
 */
public class Utils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 秒转换分钟
     */
    public static String getTimeFromInt(int time) {
        if (time > 60) {
            int second = time % 60;
            int minute = time / 60;
            return minute+"分"+second +"秒";
        }else {
            return time+"秒";
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            //json.put("设备mac地址", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            //json.put("设备id", device_id);
            //json.put("手机型号", android.os.Build.MODEL);
            json.put("version", getAppVersionName(context));
            json.put("system", android.os.Build.VERSION.RELEASE);

            return json.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getVerCode(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        }
        catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        }
        catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 检查是否含有特殊字符
     *
     * @param s
     * @return true 不含有    false 含有
     */
    public static boolean isIllegal(String s) {
        String regEx = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return !m.find();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		特殊的：170号段
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }
    /**
     * 检查手机是否安装了某个app
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 验证邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" + "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static String getPingYing(String s) {
        //Map<String,String> map = new HashMap<>();
        if (s.startsWith("重庆")) {
            return "cq";
        } else if (s.startsWith("西藏")) {
            return "xz";
        }else if (s.startsWith("长春")) {
            return "cc";
        }else if (s.startsWith("长寿")) {
            return "cs";
        } else if (s.startsWith("长治")) {
            return "cz";
        }else if (s.startsWith("长沙")) {
            return "cs";
        } else {
            return AlphaUtil.getPinYinHeadChar(s);
        }
    }

    public static LoginBean.DataEntity getUserInfo(Context context){
        if (GlobalParams.userInfo == null) {
            GlobalParams.userInfo =  SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class,
                    new File(context.getDir("user",context.MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
            PreferencesUtils.saveUserInfo(GlobalParams.userInfo);
        }
        return GlobalParams.userInfo;
    }

 /*   public static void showDialog(final Context context) {
        Dialog dialog = new AlertDialog.Builder(context).setIcon(R.mipmap.bandu_icon).
                setTitle("更新提示").setMessage(updateMessage()).setPositiveButton("后台更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                UIUtils.showToastSafe("开始下载");
                //下载应用
                new ApkDownLoad(context.getApplicationContext(), GlobalParams.versionInfo.
                        getUrl(), "伴读", "版本升级").execute();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                arg0.cancel();
            }
        }).create();
        dialog.show();
    }*/

    private static String updateMessage() {

        StringBuilder sb = new StringBuilder();

        sb.append("1. 优化秒机装置，提高使用体验；");
        sb.append("\n");
        sb.append("2. 耗电进行优化，使用电量降低；");
        sb.append("\n");
        sb.append("3. 解决用户反馈，优化性能及界面细节；");

        return sb.toString();
    }


    public static void showCommonDialog(final Context context, String title, String msg, String leftButton) {
        Dialog dialog = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(title)
                        .setMessage(msg)
                        .setPositiveButton(leftButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SharedPreferences sp= context.getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean("isShow",false);
                                //提交当前数据
                                editor.commit();
                                arg0.cancel();
                            }
                        }).create();
        try {
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


 /*   public static void showDialog1(Context context){
        View layout = LayoutInflater.from(context).inflate(R.layout.exit_dialog,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog.setView(layout);
        //透明
*//*        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        window.setAttributes(lp);*//*
        dialog.show();

    }
*/

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

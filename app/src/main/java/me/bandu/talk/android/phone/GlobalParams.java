package me.bandu.talk.android.phone;

import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.VersionBean;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 09:42
 * 类描述：全局变量类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GlobalParams {

    public static String sue;
    public static String sup;
    public static int SHARE_TYPE = -1;
   // public static int role;
   // public static int uid;
   // public static String userName;
   // public static String school; //学校名称
    public static int sid; //学校id
   // public static String avatar;

    public static boolean checkDownloadTask = false;
    public static boolean exerciseDatabaseChange = false;
    public static boolean taskChange = true;
    public static boolean exerciseProgressChange = true;
    public static boolean imgflag = false;
    public static LoginBean.DataEntity userInfo;
    public static VersionBean.DataEntity versionInfo;
    public static boolean uid;

    public static boolean HOME_CHANGED = false;
    public static int WORK_CHANGED = -1;//小红点
    public static boolean ADD_CLASS = false;//加入班级
    public static boolean ADD_EXERCISE = false;
    public static boolean IS_CHECKED = false;//作业是否检查
    public static boolean isShow = false; //是否有未读消息
    // TODO: 2016/1/5 正式上线时候,将true 改成false;
    public static boolean debug = false;
    public static boolean isUpdateSort = true;
    public static long classID = 3931L;
}

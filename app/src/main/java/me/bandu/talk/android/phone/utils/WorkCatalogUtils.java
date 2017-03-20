package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.SshhjrJSONUtil;
import com.DFHT.utils.UIUtils;
import com.alibaba.fastjson.JSON;
import com.umeng.common.message.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.dao.Quiz;
import me.bandu.talk.android.phone.dao.Sentence;

/**
 * 创建者：高楠
 * 时间：on 2016/4/14
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCatalogUtils {
    /**
     * 单例
     */
    static WorkCatalogUtils workCatalogUtils;

    private WorkCatalogUtils() {
    }

    public static WorkCatalogUtils getInstance() {
        if (workCatalogUtils == null) {
            workCatalogUtils = new WorkCatalogUtils();
        }
        return workCatalogUtils;
    }


    /**
     * 回调的接口
     */
    public interface WorkCatalogLisenter {
        void zipEnd(boolean flag, File file);
    }

    private WorkCatalogLisenter lisenter;

    public void setLisenter(WorkCatalogLisenter lisenter) {
        this.lisenter = lisenter;

    }

    /********************************
     * 压缩相关
     *****************************************************/
    public int getHomeWorkScore(final Context context, final List<Quiz> quizList) {
        return DaoUtils.getInstance().getHomeWorkScore(quizList);
    }
    /********************************压缩相关*****************************************************/
    /**
     * 压缩结果
     */
    public void zipResult(final boolean flag, final File file) {
        if (lisenter != null) {
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    lisenter.zipEnd(flag, file);
                }
            });
        }
    }

    /**
     * 压缩文件
     *
     * @param upload_data
     * @param zipname
     */
    public void uploadFileToZip(final List<Sentence> upload_data, final String zipname) {
        UIUtils.startTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                if (upload_data == null || upload_data.size() == 0 || zipname == null || zipname.equals("")) {
                    zipResult(false, null);
                }
                File zipfile = new File(zipname);
                List<File> files = new ArrayList<>();
                for (int i = 0; i < upload_data.size(); i++) {
                    Sentence sentence = upload_data.get(i);
                    files.add(new File(sentence.getStu_mp3()));
                }
                try {
                    Ziputils.zipFiles(files, zipfile);
                    zipResult(true, zipfile);
                } catch (IOException e) {
                    e.printStackTrace();
                    zipResult(false, null);
                }
            }
        });
    }
    /**************************************************************/
    /**
     * 上传相关
     */
    /****************************
     * 获取数据
     **************************************/
    //uid（学号），stu_job_id（学生作业编号）,hw_quiz_id（作业题编号）
    public Map<String, Object> getDataMap(String uid, String stu_job_id, List<Sentence> sentences, String spend_time, int percent) {
        Map<String, Object> datamap = new HashMap();
        datamap.put("uid", uid);
        datamap.put("spend_time", spend_time);
        datamap.put("percent", percent);
        datamap.put("stu_job_id", stu_job_id);
        datamap.put("format", "mp3");
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            Map<String, Object> map = new HashMap();
            Sentence sentence = sentences.get(i);
            map.put("hw_quiz_id", sentence.getHw_quiz_id());
            map.put("sentence_id", sentence.getSentence_id() + "");
            map.put("mp3", TextUtils.isEmpty(sentence.getMp3_url()) ? "" : sentence.getMp3_url());//3.3
            map.put("score", sentence.getStu_score());
            try {
//                map.put("words_score",new JSONArray(sentence.getWords_score()));
                LogUtils.i("sentence.getWords_score() === " + sentence.getWords_score());
                map.put("words_score", sentence.getWords_score());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //String mp3 = sentence.getStu_mp3();
            // map.put("mp3",mp3.substring(mp3.lastIndexOf("/")+1)); //3.3
            map.put("seconds", sentence.getStu_seconds());
            list.add(map);
        }
        datamap.put("sentences", list);
        return datamap;
    }

    public void finishWork(List<Sentence> sentences) {
        if (sentences == null) {
            return;
        }
        for (int i = 0; i < sentences.size(); i++) {
            Sentence sentence = sentences.get(i);
            sentence.setStu_done(false);
            DaoUtils.getInstance().updataSentence(sentence);
        }
    }

    /**
     * 获取耗时计算结果
     *
     * @param s
     * @return
     */
    public static String getSpeendTimeStr(String s) {
        try {
            if (s.contains(".")) {
                s = s.substring(0, s.indexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s != null) {
            long time = Integer.parseInt(s);
            long second = (time) % 60;
            long minut = (time / 60) % 60;
            long hour = (time / 3600) % 24;
            long day = time / 3600 / 24;
            return (day == 0 ? "" : day + "天") +
                    (hour == 0 ? "" : hour + "小时") +
                    (minut == 0 ? "" : minut + "分") +
                    (second == 0 ? "" : second + "秒")
                    ;
        } else {
            return "0";
        }
    }

    /**
     * 3.3新加需求需要
     */
    public static String getSpeendTime(String s) {
        try {
            if (s.contains(".")) {
                s = s.substring(0, s.indexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s != null) {
            long time = Integer.parseInt(s);
            long second = (time) % 60;
            long minut = (time / 60) % 60;
            long hour = (time / 3600) % 24;
            long day = time / 3600 / 24;
            if(day > 0) {
                return day + "天" + (hour == 0 ? "0时" : hour + "时");
            }
            if (hour > 0) {
                return hour + "时" + (minut == 0 ? "0分" : minut + "分");
            }
            if(minut > 0){
                return minut + "分" + (second == 0 ? "0秒" : second + "秒");
            }else {
                return second + "秒";
            }
        } else {
            return "0";
        }
    }

    /**
     * 设置View是否可点击
     *
     * @param view
     * @param flag
     */
    public static void setViewClickable(View view, boolean flag) {
//        try {
//            view.setClickable(flag);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtils.e("View出问题了 view:" + view);
//        }
    }

    /**
     * 获取作业要求的文本
     *
     * @param level
     * @return
     */
    public static String getLevel(int level) {
        String l = "";
        switch (level) {
            case 0:
                l = "无要求";
                break;
            case 1:
                l = "及格";
                break;
            case 2:
                l = "良好";
                break;
            case 3:
                l = "优秀";
                break;
        }
        return l;
    }

    public static int getScoreImage(int score) {
        if (score < 55) {
            return R.mipmap.score_c;
        } else if (score < 85) {
            return R.mipmap.score_b;
        } else {
            return R.mipmap.score_a;
        }
    }

    public void deleteZip() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(MediaPathUtils.DOWNLOAD_DIR);
                File[] files = file.listFiles();
                for (File f : files) {
                    try {
                        if (f.isFile() && f.getAbsolutePath().contains(".zip")) {
                            f.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e("压缩文件删除失败");
                    }
                }
            }
        }).start();
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void refreshUserInfo(LoginBean.DataEntity uerInfo,Context context) {
        //存用户信息
        LoginBean.DataEntity bean = new LoginBean.DataEntity();
        bean.setUid(uerInfo.getUid());
        bean.setPassword(uerInfo.getPassword());
        bean.setRole(uerInfo.getRole());
        bean.setName(uerInfo.getName());
        bean.setSchool(uerInfo.getSchool());
        bean.setAvatar(uerInfo.getAvatar());
        bean.setBindcode(uerInfo.getBindcode());
        bean.setPhone(uerInfo.getPhone());
        bean.setIs_first_login(uerInfo.is_first_login());
        bean.setState(uerInfo.getState());
        bean.setClass_name(uerInfo.getClass_name());
        bean.setCid(uerInfo.getCid());
        UserUtil.saveUserInfo(context,bean);
    }

    public static String reSetTime(String cdate) {
        String[] strs = cdate.split("-");
        return strs[1] + "/" + strs[2];
    }
}

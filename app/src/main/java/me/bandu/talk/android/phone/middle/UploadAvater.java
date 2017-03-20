package me.bandu.talk.android.phone.middle;

import com.DFHT.net.NetworkUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.dao.Sentence;
import me.bandu.talk.android.phone.utils.HttpUtilsJames;
import me.bandu.talk.android.phone.utils.UploadUtil;

/**
 * 创建者:taoge
 * 时间：2015/12/15
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/15
 * 修改备注：
 */
public class UploadAvater {

    public static JSONObject upload(String filePath) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(new BasicNameValuePair("avatar", filePath));
        Map<String,String> param = new HashMap<>();
        param.put("uid", GlobalParams.userInfo.getUid()); //此处参数自己添加
        pairList.add(new BasicNameValuePair("data", NetworkUtil.mapToDESStr(param)));
        JSONObject jsonObject = HttpUtilsJames.postFile(ConstantValue.MODIFY_AVATAR, pairList);
        return jsonObject;
    }
    public static JSONObject uploadWork(String uid, Sentence sentence, String filePath) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(new BasicNameValuePair("audio", filePath));
        Map param = new HashMap();
        param.put("uid", uid);
        param.put("stu_job_id", sentence.getStu_job_id()+"");
        param.put("hw_quiz_id", sentence.getHw_quiz_id()+"");
        param.put("sentence_id", sentence.getSentence_id()+"");
        param.put("score", sentence.getStu_score());
//        int time = New_VoiceUtils.getVoiceLength(filePath);
//        if (time<1000&&time>0){
//            time = 1000;
//        }
        param.put("seconds", sentence.getStu_seconds());
        try {
            param.put("words_score", new JSONArray(sentence.getWords_score()));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        param.put("format", "wav");
        pairList.add(new BasicNameValuePair("data", NetworkUtil.mapToDESStr(param, "gaonan")));
        JSONObject jsonObject = HttpUtilsJames.postFile(ConstantValue.UP_LOAD, pairList);
        return jsonObject;
    }

    /**
     * 上传头像
     * @param filePath 头像路径
     */
    public static void uploadFile(String filePath){
        Map<String,String> param = new HashMap<>();
        param.put("uid", GlobalParams.userInfo.getUid());
        Map<String,String> param1 = new HashMap<>();
        param1.put("data", NetworkUtil.mapToDESStr(param));
        UploadUtil.getInstance().uploadFile(filePath,"avatar",ConstantValue.MODIFY_AVATAR,param1);
    }
}

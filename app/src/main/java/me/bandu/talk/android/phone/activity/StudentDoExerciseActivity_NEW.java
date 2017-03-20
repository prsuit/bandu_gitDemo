package me.bandu.talk.android.phone.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.base.BaseBean;
import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.NetworkUtil;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.ChivoxConstants;
import com.chivox.aimenu.EnginType;
import com.chivox.bean.AIError;
import com.chivox.utils.ChivoxCreateUtil;
import com.xs.SingEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.StudentDoExerciseAdapter_NEW;
import me.bandu.talk.android.phone.bean.GetKeyBean;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.MyRecordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.db.mdao.MPartDao;
import me.bandu.talk.android.phone.middle.ErrorMiddle;
import me.bandu.talk.android.phone.middle.RecordUpdateMiddle;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.middle.YouDaoMiddle;
import me.bandu.talk.android.phone.recevice.FinishRecevice;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.TimeUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.LoudView;
import me.bandu.talk.android.phone.voiceengine.VoiceEngCreateUtils;
import me.bandu.talk.android.phone.voiceengine.VoiceEngineUtils;

import static me.bandu.talk.android.phone.utils.FileUtil.getExerciseCurrentPath;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：做练习
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentDoExerciseActivity_NEW extends BaseStudentActivity implements View.OnClickListener,EngOkCallBack, SingEngine.ResultListener {
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    @Bind(R.id.title_middle)
    TextView title_middle;
    @Bind(R.id.title_right)
    TextView title_right;
    @Bind(R.id.image_play_icon)
    ImageView imageicon;//玩一玩的图标
    @Bind(R.id.lv_exericises)
    ListView lv_exericises;
    /*@Bind(R.id.rl_lesson)
    RelativeLayout rl_lesson;
    @Bind(R.id.rl_part)
    RelativeLayout rl_part;
    @Bind(R.id.tv_lessonname)
    TextView tv_lessonname;
    @Bind(R.id.tv_partname)
    TextView tv_partname;*/
    @Bind(R.id.ll_exercise_start)
    LinearLayout ll_exercise_start;
    @Bind(R.id.rl_exercise_recordbg)
    RelativeLayout rl_exercise_recordbg;
    @Bind(R.id.tv_exercise_content)
    TextView tv_exercise_content;
    @Bind(R.id.lv_exercise_loudView)
    LoudView lv_exercise_loudView;
    /*@Bind(R.id.btn_title_left_image)
    ImageView btn_title_left_image;
    @Bind(R.id.btn_title_left_tv)
    TextView btn_title_left_tv;
    @Bind(R.id.ll_select)
    LinearLayout ll_select;*/

    private final int WITCH_LESSON = 2;
    private final int WITCH_PART = 3;
    private long unitid;
    private List<LessonBean> lessons;
    private List<PartBean> parts;
    private List<CentenceBean> centences;
    private int curLessonP,curPartP;
    private StudentDoExerciseAdapter_NEW adapter;
    //private PopupWindow pupLesson,pupPart;
    private boolean showLesson,showPart,isTouch;
    private VoiceEngineUtils util;
    private int state,allsum,allsource;
    private boolean lisFirst;
    private int position;
    private View pupView;
    private String clickstr = "";
    //private Drawable db_up,db_down;

    private long partid;
    private PartBean partBean;
    private MyRecordBean myRecordBean;

    public static StudentDoExerciseActivity_NEW thiz;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_do_exercise;
    }
    private FinishRecevice recevice;
    private void registReceiver() {
        recevice = new FinishRecevice(new FinishRecevice.DoSomething() {
            @Override
            public void doSomething() {
                StudentDoExerciseActivity_NEW.this.finish();
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(FinishRecevice.FINISH_ACTION);
        registerReceiver(recevice, filter);
    }

    @Override
    protected void toStart() {
        thiz = this;
        initView();
        setData();
        showMyprogressDialog();
        /*  初始化sk引擎
        if(TextUtils.isEmpty(ChivoxConstants.secretKey)){
            getKey();
        }else {
            createChivox();
        }
        */
        createChivox();
        //初始化先声引擎
        VoiceEngCreateUtils.createXSEngine(this,this);
        //注册不保存返回练习本的广播
        registReceiver();
    }

    public void getKey(){
        Map map = new HashMap();
        map.put("pkg", getPackageName());
        EasyNetParam param = new EasyNetParam(ConstantValue.GET_KEY, map, new GetKeyBean());
        new EasyNetAsyncTask(-52, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                hideMyprogressDialog();
                if(requestCode == -52){
                    GetKeyBean bean = (GetKeyBean) result;
                    ChivoxConstants.secretKey = bean.getData();
                    Log.e("tag", "success-->: " + ChivoxConstants.secretKey);
                    createChivox();
                }

            }
            @Override
            public void failed(int requestCode) {
                hideMyprogressDialog();
                UIUtils.showToastSafe("请检查网络是否畅通");
            }
        }).execute(param);
    }

    private void createChivox(){
        //驰骋初始化 如果失败则结束此页面
//        ChivoxCreateUtil.createEnginAndAIRecorder();
        VoiceEngCreateUtils.createEnginAndAIRecorder(new OnSpeechEngineLoaded() {
            @Override
            public void onLoadSuccess(int state) {
                hideMyprogressDialog();
                StudentDoExerciseActivity_NEW.this.state = state;
                setListeners();
            }

            @Override
            public void onLoadError() {
                hideMyprogressDialog();
                finish();
            }
        });
    }

    @Override
    public void initView() {
        title_middle.setText("练习页");
        title_right.setVisibility(View.VISIBLE);
        if(UserUtil.isLogin()){
            title_right.setText("玩一玩");
            imageicon.setVisibility(View.VISIBLE);
        }else{
            title_right.setText("登录");
        }


        /*db_down = getResources().getDrawable(R.mipmap.down);
        db_up = getResources().getDrawable(R.mipmap.up);
        db_down.setBounds(0, 0, db_down.getMinimumWidth(), db_down.getMinimumHeight());
        db_up.setBounds(0, 0, db_up.getMinimumWidth(), db_up.getMinimumHeight());
        tv_partname.setCompoundDrawables(null,null,db_down,null);
        tv_lessonname.setCompoundDrawables(null,null,db_down,null);*/
    }

    @Override
    public void setData() {
        //获取上个界面传过来的值
        long lessonid = getIntent().getLongExtra("lessonid",-1);
        unitid = getIntent().getLongExtra("unitid",-1);
        partid = getIntent().getLongExtra("partid",-1);
        long perETime = UserUtil.getExerciseTime(this);
        long currETime = System.currentTimeMillis();
        setBaseData(perETime,currETime);
        setLessons();
        setCurrentLesson(lessonid);
        //setLessonName();
        setParts();
        setCurrentPart(partid);
        //setPartName();
        setCentences();
        //showSelect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPosition(0);
        if (null != adapter) {
            adapter.setPreReadposition(-1);
        }
    }

    public void setSelection(int index) {
        if (null != lv_exericises && adapter.getCount() > 0) {
            lv_exericises.setSelection(index);
        }
    }

    //添加选择栏显示动画
    /*private void showSelect() {
        ll_select.setAnimation(AnimationUtil.getYShowTranslateAnimation(500));
        ll_select.setVisibility(View.VISIBLE);
    }*/

    //设置一些基本数据
    private void setBaseData(long perETime, long currETime) {
        if (!TimeUtil.isSameDayOfMillis(perETime,currETime)){
            int day = UserUtil.getExerciseDay(this);
            day++;
            UserUtil.saveExerciseTime(this,day);
            UserUtil.saveExerciseTime(this,currETime);
            GlobalParams.exerciseProgressChange = true;
        }

        lessons = new ArrayList<>();
        parts = new ArrayList<>();
        centences = new ArrayList<>();
        adapter = new StudentDoExerciseAdapter_NEW(this, centences,this,this);
        lv_exericises.setAdapter(adapter);
    }


    private void setCurrentPart(long partid) {
        for (int i = 0;i<parts.size();i++){
            if (parts.get(i).getPart_id() == partid){
                curPartP = i;
                return;
            }
        }
    }

    private void setCurrentLesson(long lessonid) {
        for (int i = 0;i<lessons.size();i++){
            if (lessons.get(i).getLesson_id() == lessonid){
                curLessonP = i;
                return;
            }
        }
    }

    private CentenceBean getCurrentCentence(){
        if (position < centences.size())
            return centences.get(position);
        else
            return null;
    }

    private void setCentences() {
        if (parts.size() > curPartP){
            MCentenceDao centenceDao = new MCentenceDao(this);
            partBean = parts.get(curPartP);
            centences.removeAll(centences);
            centences.addAll(centenceDao.getCentencesByPartId(partBean.getPart_id()));
            adapter.setPosition(0);
            lv_exericises.smoothScrollToPosition(0);
            CentenceBean centenceBean = getCurrentCentence();
        }

    }


    private void setParts() {
        if (lessons.size() > curLessonP){
            MPartDao partDao = new MPartDao(this);
            LessonBean lessonBean = lessons.get(curLessonP);
            parts.removeAll(parts);
            parts.addAll(partDao.getPartsByLessonid(lessonBean.getLesson_id()));
        }
    }

    /*private PartBean getCurrentPart(){
        if (parts.size() > curPartP){
            return parts.get(curPartP);
        }
        return null;
    }

    private void setPartName() {
        if (parts.size() > curPartP){
            tv_partname.setText(parts.get(curPartP).getPart_name());
        }
    }*/

    private void setLessons() {
        if (unitid != -1){
            MLessonDao lessonDao = new MLessonDao(this);
            lessons.removeAll(lessons);
            lessons.addAll(lessonDao.getLessonsByUnitid(unitid));
        }
    }

   /* private void setLessonName() {
        if (lessons.size() > curLessonP){
            tv_lessonname.setText(lessons.get(curLessonP).getLesson_name());
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        New_VoiceUtils.getInstance().pauseVoice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        New_VoiceUtils.getInstance().stopVoice();
        lv_exercise_loudView.onDestory();
        if(recevice != null)
            unregisterReceiver(recevice);
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
        //rl_lesson.setOnClickListener(this);
        //rl_part.setOnClickListener(this);
        title_right.setOnClickListener(this);
        ll_exercise_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        if (isTouch)
//                            return true;
//                        UIUtils.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                New_VoiceUtils.getInstance().pauseVoice();
//                            }
//                        },100);
//                        isTouch = true;
//                        downRelativeLayout(getCurrentCentence().getEnglish());
//                        if (util == null)
//                            util = new ChivoxUtil(StudentDoExerciseActivity_NEW.this,UserUtil.getUid());
//
//                        setEventDown();
                        if (isTouch)
                            return true;
                        // 引入布尔变量防止过快点击导致录音时还在播放原音频文件
                        New_VoiceUtils.getInstance().setIsClickForEx(true);
                        New_VoiceUtils.getInstance().pauseVoice();

                        isTouch = true;
                        downRelativeLayout(getCurrentCentence().getEnglish());
                        if (util == null)
                            util = new VoiceEngineUtils(StudentDoExerciseActivity_NEW.this,UserUtil.getUid());
                        if(NetworkUtil.checkNetwork(StudentDoExerciseActivity_NEW.this))
                            setEventDown();
                        else
                            UIUtils.showToastSafe("网络异常，请检查网络");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        // 引入布尔变量防止过快点击导致录音时还在播放原音频文件
                        New_VoiceUtils.getInstance().setIsClickForEx(false);
                        isTouch = false;
//                        if (!isTouch)
//                            return true;

                        //停止sk的引擎
                        if (util != null)
                            util.stop();

                        VoiceEngCreateUtils.xsEngine.stop();//停止先声引擎
                        upRelativeLayout();
                        break;
                }
                return true;
            }
        });
    }

    //执行按下事件
    private void setEventDown() {
        CentenceBean bean = getCurrentCentence();
        EnginType type = null;
        switch (state){
            case OnSpeechEngineLoaded.ONLINE_OK:
            case OnSpeechEngineLoaded.ALL_OK:
                type = EnginType.TYPE_ONLINE;
                break;
            case OnSpeechEngineLoaded.OFFLINE_OK:
                type = EnginType.TYPE_OFFLINE;
                break;
        }
        String path = getExerciseCurrentPath(this,unitid) + bean.getCentence_id() + ".mp3";
        Log.i("FUCK", "setEventDown()-->path:"+path);
        Map<String,Object> map = new HashMap<>();
        map.put("centence",bean);
        adapter.cleanStatementResult();
        File file = new File(path);
        if (file.exists())
            file.delete();
        util.start(bean.getEnglish(),path,bean.getCentence_id() + "",position,map,this);//调用sk评分
        //调用先声
       // VoiceEngineUtils.start_XS(bean.getEnglish(),UserUtil.getUid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
            /*case R.id.rl_lesson:
                showLesson = !showLesson;
                showOrhideLessonPopupwindow();
                break;
            case R.id.rl_part:
                showPart = !showPart;
                //createPartPopupwindow();
                showOrhidePartPopupwindow();
                break;*/
            case R.id.title_right:
                //在跳转之前要做判断，如果练习页的每一个item都有录音，那么把录音的传上去
                //如果没有录音就提示去练习，不能跳转到玩一玩
                title_right.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        title_right.setClickable(true);
                    }
                }, 3000);

                if(UserUtil.isLogin()){
                    boolean isFinish = true;
                    for(int i = 0; i < centences.size(); i++) {
                        if (centences.get(i).getDone() == null || !centences.get(i).getDone()) {
                            isFinish = false;
                        }
                    }
                    if (isFinish) {
                        int sum = 0;
                        List<Map> list = new ArrayList<>();
                        for (int i = 0 ; i < centences.size() ; i ++){
                            sum += centences.get(i).getSorce_current();
                            Map<String,Object> map = new HashMap();
                            CentenceBean sentence = centences.get(i);
                            map.put("sentence_id",sentence.getCentence_id()+"");
                            map.put("mp3", TextUtils.isEmpty(sentence.getMp3_url())? "": sentence.getMp3_url());//3.3
                            map.put("score",sentence.getSorce_current());
                            map.put("duration",sentence.getSeconds());
                            list.add(map);
                        }
                         // 全部录音完成后才上传录音 ，，上传成功后跳转玩一玩
                        new RecordUpdateMiddle(this, this).RecordUpdate(UserUtil.getUid(),
                                partBean.getPart_id() + "", sum / centences.size() + "", list);

                    } else {
                        // 用dialog给出提示
                        showDialog();
                    }
                } else {
                    //立即使用进来的
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();

                }

                break;
            case R.id.ll_main:
                setCurrentCentence(v);
                break;
        }
    }
    public void showDialog(){
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_show,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
//        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog1.dismiss();
//            }
//        });
        dialog1.show();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        final BaseBean basebean = (BaseBean) result;
        if (basebean != null && basebean.getStatus() == 1) {
            //这是我自己封装的自己录音的bean对象
            myRecordBean = new MyRecordBean();
            MyRecordBean banRecordBean = new MyRecordBean();

            MyRecordBean.DataBean dataBean = new MyRecordBean.DataBean();
            MyRecordBean.DataBean bandataBean = new MyRecordBean.DataBean();

            List<MyRecordBean.DataBean.SentencesBean> list = new ArrayList<>();
            List<MyRecordBean.DataBean.SentencesBean> banList = new ArrayList<>();
            if (centences.size() != 0){
                for (int i = 0; i < centences.size(); i++) {
                    MyRecordBean.DataBean.SentencesBean bean = new MyRecordBean.DataBean.SentencesBean();
                    MyRecordBean.DataBean.SentencesBean banBean = new MyRecordBean.DataBean.SentencesBean();

                    bean.setSentence_id((centences.get(i).getCentence_id()));
                    banBean.setSentence_id((centences.get(i).getCentence_id()));

                    bean.setMp3(centences.get(i).getMp3_url());
                    banBean.setMp3(centences.get(i).getUrl_exemple());
                    Integer seconds = centences.get(i).getSeconds();
                    bean.setSeconds(seconds == null ? 0 : seconds);
                    banBean.setSeconds(seconds == null ? 0 : seconds);

                    bean.setEn(centences.get(i).getEnglish());
                    banBean.setEn(centences.get(i).getEnglish());

                    bean.setHead(UserUtil.getUerInfo(this).getAvatar());
                    banBean.setHead("banHead");

                    list.add(bean);
                    banList.add(banBean);
                }
            }


            dataBean.setSentences(list);
            bandataBean.setSentences(banList);

            myRecordBean.setData(dataBean);
            banRecordBean.setData(bandataBean);
            //上传成功的回调
            //录音上传成功后才可以跳转玩一玩界面
            Intent intent = new Intent(this, CombinaRecordActivity.class);
            intent.putExtra("uid", UserUtil.getUid());
            intent.putExtra("myRecord", myRecordBean);
            intent.putExtra("banRecord", banRecordBean);
            intent.putExtra("quiz_id", partBean.getPart_id());
            startActivity(intent);
        }

    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        UIUtils.showToastSafe("当前网络不稳定，请重新尝试");

    }
    /*private void showOrhidePartPopupwindow() {
        if (pupPart == null)
            pupPart = PopSelectUtil.createPartPopupwindow(this,parts,curPartP,WITCH_PART,this);

        if (showPart){
            pupPart.showAsDropDown(rl_lesson);
            tv_partname.setCompoundDrawables(null,null,db_up,null);
        }else {
            pupPart.dismiss();
        }

    }*/

    /*private void showOrhideLessonPopupwindow() {
        if (pupLesson == null)
            pupLesson = PopSelectUtil.createLessonPopupwindow(this,lessons,curLessonP,WITCH_LESSON,this);

        if (showLesson){
            pupLesson.showAsDropDown(rl_lesson);
            tv_lessonname.setCompoundDrawables(null,null,db_up,null);
        }else {
            pupLesson.dismiss();
        }
    }*/

    private void setCurrentCentence(View v) {
        int position = (int) v.getTag();
        this.position = position;
        adapter.setPosition(position);
    }

    private void setPosition(int index) {
        int position = index;
        this.position = position;
        adapter.setPosition(position);
    }

    // 对布局的操作
    private void downRelativeLayout(String content) {
        lv_exercise_loudView.startChange();
        ll_exercise_start.setBackgroundColor(getResources().getColor(R.color.transparent));
        rl_exercise_recordbg.setVisibility(View.VISIBLE);
        tv_exercise_content.setTextSize(30);
        tv_exercise_content.setText(content);
    }
    private void upRelativeLayout() {
//        showMyprogressDialog();
        lv_exercise_loudView.stopChange();
        ll_exercise_start.setBackgroundColor(getResources().getColor(R.color.gray_bg));
        rl_exercise_recordbg.setVisibility(View.GONE);
    }


    /*private void setPartPoupChange() {
        pupPart = PopSelectUtil.createPartPopupwindow(this,parts,curPartP,WITCH_PART,this);
    }*/

    /**
     *
     * @param score        总分数
     * @param path         录音文件的位置
     * @param sentenceID   句子ID
     * @param position     当前的位置
     * @param details      每个单词的得分
     * @param holderObj    持有的obj对象
     */
    //驰骋评分两个接口方法
    @Override
    public void getScore(final String score, final String path, String sentenceID, final int position, final List<AIRecorderDetails> details, final Map<String, Object> holderObj) {
        Log.i("FUCK", "执行回调getScore");
        Log.i("FUCK", "执行回调getScore-->"+score);
        Log.i("FUCK", "执行回调getScore-->path:"+path);
        isTouch = false;
        UIUtils.startTaskInThreadPool(new Runnable() {

            @Override
            public void run() {
                //网络的录音地址
                String mp3_url = (String) holderObj.get("mp3_url");
                Log.i("FUCK", "执行回调getScore-->mp3_url:"+mp3_url);
                int current = Integer.parseInt(score);
                allsum++;
                allsource += current;
                CentenceBean bean = null;
                if (centences.size() > position && position >= 0)
                    bean  = centences.get(position);
                else
                    return;
                bean.setMp3_url(mp3_url);
                bean.setUrl_current(path);
                bean.setDone(true);
                bean.setSorce_current(current);
                bean.setSeconds(New_VoiceUtils.getVoiceLength(path));
                Integer best = bean.getSorce_best();
                //  word:   sss    ,score:ssss
                JSONArray jsonArray = new JSONArray();
                for (int i = 0 ;i<details.size();i++){
                    JSONObject object = new JSONObject();
                    try {
                        object.put("word",details.get(i).getCharStr());
                        object.put("score",details.get(i).getScore());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                }
                bean.setDetails(jsonArray.toString());
                Log.e("FUCK", "details getScore -->"+jsonArray.toString());
                //TextColorUtils.changTextColor(bean.getEnglish(),jsonArray.toString());
                if (best == null)
                    best = 0;
                int code = 0;
                if (current > best){
                    bean.setSorce_best(current);
                    String besturl = FileUtil.getExerciseBestPath(StudentDoExerciseActivity_NEW.this,unitid) + bean.getCentence_id() + ".mp3";
                    code = FileUtil.copySdcardFile(path,besturl);
                    bean.setUrl_best(besturl);
                    //int time = (int) details.get(0).getDur();
                    //bean.setSeconds(time);
                }


                GlobalParams.exerciseDatabaseChange = true;
                final int finalCode = code;
                final int finalCode1 = code;
                final CentenceBean finalBean = bean;
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        MCentenceDao dao = new MCentenceDao(StudentDoExerciseActivity_NEW.this);
                        if (finalCode1 == 0) {
                            try{
                                dao.addData(finalBean);
                                adapter.notifyDataSetChanged();
                                hideMyprogressDialog();
                                if (finalCode != 0){
                                    Toast.makeText(StudentDoExerciseActivity_NEW.this, "保存数据失败", Toast.LENGTH_SHORT).show();
                                }
                                isTouch = false;
                            }catch (Exception e){
                                e.printStackTrace();
                                isTouch = false;
                                UIUtils.showToastSafe("保存数据失败");
                            }
                        }

                    }
                });
            }
        });

    }

    @Override
    public void faild(String content, String path, String sentenceID, int position, String msg, Map<String, Object> holderObj) {
        hideMyprogressDialog();
        isTouch = false;

        ErrorMiddle middle = new ErrorMiddle(this, this);
        String json = (String) holderObj.get("json");
        middle.sendError(sentenceID, msg, json);

        AIError error = JSONUtils.readValue(msg, AIError.class);

        if (ChivoxCreateUtil.onLineCreateOk) {
            ChivoxCreateUtil.notifyChanged();
            if (error != null)
                UIUtils.showToastSafe("评分失败,错误编号=" + error.getErrId() + "");
            else
                UIUtils.showToastSafe("评分失败,请重新尝试");
            return;
        }

        if (error != null)
            UIUtils.showToastSafe("评分失败,错误编号=" + error.getErrId() + "");
        else
            UIUtils.showToastSafe("评分系统故障,请尝试重新进入");
        ChivoxCreateUtil.deleteEngineAndRecorder();
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        if (WordsMiddle.GET_WORD == (int)obj[1]){
            final MWordBean wordBean = (MWordBean) obj[0];
            if(wordBean.getStatus() == 0){
                YouDaoMiddle youDaoMiddle = new YouDaoMiddle(this,this);
                youDaoMiddle.getWord(clickstr);
            }else {
                showWordPopupwindow(wordBean.getData());
            }
        }else if(WordsMiddle.CREATE_WORDS == (int)obj[1]){
            BaseBean baseBean = (BaseBean) obj[0];
            UIUtils.showToastSafe(baseBean.getMsg());
            if (pupView != null){
                Button btn = (Button) pupView.findViewById(R.id.btnAddWord);
                btn.setText("已添加");
                btn.setBackgroundResource(R.drawable.corners_gray);
                btn.setTextColor(ColorUtil.getResourceColor(StudentDoExerciseActivity_NEW.this,R.color.white));
                btn.setFocusable(false);
                btn.setClickable(false);
            }
        }else if(YouDaoMiddle.YOUDAO == (int)obj[1]){
            WordBean wordBean = (WordBean) obj[0];
            showWordPopupwindow(wordBean);
        }
    }

    private void showWordPopupwindow(WordBean data) {
        if (ScreenUtil.isForeground(this, getClass().getName())){
            pupView = PopWordUtils.getInstance().showWorkPop(this, findViewById(R.id.rlRoot), data, new PopWordUtils.PopWordViewOnClick() {
                @Override
                public void btnOnClick(WordBean word) {
                    clickstr = word.getQuery();
                    if (UserUtil.isLogin()){
                        WordsMiddle wordsMiddle = new WordsMiddle(StudentDoExerciseActivity_NEW.this,StudentDoExerciseActivity_NEW.this);
                        wordsMiddle.createWord(word.getQuery(),word);
                    }else {
                        UIUtils.showToastSafe("注册登录后可使用此功能");
                    }
                }

                @Override
                public void imgOnClick(WordBean word) {
                    New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio="+word.getQuery());
                }
            }, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        adapter.notifyDataSetChanged();
    }


    //先声的回调接口
    @Override
    public void onBegin() {
    }
    @Override
    public void onResult(JSONObject jsonObject) {
        Log.i("FUCK", "执行回调onResult-->");
        isTouch = false;
        Log.e("FUCK", "result->"+jsonObject.toString());
        CentenceBean beanCurrent = getCurrentCentence();
        String path = FileUtil.getExerciseCurrentPath(this,unitid) + beanCurrent.getCentence_id() + ".mp3";
        Log.e("FUCK", "onResult-->path-->"+path);
        JSONObject resultObject = null;
        try {
            resultObject = jsonObject.getJSONObject("result");
            String overall = resultObject.getString("overall");
            int score_curr = Integer.parseInt(overall);
            Log.e("FUCK", "onResult-->overall-->"+overall);

            JSONArray jsonArray = new JSONArray();
            JSONArray details = resultObject.getJSONArray("details");

            Log.e("FUCK", "onResult-->details-length---->" + details.length());

            String audioUrl = jsonObject.getString("audioUrl");
            Log.e("FUCK", "onResult-->audioUrl-->" + audioUrl);

            CentenceBean bean = null;
            if (centences.size() > position && position >= 0)
                bean  = centences.get(position);
            else
                return;
            bean.setMp3_url(audioUrl);
            bean.setUrl_current(path);
            bean.setDone(true);
            bean.setSorce_current(score_curr);
            bean.setSeconds(New_VoiceUtils.getVoiceLength(path));

            for (int i = 0; i < details.length(); i++) {
                JSONObject obj_detail = details.getJSONObject(i);
                JSONObject object = new JSONObject();
                object.put("word",obj_detail.get("char"));
                object.put("score",obj_detail.get("score"));
                jsonArray.put(object);
            }
            bean.setDetails(jsonArray.toString());
            Log.e("FUCK", "details onResult -->"+jsonArray.toString());

            Integer best = bean.getSorce_best();
            if (best == null)
                best = 0;
            int code = 0;
            if (score_curr > best){
                bean.setSorce_best(score_curr);
                String besturl = FileUtil.getExerciseBestPath(StudentDoExerciseActivity_NEW.this,unitid) + bean.getCentence_id() + ".mp3";
                code = FileUtil.copySdcardFile(path,besturl);
                bean.setUrl_best(besturl);
                //int time = (int) details.get(0).getDur();
                //bean.setSeconds(time);
            }

            GlobalParams.exerciseDatabaseChange = true;
            final int finalCode = code;
            final int finalCode1 = code;
            final CentenceBean finalBean = bean;
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    MCentenceDao dao = new MCentenceDao(StudentDoExerciseActivity_NEW.this);
                    if (finalCode1 == 0) {
                        try{
                            dao.addData(finalBean);
                            adapter.notifyDataSetChanged();
                            hideMyprogressDialog();
                            if (finalCode != 0){
                                Toast.makeText(StudentDoExerciseActivity_NEW.this, "保存数据失败", Toast.LENGTH_SHORT).show();
                            }
                            isTouch = false;
                        }catch (Exception e){
                            e.printStackTrace();
                            isTouch = false;
                            UIUtils.showToastSafe("保存数据失败");
                        }
                    }

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onEnd(final int i, final String s) {

    }

    @Override
    public void onUpdateVolume(final int i) {

    }
    @Override
    public void onFrontVadTimeOut() {

    }
    @Override
    public void onBackVadTimeOut() {

    }
    @Override
    public void onRecordingBuffer(byte[] bytes) {
    }

    @Override
    public void onPlayCompeleted() {
    }

    @Override
    public void onRecordLengthOut() {
    }

    @Override
    public void onReady() {
        setListeners();
    }

    /*
    @Override
    public void onWitchDissmiss(int witch) {
        if (witch == WITCH_LESSON){
            showLesson = false;
            tv_lessonname.setCompoundDrawables(null,null,db_down,null);
        }else if (witch == WITCH_PART){
            showPart = false;
            tv_partname.setCompoundDrawables(null,null,db_down,null);
        }
    }

    @Override
    public void onWitchCheckedChange(int witch, RadioGroup group, int checkedId) {
        if (witch == WITCH_LESSON){
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            int tag = (int) rb.getTag();
            if (tag == curLessonP){
            }else {
                curLessonP = tag;
                curPartP = 0;
                position = 0;
                setLessonName();
                setParts();
                setPartName();
                setPartPoupChange();
                setCentences();
            }
            pupLesson.dismiss();
        }else if (witch == WITCH_PART){
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            int tag = (int) rb.getTag();
            if (tag == curPartP){
            }else {
                curPartP = tag;
                //listViewBean.setPosition(0);
                position = 0;
                setCentences();
                setPartName();
            }
            pupPart.dismiss();
        }
    }*/
}

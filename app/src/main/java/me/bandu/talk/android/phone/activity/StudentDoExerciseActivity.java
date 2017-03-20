package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.chivox.ChivoxConstants;
import com.chivox.aimenu.EnginType;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.utils.ChivoxCreateUtil;
import com.chivox.utils.ChivoxUtil;

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
import me.bandu.talk.android.phone.adapter.StudentDoExerciseAdapter;
import me.bandu.talk.android.phone.bean.GetKeyBean;
import me.bandu.talk.android.phone.bean.ListViewBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.db.mdao.MPartDao;
import me.bandu.talk.android.phone.myenum.ExerciseEnum;
import me.bandu.talk.android.phone.myenum.ExerciseStateEnum;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.TimeUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.LoudView;
import me.bandu.talk.android.phone.voiceengine.VoiceEngCreateUtils;
import me.bandu.talk.android.phone.voiceengine.VoiceEngineUtils;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：做练习
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentDoExerciseActivity extends BaseStudentActivity implements View.OnClickListener,EngOkCallBack {
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    @Bind(R.id.title_middle)
    TextView title_middle;
    @Bind(R.id.title_right)
    TextView title_right;
    @Bind(R.id.lv_exericises)
    ListView lv_exericises;
    @Bind(R.id.rl_lesson)
    RelativeLayout rl_lesson;
    @Bind(R.id.rl_part)
    RelativeLayout rl_part;
    @Bind(R.id.tv_lessonname)
    TextView tv_lessonname;
    @Bind(R.id.tv_partname)
    TextView tv_partname;
    @Bind(R.id.ll_exercise_start)
    LinearLayout ll_exercise_start;
    @Bind(R.id.rl_exercise_recordbg)
    RelativeLayout rl_exercise_recordbg;
    @Bind(R.id.tv_exercise_content)
    TextView tv_exercise_content;
    @Bind(R.id.lv_exercise_loudView)
    LoudView lv_exercise_loudView;
    @Bind(R.id.btn_title_left_image)
    ImageView btn_title_left_image;
    @Bind(R.id.btn_title_left_tv)
    TextView btn_title_left_tv;

    long unitid;
    List<LessonBean> lessons;
    List<PartBean> parts;
    List<CentenceBean> centences;
    private int curLessonP,curPartP;
    //Integer currentCentence = 0;
    private ListViewBean listViewBean;
    private StudentDoExerciseAdapter adapter;
    private PopupWindow pupLesson,pupPart;
    private boolean showLesson,showPart,isTouch;
    private VoiceEngineUtils util;
    private int state,allsum,allsource;
    private ExerciseEnum[] exerciseEnum = new ExerciseEnum[1];
    private ExerciseStateEnum[] exerciseStateEna = new ExerciseStateEnum[1];
    private boolean lisFirst;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_do_exercise;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        showMyprogressDialog();
        if(TextUtils.isEmpty(ChivoxConstants.secretKey)){
            getKey();
        }else{
            createChivox();
        }

    }

    public void getKey(){
        Map map = new HashMap();
        map.put("pkg", getPackageName());
        EasyNetParam param = new EasyNetParam(ConstantValue.GET_KEY, map, new GetKeyBean());
        new EasyNetAsyncTask(-52, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                if(requestCode == -52){
                    GetKeyBean bean = (GetKeyBean) result;
                    ChivoxConstants.secretKey = bean.getData();
                    createChivox();
                }
            }
            @Override
            public void failed(int requestCode) {
                UIUtils.showToastSafe("请检查网络是否畅通");
            }
        }).execute(param);
    }

    private void createChivox(){

        VoiceEngCreateUtils.createEnginAndAIRecorder(new OnSpeechEngineLoaded() {
            @Override
            public void onLoadSuccess(int state) {
                hideMyprogressDialog();
                StudentDoExerciseActivity.this.state = state;
                setListeners();
            }

            @Override
            public void onLoadError() {
                hideMyprogressDialog();
                finish();
            }
        });

//        ChivoxCreateUtil.createEnginAndAIRecorder();
    }
    @Override
    public void initView() {
        title_middle.setText("练习页");
       /* if (!UserUtil.isLogin()){
            title_right.setText("登录");
        }else {
            title_right.setText("查看进度");
        }*/
        //btn_title_left_image.setVisibility(View.GONE);
        //btn_title_left_tv.setVisibility(View.VISIBLE);
        //btn_title_left_tv.setText("完成");
        //title_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData() {
        exerciseEnum[0] = ExerciseEnum.DOEXERCISE;
        exerciseStateEna[0] = ExerciseStateEnum.ENGLISEANDCHINESE;
        listViewBean = new ListViewBean();
        //state = getIntent().getIntExtra("state",OnSpeechEngineLoaded.OFFLINE_OK);
        long lessonid = getIntent().getLongExtra("lessonid",-1);
        //long centenceid = getIntent().getLongExtra("centenceid",-1);
        unitid = getIntent().getLongExtra("unitid",-1);
        long partid = getIntent().getLongExtra("partid",-1);
        long perETime = UserUtil.getExerciseTime(this);
        long currETime = System.currentTimeMillis();
        if (!TimeUtil.isSameDayOfMillis(perETime,currETime)){
            int day = UserUtil.getExerciseDay(this);
            day++;
            UserUtil.saveExerciseTime(this,day);
            UserUtil.saveExerciseTime(this,currETime);
        }

        lessons = new ArrayList<>();
        parts = new ArrayList<>();
        centences = new ArrayList<>();
        adapter = new StudentDoExerciseAdapter(this, centences, listViewBean, exerciseEnum, exerciseStateEna, null, new StudentDoExerciseAdapter.OnItemMainClickListener() {
            @Override
            public void onItemMainClickList(int position) {
                if(centences.size() > position){
                    listViewBean.setPosition(position);
                    lv_exericises.setSelection(listViewBean.getPosition());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onVedioStopClick() {

            }
        });
        lv_exericises.setAdapter(adapter);

        setLessons();
        setCurrentLesson(lessonid);
        setLessonName();
        setParts();
        setCurrentPart(partid);
        setPartName();
        setCentences();
        //setCurrentCentence(centenceid);
    }

    private void setCurrentCentence(long centenceid) {
        for (int i = 0;i<centences.size();i++){
            if (centences.get(i).getCentence_id() == centenceid){
                listViewBean.setPosition(i);
                lv_exericises.setSelection(listViewBean.getPosition());
                adapter.notifyDataSetChanged();
                return;
            }
        }
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
        return centences.get(listViewBean.getPosition());
    }

    private void setCentences() {
        if (parts.size() > curPartP){
            MCentenceDao centenceDao = new MCentenceDao(this);
            PartBean partBean = parts.get(curPartP);
            centences.removeAll(centences);
            centences.addAll(centenceDao.getCentencesByPartId(partBean.getPart_id()));
            adapter.notifyDataSetChanged();
        }
        New_VoiceUtils.getInstance().startVoiceFile(new File(StringUtil.getShowText(getCurrentCentence().getUrl_exemple())));
    }


    private void setParts() {
        if (lessons.size() > curLessonP){
            MPartDao partDao = new MPartDao(this);
            LessonBean lessonBean = lessons.get(curLessonP);
            parts.removeAll(parts);
            parts.addAll(partDao.getPartsByLessonid(lessonBean.getLesson_id()));
        }
    }

    private PartBean getCurrentPart(){
        if (parts.size() > curPartP){
            return parts.get(curPartP);
        }
        return null;
    }

    private void setPartName() {
        if (parts.size() > curPartP){
            tv_partname.setText(parts.get(curPartP).getPart_name());
        }
    }

    private void setLessons() {
        if (unitid != -1){
            MLessonDao lessonDao = new MLessonDao(this);
            lessons.removeAll(lessons);
            lessons.addAll(lessonDao.getLessonsByUnitid(unitid));
        }
    }

    private void setLessonName() {
        if (lessons.size() > curLessonP){
            tv_lessonname.setText(lessons.get(curLessonP).getLesson_name());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*MPartDao partDao = new MPartDao(this);
        PartBean bean = parts.get(curPartP);
        bean.setCentence_start(listViewBean.getPosition());
        partDao.addData(bean);*/
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
        rl_lesson.setOnClickListener(this);
        rl_part.setOnClickListener(this);
        title_right.setOnClickListener(this);
        lv_exericises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        ll_exercise_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (isTouch)
                            return true;
                        isTouch = true;
                        downRelativeLayout(getCurrentCentence().getEnglish());
                        if (util == null)
                            util = new VoiceEngineUtils(StudentDoExerciseActivity.this,UserUtil.getUid());
                        setEventDown();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (!isTouch)
                            return true;
                        if (util != null)
                            util.stop();
                        upRelativeLayout();
                        break;
                }
                return true;
            }
        });
    }

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
        String path = FileUtil.getExerciseCurrentPath(this,unitid) + bean.getCentence_id() + ".mp3";
        Map<String,Object> map = new HashMap<>();
        map.put("centence",bean);
        adapter.cleanStatementResult();
        File file = new File(path);
        if (file.exists())
            file.delete();
        util.start(bean.getEnglish(),path,bean.getCentence_id() + "",listViewBean.getPosition(),map,this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.rl_lesson:
                showLesson = !showLesson;
                createLessonPopupwindow();
                break;
            case R.id.rl_part:
                showPart = !showPart;
                createPartPopupwindow();
                break;
            case R.id.title_right:
                if ("".equals(UserUtil.getUid())){
                    intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    SystemApplation.getInstance().exit(true);
                }else {
                    intent = new Intent(this,StudentExerciseProgressActivity.class);
                    intent.putExtra("unitid",unitid);
                    intent.putExtra("allsum",allsum);
                    intent.putExtra("allsource",allsource);
                    startActivity(intent);
                }
                break;
        }
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
        showMyprogressDialog();
        lv_exercise_loudView.stopChange();
        ll_exercise_start.setBackgroundColor(getResources().getColor(R.color.gray_bg));
        rl_exercise_recordbg.setVisibility(View.GONE);
    }

    private void createLessonPopupwindow() {
        if (pupLesson == null){
            pupLesson = new PopupWindow();
            pupLesson.setWidth(ScreenUtil.getScreenWidth(this));
            pupLesson.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            final RadioGroup radioGroup = (RadioGroup) LayoutInflater.from(this).inflate(R.layout.layout_doexercise_radiogroup,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.setLayoutParams(params);
            pupLesson.setContentView(radioGroup);
            pupLesson.setFocusable(true);
            pupLesson.setBackgroundDrawable(new BitmapDrawable());
            pupLesson.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StudentDoExerciseActivity.this.showLesson = false;
                }
            });
            RadioButton radioButton;

            RadioGroup.LayoutParams bt_params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50,this));
            bt_params.setMargins(0,ScreenUtil.dp2px(10,this),0,0);
            for (int i = 0 ;i<lessons.size();i++){
                LessonBean lessonBean = lessons.get(i);
                radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.layout_doexercise_radiobutton,null);
                radioButton.setTag(i);
                radioButton.setId(10000 + i);
                radioButton.setText(lessonBean.getLesson_name());
                radioGroup.addView(radioButton);
                radioButton.setLayoutParams(bt_params);
                if (curLessonP == i){
                    radioButton.setChecked(true);
                }
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkedId);
                    int tag = (int) rb.getTag();
                    if (tag == curLessonP){
                    }else {
                        curLessonP = tag;
                        curPartP = 0;
                        listViewBean.setPosition(0);
                        setLessonName();

                        setParts();
                        setPartName();
                        setPartPoupChange();

                        setCentences();
                    }
                    pupLesson.dismiss();
                }
            });
        }
        if (showLesson){
            pupLesson.showAsDropDown(rl_lesson);
        }else {
            pupLesson.dismiss();
        }
    }
    private void createPartPopupwindow() {
        if (pupPart == null){
            pupPart = new PopupWindow();
            pupPart.setWidth(ScreenUtil.getScreenWidth(this));
            pupPart.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            final RadioGroup radioGroup = (RadioGroup) LayoutInflater.from(this).inflate(R.layout.layout_doexercise_radiogroup,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.setLayoutParams(params);
            pupPart.setContentView(radioGroup);
            pupPart.setFocusable(true);
            pupPart.setBackgroundDrawable(new BitmapDrawable());
            pupPart.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StudentDoExerciseActivity.this.showPart = false;
                }
            });
            setPartPoupChange();
        }
        if (showPart){
            pupPart.showAsDropDown(rl_part);
        }else {
            pupPart.dismiss();
        }
    }

    private void setPartPoupChange() {
        if(pupPart == null){
            return;
        }

        RadioGroup radioGroup = (RadioGroup) pupPart.getContentView();
        radioGroup.removeAllViews();
        RadioButton radioButton;

        RadioGroup.LayoutParams bt_params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50,this));
        bt_params.setMargins(0,ScreenUtil.dp2px(10,this),0,0);
        for (int i = 0 ;i<parts.size();i++){
            PartBean partBean = parts.get(i);
            radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.layout_doexercise_radiobutton,null);
            radioButton.setTag(i);
            radioButton.setId(20000 + i);
            radioButton.setText(partBean.getPart_name());
            radioGroup.addView(radioButton);
            radioButton.setLayoutParams(bt_params);
            if (i == curPartP){
                radioButton.setChecked(true);
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                int tag = (int) rb.getTag();
                if (tag == curPartP){
                }else {
                    curPartP = tag;
                    listViewBean.setPosition(0);
                    setCentences();
                    setPartName();
                }
                pupPart.dismiss();
            }
        });
    }

    //驰骋评分两个接口方法
    @Override
    public void getScore(final String score, final String path, String sentenceID, final int position, final List<AIRecorderDetails> details, Map<String, Object> holderObj) {
        UIUtils.startTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                int current = Integer.parseInt(score);
                allsum++;
                allsource += current;
                CentenceBean bean = null;
                if (centences.size() > position)
                    bean  = centences.get(position);
                else
                    return;
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
                //TextColorUtils.changTextColor(bean.getEnglish(),jsonArray.toString());
                if (best == null)
                    best = 0;
                int code = 0;
                if (current > best){
                    bean.setSorce_best(current);
                    String besturl = FileUtil.getExerciseBestPath(StudentDoExerciseActivity.this,unitid) + bean.getCentence_id() + ".mp3";
                    code = FileUtil.copySdcardFile(path,besturl);
                    bean.setUrl_best(besturl);
                    //int time = (int) details.get(0).getDur();
                    //bean.setSeconds(time);
                }

                MCentenceDao dao = new MCentenceDao(StudentDoExerciseActivity.this);
                if (code == 0)
                    dao.addData(bean);
                GlobalParams.exerciseDatabaseChange = true;
                /*int currentCentence = listViewBean.getPosition();
                currentCentence++;
                if (currentCentence == centences.size())
                    currentCentence = 0;*/
                //listViewBean.setPosition(currentCentence);
                final int finalCode = code;
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //lv_exericises.setSelection(listViewBean.getPosition());
                        adapter.notifyDataSetChanged();
                        hideMyprogressDialog();
                        if (finalCode != 0){
                            Toast.makeText(StudentDoExerciseActivity.this, "保存数据失败", Toast.LENGTH_SHORT).show();
                        }
                        isTouch = false;
                    }
                });
            }
        });

    }

    @Override
    public void faild(String content, String path, String sentenceID, int position, String msg, Map<String, Object> holderObj) {
        hideMyprogressDialog();
        isTouch = false;
        Toast.makeText(StudentDoExerciseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

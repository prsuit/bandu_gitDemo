package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxGlobalParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.DoWorkListAdapter;
import me.bandu.talk.android.phone.adapter.DoWorkListAdapterNewFore;
import me.bandu.talk.android.phone.adapter.DoWorkListAdapterNewOne;
import me.bandu.talk.android.phone.adapter.DoWorkListBaseAdapter;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
import me.bandu.talk.android.phone.view.LoudView;

/**
 * author by Mckiera
 * time: 2015/12/18  17:04
 * description:
 * updateTime:
 * update description:
 */
public class DoWorkActivity extends BaseStudentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.do_work_list_activity;
    }

    @Bind(R.id.goback)
    ImageView goback;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.title_tv)
    TextView title_tv;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.tvCurrCountAndCount)
    TextView tvCurrCountAndCount;
    @Bind(R.id.tvCurrentType)
    TextView tvCurrentType;
    @Bind(R.id.title_right)
    RelativeLayout title_right;
    @Bind(R.id.lv)
    ListView lv;
    //    @Bind(R.id.layout1)
//    LinearLayout layout1;
    @Bind(R.id.llRecordBtn)
    LinearLayout llRecordBtn;

    @Bind(R.id.rl_dowork_recordbg)
    RelativeLayout rl_dowork_recordbg;

    @Bind(R.id.tv_dowork_content)
    TextView tv_dowork_content;
    @Bind(R.id.lv_dowork_loudView)
    LoudView lv_dowork_loudView;

    @Bind(R.id.rlRoot)
    public View rlRoot;

    //驰声初始化状态.
    // OnSpeechEngineLoaded   ONLINE_OK = 1 int OFFLINE_OK = 2 int ALL_OK = 3;
//    private int state;
    private Detail detail;

//    private DoWorkListAdapter mAdapter;

    private DoWorkListBaseAdapter mAdapter;

    private String currentType; //0:跟读 1:背诵 2:朗读
    private long quiz_id;
    private long stu_job_id;
    private long classID;

    private long startTime;
    private long endTime;
    private boolean showBestWork;

    private List<Long> times;
    //题干
    private String description;
    //八种新类型
    private String quiz_type;
    //是否是八种新类型
    private boolean isNew = false;


    @Override
    protected void toStart() {
        startTime = System.currentTimeMillis();
        times = new ArrayList<>();
        initView(); //界面初始化.
        //TODO 这个页面设置成启动页时候.注释掉这行代码
        setData();  //初始化数据
    }


    @Override
    public void initView() {
        goback.setVisibility(View.INVISIBLE);
        title_tv.setText(UIUtils.getString(R.string.title_string_workinfo));
        more.setText(UIUtils.getString(R.string.title_string_save));
        more.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        title_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null) {
//            state = intent.getIntExtra("state", -1);
            currentType = intent.getStringExtra("currentType");
            quiz_id = intent.getLongExtra("quiz_id", -1L);
            detail = (Detail) intent.getSerializableExtra("detail");
            showBestWork = intent.getBooleanExtra("showBestWork", false);
            classID = intent.getLongExtra("classID", -1);
            stu_job_id = intent.getLongExtra("stu_job_id", -1);
            description = intent.getStringExtra("description");
            quiz_type = intent.getStringExtra("quiz_type");
        }
        if (!TextUtils.isEmpty(currentType)) {
            String type = "";
            switch (currentType) {
                case "0":
                    type = "本遍为跟读作业";
                    break;
                case "1":
                    type = "本遍为背诵作业";
                    break;
                case "2":
                    type = "本遍为朗读作业";
                    break;
            }
            tvCurrentType.setText("" + type);
        }
        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(quiz_type)) {
            isNew = true;
            tvCurrCountAndCount.setText("题干");
            tvCurrCountAndCount.setVisibility(View.VISIBLE);
            tvCurrCountAndCount.setTextColor(UIUtils.getColor(R.color.teacher_color));
            tvCurrCountAndCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialogUtils.getInstance().init(DoWorkActivity.this, description);
                    AlertDialogUtils.getInstance().initDialog(DoWorkActivity.this, description);
                }
            });


            if (detail != null) {
                //新八种类型
                int quiz_type_new = getQuizTypeNew(quiz_type);
                if (quiz_type_new != 2) {
//                    AlertDialogUtils.getInstance().init(DoWorkActivity.this, description);
                    AlertDialogUtils.getInstance().initDialog(DoWorkActivity.this, description);
                }
                switch (quiz_type_new) {
                    case 1:
                    case 2:
                    case 3:
                    case 6:
                    case 7:
                    case 8:
                        mAdapter = new DoWorkListAdapterNewOne(detail, this, currentType, showBestWork, lv, classID, stu_job_id);
                        break;
                    case 4:
                    case 5:
                        mAdapter = new DoWorkListAdapterNewFore(detail, this, currentType, showBestWork, lv, classID, stu_job_id);
                        break;
                }
                lv.setAdapter(mAdapter);

                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO 不能是第0位. 是已有的位置.
                        if (/*("1".equals(currentType) || "2".equals(currentType)) &&*/ mAdapter.getDoneMap().containsValue(false)) {
                            if (mAdapter.getCurrPosition() == -1)
                                mAdapter.setCurrPosition(0);
                            if (android.os.Build.VERSION.SDK_INT >= 8) {
                                lv.smoothScrollToPosition(mAdapter.getCurrPosition());
                            } else {
                                lv.setSelected(true);
                                lv.setSelection(mAdapter.getCurrPosition());
                            }
//                          mAdapter.aotuNext(mAdapter.getCurrPosition());
                        }
                    }
                }, 1000);
            } else {
                UIUtils.showToastSafe("作业加载失败,请重新尝试进入");
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DoWorkActivity.this.finish();
                    }
                }, 1000);
            }
        } else {
            isNew = false;
            //以前的
            if (detail != null) {
                mAdapter = new DoWorkListAdapter(detail, this, currentType, showBestWork, lv, classID, stu_job_id);
//                mAdapter.cleanStatementResult();
//                ChivoxConstants.statementResult = new ArrayList<>();
                lv.setAdapter(mAdapter);

                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO 不能是第0位. 是已有的位置.
                        if (("1".equals(currentType) || "2".equals(currentType)) && mAdapter.getDoneMap().containsValue(false)) {
                            if (mAdapter.getCurrPosition() == -1)
                                mAdapter.setCurrPosition(0);
                            if (android.os.Build.VERSION.SDK_INT >= 8) {
                                lv.smoothScrollToPosition(mAdapter.getCurrPosition());
                            } else {
                                lv.setSelected(true);
                                lv.setSelection(mAdapter.getCurrPosition());
                            }
//                        mAdapter.aotuNext(mAdapter.getCurrPosition());
                        }
                    }
                }, 1000);
            } else {
                UIUtils.showToastSafe("作业加载失败,请重新尝试进入");
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DoWorkActivity.this.finish();
                    }
                }, 1000);
            }
        }
    }

    /**
     * 以前的                              0
     * 朗读文本	text reading        read    1
     * 视频配音	video dubbing	    repeat  2
     * 角色朗读	dialogue reading	read    3
     * 选读答案	selected reading	read    4
     * 情景问答	situation answering	read    5
     * 英汉转换	translation	        read    6
     * 选词填空	context reading	    read    7
     * 人机问答	listen and answer	read    8
     */
    private int getQuizTypeNew(String quiz_type) {
        int type;
        switch (quiz_type) {
            case "text reading":
                type = 1;
                break;
            case "video dubbing":
                type = 2;
                break;
            case "dialogue reading":
                type = 3;
                break;
            case "selected reading":
                type = 4;
                break;
            case "situation answering":
                type = 5;
                break;
            case "translation":
                type = 6;
                break;
            case "context reading":
                type = 7;
                break;
            case "listen and answer":
                type = 5;
                break;
            default:
                type = 0;
                break;
        }
        return type;
    }

    @Override
    public void setListeners() {
        //do nothing
    }
/*    public boolean selfPermissionGranted(String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }*/

    private boolean isTouchDown = false;


    @OnTouch(R.id.llRecordBtn)
    public boolean llRecordBtnOnTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

        /*        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int i = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
                    if(i != PackageManager.PERMISSION_GRANTED){
                        //ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.RECORD_AUDIO},1);
                        //TODO 权限问题... v4 21没有这个方法...日了狗了...
                    }
                }*/
                isTouchDown = !isTouchDown;
                if (isTouchDown) {
//                    startTime = System.currentTimeMillis();
                    if (mAdapter != null) {
                        int currPosition = mAdapter.getCurrPosition();
                        if (currPosition < 0) {
                            UIUtils.showToastSafe("请选择题目");
                            return true;
                        }
                        String content = getContent();
                        String sentenceID = mAdapter.getSentenceID(currPosition);
                        ChivoxGlobalParams.START_CHIVOX = true;
                        New_VoiceUtils.stopVoice();
                        doDownEvent(currPosition, content, sentenceID);
                    }
                }else{
                    if (mAdapter != null) {
                        return doUpEvent();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouchDown = !isTouchDown;
                if (!isTouchDown) {
                    if (mAdapter != null) {
                        return doUpEvent();
                    }
                }
                break;
        }
        return true;
    }


    private void doDownEvent(final int currPosition, final String content, final String sentenceID) {
        initRelativeLayout(getContentShow());
        lv_dowork_loudView.startChange();
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                startRecord(currPosition, content, sentenceID);
            }
        },100);
    }

    private void startRecord(int currPosition, String content, String sentenceID) {
        //TODO 调用驰声
        if (mAdapter != null) {
            //这里是开始
            int i = mAdapter.startRecord(currPosition, content, sentenceID, new HashMap<String, Object>());
            if (i == -1) {
                UIUtils.showToastSafe("评分系统故障,请重新尝试进入页面");
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }
        }
    }

    // 对布局的操作
    private void initRelativeLayout(String content) {
        String co = new String(content);
        if (co.contains("#")) {
            String[] split = co.split("#");
            StringBuilder sbEn = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    sbEn.append(split[i] + "\r\n");
                else
                    sbEn.append("  " + split[i] + "\r\n");
            }
            co = sbEn.toString();
        }
        llRecordBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        rl_dowork_recordbg.setVisibility(View.VISIBLE);
        // 当前的类型. 0 跟读, 1背诵, 2朗读
        if ("1".equals(currentType)) {
            tv_dowork_content.setTextSize(40);
            tv_dowork_content.setText("正在录音");
        } else {
            tv_dowork_content.setTextSize(30);
            tv_dowork_content.setText(co);
        }
    }

    private boolean doUpEvent() {
//        endTime = System.currentTimeMillis();
//        if(startTime <= 0){
//            startTime = System.currentTimeMillis() - 2 * 1000;
//        }
//        times.add(endTime - startTime);

//        endTime = 0;
//        startTime = 0;
        rl_dowork_recordbg.setVisibility(View.GONE);
        llRecordBtn.setBackgroundColor(getResources().getColor(R.color.home_bg));
        lv_dowork_loudView.stopChange();
        int currPosition = mAdapter.getCurrPosition();
        if (currPosition < 0) {
            return true;
        }
        mAdapter.showPd(this);
        stopRecord();
        if (!ChivoxGlobalParams.START_CHIVOX)
            mAdapter.closePd();
        return false;
    }

    // mAdapter中调用驰骋的方法
    private void stopRecord() {
        mAdapter.stopRecord();
    }

    private String getContent() {
        if (mAdapter != null) {
            if (mAdapter.getCurrPosition() >= 0) {
                if (isNew) {
                    return mAdapter.getAnswer(mAdapter.getCurrPosition());
                }
                return mAdapter.getContent(mAdapter.getCurrPosition());
            }
        }
        return "";
    }

    private String getContentShow() {
        if (mAdapter != null) {
            if (mAdapter.getCurrPosition() >= 0) {
                if (isNew) {
                    int quiz_type_new = getQuizTypeNew(quiz_type);
                    switch (quiz_type_new) {
                        case 8:
                        case 7:
                        case 6:
                        case 5:
                        case 4:
                            return mAdapter.getContent(mAdapter.getCurrPosition());
                    }
                    return mAdapter.getAnswer(mAdapter.getCurrPosition());
                }
                return mAdapter.getContent(mAdapter.getCurrPosition());
            }
        }
        return "";
    }

    @Override
    protected void onDestroy() {
//        if (mAdapter != null) {
//            mAdapter.cleanStatementResult();
//        }
        super.onDestroy();
    }

    @OnClick({R.id.more})
    public void save(View view) {
        switch (view.getId()) {
            case R.id.more:
                if (mAdapter != null) {
                    endTime = System.currentTimeMillis();
                    times.add(endTime - startTime);
                    long hw_quiz_id = mAdapter.getHw_quiz_id();
                   /* Intent intent = new Intent();
                    intent.putExtra("spend_time", getTimes());
                    intent.putExtra("hw_quiz_id", hw_quiz_id);
                    intent.putExtra("isdone", !mAdapter.getDoneMap().containsValue(false)*//* && mAdapter.isNeedNewPage()*//*);
                    intent.putExtra("currentType", currentType);
                    intent.putExtra("quiz_id", quiz_id);
                    setResult(RESULT_OK, intent);*/
                    Bundle bundle = new Bundle();
                    bundle.putLong("spend_time", getTimes());
                    bundle.putLong("hw_quiz_id", hw_quiz_id);
                    bundle.putString("currentType", currentType);
                    bundle.putBoolean("isdone", !mAdapter.getDoneMap().containsValue(false)/* && mAdapter.isNeedNewPage()*/);
                    bundle.putLong("quiz_id", quiz_id);
                    EventBus.getDefault().post(bundle);
                    finish();
                }
                break;
        }
    }

    private long getTimes() {
        long timesL = 0;
        for (int i = 0; i < times.size(); i++) {
            Long t = times.get(i);
            if (t > 30 * 1000) {
                t = 30L * 1000L;
            }
            timesL += t;
        }
        return timesL;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        New_VoiceUtils.stopVoice();
        if(isTouchDown) {
            if (mAdapter != null) {
                doUpEvent();
            }
            isTouchDown = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTouchDown) {
            if (mAdapter != null) {
                doUpEvent();
            }
            isTouchDown = false;
        }
    }
}

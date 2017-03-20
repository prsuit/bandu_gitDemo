package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.StudentListenLessonChoseExpandableAdapter;
import me.bandu.talk.android.phone.adapter.StudentVideoAdapter;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.db.mdao.MPartDao;
import me.bandu.talk.android.phone.impl.OnViedoViewPositionListener;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.middle.YouDaoMiddle;
import me.bandu.talk.android.phone.myenum.ExerciseStateEnum;
import me.bandu.talk.android.phone.recevice.FinishRecevice;
import me.bandu.talk.android.phone.utils.AnimationUtil;
import me.bandu.talk.android.phone.utils.ClickableSpanUtil;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.VedioView;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：练习预览页
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentVideoActivity extends BaseStudentActivity implements View.OnClickListener, OnViedoViewPositionListener, ClickableSpanUtil.WordClickListener, ExpandableListView.OnChildClickListener {
    @Bind(R.id.vp_video)
    VedioView vp_video;
    @Bind(R.id.lv_exericises)
    ListView lv_exericises;
    @Bind(R.id.elv_chose)
    ExpandableListView elv_chose;
    @Bind(R.id.civ_back)
    ImageView civ_back;
    @Bind(R.id.bt_chose)
    Button bt_chose;
    @Bind(R.id.tv_statechange)
    TextView tv_statechange;
    @Bind(R.id.bt_goexercise)
    Button bt_goexercise;


    private long unitid;
    private List<LessonBean> lessons;
    private List<List<PartBean>> parts;
    private List<CentenceBean> centences;
    private int curLesson, curCentence, curPart;  //当前选中的数据
    private StudentVideoAdapter adapter;
    private View pupView;
    private int subject;
    private boolean readSingleLine;
    private boolean isPrepared;
    private String clickstr = "";
    private WordsMiddle wordsMiddle;
    private StudentListenLessonChoseExpandableAdapter expandableAdapter;
    private boolean onPause;
    private boolean first = false; // 取色只能调一次
    private String lastText; // 上一次选中的文字
    private TextView tv;
    //第一次进来把播放的视频的文本颜色变为绿色
    private boolean fristcome;

    @Override
    protected void onResume() {
        super.onResume();
        onPause = false;
        onReplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPause = true;
        if (vp_video.isPlaying()) {
            vp_video.playerPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp_video.onDestory();
        if (recevice != null)
            unregisterReceiver(recevice);
    }

    @Override
    public void init() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_video;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setDatabase();
        setListeners();
        //注册不保存返回按钮的广播
        registReceiver();

    }

    private FinishRecevice recevice;

    private void registReceiver() {
        recevice = new FinishRecevice(new FinishRecevice.DoSomething() {
            @Override
            public void doSomething() {
                StudentVideoActivity.this.finish();
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(FinishRecevice.FINISH_ACTION);
        registerReceiver(recevice, filter);
    }


    private void setDatabase() {
        setLessons();
        setParts();
        setCentences();
    }

    @Override
    public void initView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.getScreenWidth(this) / 2, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        elv_chose.setLayoutParams(params);

    }

    @Override
    public void setData() {
        unitid = getIntent().getLongExtra("unitid", -1);
        subject = getIntent().getIntExtra("subject", 0);
        lessons = new ArrayList<>();
        parts = new ArrayList<>();
        centences = new ArrayList<>();
        adapter = new StudentVideoAdapter(this, centences, this, this);
        lv_exericises.setAdapter(adapter);
        wordsMiddle = new WordsMiddle(this, this);

        lessons = new ArrayList<>();
        parts = new ArrayList<>();
        expandableAdapter = new StudentListenLessonChoseExpandableAdapter(this, lessons, parts);
        elv_chose.setAdapter(expandableAdapter);

        if (curLesson != 0 && curPart != 0) {
            elv_chose.setSelectedChild(curLesson, curPart, true);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (groupPosition >= 0 && groupPosition < lessons.size()
                && childPosition >= 0 && childPosition < parts.get(groupPosition).size()) {
            curLesson = groupPosition;
            curPart = childPosition;
            curCentence = 0;
            readSingleLine = true;

            expandableAdapter.setSelectPosition(groupPosition, childPosition);
            tv = (TextView) v.findViewById(R.id.tv_child_text);

            if (TextUtils.isEmpty(lastText) && !fristcome) {
                lastText = parts.get(0).get(0).getPart_name();
            }

            for (int i = 0; i < parent.getChildCount(); i++) {
                if (!TextUtils.isEmpty(lastText) && null != (parent.getChildAt(i).findViewById(R.id.tv_child_text))
                        && lastText.equals(((TextView) parent.getChildAt(i).findViewById(R.id.tv_child_text)).getText())) {
                    ((TextView) parent.getChildAt(i).findViewById(R.id.tv_child_text)).setTextColor(tv.getCurrentTextColor());
                }
            }

            if (!first) {
                expandableAdapter.setTextColor(tv.getCurrentTextColor());
                first = true;
                expandableAdapter.setFirstClick(true);
            }
            //设置文本的颜色
            tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
//            elv_chose.setChildDivider(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

            if (fristcome){
                lastText = tv.getText().toString();
                setCentences();
                hideChoseLessons();
            }
        }
        return false;
    }


    @Override
    public void setListeners() {
        bt_chose.setOnClickListener(this);
        civ_back.setOnClickListener(this);
        bt_goexercise.setOnClickListener(this);
        tv_statechange.setOnClickListener(this);
        elv_chose.setOnChildClickListener(this);
//      elv_chose.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                if (groupPosition >= 0 && groupPosition < lessons.size()
//                        && childPosition >= 0 && childPosition < parts.get(groupPosition).size()) {
//                    curLesson = groupPosition;
//                    curPart = childPosition;
//                    curCentence = 0;
//                    readSingleLine = true;
//
//                    expandableAdapter.setSelectPosition(groupPosition, childPosition);
//                    TextView tv = (TextView) v.findViewById(R.id.tv_child_text);
//
//                    for (int i = 0; i < parent.getChildCount(); i++) {
//                        if (!TextUtils.isEmpty(lastText) && null != (parent.getChildAt(i).findViewById(R.id.tv_child_text))
//                                && lastText.equals(((TextView)parent.getChildAt(i).findViewById(R.id.tv_child_text)).getText())) {
//                            ((TextView)parent.getChildAt(i).findViewById(R.id.tv_child_text)).setTextColor(tv.getCurrentTextColor());
//                        }
//                    }
//
//                    lastText = tv.getText().toString();
//                    if (!first) {
//                        expandableAdapter.setTextColor(tv.getCurrentTextColor());
//                        first = true;
//                        expandableAdapter.setFirstClick(true);
//                    }
//                    tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
//
//                    setCentences();
//                    hideChoseLessons();
//                }
//                return false;
//            }
//        });
    }


    //获取lesson数据
    private void setLessons() {
        if (unitid != -1) {
            MLessonDao lessonDao = new MLessonDao(this);
            lessons.clear();
            lessons.addAll(lessonDao.getLessonsByUnitid(unitid));
        }
    }

    //获取part数据
    private void setParts() {
        parts.clear();
        MPartDao partDao = new MPartDao(this);
        for (int i = 0; i < lessons.size(); i++) {
            LessonBean lessonBean = lessons.get(i);
            List<PartBean> list = partDao.getPartsByLessonid(lessonBean.getLesson_id());
            parts.add(list);
            isPrepared = false;
        }
        expandableAdapter = new StudentListenLessonChoseExpandableAdapter(this, lessons, parts);
        elv_chose.setAdapter(expandableAdapter);
        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
            elv_chose.expandGroup(i);
        }
        elv_chose.setGroupIndicator(null);
    }

    private void setCentences() {
        MCentenceDao centenceDao = new MCentenceDao(this);
        PartBean partBean = getCurrentPart();
        if (partBean == null)
            return;
        centences.clear();
        centences.addAll(centenceDao.getCentencesByPartId(partBean.getPart_id()));
        refreshCentence();
        initPlayData();
    }

    private void initPlayData() {
        if (subject == 0) {
            playNotError();
            vp_video.setFrame();
        } else {
            PartBean part = getCurrentPart();
            if (part == null)
                return;
            vp_video.setViewURL(StringUtil.getShowText(part.getVideo_path()), true, this);
        }
        readSingleLine = false;
        vp_video.clearTime();
        vp_video.playerPlay();
    }

    private void refreshCentence() {
        adapter.setPosition(curCentence);
        adapter.notifyDataSetChanged();
        lv_exericises.setSelection(curCentence);
    }

    private int getLessonIndex;

    private LessonBean getCurrentLesson() {
        if (lessons.size() > curLesson) {
            return lessons.get(curLesson);
        } else {
            if (getLessonIndex > 3) {
                UIUtils.showToastSafe("请重新获取数据");
                finish();
                return null;
            }
            getLessonIndex++;
            return getCurrentLesson();
        }
    }

    private int getPartIndex;

    private PartBean getCurrentPart() {
        if (parts.size() > curLesson) {
            if (parts.get(curLesson).size() > curPart) {
                return parts.get(curLesson).get(curPart);
            } else {
                if (getPartIndex > 3) {
                    UIUtils.showToastSafe("请重新获取数据");
                    finish();
                    return null;
                }
                getPartIndex++;
                return getCurrentPart();
            }
        }
        UIUtils.showToastSafe("请重新获取数据");
        finish();
        return null;
    }

    private int getCentenceIndex;

    private CentenceBean getCurrentCentence() {
        if (centences.size() > curCentence) {
            return centences.get(curCentence);
        } else {
            if (getCentenceIndex > 3) {
                UIUtils.showToastSafe("请重新获取数据");
                finish();
                return null;
            }
            getCentenceIndex++;
            return getCurrentCentence();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_chose:
                showChoseLessons();
                break;
            case R.id.civ_back:
                if (elv_chose.getVisibility() == View.VISIBLE) {
                    hideChoseLessons();
                } else {
                    finish();
                }
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                if (!UserUtil.isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    SystemApplation.getInstance().exit(true);
                } else {
                    intent = new Intent(this, StudentExerciseProgressActivity.class);
                    intent.putExtra("unitid", unitid);
                    startActivity(intent);
                }
                break;
            case R.id.bt_goexercise:
                gotoExercise();
                break;
            case R.id.tv_statechange:
                setExerciseState();
                break;
            case R.id.ll_main:
                curCentence = (int) v.getTag();
                refreshCentence();
                if (subject == 1) {
                    PartBean part = getCurrentPart();
                    if (part == null)
                        return;
                    if (!TextUtils.isEmpty(StringUtil.getShowText(part.getVideo_path()))) {
                        CentenceBean centence = getCurrentCentence();
                        if (centence == null)
                            return;
                        vp_video.playerSeekToPosition(StringUtil.getIntegerNotnull(centence.getVideo_start()), StringUtil.getIntegerNotnull(centence.getVideo_end()));
                    }
                } else if (subject == 0) {
                    CentenceBean centence = getCurrentCentence();
                    readSingleLine = true;
                    vp_video.setViewURL(centence.getUrl_exemple(), false, StudentVideoActivity.this);
                }
                break;
        }
    }

    private void showChoseLessons() {
        bt_chose.setVisibility(View.GONE);
        Animation animShow = AnimationUtil.getXLeftShowTranslateAnimation(500);
        elv_chose.startAnimation(animShow);
        elv_chose.setVisibility(View.VISIBLE);
        if (!fristcome) {
            onChildClick(elv_chose, View.inflate(this, R.layout.layout_listen_lessonchose_child_item, null), 0, 0, 0);
            fristcome = true;
        }

//        // 打开选课列表设置默认选中第一个的颜色
//        TextView tv = (TextView)elv_chose.getChildAt(0).findViewById(R.id.tv_child_text);
//        if (null != tv) {
//            tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
//        }
    }

    private void hideChoseLessons() {
        Animation animShow = AnimationUtil.getXRightHideTranslateAnimation(500);
        elv_chose.startAnimation(animShow);
        animShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bt_chose.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        elv_chose.setVisibility(View.GONE);
    }


    private void gotoExercise() {
        if (vp_video != null && vp_video.isPlaying())
            vp_video.playerPause();
        //清空组合录音保存的数据
        App.ComRecData.clear();
        Intent intent = new Intent(this, StudentDoExerciseActivity_NEW.class);
        intent.putExtra("unitid", unitid);
        LessonBean lesson = getCurrentLesson();
        if (lesson == null)
            return;
        intent.putExtra("lessonid", lesson.getLesson_id());
        PartBean part = getCurrentPart();
        if (part == null)
            return;
        intent.putExtra("partid", part.getPart_id());
        startActivity(intent);
    }

    private void setExerciseState() {
        if (adapter.getExerciseState() == ExerciseStateEnum.ENGLISEANDCHINESE) {
            adapter.setExerciseState(ExerciseStateEnum.ENGLISE);
            tv_statechange.setText("英");
        } else if (adapter.getExerciseState() == ExerciseStateEnum.ENGLISE) {
            adapter.setExerciseState(ExerciseStateEnum.NONE);
            tv_statechange.setText("无");
        } else if (adapter.getExerciseState() == ExerciseStateEnum.NONE) {
            adapter.setExerciseState(ExerciseStateEnum.ENGLISEANDCHINESE);
            tv_statechange.setText("中 英");
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void currentPosition(int playposition) {
        if (subject == 1) {
            int position = getPlayPosition(playposition);
            if (position != -1) {
                if (position != curCentence) {
                    curCentence = position;
                    refreshCentence();
                }
            }
        }

    }


    @Override
    public void prepared() {
        isPrepared = true;
    }

    @Override
    public void onCompletion() {
        if (subject == 0 && !readSingleLine) {
            if (!onPause) {
                if (curCentence < centences.size() - 1) {
                    curCentence++;
                    initPlayData();
                } else {
                    curCentence = 0;
                }
            }
        } else if (subject == 1) {
            curCentence = 0;
        }
        refreshCentence();

    }

    @Override
    public void onReplay() {
        LogUtils.i("onReplay");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                curCentence = 0;
                readSingleLine = false;
                if (subject == 0) {
                    playNotError();
                } else if (subject == 1 && isPrepared) {
                    PartBean part = getCurrentPart();
                    if (part == null)
                        return;
                    vp_video.setViewURL(StringUtil.getShowText(part.getVideo_path()), true, StudentVideoActivity.this);
                    vp_video.clearTime();
                    vp_video.rePlay();
                }
                adapter.setPosition(curCentence);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private int getPlayPosition(int playposition) {
        for (int i = 0; i < centences.size(); i++) {
            CentenceBean centenceBean = centences.get(i);
            if (playposition >= centenceBean.getVideo_start() && playposition < centenceBean.getVideo_end()) {
                return i;
            }
        }
        return -1;
    }

    private void playNotError() {
        CentenceBean centence = getCurrentCentence();
        if (centence == null)
            return;
        if (TextUtils.isEmpty(centence.getUrl_exemple())) {
            if (curCentence < centences.size() - 1) {
                curCentence++;
                adapter.setPosition(curCentence);
                adapter.notifyDataSetChanged();
                playNotError();
            }
        } else {
            vp_video.setViewURL(centence.getUrl_exemple(), false, StudentVideoActivity.this);
            if (!vp_video.isPlaying())
                vp_video.playerPlay();
        }
    }


    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        if (WordsMiddle.GET_WORD == (int) obj[1]) {
            /*if (vp_video != null && vp_video.isPlaying())
                vp_video.playerPause();*/
            final MWordBean wordBean = (MWordBean) obj[0];
            if (wordBean.getStatus() == 0) {
                YouDaoMiddle youDaoMiddle = new YouDaoMiddle(this, this);
                youDaoMiddle.getWord(clickstr);
            } else {
                showWordPopupwindow(wordBean.getData());
            }
        } else if (WordsMiddle.CREATE_WORDS == (int) obj[1]) {
            BaseBean baseBean = (BaseBean) obj[0];
            UIUtils.showToastSafe(baseBean.getMsg());
            if (pupView != null) {
                Button btn = (Button) pupView.findViewById(R.id.btnAddWord);
                btn.setText("已添加");
                btn.setBackgroundResource(R.drawable.corners_gray);
                btn.setTextColor(ColorUtil.getResourceColor(StudentVideoActivity.this, R.color.white));
                btn.setFocusable(false);
                btn.setClickable(false);
            }
        } else if (YouDaoMiddle.YOUDAO == (int) obj[1]) {
            WordBean wordBean = (WordBean) obj[0];
            showWordPopupwindow(wordBean);
        }
    }

    private void showWordPopupwindow(WordBean data) {
        if (ScreenUtil.isForeground(this, getClass().getName())) {
            vp_video.playerPause();
            pupView = PopWordUtils.getInstance().showWorkPop(this, findViewById(R.id.rlRoot), data, new PopWordUtils.PopWordViewOnClick() {
                @Override
                public void btnOnClick(WordBean word) {
                    clickstr = word.getQuery();
                    if (UserUtil.isLogin()) {
                        WordsMiddle wordsMiddle = new WordsMiddle(StudentVideoActivity.this, StudentVideoActivity.this);
                        wordsMiddle.createWord(word.getQuery(), word);
                    } else {
                        UIUtils.showToastSafe("注册登录后可使用此功能");
                    }

                }

                @Override
                public void imgOnClick(WordBean word) {
                    New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio=" + word.getQuery());
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


    @Override
    public void wordClick(String word) {
        //之所以把回调写到activity，是为了能够及时停止播放，已经修改popupwindow
        //然而好像不行
        /*if (vp_video != null && vp_video.isPlaying())
            vp_video.playerPause();*/
        wordsMiddle.getWord(word);
    }
}

package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.FeedBackActivity;
import me.bandu.talk.android.phone.activity.StudentHomeActivity;
import me.bandu.talk.android.phone.activity.StudentTextbookChoseActivity;
import me.bandu.talk.android.phone.activity.StudentVideoActivity;
import me.bandu.talk.android.phone.adapter.StudentExerciseAdapter;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.db.dao.DaoSession;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MUnitDao;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentExerciseFragment extends BaseFragment implements View.OnClickListener {
    //    public static final String ACTION_HEANDIMAGE_CHANGE = "action_heandimage_change";
//    public static final String ACTION_DATABASE_CHANGE = "action_database_change";
//    public static final String ACTION_DOWNTASK_CHANGE = "action_downtask_change";
    private ListView lv_exericises;
    private View view;
    private ImageView image_idea;
    private TextView tv_exercise_sum_, tv_exercise_time, tv_exericise_edit_, tv_exercise_tasks, title_middle;
    private StudentExerciseAdapter adapter;
    private List<UnitBean> exercises;
    private ImageView iv_exericise_add_;
    private View headView;
    private CircleImageView civ_head, civ_head_;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private RelativeLayout rl_edit, title_left, title_main;
    private final long DEFAULT_LESSONID = 100000, DEFAULT_PARTID = 100000, DEFAULT_UNIT = 100000, DEFAULT_CENTENCEID = 1000000;
    private List<Map<String, Integer>> progress;
    private MDownloadManager downloadManager;
    //    private BroadcastReceiver mReceiver;
    private IntentFilter filter;
    //private int lvState = -1;
    private int deletePosition;
    private int headViewHeight;
    private float headImageStartX, headImageStartY,
            headImageEndX, headImageEndY;
    private int headImageStartWidth, headImageStartHeight,
            headImageEndWidth, headImageEndHeight, titleHeight;
    private int resetTop;

    @Override
    public void onResume() {
        super.onResume();
        //之所以每次都要获取，是因为还要根据是否登录显示不同的信息
        setUiData();
        //如果数据库中的练习改变，重新查询数据库
        if (GlobalParams.exerciseDatabaseChange) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    getBaseData();
                }
            });
        }
        //如果下载任务发生改变，重新获取下载任务
        if (GlobalParams.taskChange) {
            int downloadsum = downloadManager.getLoaddingsum() + downloadManager.getWaittingsum();
            if (downloadsum != 0) {
                tv_exercise_tasks.setText("正在下载" + downloadsum + "个练习本");
            } else {
                tv_exercise_tasks.setText("");
            }
            GlobalParams.taskChange = false;
        }
        //注册广播 之前是写到 onstart 和 ondestory中的，貌似不起作用?
//        if (mReceiver != null)
//            getActivity().registerReceiver(mReceiver, filter);

        lv_exericises.smoothScrollToPosition(0);
        setHeadImageTransition(headImageStartX, headImageStartY, headImageStartWidth, headImageStartHeight);
    }

    @Override
    public void onPause() {
        super.onPause();
        //销毁广播
//        if (null != mReceiver) {
//            getActivity().unregisterReceiver(mReceiver);
//        }
        resetTop = headView.getTop();
        canImageChange = false;
    }

    //获取练习进度以及根据是否登录显示信息
    private void setUiData() {
        if (UserUtil.isLogin()) {
            String strs = "您已累计练习了" + UserUtil.getExerciseDay(getActivity()) + "天";
            SpannableStringBuilder style = new SpannableStringBuilder(strs);
            style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(getActivity(), R.color.student_title_bg)), 7, strs.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(ColorUtil.getResourceColor(getActivity(), R.color.white)), 7, strs.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_exercise_time.setText(style);
        } else {
            tv_exercise_time.setText("欢迎使用伴读，\n想要更多功能，快去注册吧！");
        }
        if (UserUtil.isLogin()) {
            if (TextUtils.isEmpty(GlobalParams.userInfo.getAvatar())) {
                civ_head.setImageResource(R.mipmap.default_avatar);
            } else {
                ImageLoader.getInstance().displayImage(StringUtil.getShowText(GlobalParams.userInfo.getAvatar()), civ_head);
            }
            if (TextUtils.isEmpty(GlobalParams.userInfo.getAvatar())) {
                civ_head_.setImageResource(R.mipmap.default_avatar);
            } else {
                ImageLoader.getInstance().displayImage(StringUtil.getShowText(GlobalParams.userInfo.getAvatar()), civ_head_);
            }
        }
        GlobalParams.exerciseProgressChange = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_student_exercise, null);
            headView = inflater.inflate(R.layout.layout_exercise_head, null);
            initView(view);
            setData();
            setListeners();
            setDataBase();
        }
        setTv_exercise_tasks();
        return view;
    }

    private void setDataBase() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setDefaultExercise();
                getBaseData();
            }
        });
    }


    public void setTv_exercise_tasks() {
        if (downloadManager != null && tv_exercise_tasks != null) {
            //下载任务改变
            if (downloadManager.getLoaddingsum() + downloadManager.getWaittingsum() != 0) {
                tv_exercise_tasks.setText("正在下载" + (downloadManager.getLoaddingsum() + downloadManager.getWaittingsum()) + "个练习本");
            } else {
                tv_exercise_tasks.setText("");
            }
        }
    }

    //从数据库获取数据
    public void getBaseData() {
        DaoFactory.getInstence(getActivity()).reset(getActivity());  // 清空session
        MUnitDao unitDao = new MUnitDao(getActivity());
        if (exercises != null) {
            exercises.removeAll(exercises);
            exercises.addAll(unitDao.getAllData());
            if (progress != null)
                progress.removeAll(progress);
            //练习的进度
            for (UnitBean unitBean : exercises) {
                Integer[] pro = unitDao.getProgress(unitBean.getUnit_id());
                Map<String, Integer> map = new HashMap<>();
                map.put("yj", pro[0]);
                map.put("zg", pro[1]);
                progress.add(map);
            }
        }
        if (tv_exercise_sum_ != null)
            tv_exercise_sum_.setText("练习本(共" + exercises.size() + "个)");
        if (adapter != null)
            adapter.notifyDataSetChanged();
        GlobalParams.exerciseDatabaseChange = false;
    }


    //将默认的练习保存到数据库中
    private void setDefaultExercise() {
        MUnitDao unitDao = new MUnitDao(getActivity());
        if (!unitDao.hasKey(DEFAULT_UNIT) && !UserUtil.getDefaultExerciseLoad(getActivity())) {
            final MCentenceDao centenceDao = new MCentenceDao(getActivity());

            UnitBean unitBean = new UnitBean();
            unitBean.setUnit_id(DEFAULT_UNIT);
            unitBean.setUnit_name(StringUtil.getResourceString(getActivity(), R.string.default_exercise_unitone));
            unitBean.setTextbook_subject(0);
            unitBean.setInsert_time(System.currentTimeMillis());

            LessonBean lessonBean = new LessonBean();
            lessonBean.setUnitBean(unitBean);
            lessonBean.setLesson_id(DEFAULT_LESSONID);
            lessonBean.setLesson_name(getActivity().getString(R.string.default_exercise_lessonone));

            PartBean partBean = new PartBean();
            partBean.setPart_id(DEFAULT_PARTID);
            partBean.setPart_name(getActivity().getString(R.string.default_exercise_partone));
            partBean.setLessonBean(lessonBean);

            final List<CentenceBean> centences = new ArrayList<>();
            String[] exercise_chinese = getActivity().getResources().getStringArray(R.array.defaultexercise_chinese);
            String[] exercise_englist = getActivity().getResources().getStringArray(R.array.defaultexercise_english);


            for (int i = 0; i < exercise_chinese.length && i < exercise_englist.length; i++) {
                CentenceBean centenceBean = new CentenceBean();
                centenceBean.setCentence_id(DEFAULT_CENTENCEID + i);
                String path = FileUtil.getExerciseExamplePath(getActivity(), unitBean.getUnit_id());
                String toPath = path + "test" + (i + 1) + ".mp3";
                try {
                    InputStream in = getActivity().getAssets().open("test" + (i + 1) + ".mp3");
                    FileUtil.copySdcardFile(in, toPath);

                    centenceBean.setUrl_exemple(toPath);
                    centenceBean.setChines(exercise_chinese[i]);
                    centenceBean.setEnglish(exercise_englist[i]);
                    centenceBean.setPartBean(partBean);
                    centences.add(centenceBean);
                    DaoSession session = DaoFactory.getInstence(getActivity()).getSesson();
                    session.runInTx(new Runnable() {
                        @Override
                        public void run() {
                            centenceDao.addListData(centences);
                            UserUtil.saveDefaultExerciseLoad(getActivity(), true);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initView(View view) {
        lv_exericises = (ListView) view.findViewById(R.id.lv_exericises);
        tv_exericise_edit_ = (TextView) view.findViewById(R.id.tv_exericise_edit_);
        iv_exericise_add_ = (ImageView) view.findViewById(R.id.iv_exericise_add_);
        civ_head = (CircleImageView) headView.findViewById(R.id.civ_head);
        civ_head_ = (CircleImageView) view.findViewById(R.id.civ_head_);
        tv_exercise_sum_ = (TextView) view.findViewById(R.id.tv_exercise_sum_);
        tv_exercise_time = (TextView) headView.findViewById(R.id.tv_exercise_time);
        rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        tv_exercise_tasks = (TextView) headView.findViewById(R.id.tv_exercise_tasks);
        title_left = (RelativeLayout) view.findViewById(R.id.title_left);
        title_left.setVisibility(View.GONE);
        title_main = (RelativeLayout) view.findViewById(R.id.title_rl);
        title_main.setBackgroundColor(ColorUtil.getResourceColor(getActivity(), R.color.student_title_bg));
        title_middle = (TextView) view.findViewById(R.id.title_middle);
        title_middle.setText("练习");
        image_idea = (ImageView) view.findViewById(R.id.image_idea);
        image_idea.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData() {
        if (!UserUtil.isLogin()) {
            tv_exericise_edit_.setVisibility(View.GONE);
        } else {
            tv_exericise_edit_.setVisibility(View.VISIBLE);
        }
        rl_edit.setVisibility(View.GONE);
        downloadManager = MDownloadManager.getInstence(getActivity());
        lv_exericises.addHeaderView(headView);
        //这两个list要在线程中做处理，考虑到线程安全，用synchronizedList
        exercises = Collections.synchronizedList(new ArrayList<UnitBean>());
        progress = Collections.synchronizedList(new ArrayList<Map<String, Integer>>());
        adapter = new StudentExerciseAdapter(getActivity(), exercises, progress, this);
        lv_exericises.setAdapter(adapter);
//        mReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (ACTION_DATABASE_CHANGE.equals(action)) {
//                    //数据库中的数据发生改变
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            getBaseData();
//                        }
//                    });
//                } else if (ACTION_DOWNTASK_CHANGE.equals(action)) {
//                    //下载任务改变
//                    if (downloadManager.getLoaddingsum() + downloadManager.getWaittingsum() != 0) {
//                        tv_exercise_tasks.setText("正在下载" + (downloadManager.getLoaddingsum() + downloadManager.getWaittingsum()) + "个练习本");
//                    } else {
//                        tv_exercise_tasks.setText("");
//                    }
//                } else if (ACTION_HEANDIMAGE_CHANGE.equals(action)) {
//                    //准备在这写头像改变，目前没有用到
//                }
//            }
//        };
//        filter = new IntentFilter();
//        filter.addAction(ACTION_DATABASE_CHANGE);
//        filter.addAction(ACTION_DOWNTASK_CHANGE);
//        filter.addAction(ACTION_HEANDIMAGE_CHANGE);
    }

    //前三个变量是为了控制视图只在绘制第一次的时候获取数据
    //canImageChange 的作用是防止listview在设置数据源的时候出发滚动监听
    private boolean isHeadViewMeasured, isHeadImageMeasured,
            isTitleMiddleMeasured, canImageChange;

    @Override
    public void setListeners() {
        image_idea.setOnClickListener(this);
        tv_exericise_edit_.setOnClickListener(this);
        iv_exericise_add_.setOnClickListener(this);
        civ_head.setOnClickListener(this);
        civ_head_.setOnClickListener(this);
        //title_middle(标题的中间的textview)布局第一次改变时，获取其位置和高度
        //高度-10dp 作为滚动头像滚动结束时的高度和宽度 根据title_middle的位置可以获取到
        //滚动头像滚动结束后的位置
        title_middle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isTitleMiddleMeasured) {
                    titleHeight = title_middle.getHeight();
                    float x = title_middle.getX();
                    headImageEndHeight = headImageEndWidth = titleHeight - ScreenUtil.dp2px(10, getActivity());
                    headImageEndX = x - headImageEndWidth - ScreenUtil.dp2px(5, getActivity());
                    headImageEndY = ScreenUtil.dp2px(5, getActivity());
                    isTitleMiddleMeasured = true;
                }
            }
        });
        //civ_head头像布局第一次改变时，获取到其位置和大小，将其隐藏，并将civ_head_滚动头像设置成和它一模一样的
        //将civ_head_显示 这儿获取到的位置和大小作为滚动头像的初始位置和初始大小
        civ_head.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isHeadImageMeasured) {
                    headImageStartWidth = civ_head.getWidth();
                    headImageStartHeight = civ_head.getHeight();

                    headImageStartX = civ_head.getX();
                    headImageStartY = civ_head.getY() + ScreenUtil.dp2px(50, getActivity());

                    setHeadImageTransition(headImageStartX, headImageStartY,
                            headImageStartWidth, headImageStartHeight);

                    civ_head.setVisibility(View.GONE);
                    civ_head_.setVisibility(View.VISIBLE);


                    isHeadImageMeasured = true;
                }
            }
        });
        //listview的headView布局发成改变时，获取headview的高度
        headView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isHeadViewMeasured) {
                    headViewHeight = headView.getHeight();
                    isHeadViewMeasured = true;
                }
            }
        });
        lv_exericises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position -= 2;
                if (position >= 0 && position < exercises.size())
                    gotoExercise(position);
            }
        });
        lv_exericises.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果状态为停止滑动
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (view.getFirstVisiblePosition() == 0) {
                        View v = view.getChildAt(0);
                        if (v != null) {
                            int top = Math.abs(v.getTop());
                            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                if (top < ScreenUtil.dp2px(50, getActivity())) {
                                    view.smoothScrollBy(-10, 10);
                                }
                            } else
                                //如果大于headview高度的一半，分两种情况
                                if (top > headViewHeight / 2 && top < headViewHeight) {
                                    //如果已经到了底部，不能向上滑，让他滑回去
                                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                        //这儿原来用view.smoothScrollBy(-10, 10); 发现会卡住，并且不停的滑动
                                        //setHeadImageTransition(headImageEndX, headImageEndY, headImageEndWidth, headImageEndHeight);
                                    } else {
                                        //否则继续滑动，直到headview完全不可见
                                        view.smoothScrollBy(10, 10);
                                    }
                                } else if (top > 0 && top <= headViewHeight / 2) {
                                    //如果滑动高度小于headview高度的一般，再滑回去
                                    view.smoothScrollBy(-10, 10);
                                }
                        }
                    }
                } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    canImageChange = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //如果可见item位置大于1(headview位置为0 选择的view位置为1) 或者可见item位置为1且选择view的top<=0 显示选择view的悬浮view
                if (firstVisibleItem > 1 || (firstVisibleItem == 1 && view.getChildAt(0) != null && view.getChildAt(0).getTop() < 0)) {
                    if (rl_edit.getVisibility() == View.GONE)
                        rl_edit.setVisibility(View.VISIBLE);
                } else {
                    if (rl_edit.getVisibility() == View.VISIBLE)
                        rl_edit.setVisibility(View.GONE);
                }

                if (canImageChange) {
                    int firstposition = view.getFirstVisiblePosition();
                    if (firstposition == 0) {
                        View v = view.getChildAt(firstposition);
                        if (v != null) {
                            float y = v.getTop();
                            float abs_y = Math.abs(y);
                            if (headViewHeight != 0 && canImageChange) {

                                if (abs_y > 0) {
                                    //滑动过程中，不停的设置滚动头像的位置和宽度高度
                                    setHeadImageTransition(headImageStartX - (headImageStartX - headImageEndX) * abs_y / headViewHeight,
                                            headImageStartY - (headImageStartY - headImageEndY) * abs_y / headViewHeight,
                                            headImageStartWidth - (headImageStartWidth - headImageEndWidth) * abs_y / headViewHeight,
                                            headImageStartHeight - (headImageStartHeight - headImageEndHeight) * abs_y / headViewHeight);
                                } else {
                                    //headview又滑回去了
                                    setHeadImageTransition(headImageStartX, headImageStartY, headImageStartWidth, headImageStartHeight);
                                }
                            }
                            if (view.getLastVisiblePosition() == view.getCount() - 1 && canImageChange) {
                                if (abs_y >= ScreenUtil.dp2px(50, getActivity())) {
                                    setHeadImageTransition(headImageEndX, headImageEndY, headImageEndWidth, headImageEndHeight);
                                }
                            }
                        }

                    } else {
                        //headview滑动的完全不可见了
                        setHeadImageTransition(headImageEndX, headImageEndY, headImageEndWidth, headImageEndHeight);
                    }
                }
            }
        });
    }

    //设置滚动头像的位置和高度宽度
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private void setHeadImageTransition(float x, float y, float width, float height) {
        if (width != 0 && height != 0) {
            params.width = (int) width;
            params.height = (int) height;
            civ_head_.setLayoutParams(params);
            civ_head_.setX(x);
            civ_head_.setY(y);
        }
        LogUtils.i("x:" + x + " y:" + y + " width:" + width + " height:" + height);
    }

    //去练习
    private void gotoExercise(int position) {
        UnitBean unitBean = exercises.get(position);
        Intent intent = new Intent(getActivity(), StudentVideoActivity.class);
        intent.putExtra("unitid", unitBean.getUnit_id());
        intent.putExtra("subject", StringUtil.getIntegerNotnull(unitBean.getTextbook_subject()));
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exericise_edit:
            case R.id.tv_exericise_edit_:
                //点击编辑按钮或者取消按钮
                adapter.setIsDelete();
                if (adapter.getIsDelete()) {
                    tv_exericise_edit_.setText(StringUtil.getResourceString(getActivity(), R.string.cancel));
                } else {
                    tv_exericise_edit_.setText(StringUtil.getResourceString(getActivity(), R.string.edit));
                }
                break;
            case R.id.iv_exericise_add_:
            case R.id.iv_exericise_add:
                //点击增加按钮
                Intent intent = new Intent(getActivity(), StudentTextbookChoseActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.civ_head_:
            case R.id.civ_head:
                //点击头像
                if (!UserUtil.isLogin()) {
                    ((StudentHomeActivity) getActivity()).gotoLogin(view);
                } else {
                    ((StudentHomeActivity) getActivity()).toPerCentence();
                }
                break;
            case R.id.iv_exericise_delete:
                //点击item的删除
                deletePosition = (int) view.getTag();
                createDialog();
                break;
            case R.id.image_idea:
                //点击进入意见反馈
                Intent in = new Intent(getActivity(), FeedBackActivity.class);
                startActivityForResult(in, 9);
                break;
        }
    }

    //这个现在应该是没用了
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            boolean change = data.getBooleanExtra("change", false);
            if (change) {
                //setDataBase();
            }
        }
    }

    //删除练习
    private void deleteExercise(final int position) {
        if (exercises.size() > position) {
            showMyprogressDialog();
            //在线程中删除
            UIUtils.startTaskInThreadPool(new Runnable() {
                @Override
                public void run() {
                    final MUnitDao unitDao = new MUnitDao(getActivity());
                    final UnitBean unitBean = exercises.get(position);
                    //新建事务
                    DaoSession session = DaoFactory.getInstence(getActivity()).getSesson();
                    session.runInTx(new Runnable() {
                        @Override
                        public void run() {
                            unitDao.deleteData(unitBean.getUnit_id());
                        }
                    });
                    //并将数据库对应的文件删除
                    FileUtil.deleteExercise(getActivity(), unitBean.getUnit_id());
                    exercises.remove(position);
                    progress.remove(position);
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            hideMyprogressDialog();
                            adapter.notifyDataSetChanged();
                            tv_exercise_sum_.setText("练习本(共" + exercises.size() + "个)");
                        }
                    });
                }
            });
        }

    }

    //创建删除练习提示框
    private void createDialog() {
        if (builder == null) {
            builder = new AlertDialog.Builder(getActivity());
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    deleteExercise(deletePosition);
                }
            }).setMessage("\n是否删除练习\n");
        }

        if (alertDialog == null) {
            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
        }

        alertDialog.show();
    }
}

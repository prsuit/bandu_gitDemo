package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.SysMsgNumBean;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.mdao.MDownloadDao;
import me.bandu.talk.android.phone.fragment.StudentExerciseFragment;
import me.bandu.talk.android.phone.fragment.factory.HomeFragmentFactory;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.middle.SysMsgNumMiddle;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.UpdateBandu;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.Utils;

public class StudentHomeActivity extends BaseStudentActivity {

    public static final String ACTION_HEANDIMAGE_CHANGE = "action_heandimage_change";
    public static final String ACTION_DATABASE_CHANGE = "action_database_change";
    public static final String ACTION_DOWNTASK_CHANGE = "action_downtask_change";

    Fragment mFragment;
    @Bind(R.id.title_rl)
    RelativeLayout title_rl;
    @Bind(R.id.title_middle)
    TextView title_middle;

    @Bind(R.id.tvStudentExercise)
    TextView tvStudentExercise;

    @Bind(R.id.tvStudentWork)
    TextView tvStudentWork;

    @Bind(R.id.tvMyInfo)
    TextView tvMyInfo;

    @Bind(R.id.ivStudentExercise)
    ImageView ivStudentExercise;
    @Bind(R.id.ivStudentWork)
    ImageView ivStudentWork;
    @Bind(R.id.ivMyInfo)
    ImageView ivMyInfo;
    @Bind(R.id.red_point)
    TextView redTv;
    @Bind(R.id.redTv)
    TextView redTvB;
    @Bind(R.id.title_right)
    ImageView rightImg;

    @Bind(R.id.rlStudentWork)
    RelativeLayout rlStudentWork;

    // 轻直播
    @Bind(R.id.tvStudentQingLive)
    TextView tvStudentQingLive;
    @Bind(R.id.ivStudentQingLive)
    ImageView ivStudentQingLive;
    int index;

    private static boolean isExit = false;
    private MDownloadManager mDownloadManager;
    private IntentFilter filter;
    private BroadcastReceiver mReceiver;
    public StudentExerciseFragment studentExerciseFragment;
    private UpdateBandu updateBandu;

    @Override
    protected void onResume() {
        super.onResume();
        if (!GlobalParams.checkDownloadTask && UserUtil.isLogin()) {
            MDownloadDao mDownloadDao = new MDownloadDao(this);
            List<DownloadBean> downloadBeanList = mDownloadDao.getAllData();
            if (downloadBeanList.size() > 0) {
                showdownloadDialog(downloadBeanList);
            }
            GlobalParams.checkDownloadTask = true;
        }
        if (mReceiver != null)
            registerReceiver(mReceiver, filter);
    }


    private void showdownloadDialog(final List<DownloadBean> downloadBeanList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("\n您还有未完成的任务，是否继续下载\n")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MDownloadDao mDownloadDao = new MDownloadDao(StudentHomeActivity.this);
                        mDownloadDao.deleteList(downloadBeanList);
                        dialog.dismiss();
                    }
                }).setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < downloadBeanList.size(); i++) {
                    if (downloadBeanList.size() > i) {
                        DownloadBean downloadBean = downloadBeanList.get(i);
                        MDownloadTask task = new MDownloadTask(StudentHomeActivity.this, downloadBean.getDownload_id(), downloadBean.getDownload_name(), downloadBean.getDownload_subject(), downloadBean.getDownload_category());
                        mDownloadManager.addTask(task);
                    }

                }
            }
        }).create().show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_home;
    }

    @Override
    protected void toStart() {
        EventBus.getDefault().register(this);
        initView();

        // 朦版
        // SharedPreferences sp = getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE);
        // boolean first = sp.getBoolean("student_first", true);
        if (UserUtil.isLogin() && GlobalParams.userInfo != null && PreferencesUtils.isFirstLogin() && getSharedPreferences(ConstantValue.SPCONFIG, Context.MODE_PRIVATE).getBoolean("student_first", true)) {
            hazyDialog();
            PreferencesUtils.setFirstLogin(false);
            GlobalParams.userInfo.setIs_first_login(false);
        }
        //数据库中的数据发生改变
//下载任务改变
//准备在这写头像改变，目前没有用到
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_DATABASE_CHANGE.equals(action)) {
                    //数据库中的数据发生改变
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (studentExerciseFragment != null)
                                studentExerciseFragment.getBaseData();
                        }
                    });
                } else if (ACTION_DOWNTASK_CHANGE.equals(action)) {
                    if (studentExerciseFragment != null)
                        studentExerciseFragment.setTv_exercise_tasks();
                } else if (ACTION_HEANDIMAGE_CHANGE.equals(action)) {
                    //准备在这写头像改变，目前没有用到
                }
            }
        };
        filter = new IntentFilter();
        filter.addAction(ACTION_DATABASE_CHANGE);
        filter.addAction(ACTION_DOWNTASK_CHANGE);
        filter.addAction(ACTION_HEANDIMAGE_CHANGE);
        setData();
        setListeners();
    }

    public void setNoload() {
       /* rlStudentWork.setEnabled(false);
        rlStudentWork.setClickable(false);*/
    }

    public void setLoad() {
        /*rlStudentWork.setEnabled(true);
        rlStudentWork.setClickable(true);*/
    }

    @Override
    public void initView() {
        //清空录音组合的保存数据
        App.ComRecData.clear();
        title_middle.setText(R.string.work);
        redTv.setVisibility(View.GONE);
        SharedPreferences sp = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        boolean isShow = sp.getBoolean("isShow", true);
        if (isShow) {
            Utils.showCommonDialog(this, getString(R.string.sys_msg_notification), getString
                    (R.string.msg_body), getString(R.string.confirm));
        }
//        UmengUpdateAgent.forceUpdate(getApplicationContext());
//        UmengUpdateAgent.update(this);
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
//
//            @Override
//            public void onClick(int status) {
//                switch (status) {
//                    case UpdateStatus.Update:
//                        break;
//                    default:
//                        SystemApplation.getInstance().exit(true);
//                }
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            updateBandu = UpdateBandu.getUpdateBandu();
            if (!updateBandu.isShowOff()) {//显示过就不在重复显示了
                updateBandu.setShowOff(true);
                Point point = new Point();
                getWindowManager().getDefaultDisplay().getSize(point);
                int versionCode = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
                updateBandu.setWidthAndHeight(point.x, point.y);
                updateBandu.update(context == null ? this : context, versionCode,2);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData() {
        mDownloadManager = MDownloadManager.getInstence(this);
        setTitleGone(true);//3.2设置标题隐藏
        if (!getIntent().getBooleanExtra("fromTest", false)) {
            getMsgNum();
            mFragment = HomeFragmentFactory.createFragment(R.id.rlStudentWork);
            ivStudentWork.setSelected(true);
            tvStudentWork.setTextColor(UIUtils.getColor(R.color.radio_green));
        } else {
            //每次要先移除之前的那个fragment，然后在创建
            HomeFragmentFactory.destroyFragment(R.id.rlStudentExercise);
            mFragment = HomeFragmentFactory.createFragment(R.id.rlStudentExercise);
            title_middle.setText(R.string.exercise_text_student_home);
            ivStudentExercise.setSelected(true);
            tvStudentExercise.setTextColor(UIUtils.getColor(R.color.radio_green));
        }

        replaceFragment(mFragment);

    }

    private void replaceFragment(final Fragment fragment) {
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain, fragment).addToBackStack(null).commitAllowingStateLoss();
            }
        }, 100);
    }


    @Override
    public void setListeners() {
    }

    public void getMsgNum() {
        if(UserUtil.getUerInfo(this) != null)
            new SysMsgNumMiddle(this, this).getSysMsgNum(UserUtil.getUerInfo(this).getUid(), new SysMsgNumBean());
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (l != null)
            l.setDbOver();
        if (null != mReceiver) {
            unregisterReceiver(mReceiver);
        }
        if (null != updateBandu)
            updateBandu.myUpdateDialogDismiss();
        System.gc();
    }


    @OnClick({R.id.rlStudentExercise, R.id.rlStudentWork, R.id.rlMyInfo, R.id.title_rl, R.id.title_right,R.id.rlStudentQingLive})
    public void checkBottom(View v) {
        switch (v.getId()) {
            case R.id.rlStudentExercise:
                setTitleGone(true);//3.2设置标题隐藏
                index = 0;
                rightImg.setImageResource(R.mipmap.icon_idea);
                redTv.setVisibility(View.GONE);
                title_middle.setText(R.string.exercise_text_student_home);
                // TODO 同样道理，应该先移除再创建
                HomeFragmentFactory.destroyFragment(R.id.rlStudentExercise);
                studentExerciseFragment = (StudentExerciseFragment) HomeFragmentFactory.createFragment(R.id.rlStudentExercise);
                replaceFragment(studentExerciseFragment);
//                getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain,
//                        HomeFragmentFactory.createFragment(R.id.rlStudentExercise)).commitAllowingStateLoss();
                changedIcon(v);
                break;
            case R.id.rlStudentWork:
                redTv.setVisibility(View.GONE);
                if (UserUtil.isLogin()) {
                    setTitleGone(true);//3.2设置标题隐藏
                    rightImg.setImageResource(R.mipmap.icon_idea);
                    index = 1;
                    title_middle.setText(R.string.work);
                    Fragment fragment1 = HomeFragmentFactory.createFragment(R.id.rlStudentWork);
                    replaceFragment(fragment1);
                    //getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain, HomeFragmentFactory.createFragment(R.id.rlStudentWork)).commitAllowingStateLoss();
                    changedIcon(v);
                } else {
                    showLoginDialog();
                }
                break;
            case R.id.rlMyInfo:
                setTitleGone(false);//3.2设置标题显示
                index = 2;
                rightImg.setImageResource(R.mipmap.mail);
                if (GlobalParams.isShow) {
                    redTv.setVisibility(View.VISIBLE);
                } else {
                    redTv.setVisibility(View.GONE);
                }
                title_middle.setText(R.string.personal_center);
                //防止fragement出现错乱，就先移除，然后在添加。。。
                HomeFragmentFactory.destroyFragment(R.id.rlMyInfo);
                Fragment fragment1 = HomeFragmentFactory.createFragment(R.id.rlMyInfo);
                replaceFragment(fragment1);
//                getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain,
//                        HomeFragmentFactory.createFragment(R.id.rlMyInfo)).commitAllowingStateLoss();
                changedIcon(v);
                break;
            case R.id.rlStudentQingLive:
                redTv.setVisibility(View.GONE);
                if (UserUtil.isLogin()) {
                    setTitleGone(true);//3.2设置标题显示
                   /* rightImg.setImageResource(R.mipmap.search);
                    index = 3;
                    title_middle.setText("轻直播");
                    // TODO 同样道理，应该先移除再创建
                    HomeFragmentFactory.destroyFragment(R.id.rlStudentQingLive);
                    Fragment fragment_qing = HomeFragmentFactory.createFragment(R.id.rlStudentQingLive);
                    replaceFragment(fragment_qing);*/
                    //getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain, HomeFragmentFactory.createFragment(R.id.rlStudentWork)).commitAllowingStateLoss();
                    // Intent it = new Intent(StudentHomeActivity.this, com.bigsight.weavebird.MainActivity.class);
                    HomeFragmentFactory.destroyFragment(R.id.rlStudentQingLive);
                    Fragment fragment_qing = HomeFragmentFactory.createFragment(R.id.rlStudentQingLive);
                    replaceFragment(fragment_qing);
                    // startActivity(new Intent(this, me.bandu.talk.android.phone.MainActivity.class));
                    changedIcon(v);
                    //  startActivity(it);
                } else {
                    showLoginDialog();
                }
                break;
            case R.id.title_right:
                Intent intent;
                if (index == 2) {
//                    intent = new Intent(this, SystemMessageActivity.class);
                    // 3.4.4修改bug：立即试用进来点击信封闪退
                    if (UserUtil.isLogin()){       //判断是否登录
                        intent = new Intent(this, SystemMessageActivity.class);
                        startActivityForResult(intent, 9);
                    }else {
                        showLoginDialog();
                    }
                } else {
                    intent = new Intent(this, FeedBackActivity.class);
                    startActivityForResult(intent, 9);
                }
//                startActivityForResult(intent, 9);
                break;

        }

    }

    private void showLoginDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        TextView tvMsg = (TextView) layout.findViewById(R.id.msg);
        tvMsg.setText("登录后才能使用此功能呦~快\n去登录吧");
        TextView tvLeft = (TextView) layout.findViewById(R.id.make_sure);
        tvLeft.setText("继续试用");
        TextView tvRight = (TextView) layout.findViewById(R.id.cancel);
        tvRight.setText("登录");
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(StudentHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog1.show();
    }

    /**
     * 点击更改下面图标的颜色.和文字的颜色
     *
     * @param v
     */
    private void changedIcon(View v) {
        tvMyInfo.setTextColor(UIUtils.getColor(v.getId() == R.id.rlMyInfo ? R.color.radio_green : R.color.tv_color_my_default));
        tvStudentExercise.setTextColor(UIUtils.getColor(v.getId() == R.id.rlStudentExercise ? R.color.radio_green : R.color.tv_color_my_default));
        tvStudentWork.setTextColor(UIUtils.getColor(v.getId() == R.id.rlStudentWork ? R.color.radio_green : R.color.tv_color_my_default));
        tvStudentQingLive.setTextColor(UIUtils.getColor(v.getId() == R.id.rlStudentQingLive ? R.color.radio_green : R.color.tv_color_my_default));

        ivStudentExercise.setSelected(v.getId() == R.id.rlStudentExercise);
        ivStudentWork.setSelected(v.getId() == R.id.rlStudentWork);
        ivMyInfo.setSelected(v.getId() == R.id.rlMyInfo);
        ivStudentQingLive.setSelected(v.getId() == R.id.rlStudentQingLive);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownloadManager.stopAll();
        mDownloadManager.unLogin();
        DaoFactory.getInstence(this).onDestory();
        GlobalParams.checkDownloadTask = false;
        EventBus.getDefault().unregister(this);
        HomeFragmentFactory.cleanFragment();
    }

    public void toPerCentence() {
        View v = findViewById(R.id.rlMyInfo);
        title_middle.setText(R.string.personal_center);
        Fragment fragment = HomeFragmentFactory.createFragment(R.id.rlMyInfo);
        replaceFragment(fragment);
//        getFragmentManager().beginTransaction().replace(R.id.fl_setudentmain, HomeFragmentFactory.createFragment(R.id.rlMyInfo)).commit();
        changedIcon(v);
    }

    //双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                isExit = false;
            }
        }
    };

    private void exit() {
        if (GlobalParams.imgflag) {
            RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(100);//设置动画持续时间
            animation.setFillAfter(true);
            ImageView img = (ImageView) findViewById(R.id.up_down);
            img.startAnimation(animation);
            GlobalParams.imgflag = false;
        } else {
            if (!isExit) {
                isExit = true;
                UIUtils.showToastSafe(getString(R.string.press_again));
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                UpdateBandu updateBandu = UpdateBandu.getUpdateBandu();
                updateBandu.setShowOff(false);
                SystemApplation.getInstance().exit(true);
            }
        }
    }

    public int curr_position = 0;
    public int imageResID[] = new int[]{R.mipmap.delete_stu1, R.mipmap.delete_stu2};

    public void hazyDialog() {
//        final Dialog dialog = new Dialog(this, R.style.custom_dialog);
        final ImageView imageView = (ImageView) findViewById(R.id.ivHazy);
        imageView.setVisibility(View.VISIBLE);
        curr_position = 0;
//        imageView.setImageResource();
        imageView.setImageResource(imageResID[curr_position]);
//        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //if (dialog.isShowing()) {
                if (curr_position == imageResID.length - 1) {
                    imageView.setVisibility(View.GONE);
                }
                // dialog.dismiss();
                else {
                    curr_position++;
                    imageView.setImageResource(imageResID[curr_position]);
                }
                //}
            }
        });
       /* WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.addContentView(imageView, layoutParams);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight());

        dialog.getWindow().setAttributes(lp);*/
        //TODO 学生第一次点开的第一次执行
        getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE).edit().putBoolean("student_first", false).commit();
    }


    @Override
    public void onSuccess(Object result, int requestCode) {
        if (requestCode == SysMsgNumMiddle.SYS_MSG_NUM) {
            SysMsgNumBean bean = (SysMsgNumBean) result;
            if (bean.getStatus() == 1) {
                if ("0".equals(bean.getData().getUn_read())) {
                    GlobalParams.isShow = false;
                    redTvB.setVisibility(View.INVISIBLE);
                    redTv.setVisibility(View.INVISIBLE);
                } else {
                    GlobalParams.isShow = true;
                    redTvB.setVisibility(View.VISIBLE);
                    if (index == 2) {
                        redTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

    public void onEventMainThread(String message) {
        if ("msg".equals(message)) {
            getMsgNum();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (9 == requestCode) {
            getMsgNum();
        }
    }

    public void setTitleGone(boolean flag) {
        if (flag) {
            title_rl.setVisibility(View.GONE);
        } else {
            title_rl.setVisibility(View.VISIBLE);
        }
    }

    private OnDbOverListener l;

    public void setOnDbOverListener(OnDbOverListener l) {
        this.l = l;
    }

    public interface OnDbOverListener {
        void setDbOver();
    }


}

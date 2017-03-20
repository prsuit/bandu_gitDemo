package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.SysMsgNumBean;
import me.bandu.talk.android.phone.fragment.factory.HomeFragmentFactory;
import me.bandu.talk.android.phone.middle.SysMsgNumMiddle;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.UpdateBandu;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.Utils;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/23 13:38
 * 类描述：教师首页
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherHomeActivity extends BaseAppCompatActivity {

    private Fragment thlf;
    @Bind(R.id.title_middle)
    TextView title_middle;

    @Bind(R.id.tvMyClass)
    TextView tvMyClass;

    @Bind(R.id.tvMyInfo)
    TextView tvMyInfo;

    @Bind(R.id.ivMyClass)
    ImageView ivMyClass;

    @Bind(R.id.ivMyInfo)
    ImageView ivMyInfo;
    @Bind(R.id.red_point)
    TextView redTv;
    @Bind(R.id.redTv)
    TextView redTvB;
    @Bind(R.id.title_right)
    ImageView rightImg;
    //直播
    @Bind(R.id.tvTeacherQingLive)
    TextView tvTeacherQingLive;
    @Bind(R.id.ivTeacherQingLive)
    ImageView ivTeacherQingLive;
    @Bind(R.id.title_rl)
    RelativeLayout title_rl;

    private static boolean isExit = false;
    int index;
    private UpdateBandu updateBandu;

    @Override
    protected int getLayoutId() {
        return R.layout.teacher_home_activity;
    }

    @Override
    protected void toStart() {
        EventBus.getDefault().register(this);
        getMsgNum();
        redTv.setVisibility(View.GONE);
        //GlobalParams.userInfo.setSchool(null);
        SharedPreferences sp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
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

        HomeFragmentFactory.destroyFragment(R.id.llMyClass);
        thlf = HomeFragmentFactory.createFragment(R.id.llMyClass);
        updateUI(R.id.llMyClass);
        title_middle.setText("我的班级");
        replaceFragment(thlf);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            updateBandu = UpdateBandu.getUpdateBandu();
            if(!updateBandu.isShowOff()){//显示过就不在重复显示了
                updateBandu.setShowOff(true);
                Point point = new Point();
                getWindowManager().getDefaultDisplay().getSize(point);
                int versionCode = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
                updateBandu.setWidthAndHeight(point.x,point.y);
                updateBandu.update(context == null ? this : context, versionCode,2);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != updateBandu)
            updateBandu.myUpdateDialogDismiss();
    }

    public void getMsgNum() {
        new SysMsgNumMiddle(this, this).getSysMsgNum(UserUtil.getUerInfo(this).getUid(), new SysMsgNumBean());
    }


    @OnClick({R.id.llMyClass, R.id.llMyInfo, R.id.title_right,R.id.rlTeacherQingLive})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.llMyClass:
                index = 0;
                rightImg.setImageResource(R.mipmap.icon_idea);
                redTv.setVisibility(View.GONE);
                setTitleGone(false);
                title_middle.setText("我的班级");
                Fragment fragment1 = HomeFragmentFactory.createFragment(R.id.llMyClass);
                replaceFragment(fragment1);
                //getFragmentManager().beginTransaction().replace(R.id.flContent, HomeFragmentFactory.createFragment(R.id.llMyClass)).commit();
                updateUI(view.getId());
                break;
            case R.id.llMyInfo:
                index = 1;
                rightImg.setImageResource(R.mipmap.mail);
                if (GlobalParams.isShow) {
                    redTv.setVisibility(View.VISIBLE);
                } else {
                    redTv.setVisibility(View.GONE);
                }
                setTitleGone(false);
                title_middle.setText("个人中心");
               // getFragmentManager().beginTransaction().replace(R.id.flContent, HomeFragmentFactory.createFragment(R.id.llMyInfo)).commit();
                HomeFragmentFactory.destroyFragment(R.id.llMyInfo);
                Fragment fragment = HomeFragmentFactory.createFragment(R.id.llMyInfo);
                replaceFragment(fragment);
                updateUI(view.getId());
                break;
            //添加直播
            case R.id.rlTeacherQingLive:
                if(UserUtil.isLogin()){
                    setTitleGone(true);//3.2设置标题显示/隐藏
                    HomeFragmentFactory.destroyFragment(R.id.rlTeacherQingLive);
                    Fragment teachLiveFgment = HomeFragmentFactory.createFragment(R.id.rlTeacherQingLive);
                    replaceFragment(teachLiveFgment);
                    updateUI(view.getId());
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.title_right:
                if (index == 0) {
                    startActivityForResult(new Intent(this, FeedBackActivity.class), 9);
                } else {
                    startActivityForResult(new Intent(this, SystemMessageActivity.class), 9);
                }
                break;
        }
    }

    public void setTitleGone(boolean flag) {
        if (flag) {
            title_rl.setVisibility(View.GONE);
        } else {
            title_rl.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(TeacherHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog1.show();
    }

    private void replaceFragment(final Fragment fragment){
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragmentManager().beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commitAllowingStateLoss();
            }
        }, 100);
    }
    private void updateUI(int viewID) {
        tvMyClass.setTextColor(UIUtils.getColor(viewID == R.id.llMyClass ? R.color.tv_color_my_pressed : R.color.tv_color_my_default));
        ivMyClass.setImageResource(viewID == R.id.llMyClass ? R.mipmap.icon_myclass_pressed : R.mipmap.icon_myclass);

        tvMyInfo.setTextColor(UIUtils.getColor(viewID == R.id.llMyInfo ? R.color.tv_color_my_pressed : R.color.tv_color_my_default));
        ivMyInfo.setImageResource(viewID == R.id.llMyInfo ? R.mipmap.icon_myinfo_pressed : R.mipmap.icon_myinfo);

        tvTeacherQingLive.setTextColor(UIUtils.getColor(viewID == R.id.rlTeacherQingLive ? R.color.tv_color_my_pressed : R.color.tv_color_my_default));
        ivTeacherQingLive.setImageResource(viewID == R.id.rlTeacherQingLive ? R.mipmap.qing_icon : R.mipmap.qing_icon_wei);
    }

    public int curr_position = 0;
    public int imageResID[] = new int[]{R.mipmap.delete, R.mipmap.delete1, R.mipmap.delete2};


    public void showDialog(){
        // 朦版
        SharedPreferences spTea = getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE);
        boolean first = spTea.getBoolean("teacher_first", true);
        if (PreferencesUtils.getUserInfo() != null && PreferencesUtils.isFirstLogin() && first) {
            hazyDialog();
            PreferencesUtils.setFirstLogin(false);
            PreferencesUtils.getUserInfo().setIs_first_login(false);
        }
    }
    public void hazyDialog() {
//        final Dialog dialog = new Dialog(this, R.style.custom_dialog);
        final ImageView imageView = (ImageView) findViewById(R.id.ivHazy);
        imageView.setVisibility(View.VISIBLE);
        curr_position = 0;
        imageView.setImageResource(imageResID[curr_position]);
//        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                if (dialog.isShowing()) {
                    if (curr_position == imageResID.length - 1) {
//                        dialog.dismiss();
                        imageView.setVisibility(View.GONE);
                    }
                    else {
                        curr_position++;
                        imageView.setImageResource(imageResID[curr_position]);
                    }
//                }
            }
        });

        /*
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.addContentView(imageView, layoutParams);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight());

        dialog.getWindow().setAttributes(lp);*/
        //TODO 教师点开的第一次执行
        getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE).edit().putBoolean("teacher_first", false).commit();
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
        if (!isExit) {
            isExit = true;
            UIUtils.showToastSafe("再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            UpdateBandu updateBandu = UpdateBandu.getUpdateBandu();
            updateBandu.setShowOff(false);
            SystemApplation.getInstance().exit(true);
            HomeFragmentFactory.cleanFragment();
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        if (requestCode == SysMsgNumMiddle.SYS_MSG_NUM) {
            SysMsgNumBean bean = (SysMsgNumBean) result;
            if (bean.getStatus() == 1) {
                if ("0".equals(bean.getData().getUn_read())) {
                    GlobalParams.isShow = false;
                    //redTvB.setVisibility(View.INVISIBLE);
                    refreshSchool();
                    redTv.setVisibility(View.INVISIBLE);
                } else {
                    GlobalParams.isShow = true;
                    redTvB.setVisibility(View.VISIBLE);
                    if (index == 1) {
                        redTv.setVisibility(View.VISIBLE);
                    } else {
                        redTv.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }
    public void refreshSchool(){
        LoginBean.DataEntity uerInfo = UserUtil.getUerInfo(this);
        if(uerInfo != null) {
            if (uerInfo.getSchool() != null){
                if (uerInfo.getSchool().equals("")) {
                    redTvB.setVisibility(View.VISIBLE);
                } else {
                    redTvB.setVisibility(View.INVISIBLE);
                }
            }else{
                redTvB.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onEventMainThread(String message) {
        if ("msg".equals(message)) {
            getMsgNum();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (9 == requestCode) {
            getMsgNum();
        }
    }
}

package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.middle.LoginMiddle;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
import me.bandu.talk.android.phone.utils.SystemApplation;
import me.bandu.talk.android.phone.utils.UpdateBandu;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.Utils;

/**
 * 创建者：tg
 * 类描述：登录
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class LoginActivity extends BaseAppCompatActivity {

    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.pass_edt)
    EditText passEdt;
    @Bind(R.id.login)
    Button loginBtn;
    @Bind(R.id.no_pass)
    TextView forgetPassTv;
    @Bind(R.id.regist_btn)
    Button registTv;
    @Bind(R.id.use_btn)
    Button useTv;
    @Bind(R.id.pass_img)
    ImageView passImg;

    String password;
    boolean visiable;
    private static boolean isExit = false;
    public Handler handler = new Handler();
    private UpdateBandu updateBandu;
    public static int role;//判断老师还是学生

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void toStart() {
        initView();
    }

    public void initView() {
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
                updateBandu.update(context == null ? this : context, versionCode, 2);
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

    @OnClick({R.id.login, R.id.no_pass, R.id.regist_btn, R.id.use_btn, R.id.pass_img, R.id.ivTest})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login:
                String phone = phoneEdt.getText().toString().trim();
                password = passEdt.getText().toString().trim();
                String app_info = "手机型号:" + android.os.Build.MODEL + "," + "应用版本号:" + Utils.getAppVersionName(this);
                showMyprogressDialog();
                new LoginMiddle(this, this).login(phone, password, app_info, new LoginBean());
                break;
            case R.id.no_pass:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
            case R.id.regist_btn:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.use_btn:
                GlobalParams.userInfo = new LoginBean.DataEntity();
                Intent intent = new Intent(this, StudentHomeActivity.class);
                intent.putExtra("fromTest", true);
                startActivity(intent);
                break;
            case R.id.pass_img:
                if (!visiable) {
                    passEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.visiable);
                    visiable = true;
                } else {
                    passEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.invisiable);
                    visiable = false;
                }
                break;
            case R.id.ivTest:
                break;
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        hideMyprogressDialog();
        final LoginBean bean = (LoginBean) result;
        if (bean != null && bean.getStatus() == 1) {
            //UIUtils.showToastSafe(getString(R.string.login_succ));
            GlobalParams.userInfo = bean.getData();
            PreferencesUtils.setFirstLogin(bean.getData().is_first_login());
            PreferencesUtils.setFirstBg(bean.getData().is_first_login());
            ChivoxConstants.userId = bean.getData().getUid() + "";
            PreferencesUtils.setUid(ChivoxConstants.userId);
            GlobalParams.userInfo.setPassword(password);
            saveUserInfo(bean.getData());
            PreferencesUtils.saveUserInfo(bean.getData());
            PushAgent mPushAgent = PushAgent.getInstance(this);
            mPushAgent.enable(new IUmengRegisterCallback() {
                @Override
                public void onRegistered(String s) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PushAgent.getInstance(LoginActivity.this).setAlias(UserUtil.getUid(), "uid");
                            PushAgent.getInstance(LoginActivity.this).setExclusiveAlias(UserUtil.getUid(), "uid");
                        }
                    });
                }
            });
            mPushAgent.setAlias(UserUtil.getUid(), "uid");
            mPushAgent.setExclusiveAlias(UserUtil.getUid(), "uid");
            Intent intent = null;
            if (bean.getData().getRole() == 1) {
                //进入教师主页
                role = 1;
                intent = new Intent(this, TeacherHomeActivity.class);
            } else if (bean.getData().getRole() == 2) {
                //进入学生主页
                role = 2;
                intent = new Intent(this, StudentHomeActivity.class);
            }
            startActivity(intent);
            this.finish();
        } else {
            UIUtils.showToastSafe(bean.getMsg());
        }
    }

    /**
     * 保存登录信息
     *
     * @param bean
     */
    public void saveUserInfo(LoginBean.DataEntity bean) {
        PreferencesUtils.saveUserInfo(bean);
        SharedPreferences sp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", true);  //是否登录过  true：是   false：否
        //提交当前数据
        editor.commit();
        File tempFile = new File(getDir("user", MODE_PRIVATE).getAbsolutePath() + "/user");
        tempFile.mkdirs();
        SaveBeanToFile.beanToFile(bean, new File(tempFile, "user.data"));
    }

    @Override
    public void onFailed(int requestCode) {
        UIUtils.showToastSafe(getString(R.string.login_failed_info));
        hideMyprogressDialog();
    }


    @Override
    protected void onResume() {
        super.onResume();
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
            UIUtils.showToastSafe(getString(R.string.press_again));
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            UpdateBandu updateBandu = UpdateBandu.getUpdateBandu();
            updateBandu.setShowOff(false);
            SystemApplation.getInstance().exit(true);
        }
    }
/*    @Override
    public void init(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }*/


    //    点击响应方法getEachWord（）内容如下
    public void getEachWord(TextView textView) {
        Spannable spans = (Spannable) textView.getText();
        Integer[] indices = getIndices(textView.getText().toString().trim() + " ", ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i < indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
//          to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
//         改变选中文本的高亮颜色
        textView.setHighlightColor(Color.BLUE);
    }

    private ClickableSpan getClickableSpan() {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                String s = tv.getText().subSequence(tv.getSelectionStart(), tv.getSelectionEnd()).toString();
                Log.w("FUCK", s);
                //去掉标点符号
                //展示内容
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    public Integer[] getIndices(String ss, char c) {
        int pos = ss.indexOf(c, 0);
        List<Integer> integers = new ArrayList<>();
        while (pos != -1) {
            integers.add(pos);
            pos = ss.indexOf(c, pos + 1);
        }
        return integers.toArray(new Integer[0]);
    }


}

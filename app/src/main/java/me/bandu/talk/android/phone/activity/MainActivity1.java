package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.DFHT.ENGlobalParams;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.File;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.StartPageBean;
import me.bandu.talk.android.phone.middle.StartPageMiddle;
import me.bandu.talk.android.phone.utils.CacheUtils;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;

/**
 * 创建者:taoge
 * 时间：2015/12/11
 * 类描述：每日一句
 * 修改人:taoge
 * 修改时间：2015/12/11
 * 修改备注：
 */
public class MainActivity1 extends BaseAppCompatActivity {

    WebView webView;
    Button btn;
    boolean isLogin;
    LoginBean.DataEntity info;

    public Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void toStart() {
        webView = (WebView) findViewById(R.id.webview);
        btn = (Button) findViewById(R.id.btn);
        new StartPageMiddle(this, this).getHtml(new StartPageBean());
        SharedPreferences sp = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", false);
        try {
            info = SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class, new File(getDir("user", MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
            PreferencesUtils.saveUserInfo(info);
            PreferencesUtils.setUid(info.getUid());
        } catch (Exception e) {
            info = null;
            //FileUtil.deleteFile(new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
        }


        //String customHtml = "<html><body><font color='red'>hello baidu!</font></body></html>";
        //CacheUtils.getInstance().putStringCache(this,customHtml,"htmlString");
        // CacheUtils.getInstance().getStringCache(this,"htmlString");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBean.DataEntity userInfo = PreferencesUtils.getUserInfo();
                if (isLogin || info != null || userInfo != null) {
                    if (info != null && userInfo != null) {
                        info.setIs_first_login(false);
                        GlobalParams.userInfo = info;

                        PreferencesUtils.setUid(info.getUid());
                        Intent intent = null;
                        if (info.getRole() == 1) {
                            //进入老师主页
                            intent = new Intent(MainActivity1.this, TeacherHomeActivity.class);
                        } else if (info.getRole() == 2) {
                            //进入学生主页
                            intent = new Intent(MainActivity1.this, StudentHomeActivity.class);
                        }else {
                            intent = new Intent(MainActivity1.this, LoginActivity.class);
                        }
                        PushAgent mPushAgent = PushAgent.getInstance(MainActivity1.this);
                        mPushAgent.enable(new IUmengRegisterCallback() {
                            @Override
                            public void onRegistered(String s) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        PushAgent.getInstance(MainActivity1.this).setAlias(info.getUid(), "uid");
                                        PushAgent.getInstance(MainActivity1.this).setExclusiveAlias(info.getUid(), "uid");
                                    }
                                });
                            }
                        });
                        mPushAgent.setAlias(info.getUid(), "uid");
                        mPushAgent.setExclusiveAlias(info.getUid(), "uid");
                        startActivity(intent);
                        //MainActivity1.this.finish();
                    } else {
                        startActivity(new Intent(MainActivity1.this, LoginActivity.class));
                    }
                } else {
                    // startActivity(new Intent(MainActivity1.this,MainActivity.class));
                    startActivity(new Intent(MainActivity1.this, LoginActivity.class));
                }
                finish();
            }
        });
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        StartPageBean bean = (StartPageBean) result;
        if (bean != null && bean.getStatus() == 1) {
            String htmlString = bean.getData().getContent();
            CacheUtils.getInstance().putStringCache(this, htmlString, "start");
            webView.loadData(htmlString, "text/html;charset=UTF-8", null);

        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        String htmlString = CacheUtils.getInstance().getStringCache(this, "start");
        webView.loadData(htmlString, "text/html;charset=UTF-8", null);
    }
}

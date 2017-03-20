package me.bandu.talk.android.phone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseActivity;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.SystemApplation;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/23 13:19
 * 类描述：基类activity
 * 修改人：gaoye
 * 修改时间：11/30
 * 修改备注：状态栏一体化与title颜色设置
 */
public abstract class BaseAppCompatActivity extends BaseActivity {
    private MyProgressDialog mygProgressDialog = null;
    @Nullable
    @Bind(R.id.title_tv)
    TextView title_tv;
    @Nullable
    @Bind(R.id.title_rl)
    RelativeLayout rl_title;
    InputMethodManager manager;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        init();
//        setContentView(getLayoutId());
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        } else if (getLayoutView() != null) {
            setContentView(getLayoutView());
        }
        ButterKnife.bind(this);
        //如果sdk版本是4.4以上 5.0以下
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && rl_title != null){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            initSystemBar();
        }*/
        if (title_tv != null)
            title_tv.setText(setTitle());
        SystemApplation.getInstance().addActivity(this);
        toStart();
    }


    /**
     * 根据屏幕状态栏高度 重新设置title高度 并设置title的padding
     */
    private void initSystemBar() {
        int statebarheight = ScreenUtil.getStatusBarHeight(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50, this) + statebarheight);
        rl_title.setLayoutParams(params);
        rl_title.setPadding(0, statebarheight, 0, 0);
    }

    /**
     * 设置title的背景颜色值
     */
    protected void setTitleColorResource(int colorResource) {
        if (rl_title != null) {
            rl_title.setBackgroundResource(colorResource);
        }
    }

    /**
     * 返回按钮点击事件
     *
     * @return
     */
    @Nullable
    @OnClick(R.id.goback)
    public void goback(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Nullable
    @OnClick(R.id.title_right)
    public void titleRight(View view) {
        clickRight();
    }

    /**
     * 标题栏右侧点击事件
     *
     * @return
     */
    public void clickRight() {
    }

    ;

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 布局文件
     *
     * @return
     */
    protected View getLayoutView() {
        return null;
    }

    /**
     * 设置标题
     *
     * @return
     */
    protected String setTitle() {
        return "";
    }

    /**
     * 业务逻辑开始方法
     */
    protected abstract void toStart();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void success(Object result, int requestCode) {

    }

    @Override
    public void failed(int requestCode) {

    }

    public void init() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            try {
                if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onTouchEvent(event);
    }

    public void showMyprogressDialog() {
        showMyprogressDialog("");
    }

    public void showMyprogressDialog(String message) {
        if (mygProgressDialog == null) {
            mygProgressDialog = MyProgressDialog.createProgressDialog(this, StringUtil.getShowText(message));
            mygProgressDialog.setCancelable(true);
            mygProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!mygProgressDialog.isShowing())
            mygProgressDialog.show();
    }

    public void cleanMyprogressDialog() {
        if (mygProgressDialog != null) {
            mygProgressDialog.cancel();
            mygProgressDialog = null;
        }

    }

    public void hideMyprogressDialog() {
        try {
            if (mygProgressDialog == null) {
                mygProgressDialog = MyProgressDialog.createProgressDialog(this, "");
                mygProgressDialog.setCancelable(true);
                mygProgressDialog.setCanceledOnTouchOutside(false);
            }
            if (mygProgressDialog.isShowing())
                mygProgressDialog.dismiss();
        } catch (Exception e) {
            mygProgressDialog = null;
            e.printStackTrace();
        }
    }

}

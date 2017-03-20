package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.ScreenUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseTeacherActivity extends BaseAppCompatActivity {
    public abstract void initView();
    public abstract void setData();
    public abstract void setListeners();
}

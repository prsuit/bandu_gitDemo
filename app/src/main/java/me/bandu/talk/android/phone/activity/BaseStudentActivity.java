package me.bandu.talk.android.phone.activity;

import android.os.Bundle;

import com.DFHT.prompt.dialog.MyProgressDialog;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseStudentActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleColorResource(R.color.student_title_bg);
    }

    public abstract void initView();
    public abstract void setData();
    public abstract void setListeners();

}

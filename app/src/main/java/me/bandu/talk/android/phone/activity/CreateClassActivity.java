package me.bandu.talk.android.phone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.CreateClass;
import me.bandu.talk.android.phone.middle.CreateClassMiddle;
import me.bandu.talk.android.phone.utils.Utils;
import me.bandu.talk.android.phone.view.CreateClassDialog;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 11:06
 * 类描述：创建班级
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreateClassActivity extends BaseAppCompatActivity implements BaseActivityIF {

    @Bind(R.id.title_right)
    TextView title_right;
    @Bind(R.id.title_middle)
    TextView title_middle;

    @Bind(R.id.etClassName)
    EditText etClassName;
    @Bind(R.id.clear_img)
    ImageView clearImg;

    private CreateClassMiddle middle;

    private String cid;
    private String classname;

    private void initTitle() {
        title_middle.setText(UIUtils.getString(R.string.set_classname));
        title_right.setVisibility(View.GONE);
        middle = new CreateClassMiddle(this, this);
        Intent intent = getIntent();
        if (intent != null) {
            cid = intent.getStringExtra("cid");
            classname = intent.getStringExtra("classname");
        }
        etClassName.setText(classname);  // 3.4的班级名称回现。
        etClassName.setSelection(etClassName.getText().length()); //让光标定位到最后一个字

    }

    @Override
    protected int getLayoutId() {
        return R.layout.create_class_activity;
    }

    @Override
    protected void toStart() {
        initTitle();
        initEvent();
    }

    @OnClick({R.id.btnCreateClass, R.id.title_rl, R.id.clear_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreateClass:
                String className = etClassName.getText().toString().trim();
                if (!TextUtils.isEmpty(className)) {
                    if (Utils.isIllegal(className)) {
                        showMyprogressDialog();
                        if (TextUtils.isEmpty(cid))
                            createClass(className);
                        else
                            updateClass(className);
                    } else {
                        UIUtils.showToastSafe(getString(R.string.classname_special_char_info));
                    }
                } else {
                    UIUtils.showToastSafe(getString(R.string.classname_null_info));
                }
                break;
            case R.id.title_rl:
                onBackPressed();
                break;
            case R.id.clear_img:
                etClassName.setText("");
                break;
        }
    }

    /* 更新班级 */
    private void updateClass(String className) {
        middle.modifyClass(className, cid);
    }

    /* 创建班级 */
    private void createClass(String className) {
        middle.createClass(className);
    }

    @Override
    public void successFromMid(Object... obj) {

        final CreateClass data = (CreateClass) obj[0];
        int request = (int) obj[1];
        if (data.getStatus() == 1) {
            switch (request) {
                case CreateClassMiddle.CREATE_CLASS:
                    GlobalParams.HOME_CHANGED = true;
                    UIUtils.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            createDialog(data.getData().getClass_name(), data.getData().getCid());
                            hideMyprogressDialog();
                        }
                    }, 100);
                    break;
                case CreateClassMiddle.CLASS_MODIFY:
                    Intent intent = new Intent();
                    intent.putExtra("change", true);
                    intent.putExtra("class", etClassName.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    hideMyprogressDialog();
                    this.finish();
                    break;
            }

        } else {
            hideMyprogressDialog();
            LogUtils.d("创建失败了" + data.getMsg());
            if (TextUtils.isEmpty(cid))
                UIUtils.showToastSafe(getString(R.string.create_try_again));
            else
                UIUtils.showToastSafe(getString(R.string.modify_try_again));
        }
    }

    public void initEvent() {
        etClassName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        etClassName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    clearImg.setVisibility(View.VISIBLE);
                } else {
                    clearImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void failedFrom(Object... obj) {
        hideMyprogressDialog();
        UIUtils.showToastSafe(R.string.create_try_again);
    }


    public void createDialog(String className, String num) {
        CreateClassDialog.Builder builder = new CreateClassDialog.Builder(this);
        builder.setClassName(className).setNumStr(num).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        CreateClassDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
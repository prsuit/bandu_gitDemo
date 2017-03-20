package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.AddSchoolBean;
import me.bandu.talk.android.phone.middle.AddSchoolMiddle;
import me.bandu.talk.android.phone.middle.ModifyUserInfoMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.Utils;
import me.bandu.talk.android.phone.view.CustomDialog;
/**
 * 创建者：tg
 * 类描述：添加学校
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AddSchoolActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.name)
    TextView schoolTv;
    @Bind(R.id.pass_edt)
    EditText schoolEdt;
    @Bind(R.id.clear_img)
    ImageView clearImg;

    TextView tv;
    Handler handler = new Handler();
    CustomDialog dialog;
    private int sec = 3;
    String schoolName;
    String districtId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void toStart() {
        titleTv.setText(getString(R.string.add_class));
        schoolTv.setText(getString(R.string.school));
        schoolEdt.setHint(getString(R.string.school_input_hint));
        initEvent();
        districtId = getIntent().getStringExtra("dicId");
    }

    @OnClick({R.id.ok,R.id.clear_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.ok:
                schoolName = schoolEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(schoolName)) {
                    if (Utils.isIllegal(schoolName)) {
                        new AddSchoolMiddle(this, this).createSchool(UserUtil.getUerInfo(this).getUid(), districtId, schoolName, new AddSchoolBean());
                    } else {
                        UIUtils.showToastSafe(getString(R.string.special_char_info));
                    }

                } else {
                    UIUtils.showToastSafe(getString(R.string.school_null_info));
                }
                break;
            case R.id.clear_img:
                schoolEdt.setText("");
                break;
        }

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (sec >= 0) {
                tv.setText(sec + getString(R.string.coutdown_info));
                handler.postDelayed(this, 1000);
                sec--;
            } else {
                dialog.dismiss();
                handler.removeCallbacks(runnable);
                startActivity(new Intent(AddSchoolActivity.this, PersonalDataActivity.class));
            }
        }
    };

    @Override
    public void onSuccess(Object result, int requestCode) {
        switch (requestCode) {
            case 1:
                AddSchoolBean bean = (AddSchoolBean) result;
                if (bean != null && bean.getStatus() == 1) {
                    GlobalParams.sid = bean.getData().getSid();
                    GlobalParams.userInfo.setSchool(bean.getData().getName());
                    new ModifyUserInfoMiddle(this,this).modifySchool(GlobalParams.userInfo.getUid(),bean.getData().getSid());
                    View view = LayoutInflater.from(this).inflate(R.layout.customdialog, null, false);
                    tv = (TextView) view.findViewById(R.id.time_tv);
                    dialog = new CustomDialog(this, R.style.mystyle, view);
                    dialog.show();
                    handler.postDelayed(runnable, 0);
                }
                break;
            case 2:
                BaseBean bean1 = (BaseBean) result;
                if (bean1 != null && bean1.getStatus() == 1) {
                    UserUtil.saveUserInfo(this,GlobalParams.userInfo);
                    UIUtils.showToastSafe(bean1.getMsg());
                }
                break;
        }

    }

    public void initEvent() {
        schoolEdt.addTextChangedListener(new TextWatcher() {
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
    public void onFailed(int requestCode) {
        super.failed(requestCode);
    }
}

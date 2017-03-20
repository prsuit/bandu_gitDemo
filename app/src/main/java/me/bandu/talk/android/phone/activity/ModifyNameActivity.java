package me.bandu.talk.android.phone.activity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.middle.ModifyUserInfoMiddle;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：tg
 * 类描述：修改姓名
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyNameActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pass_edt)
    EditText nameEdt;
    @Bind(R.id.clear_img)
    ImageView clearImg;

    String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.modify_name);
        nameEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        initEvent();
    }

    @OnClick({R.id.ok, R.id.clear_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.ok:
                name = nameEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    new ModifyUserInfoMiddle(this, this).modifyName(UserUtil.getUid(), name);
                } else {
                    UIUtils.showToastSafe(getString(R.string.name_input));
                }
                break;
            case R.id.clear_img:
                nameEdt.setText("");
                break;
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        BaseBean bean = (BaseBean) result;
        if (bean != null && bean.getStatus() == 1) {
            GlobalParams.userInfo.setName(name);
            LoginBean.DataEntity userInfo = PreferencesUtils.getUserInfo();
            userInfo.setName(name);
            PreferencesUtils.saveUserInfo(userInfo);
            File tempFile = new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user");
            tempFile.mkdirs();
            SaveBeanToFile.beanToFile(GlobalParams.userInfo, new File(tempFile, "user.data"));
            UIUtils.showToastSafe(bean.getMsg());
            this.finish();
        } else {

        }


    }

    public void initEvent() {
        nameEdt.addTextChangedListener(new TextWatcher() {
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
        super.onFailed(requestCode);
    }
}

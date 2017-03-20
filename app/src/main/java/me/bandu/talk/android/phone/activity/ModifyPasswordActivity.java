package me.bandu.talk.android.phone.activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import me.bandu.talk.android.phone.middle.ModifyUserInfoMiddle;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
/**
 * 创建者：tg
 * 类描述：修改密码
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyPasswordActivity extends BaseAppCompatActivity {


    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pass1_edt)
    EditText passOld;
    @Bind(R.id.pass2_edt)
    EditText passNew;
    @Bind(R.id.pass_img)
    ImageView passImg;

    String newPass;
    boolean visiable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.modify_pass);
    }

    @OnClick({R.id.ok,R.id.pass_img})
    void click(View v){
        switch (v.getId()) {
            case R.id.ok:
                String oldPass = passOld.getText().toString().trim();
                newPass = passNew.getText().toString().trim();
                if (!TextUtils.isEmpty(oldPass)) {
                    if (oldPass.equals(GlobalParams.userInfo.getPassword())) {
                        if (!TextUtils.isEmpty(newPass)) {
                            new ModifyUserInfoMiddle(this,this).modifyPass(GlobalParams.userInfo.getUid(),newPass);
                        } else {
                            UIUtils.showToastSafe(getString(R.string.input_new_pass));
                        }
                    } else {
                        UIUtils.showToastSafe(getString(R.string.wrong_pass));
                    }
                } else {
                    UIUtils.showToastSafe(getString(R.string.input_old_pass));
                }
                break;
            case R.id.pass_img:
                if (!visiable) {
                    passNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.visiable);
                    visiable = true;
                } else {
                    passNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.invisiable);
                    visiable = false;
                }
                break;
        }

    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        BaseBean bean = (BaseBean) result;
        if (bean != null && bean.getStatus() == 1) {
            GlobalParams.userInfo.setPassword(newPass);
            File tempFile = new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user");
            tempFile.mkdirs();
            SaveBeanToFile.beanToFile(GlobalParams.userInfo, new File(tempFile, "user.data"));
        } else {

        }
        UIUtils.showToastSafe(bean.getMsg());
        finish();
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }
}

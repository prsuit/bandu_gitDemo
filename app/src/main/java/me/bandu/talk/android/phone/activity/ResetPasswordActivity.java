package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.middle.GetCaptchaMiddle;
import me.bandu.talk.android.phone.middle.ResetPasswordMiddle;
import me.bandu.talk.android.phone.utils.Utils;

/**
 * 创建者：tg
 * 类描述：重置密码
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ResetPasswordActivity extends BaseAppCompatActivity {


    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.code_edt)
    EditText codeEdt;
    @Bind(R.id.code_tv)
    TextView codeTv;
    @Bind(R.id.pass1_edt)
    EditText pass1Edt;
    @Bind(R.id.confirm)
    Button okBtn;
    @Bind(R.id.pass_img)
    ImageView passImg;

    String phone;
    String code;
    String pass1;
    boolean visiable;
    CountDownTimer countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.reset_pass);
    }

    @OnClick({R.id.code_tv, R.id.confirm,R.id.pass_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.code_tv:
                phone = phoneEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.isMobile(phone)) {
                        codeTv.setClickable(false);
                        new GetCaptchaMiddle(this, this).getcaptcha(phone, 1, new BaseBean());
                    } else {
                        UIUtils.showToastSafe(getString(R.string.wrong_phone));
                    }
                } else {
                    UIUtils.showToastSafe("手机号不能为空！");
                }
                break;
            case R.id.confirm:
                code = codeEdt.getText().toString().trim();
                pass1 = pass1Edt.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(pass1)) {
                    new ResetPasswordMiddle(this, this).reset(phone, code, pass1, new BaseBean());
                } else {
                    UIUtils.showToastSafe(getString(R.string.input_all));
                }
                break;
            case R.id.pass_img:
                if (!visiable) {
                    pass1Edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.visiable);
                    visiable = true;
                } else {
                    pass1Edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.invisiable);
                    visiable = false;
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        switch (requestCode) {
            case 1:
                BaseBean bean1 = (BaseBean) result;
                if (bean1 != null && bean1.getStatus() == 1) {
                    UIUtils.showToastSafe(bean1.getMsg());
                    startActivity(new Intent(this, LoginActivity.class));
                } else if (bean1 != null && bean1.getStatus() == 0) {
                    UIUtils.showToastSafe(bean1.getMsg());
                }
                break;
            case 2:
                BaseBean baseBean = (BaseBean) result;
                if (baseBean != null && baseBean.getStatus() == 1) {
                    countDownTimer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            codeTv.setText(millisUntilFinished/1000+"");
                        }

                        @Override
                        public void onFinish() {
                            codeTv.setText(R.string.get_again);
                            codeTv.setClickable(true);
                        }
                    }.start();
                } else {
                    codeTv.setClickable(true);
                }
                UIUtils.showToastSafe(baseBean.getMsg());
                break;
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

}

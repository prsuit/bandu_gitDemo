package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.middle.GetCaptchaMiddle;
import me.bandu.talk.android.phone.middle.ModifyPhoneMiddle;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：修改密码 验证手机
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyPhoneDetailActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.code_edt)
    EditText codeEdt;
    @Bind(R.id.code_tv)
    TextView codeTv;

    String phone;
    String code;
    CountDownTimer countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_phone_detail;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.modify_phone);
    }

    @OnClick({R.id.code_tv, R.id.ok})
    void click(View v) {
        switch (v.getId()) {
            case R.id.code_tv:
                phone = phoneEdt.getText().toString().trim();
                if(TextUtils.isEmpty(phone)) {
//                    Toast.makeText(this, getResources().getText(R.string.check_phone_number), Toast.LENGTH_SHORT).show();
                    return;
                }
                new GetCaptchaMiddle(this, this).getcaptcha(phone,0,new BaseBean());
                break;
            case R.id.ok:
                code = codeEdt.getText().toString().trim();
                phone = phoneEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)) {
                    if (Utils.isMobile(phone)) {
                        codeTv.setClickable(false);
                        new ModifyPhoneMiddle(this, this).modifyPhone(GlobalParams.userInfo.getUid(), phone, code, new BaseBean());
                    } else {
                        UIUtils.showToastSafe(getString(R.string.wrong_phone));
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        switch (requestCode) {
            case 1:
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                BaseBean baseBean1 = (BaseBean) result;
                if (baseBean1 != null && baseBean1.getStatus() == 1) {
                    UIUtils.showToastSafe(getString(R.string.modify_succ));
                    GlobalParams.userInfo.setPhone(phone);
                    File tempFile = new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user");
                    tempFile.mkdirs();
                    SaveBeanToFile.beanToFile(GlobalParams.userInfo, new File(tempFile, "user.data"));
                    startActivity(new Intent(this,PersonalDataActivity.class));
                } else {
                    UIUtils.showToastSafe(baseBean1.getMsg());
                }
                break;
            case 2:
                BaseBean baseBean2 = (BaseBean) result;
                if (baseBean2 != null && baseBean2.getStatus() == 1) {
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
                    UIUtils.showToastSafe(getString(R.string.send_succ));
                } else {
                    codeTv.setClickable(true);
                    UIUtils.showToastSafe(baseBean2.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }
}

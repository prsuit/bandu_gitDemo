package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.PhoneUtil;
import com.DFHT.utils.UIUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.RegistBean;
import me.bandu.talk.android.phone.middle.GetCaptchaMiddle;
import me.bandu.talk.android.phone.middle.LoginMiddle;
import me.bandu.talk.android.phone.middle.RegistMiddle;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：注册
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RegistActivity extends BaseAppCompatActivity {


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
    @Bind(R.id.agree_img)
    ImageView iBtn;
    @Bind(R.id.agreement_tv)
    TextView agreementTv;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.name_edt)
    EditText nameEdt;
    @Bind(R.id.student_tv)
    TextView studentTv;
    @Bind(R.id.teacher_tv)
    TextView teacherTv;
    @Bind(R.id.pass_img)
    ImageView passImg;

    private boolean isSelect1;
    private boolean isSelect2;

    private boolean isCheck = true;
    private boolean visiable;
    String name; //姓名
    String phone;//手机号
    String code;//验证码
    String password; //密码
    int type;  //1:老师   2：学生
    CountDownTimer countDownTimer;
    public Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.regist);
    }

    @OnClick({R.id.code_tv, R.id.agree_img, R.id.agreement_tv, R.id.next_btn,R.id.student_tv,R.id.teacher_tv,R.id.pass_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.student_tv:
                type = 2;
                if (!isSelect1){
                    isSelect1 = true;
                    studentTv.setBackgroundResource(R.drawable.back_selected_round);
                    studentTv.setTextColor(Color.parseColor("#002380"));
                }
                isSelect2 = false;
                teacherTv.setTextColor(Color.parseColor("#ff6c17"));
                teacherTv.setBackgroundResource(R.drawable.back_select_round1);
                break;
            case R.id.teacher_tv:
                type = 1;
                if (!isSelect2){
                    isSelect2 = true;
                    teacherTv.setTextColor(Color.parseColor("#f00000"));
                    teacherTv.setBackgroundResource(R.drawable.back_selected_round1);
                }
                isSelect1 = false;
                studentTv.setTextColor(Color.parseColor("#00a1e8"));
                studentTv.setBackgroundResource(R.drawable.back_select_round);
                break;
            case R.id.code_tv:
                phone = phoneEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    if (Utils.isMobile(phone)) {
                        codeTv.setClickable(false);
                        showMyprogressDialog();
                        new GetCaptchaMiddle(this, this).getcaptcha(phone, 0, new BaseBean());
                    } else {
                        UIUtils.showToastSafe(getString(R.string.wrong_phone));
                    }
                } else {
                    UIUtils.showToastSafe("手机号不能为空！");
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
            case R.id.agree_img:
                if (!isCheck) {
                    iBtn.setImageResource(R.drawable.check);
                    isCheck = true;
                } else {
                    iBtn.setImageResource(R.drawable.uncheck);
                    isCheck = false;
                }
                break;
            case R.id.agreement_tv:
                startActivity(new Intent(this,AgreementActivity.class));
                break;
            case R.id.next_btn:
                LogUtils.i("sign:"+ PhoneUtil.getSign(this));
                phone = phoneEdt.getText().toString().trim();
                code = codeEdt.getText().toString().trim();
                password = pass1Edt.getText().toString().trim();
                name = nameEdt.getText().toString().trim();
                if (isCheck) {
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && !TextUtils.
                            isEmpty(password)&&!TextUtils.isEmpty(name)) {
                        if (Utils.isIllegal(name)) {
                            if (Utils.isMobile(phone)) {
                                showMyprogressDialog();
                                new RegistMiddle(this, this).regist(name, type, phone, code, password, new RegistBean());
                            } else {
                                UIUtils.showToastSafe(getString(R.string.wrong_phone));
                            }

                        } else {
                            UIUtils.showToastSafe(getString(R.string.name_special_char));
                        }
                    } else {
                        UIUtils.showToastSafe(getString(R.string.input_all));
                    }
                } else {
                    UIUtils.showToastSafe(getString(R.string.read_agreement));
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {

        if (2 == requestCode) {
            hideMyprogressDialog();
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
        } else if (1 == requestCode) {
            hideMyprogressDialog();
            if (countDownTimer != null) {
                countDownTimer.cancel();
                codeTv.setText(R.string.get_again);
                codeTv.setClickable(true);
            }
            RegistBean bean = (RegistBean) result;
            if (bean != null /*&& bean.getStatus() == 1*/) {
                String app_info = "手机型号:"+android.os.Build.MODEL+","+"应用版本号:"+ Utils.getAppVersionName(this);
                new LoginMiddle(this, this).login(phone, password, app_info,new LoginBean());
                GlobalParams.userInfo = new LoginBean.DataEntity();
                GlobalParams.userInfo.setUid(bean.getData().getUid());
                GlobalParams.userInfo.setRole(bean.getData().getRole());
                GlobalParams.userInfo.setName(name);
                GlobalParams.userInfo.setPhone(phone);
                GlobalParams.userInfo.setIs_first_login(true);
                PreferencesUtils.setFirstLogin(true);
                PreferencesUtils.setFirstBg(true);
                saveUserInfo(GlobalParams.userInfo);
                PreferencesUtils.saveUserInfo(GlobalParams.userInfo);
                PushAgent mPushAgent = PushAgent.getInstance(this);
                mPushAgent.enable(new IUmengRegisterCallback() {
                    @Override
                    public void onRegistered(String s) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                PushAgent.getInstance(RegistActivity.this).setAlias(GlobalParams.userInfo.getUid(), "uid");
                                PushAgent.getInstance(RegistActivity.this).setExclusiveAlias(GlobalParams.userInfo.getUid(),"uid");
                            }
                        });
                    }
                });
                if (bean.getData().getRole() == 1) {
                    startActivity(new Intent(this,TeacherHomeActivity.class));
                    getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE).edit().putBoolean("teacher_first", true).commit();
                } else if (bean.getData().getRole() == 2) {
                    startActivity(new Intent(this, StudentHomeActivity.class));
                    getSharedPreferences(ConstantValue.SPCONFIG, MODE_PRIVATE).edit().putBoolean("student_first", true).commit();
                }
            } else {
                UIUtils.showToastSafe(bean.getMsg());
            }
        }

    }

    /**
     * 保存登录信息
     * @param bean
     */
    public void saveUserInfo(LoginBean.DataEntity bean){
        SharedPreferences sp= getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",true);  //是否登录过  true：是   false：否
        //提交当前数据
        editor.commit();
        File tempFile = new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user");
        tempFile.mkdirs();
        SaveBeanToFile.beanToFile(bean, new File(tempFile, "user.data"));
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        codeTv.setClickable(true);
    }
}

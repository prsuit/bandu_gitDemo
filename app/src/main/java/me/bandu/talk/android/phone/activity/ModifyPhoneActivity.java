package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：修改手机
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ModifyPhoneActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pass_edt)
    EditText passEdt;
    @Bind(R.id.pass_img)
    ImageView passImg;

    boolean visiable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_phone;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.modify_phone);
    }

    @OnClick({R.id.ok, R.id.pass_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.ok:
                String pass = passEdt.getText().toString().trim();
                LoginBean.DataEntity userInfo = Utils.getUserInfo(this);
                if (null != userInfo) {
                    if (pass.equals(userInfo.getPassword())) {
                        startActivity(new Intent(this, ModifyPhoneDetailActivity.class));
                        finish();
                        return;
                    }
                }
                UIUtils.showToastSafe(getString(R.string.wrong_pass));
                break;
            case R.id.pass_img:
                if (!visiable) {
                    passEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.visiable);
                    visiable = true;
                } else {
                    passEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passImg.setImageResource(R.mipmap.invisiable);
                    visiable = false;
                }
                break;
        }

    }
}

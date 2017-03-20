package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.middle.ClassTransferMiddle;

/**
 * 创建者:taoge
 * 时间：2015/11/23 14:40
 * 类描述：班级转让
 * 修改人:taoge
 * 修改时间：2015/11/23 14:40
 * 修改备注：
 */
public class ClassTransferActivity extends BaseAppCompatActivity{

    @Bind(R.id.title_tv)
    TextView tv;
    @Bind(R.id.uid_edt)
    EditText uIdEdt;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.ok)
    Button okBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_transfer;
    }

    @Override
    protected void toStart() {
        tv.setText(R.string.class_transfer);
    }

    @OnClick({R.id.goback,R.id.ok})
    public void click(View v){
        switch (v.getId()){
            case R.id.goback:
                finish();
                break;
            case R.id.ok:
                String cid = getIntent().getStringExtra("cid");
                String uid = uIdEdt.getText().toString().trim();
                String phone = phoneEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(phone)) {
                    new ClassTransferMiddle(this,this).transferClass(GlobalParams.userInfo.getUid()+"",cid,uid,phone);
                } else {
                    UIUtils.showToastSafe(getString(R.string.code_phone_null_info));
                }
                break;

        }
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        BaseBean bean = (BaseBean) result;
        if (bean != null && bean.getStatus() == 1) {
            Intent intent = new Intent();
            intent.putExtra("change",true);
            setResult(RESULT_OK,intent);
            GlobalParams.HOME_CHANGED = true;
            finish();
            UIUtils.showToastSafe(bean.getMsg());
        } else {
            UIUtils.showToastSafe(bean.getMsg());
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }
}

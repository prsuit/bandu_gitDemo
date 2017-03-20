package me.bandu.talk.android.phone.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.StudentWorkAdapter;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.QueryClassBean;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.middle.AddClassMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：高楠
 * 类描述：加入班级
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AddClassActivity extends BaseAppCompatActivity {
    @Bind(R.id.class_info)
    LinearLayout linearLayout;
    @Bind(R.id.tv_error)
    TextView tv_error;
    @Bind(R.id.editext)
    EditText editext;
    @Bind(R.id.class_num)
    TextView class_num;
    @Bind(R.id.class_name)
    TextView class_name;
    @Bind(R.id.creater)
    TextView creater;
    @Bind(R.id.commit)
    TextView commit;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    private AddClassMiddle middle;
    private String uid;//TODO
    private String cid;
    private String phoneString;
    private String name;
    private String creator;
    private LoginBean.DataEntity uerInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_class;
    }

    @Override
    protected void toStart() {
        uid = UserUtil.getUid();
        commit.setText("查询");
        uerInfo = UserUtil.getUerInfo(this);
        middle = new AddClassMiddle(this, this);
    }

    @OnClick(R.id.commit)
    void onClick(View view) {
        showMyprogressDialog();
        if (commit.getText().toString().equals("查询")) {
            cid = editext.getText().toString();
            phoneString = phoneEdt.getText().toString().trim();
            middle.queryClass(cid, phoneString);
        } else if (commit.getText().toString().equals("加入")) {
            cid = editext.getText().toString();
            middle.addClass(cid, uid);
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (result != null) {
            if (AddClassMiddle.QUERY_CLASS == requestCode) {
            QueryClassBean bean = (QueryClassBean) result;
                commit.setText("加入");
                linearLayout.setVisibility(View.VISIBLE);
                cid =  bean.getData().getCid();
                name = bean.getData().getName();
                creator = bean.getData().getCreator();
                class_num.setText(cid);
                class_name.setText(name);
                creater.setText(creator);
               // tv_error.setVisibility(View.INVISIBLE);
            } else if (requestCode == AddClassMiddle.ADD_CLASS) {
                UIUtils.showToastSafe("您的申请已提交，请等待审核");
                try {
                    // 解决加入新班级，清空当前作业列表
                    StudentWorkFragment.thiz.getAdapter().getList().clear();
                } catch (NullPointerException ne) {
                    ne.printStackTrace(); // 防止空指针异常
                }
                cid = editext.getText().toString();
                uerInfo.setCid(cid);
                uerInfo.setClass_name(name.substring(3)+"(待审核)");
                uerInfo.setState("2");
                UserUtil.saveUserInfo(UIUtils.getContext(),uerInfo);
                GlobalParams.ADD_CLASS = true;
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
        int requestcode = (int) obj[0];
        if (requestcode == AddClassMiddle.ADD_CLASS) {
           // tv_error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String setTitle() {
        return UIUtils.getString(R.string.add_class_num);
    }
}

package me.bandu.talk.android.phone.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：关于我们
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AboutUsActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.version_tv)
    TextView versionNameTv; //版本名

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void toStart() {
        titleTv.setText(getString(R.string.about_us));

        versionNameTv.setText("V "+Utils.getAppVersionName(this));
    }

    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3,R.id.agreement,R.id.wx_layout})
    void click(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                startActivity(new Intent(this,MembersActivity.class));
                break;
            case R.id.rl2:
                startActivity(new Intent(this,CompanyActivity.class));
                break;
            case R.id.rl3:
                startActivity(new Intent(this,GroupActivity.class));
                break;
            case R.id.agreement:
                startActivity(new Intent(this,AgreementActivity.class));
                break;
            case R.id.wx_layout:
                copyToClipbord();
                showDialog();
                break;
        }
    }

    public void copyToClipbord(){
        ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip;
        String text = "banduhelp";
        myClip = ClipData.newPlainText("text", text);
        cmb.setPrimaryClip(myClip);
        //UIUtils.showToastSafe("伴读公众号已复制到剪贴板");
    }

    private void showDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.exit_dialog,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        TextView tvMsg = (TextView) layout.findViewById(R.id.msg);
        tvMsg.setText("公众号\"banduhelp\"已复制。\n您可以在微信中直接粘贴搜索。");
        tvMsg.setTextSize(Utils.dip2px(this,6));
        TextView tvLeft = (TextView) layout.findViewById(R.id.make_sure);
        tvLeft.setText("取消");
        TextView tvRight = (TextView) layout.findViewById(R.id.cancel);
        tvRight.setText("去微信");
        tvLeft.setTextColor(getResources().getColor(R.color.blue));
        tvRight.setTextColor(getResources().getColor(R.color.blue));
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                try {
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e) {
                    UIUtils.showToastSafe("请先安装微信");
                }
            }
        });
        dialog1.show();
    }
}

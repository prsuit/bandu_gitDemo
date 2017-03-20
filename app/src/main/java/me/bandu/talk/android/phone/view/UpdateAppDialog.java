package me.bandu.talk.android.phone.view;

import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.view.impl.UpdateAppDialogCallback;

/**
 * 创建者：wanglei
 * <p>时间：16/6/6  13:32
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class UpdateAppDialog extends Dialog implements View.OnClickListener/*android.view.View.OnClickListener */ {

    private UpdateAppDialogCallback dialogImp;
    private TextView tv, tv_version, percentage_tv;
    private View ok, no, bottom_line_view;
    private LinearLayout percentage_layout, bottom_layout, tv_layout;
    private SeekBar percentage_seek;
    private CheckBox checkBox;

    /**
     * @param context activity
     * @param width   屏幕的宽度
     * @param height  屏幕的高度
     *                //     * @param tvStr   要显示的内容
     */
    public UpdateAppDialog(Context context, UpdateAppDialogCallback dialogImp, int width, int height) {
        super(context, R.style.Theme_dialog);//设置dialog没有title
        this.dialogImp = dialogImp;
        View view = LayoutInflater.from(context).inflate(R.layout.often_dialog, null);
        tv = (TextView) view.findViewById(R.id.dialog_tv);
        tv_version = (TextView) view.findViewById(R.id.dialog_tv_version);
        ok = view.findViewById(R.id.dialog_ok);
        no = view.findViewById(R.id.dialog_no);
        checkBox = (CheckBox) view.findViewById(R.id.dialog_percentage_check_box);
        percentage_tv = (TextView) view.findViewById(R.id.dialog_percentage_tv);
        percentage_seek = (SeekBar) view.findViewById(R.id.dialog_percentage_seek);
        percentage_layout = (LinearLayout) view.findViewById(R.id.dialog_percentage_layout);
        bottom_layout = (LinearLayout) view.findViewById(R.id.dialog_bottom_layout);
        tv_layout = (LinearLayout) view.findViewById(R.id.dialog_tv_layout);
        bottom_line_view = view.findViewById(R.id.dialog_bottom_line_view);

        ok.setOnClickListener(this);
        no.setOnClickListener(this);
        setContentView(view);

        Window window = getWindow(); // 得到对话框
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = width / 8 * 6;
        wl.height = height / 8 * 4;
        window.setAttributes(wl);
        // 设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(false);
    }

    public void showOftenDealog(String tvStr, String netVersionName) {
        tv_version.setText("    " + UIUtils.getResources().getString(R.string.UMNewVersion) + " " + netVersionName + "\n    本次更新有 :");
        tv.setText(tvStr);
        show();
    }

    private boolean forceUpdateStates;

    public void forceUpdate(boolean forceUpdateStates) {
        this.forceUpdateStates = forceUpdateStates;
        if (forceUpdateStates)
            checkBox.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_no:
                if (forceUpdateStates) {
                    UIUtils.showToastSafe("请更新,谢谢");
                } else {
                    dialogImp.dialog_but_no();
                    dimisDialog();
                    dismiss();
                }
                break;
            case R.id.dialog_ok:
                dialogImp.dialog_but_ok();
                break;
        }
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.dialog_no:
//                dialogImp.dialog_but_no();
//                dimisDialog();
//                break;
//            case R.id.dialog_ok:
//                dialogImp.dialog_but_ok();
//                break;
//        }
//        dismiss();
//    }

    private void dimisDialog() {
        dialogImp = null;
        tv = null;
    }

    int progress = 0;

    public void updatePercentageSeek(int progress) {
        if (progress > this.progress) {
            percentage_seek.setProgress(progress);
            percentage_tv.setText(String.valueOf(progress) + "%");
            this.progress = progress;
        }
    }

    public void determineUpdate() {
        bottom_layout.setVisibility(View.GONE);
        tv_layout.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
        bottom_line_view.setVisibility(View.GONE);
        percentage_layout.setVisibility(View.VISIBLE);
    }

    public boolean notUpdate() {
        return checkBox.isChecked();
    }

    int goBackNum = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowing()) {
            if(goBackNum>0){
                goBackNum = 0;
                System.exit(0);
            }
            goBackNum++;
            UIUtils.showToastSafe(UIUtils.getResources().getString(R.string.press_again));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package me.bandu.talk.android.phone.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.LogUtils;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：高楠
 * 时间：on 2016/4/12
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DialogUtils {
    private static MyProgressDialog mygProgressDialog = null;
    public static void showMyprogressDialog(Context context,boolean flag){
        showMyprogressDialog(context,"",flag);
    }
    public static void showMyprogressDialog(Context context,String message,boolean flag){
        try {
            if (mygProgressDialog == null) {
                mygProgressDialog = MyProgressDialog.createProgressDialog(context, StringUtil.getShowText(message));
                mygProgressDialog.setCancelable(true);
                mygProgressDialog.setCanceledOnTouchOutside(false);
                if (flag)
                    mygProgressDialog.setOnKeyListener(keynever);
            }
            if (!mygProgressDialog.isShowing())
                mygProgressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("dialog出问题了~");
        }

    }
    public static void hideMyprogressDialog(Context context){
        try {
            if (mygProgressDialog == null) {
                mygProgressDialog = MyProgressDialog.createProgressDialog(context, "");
                mygProgressDialog.setCancelable(true);
                mygProgressDialog.setCanceledOnTouchOutside(false);
            }
            if (mygProgressDialog.isShowing())
                mygProgressDialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("dialog出问题了~");
        }

    }
    static DialogInterface.OnKeyListener keynever = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
                return false;
            }
            return true;
        }
    };


    /**
     * 自定义样式的对话框
     */
    public static void onlyWord(Context context, String message) {

        View layout = LayoutInflater.from(context).inflate(R.layout.toast_tip,null);
        TextView tv_msg = (TextView) layout.findViewById(R.id.msg);
        tv_msg.setText(message);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
}

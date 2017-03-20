package me.bandu.talk.android.phone.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 加载中Dialog
 * 
 * @author xm
 */
public class LoadingDialog extends AlertDialog {

    private TextView tips_loading_msg;

    private String message = null;

    public LoadingDialog(Context context) {
        super(context);
        message = getContext().getResources().getString(R.string.msg_load_ing);
        getWH(context);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);
        getWH(context);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCancelable(false);
        getWH(context);
    }
    int width;
    int height;
    public void getWH(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        Window win = getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = width*3/5;//设置x坐标
        params.height = height/4;//设置y坐标
        win.setAttributes(params);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}

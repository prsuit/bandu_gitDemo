package me.bandu.talk.android.phone.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * 加载中Dialog
 * 
 * @author xm
 */
public class LoadingDialogGN extends AlertDialog {

    private TextView tips_loading_msg;

    private String message = null;

    public LoadingDialogGN(Context context) {
        super(context);
        message = getContext().getResources().getString(R.string.msg_load_ing);
        this.setCanceledOnTouchOutside(false);
    }

    public LoadingDialogGN(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public LoadingDialogGN(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading_gn);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
        setOnKeyListener(keylistener);
    }
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
                return false;
            }
            return true;
        }
    };
    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}

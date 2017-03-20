package me.bandu.talk.android.phone.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.impl.AsyncListener;
import me.bandu.talk.android.phone.impl.UploadListener;
import me.bandu.talk.android.phone.utils.ScreenUtil;

/**
 * 创建者：高楠
 * 时间：on 2016/3/3
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UpLoadDialog extends Dialog  {
    Context context;
    View view;
    private TextView tv_percent;
    private TextView cancel;
    private TextView tv_type;
    private ProgressBar progressBar;
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
                return true;
            }
            return false;
        }
    };

    public UpLoadDialog(Context context) {
        super(context);
        this.context = context;
    }

    public UpLoadDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected UpLoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(context, R.layout.upload_work_layout,null);
        setContentView(view);
        tv_percent = (TextView) view.findViewById(R.id.progress_percent);
        cancel = (TextView) view.findViewById(R.id.cancel);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        tv_percent.setText("0");
        progressBar.setMax(100);
        progressBar.setProgress(0);
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isForeground((Activity) context,context.getClass().getName()))
                    dismiss();
            }
        });
    }
    public void setProgress(long position,long max){
        int percent = (int) (position*100/max);
        tv_percent.setText(percent+"%");
        progressBar.setProgress(percent);
    }
    public void setNote(int state){
        if(state == UploadListener.ERROR){
            tv_type.setText(UIUtils.getString(R.string.network_error));
        }else if (state == UploadListener.FINISH){
            tv_type.setText(UIUtils.getString(R.string.upload_success));
        }else if (state == UploadListener.CANCEL){
            tv_type.setText(UIUtils.getString(R.string.upload_cancel));
        }
    }
    public void setNote(int state,String msg){
        if (msg!=null&&!msg.equals("")){
            tv_type.setText(msg);
            return;
        }
        if(state == UploadListener.ERROR){
            tv_type.setText(UIUtils.getString(R.string.network_error));
        }else if (state == UploadListener.FINISH){
            tv_type.setText(UIUtils.getString(R.string.upload_success));
        }else if (state == UploadListener.CANCEL){
            tv_type.setText(UIUtils.getString(R.string.upload_cancel));
        }
    }
    public void setNote(String msg){
        if (msg!=null&&!msg.equals(""))
            tv_type.setText(msg);
    }
}

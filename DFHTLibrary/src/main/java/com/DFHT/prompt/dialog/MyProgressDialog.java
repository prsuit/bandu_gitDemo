package com.DFHT.prompt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.DFHT.R;


public class MyProgressDialog extends Dialog{
	public MyProgressDialog(Context context) {
		super(context);
	}
	

	public MyProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyProgressDialog createProgressDialog(Context context,String message){
		MyProgressDialog progress = new MyProgressDialog(context, R.style.dialog);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.message);
        tvMessage.setText(message);
        progress.setContentView(view);
		return progress;
    }
}

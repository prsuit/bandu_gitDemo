package me.bandu.talk.android.phone.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class CustomDialog extends Dialog{

	Context context;
	View view;


	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomDialog(Context context, View view) {
		super(context);
		this.context = context;
		this.view = view;
	}

	public CustomDialog(Context context, int theme, View view) {
		super(context, theme);
		this.context = context;
		this.view = view;
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(view);
	}

}
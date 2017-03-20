package me.bandu.talk.android.phone.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.DFHT.base.ENBaseApplication;
import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.R;

public class AlertDialogUtils {
    static AlertDialogUtils alertDialogUtils;

    public static AlertDialogUtils getInstance() {
        if (alertDialogUtils == null) {
            alertDialogUtils = new AlertDialogUtils();
        }
        return alertDialogUtils;
    }


    public interface DialogLestener {
        void ok();

        void cancel();
    }

    DialogLestener lestener;
    Context context;

    public void init(Context context, String title, String message, final DialogLestener lestener) {
        this.lestener = lestener;
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).
                setTitle(title).
                setMessage(message).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lestener != null)
                            lestener.ok();
                        dialog.dismiss();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lestener != null)
                            lestener.cancel();
                        dialog.dismiss();
                    }
                }).
                create();
        alertDialog.show();
    }

    public void init(Context context, String message, final DialogLestener lestener) {
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).
                setMessage(message).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lestener != null)
                            lestener.ok();
                        dialog.dismiss();
                    }
                }).
                create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void init(Context context, String message) {
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).
                setMessage(message).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                create();
        alertDialog.show();
    }
    public void initDialog(Context context,String message) {
        View layout = LayoutInflater.from(context).inflate(R.layout.exit_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        TextView msg = (TextView) layout.findViewById(R.id.msg);
        msg.setTextSize(16);
        msg.setText(message);
        layout.findViewById(R.id.cancel).setVisibility(View.GONE);
        dialog1.show();
    }
}
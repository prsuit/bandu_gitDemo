package me.bandu.talk.android.phone.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;

/**
 * author by Mckiera
 * time: 2016/1/18  16:06
 * description:
 * updateTime:
 * update description:
 */
public class CreateClassDialog extends Dialog {

    public CreateClassDialog(Context context) {
        super(context);
    }

    public CreateClassDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String className;
        private String numStr;
        private String positiveButtonText;
        private DialogInterface.OnClickListener positiveButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setClassName(int title) {
            this.className = (String) context.getText(title);
            return this;
        }

        public Builder setNumStr(String numStr){
            this.numStr = numStr;
            return this;
        }

        public Builder setClassName(String title) {
            this.className = title;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public CreateClassDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CreateClassDialog dialog = new CreateClassDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.create_class_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.tvClassName)).setText(className);
            ((TextView) layout.findViewById(R.id.tvNum)).setText(numStr);

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}

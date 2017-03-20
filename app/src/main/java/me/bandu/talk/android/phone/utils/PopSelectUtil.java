package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.ExerciseChoseAdapter;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;

/**
 * 创建者：gaoye
 * 时间：2016/3/8 14:26
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PopSelectUtil {
    public static PopupWindow createPopupwindow(Context context){
        PopupWindow pup = new PopupWindow();
        pup.setWidth(ScreenUtil.getScreenWidth(context));
        pup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pup.setFocusable(true);
        pup.setBackgroundDrawable(new BitmapDrawable());
        return pup;
    }
    public static void setPopupwindowListView(PopupWindow pup,ListView lv){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lv.setLayoutParams(params);
        pup.setContentView(lv);
    }
    public static PopupWindow createLessonPopupwindow(Context context, List<LessonBean> lessons, final int currentLesson, final int witch,
                 final OnWitchListener onWitchListener){
        PopupWindow pupLesson = new PopupWindow();
        pupLesson.setWidth(ScreenUtil.getScreenWidth(context));
            pupLesson.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            //View view = LayoutInflater.from(context).inflate(R.layout.layout_doexercise_radiogroup,null);
        ListView listView = new ListView(context);
            //final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_doexercise);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //radioGroup.setLayoutParams(params);
        listView.setLayoutParams(params);
        ExerciseChoseAdapter adapter = new ExerciseChoseAdapter(context,lessons);
        listView.setAdapter(adapter);
            pupLesson.setContentView(listView);
            pupLesson.setFocusable(true);
            pupLesson.setBackgroundDrawable(new BitmapDrawable());
            pupLesson.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (onWitchListener != null)
                        onWitchListener.onWitchDissmiss(witch);
                }
            });

            /*RadioButton radioButton;

            RadioGroup.LayoutParams bt_params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50,context));
            //bt_params.setMargins(0,ScreenUtil.dp2px(10,context),0,0);
            for (int i = 0 ;i<lessons.size();i++){
                LessonBean lessonBean = lessons.get(i);
                radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.layout_doexercise_radiobutton_311,null);
                radioButton.setTag(i);
                radioButton.setId(10000 + i + witch);
                radioButton.setText(lessonBean.getLesson_name());
                radioGroup.addView(radioButton);
                radioButton.setLayoutParams(bt_params);
                if (currentLesson == i){
                    radioButton.setChecked(true);
                }
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (onWitchListener != null)
                        onWitchListener.onWitchCheckedChange(witch,group,checkedId);
                }
            });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onWitchListener != null)
                    onWitchListener.onWitchCheckedChange(witch,position);
            }
        });
        return pupLesson;
    }


    public static PopupWindow createPartPopupwindow(Context context, List<PartBean> parts, final int currentPart, final int witch,
                                                    final OnWitchListener onWitchListener){
        PopupWindow pupLesson = new PopupWindow();
            pupLesson.setWidth(ScreenUtil.getScreenWidth(context));
            pupLesson.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            final RadioGroup radioGroup = (RadioGroup) LayoutInflater.from(context).inflate(R.layout.layout_doexercise_radiogroup,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.setLayoutParams(params);
            pupLesson.setContentView(radioGroup);
            pupLesson.setFocusable(true);
            pupLesson.setBackgroundDrawable(new BitmapDrawable());
            pupLesson.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (onWitchListener != null)
                        onWitchListener.onWitchDissmiss(witch);
                }
            });
            RadioButton radioButton;

            RadioGroup.LayoutParams bt_params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50,context));
            //bt_params.setMargins(0,ScreenUtil.dp2px(10,context),0,0);
            for (int i = 0 ;i<parts.size();i++){
                PartBean partBean = parts.get(i);
                radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.layout_doexercise_radiobutton_311,null);
                radioButton.setTag(i);
                radioButton.setId(10000 + i + witch);
                radioButton.setText(partBean.getPart_name());
                radioGroup.addView(radioButton);
                radioButton.setLayoutParams(bt_params);
                if (currentPart == i){
                    radioButton.setChecked(true);
                }
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (onWitchListener != null)
                        onWitchListener.onWitchCheckedChange(witch,checkedId);
                }
            });
        return pupLesson;
    }

    public interface OnWitchListener{
        public void onWitchDissmiss(int witch);
        public void onWitchCheckedChange(int witch, int checkedId);
    }
}

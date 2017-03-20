package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.view.PickerView;

/**
 * Created by GaoNan on 2015/11/20.
 * Popupwindow 时间选择器  条件选择器
 */
public class  PopUtils {
   private static PopUtils pu;
    private PopupWindow ppw;
    private PickerView picker_classname;
    private String className;


    public static PopUtils getInstance(){
        if (pu==null){
            pu = new PopUtils();
        }
        return pu;
    }


    /**
     * 时间选择器
     */
    private OnPopDismissListener listener;
    private OnClassPopDismissListener classlistener;

    public interface OnPopDismissListener{
        void onCommit(String result,int level);
        void onCommit(String year,String month,String day,String hour);
        void onCancel();
    }
    public interface OnClassPopDismissListener{
        void onCommit(String result,int level);
        void onCommit(String class_name);
        void onCancel();
    }
    public String selectClass;
    PickerView year_pv;
    PickerView mouth_pv;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int week;
    private PickerView day_pv;
    private PickerView hour_pv;
    private String yearString = "";// 选择的年份
    private String moubthString = "";// 选择的月份
    private String dayString = "";// 选择的日期
    private String hourString = "";// 选择的小时
    private List<String> dayList;// 31天
    private Context context;

    public void showTimePopou(Context context, View container, OnPopDismissListener listener){
        this.listener = listener;
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.poptime,null);
        getDate();
        initData(view);
        ppw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        ppw.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(0xB2B2B2);
        //设置SelectPicPopupWindow弹出窗体的背景
        ppw.setBackgroundDrawable(dw);
//        ppw.setBackgroundDrawable(null);
        ppw.setOutsideTouchable(true);
        ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
    }
    private void initData(View view) {
        year_pv = (PickerView) view.findViewById(R.id.picker_year);
        mouth_pv = (PickerView) view.findViewById(R.id.picker_month);
        day_pv = (PickerView) view.findViewById(R.id.picker_day);
        hour_pv = (PickerView) view.findViewById(R.id.picker_hour);
        View dismiss = view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (listener!=null)
                        listener.onCancel();
                    ppw.dismiss();
                }
            }
        });
        View commit = view.findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (isDateOK()){
                        if (listener!=null){
                            listener.onCommit(yearString,moubthString,dayString,hourString);
                        }
                        ppw.dismiss();
                    }else {
                        Toast.makeText(context,"请选择正确的时间！",Toast.LENGTH_SHORT).show();
                    }
                    Log.e("onCommit","-yearString-"+yearString+"-moubthString-"+moubthString+"-dayString-"+dayString);
                }
            }
        });
        View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (listener!=null)
                        listener.onCancel();
                    ppw.dismiss();
                }
            }
        });
        List<String> yearList = new ArrayList<String>();
        List<String> mouthList = new ArrayList<String>();
        List<String> hourList = new ArrayList<String>();
        dayList = new ArrayList<>();
        hourList = new ArrayList<>();
        for (int i = year; i <= year+1; i++) {
            yearList.add(i + "");
        }
        for (int i = 1; i < 13; i++) {
            mouthList.add(i + "");
        }
        for (int i = 1; i < 32; i++) {
            dayList.add(i + "");
        }
        for (int i = 0; i < 24; i++) {
            hourList.add(i + "");
        }
        year_pv.setData(yearList);
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                yearString = text;
            }
        });
        mouth_pv.setData(mouthList);
        mouth_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                moubthString = text;
            }
        });
        day_pv.setData(dayList);
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                dayString = text;
            }
        });
        hour_pv.setData(hourList);
        hour_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                hourString = text;
            }
        });
        hour_pv.setSelected(hourString);
        day_pv.setSelected(dayString);
        mouth_pv.setSelected(moubthString);
        year_pv.setSelected(yearString);
    }
    // 判断是否是闰年
    boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            return true;
        else
            return false;
    }
    public void getDate(){
        long time= System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        month = mCalendar.get(Calendar.MONTH)+1;
        hour = mCalendar.get(Calendar.HOUR_OF_DAY)+1;
        day = mCalendar.get(Calendar.DAY_OF_MONTH);
        year = mCalendar.get(Calendar.YEAR);
        week = mCalendar.get(Calendar.DAY_OF_WEEK);
        moubthString = month+"";
        dayString = day+"";
        yearString = year+"";
        hourString = hour+"";
    }
    public void setDate(String year,String month,String day,String hour){
        if (year!=null){
            day_pv.setSelected(day);
            mouth_pv.setSelected(month);
            year_pv.setSelected(year);
            yearString = year;
            moubthString = month;
            dayString = day;
            hourString = hour;
        }

    }
    public boolean isDateOK(){
        int y = Integer.parseInt(yearString);
        int m = Integer.parseInt(moubthString);
        int d = Integer.parseInt(dayString);
        boolean flag = true;
        if (m==2) {// 闰年二月
            if (isLeapYear(y)) {
                if (d>29){
                    flag = false;
                }
            }else{
                if (d>28){
                    flag = false;
                }
            }
        } else {
            if (!(m==1|| m==3 || m==5 || m==7 || m==8 || m==10 || m==12)) {
                if (d==31){
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 作业要求
     * @param context
     */
    private String description;
    public void showPopDescription(Context context,View container,String str,OnPopDismissListener listener){
        this.listener = listener;
        description = null;
        View view = LayoutInflater.from(context).inflate(R.layout.popdescription, null);
        initDataDescript(view,str);
        ppw = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        ppw.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(0xB2B2B2);
        //设置SelectPicPopupWindow弹出窗体的背景
        ppw.setBackgroundDrawable(dw);
        ppw.setOutsideTouchable(true);
        ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
    }
    String non = "无最低要求";
    String jige = "发音达到及格以上";
    String lianghao = "发音达到良好以上";
    String youxiu = "发音达到优秀以上";
    private void initDataDescript(View view,String str) {
        List<String> data = new ArrayList<>();
        data.add(non);
        data.add(jige);
        data.add(lianghao);
        data.add(youxiu);
        PickerView pic_description = (PickerView)view.findViewById(R.id.description);
        pic_description.setData(data,0);
        description = data.get(0);
        pic_description.setmMaxTextSize(14);
        pic_description.setmMinTextSize(10);
        if (str ==null||(str!=null&&str.equals(""))){
            pic_description.setSelected(0);
        }else{
            pic_description.setSelected(str);
        }
        pic_description.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                description = text;
            }
        });
        view.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (listener!=null)
                        listener.onCancel();
                    ppw.dismiss();
                }
            }
        });
        view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (listener!=null){
                        int level = 0;
                        if (description.equals(non)){
                            level = 0;
                        }
                        if (description.equals(jige)){
                            level = 1;
                        }
                        if (description.equals(lianghao)){
                            level = 2;
                        }
                        if (description.equals(youxiu)){
                            level = 3;
                        }
                        listener.onCommit(description,level);
                    }
                    ppw.dismiss();
                }
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppw.isShowing()){
                    if (listener!=null)
                        listener.onCancel();
                    ppw.dismiss();
                }
            }
        });
    }

}

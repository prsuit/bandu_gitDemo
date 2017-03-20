package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.bean.UserClassListBean;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.view.ClassPickerView;
import me.bandu.talk.android.phone.view.PickerView;

/**
 * Created by GaoNan on 2015/11/20.
 * Popupwindow 时间选择器  条件选择器
 */
public class ClassPopUtils implements ClassPickerView.onClassSelectListener {
    private static final int CLASS_PICKER_CODE = 103;
    private static ClassPopUtils pu;
    private PopupWindow ppw;
    private ClassPickerView picker_classname;
    private String className, Cid, state;
    private int index = 0;

    public static ClassPopUtils getInstance() {
        if (pu == null) {
            pu = new ClassPopUtils();
        }
        return pu;
    }

    /**
     * 时间选择器
     */
    private OnClassPopDismissListener classlistener;

    @Override
    public void onSelect(String classname, String cid, String state) {
        this.className = classname;
        this.Cid = cid;
        this.state = state;
    }

    public interface OnClassPopDismissListener {
        void onCommit(String classname, String cid, String state);
    }

    private Context context;

    public void showClassPopou(Context context, LoginBean.DataEntity uerInfo, ImageView img, String className, StudentWorkFragment fragment, View container, UserClassListBean result, OnClassPopDismissListener listener, ClassPickerView.onTextChanged onTextChanged) {
        this.className = null;
        this.Cid = null;
        this.state = null;
        this.classlistener = listener;
        this.context = context;
        UserClassListBean bean = result;
        View view = LayoutInflater.from(context).inflate(R.layout.popclass, null);
        initClassData(view, uerInfo, bean, img, className, fragment, onTextChanged);
        ppw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ppw.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(0xB2B2B2);
        //设置SelectPicPopupWindow弹出窗体的背景
        ppw.setBackgroundDrawable(dw);
//        ppw.setBackgroundDrawable(null);
//        ppw.setOutsideTouchable(true);
        ppw.showAtLocation(container, Gravity.BOTTOM, 0, 0);
    }

    private void initClassData(View view, final LoginBean.DataEntity uerInfo, final UserClassListBean bean, final ImageView img, final String className, final StudentWorkFragment fragment, ClassPickerView.onTextChanged onTextChanged) {
        picker_classname = (ClassPickerView) view.findViewById(R.id.picker_classname);
        picker_classname.setOnTextChanged(onTextChanged);
        View dismiss = view.findViewById(R.id.dismiss);
        final RotateAnimation animation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);//设置动画持续时间
        animation.setFillAfter(true);
        picker_classname.setOnSelectListener(this);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToastSafe("1111");
                img.startAnimation(animation);
                GlobalParams.imgflag = false;
                if (ppw.isShowing())
                    ppw.dismiss();
            }
        });
        View commit = view.findViewById(R.id.class_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.startAnimation(animation);
                if (ppw.isShowing()) {
                    GlobalParams.imgflag = false;
                    ppw.dismiss();
                }
                if ("1".equals(state))
                    fragment.getDataFromNet();
                if (classlistener != null)
                    classlistener.onCommit(ClassPopUtils.this.className, Cid, state);
            }
        });
        View cancel = view.findViewById(R.id.class_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToastSafe("3333");
                img.startAnimation(animation);
                GlobalParams.imgflag = false;
                if (ppw.isShowing())
                    ppw.dismiss();
            }
        });
        final List<UserClassListBean.DataBean.ClassListBean> classbean = new ArrayList<>();
        List<UserClassListBean.DataBean.ClassListBean> class_list = bean.getData().getClass_list();
        for (int i = 0; i < class_list.size(); i++) {
            UserClassListBean.DataBean.ClassListBean classListBean = class_list.get(i);
            if (classListBean.getStatus() == 2) {
                String class_name = classListBean.getClass_name();
                classListBean.setClass_name(class_name + "(待审核)");
            }
            classbean.add(classListBean);
        }
        long uid = Long.parseLong(UserUtil.getUid());
        DBUtils.getDbUtils().getClassListInfo(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                List<ClassTabelBean> bean = (List<ClassTabelBean>) list;
                picker_classname.setData(classbean, bean);
                picker_classname.setSelected(uerInfo.getClass_name());
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {

            }
        }, CLASS_PICKER_CODE, uid);


    }


    private PopupWindow.OnDismissListener onDismissListener;

    public void ssss(PopupWindow.OnDismissListener onDismissListener) {
        ppw.setOnDismissListener(onDismissListener);
    }

}

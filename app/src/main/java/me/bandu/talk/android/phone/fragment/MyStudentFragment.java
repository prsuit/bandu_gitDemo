package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;
import com.DFHT.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.ClassManagerActivity;
import me.bandu.talk.android.phone.activity.VerifyStudentActivity;
import me.bandu.talk.android.phone.adapter.TStudentAdapter;
import me.bandu.talk.android.phone.bean.ClassAllStudentBean;
import me.bandu.talk.android.phone.bean.TStudentBean;
import me.bandu.talk.android.phone.bean.TeacherHomeList;
import me.bandu.talk.android.phone.middle.AccessRefuseStudentMiddle;
import me.bandu.talk.android.phone.middle.ClassStudentMiddle;
import me.bandu.talk.android.phone.middle.DeleteStudentMiddle;
import me.bandu.talk.android.phone.utils.AlphaUtil;
import me.bandu.talk.android.phone.utils.AnimationUtil;
import me.bandu.talk.android.phone.view.SideBar;
/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MyStudentFragment extends BaseFragment implements View.OnClickListener,BaseActivityIF{
    private View view;
    private ListView slv_student;
    private TStudentAdapter adapter;
    private SideBar sb_selectStudent;
    private RelativeLayout rl_check;
    private TextView tv_show,tv_check_num,tv_totle;
    private List<TStudentBean> students;
    private ClassStudentMiddle classStudentMiddle;
    private ArrayList<TStudentBean> verifyStudents;
    private TeacherHomeList.DataEntity.ListEntity classInfo;
//    private AccessRefuseStudentMiddle accessRefuseStudentMiddle;
    private DeleteStudentMiddle deleteStudentMiddle;
    private final int REQUEST_ACCESSREFUSE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewUtils.removeParent(view);
        } else {
            view = inflater.inflate(R.layout.fragment_my_student, null);
            initView(view);
            setData();
            setListeners();
        }
        return view;
    }


    @Override
    public void initView(View view) {
        slv_student = (ListView) view.findViewById(R.id.slv_student);
        sb_selectStudent = (SideBar) view.findViewById(R.id.sb_selectStudent);
        rl_check = (RelativeLayout) view.findViewById(R.id.rl_check);
        tv_show = (TextView) getActivity().findViewById(R.id.tv_show);
        tv_check_num = (TextView) view.findViewById(R.id.tv_check_num);
        tv_totle = (TextView) view.findViewById(R.id.tv_totle);
    }

    @Override
    public void setData() {
        classInfo = ((ClassManagerActivity)getActivity()).getClassInfo();
        sb_selectStudent.setTextView(tv_show);
        students = Collections.synchronizedList(new ArrayList<TStudentBean>());
        adapter = new TStudentAdapter(getActivity(), students, new TStudentAdapter.OnRightDeleteClickListener() {
            @Override
            public void rightDeleteClick(int position) {
                setStudentDeleteDialog(position);
            }
        });
        slv_student.setAdapter(adapter);

        classStudentMiddle = new ClassStudentMiddle(this);
        getNetData();
    }

    private void setStudentDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setMessage("是否删除学生")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        TStudentBean studentBean = students.get(position);
                        if (classInfo == null){
                            return;
                        }
                        if (deleteStudentMiddle == null){
                            deleteStudentMiddle = new DeleteStudentMiddle(MyStudentFragment.this,getActivity());
                        }
                        deleteStudentMiddle.deleteStudent(GlobalParams.userInfo.getUid(),classInfo.getCid(),new long[]{studentBean.getUid()});
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

    }

    private void getNetData() {
        showMyprogressDialog();
        if(getActivity() != null){
            if (((ClassManagerActivity)getActivity()).getClassInfo() != null){
                classStudentMiddle.getClassStudent(((ClassManagerActivity)getActivity()).getClassInfo().getCid());
            }
        }

    }

    @Override
    public void setListeners() {
        slv_student.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                adapter.closeALL();
            }
        });
        sb_selectStudent.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                slv_student.setSelection(position);
            }
        });
        rl_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.rl_check:
                if (verifyStudents != null){
                    intent = new Intent(getActivity(), VerifyStudentActivity.class);
                    intent.putExtra("verifyStudents", verifyStudents);
                    intent.putExtra("classid",classInfo.getCid() + "");
                    startActivityForResult(intent,REQUEST_ACCESSREFUSE);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_ACCESSREFUSE){
                boolean change = data.getBooleanExtra("change",true);
                if (change){
                    getNetData();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ClassManagerActivity.fragments.clear();
    }

    @Override
    public void successFromMid(Object... obj) {
        int code = (int) obj[1];
        if (code == ClassStudentMiddle.CLASS_STUDENT){
            ClassAllStudentBean classStudentBean = (ClassAllStudentBean) obj[0];
            setClassStudents(classStudentBean);
        }else if(code == DeleteStudentMiddle.DELETESTUDENT){
            BaseBean baseBean = (BaseBean) obj[0];
            UIUtils.showToastSafe(baseBean.getMsg());
            if (1 == baseBean.getStatus()){
                getNetData();
            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        hideMyprogressDialog();
        int code = (int) obj[0];
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
    }

    @Override
    public void onFailed(int requestCode) {
    }

    public void setClassStudents(final ClassAllStudentBean classStudents) {
        UIUtils.startTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                if (classStudents == null || classStudents.getData() == null || classStudents.getData().getNomal() == null)
                    return;
                ClassAllStudentBean.DataEntity.StudentEntity studentEntity = classStudents.getData().getNomal();
                if (studentEntity == null)
                    return;
                if (studentEntity.getList() == null)
                    return;
                students.removeAll(students);
                int totle = 0;
                if (studentEntity != null && studentEntity.getList() != null){
                    totle = studentEntity.getTotal();
                    for (int i = 0;i<studentEntity.getList().size();i++){
                        TStudentBean student = studentEntity.getList().get(i);
                        String s = "";
                        if (student.getName().length() > 0){
                            char firstchar = student.getName().charAt(0);
                            if ((firstchar >= 'a' && firstchar <= 'z') || (firstchar >= 'A' && firstchar <= 'Z')){
                                s = String.valueOf(firstchar);
                                s = s.toUpperCase();
                            }else {
                                s = AlphaUtil.getPinYinHeadChar(student.getName());
                            }
                        }

                        if (s.length() > 0){
                            student.setAlpha(s.substring(0,1));
                        }else {
                            student.setAlpha("");
                        }

                        students.add(student);
                    }
                    Collections.sort(students);
                }

                ClassAllStudentBean.DataEntity.VerifyStudentEntity verifyStudentEntity = classStudents.getData().getWatting();
                if (verifyStudentEntity == null || verifyStudentEntity.getList() == null)
                    return;
                int waittotle = 0;
                if (verifyStudentEntity != null && verifyStudentEntity.getList() != null && verifyStudentEntity.getList().size() != 0){
                    if (verifyStudents == null)
                        verifyStudents = new ArrayList<>();
                    verifyStudents.removeAll(verifyStudents);
                    verifyStudents.addAll(verifyStudentEntity.getList());
                    waittotle = verifyStudentEntity.getTotal();
                }

                final int totleF = totle;
                final int waittotleF = waittotle;
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_totle.setText("本班共有" + totleF + "名学生");
                        adapter.notifyDataSetChanged();
                        tv_check_num.setText(waittotleF + "");
                        hideMyprogressDialog();
                        if (waittotleF == 0){
                            rl_check.setVisibility(View.GONE);
                        }else {
                            rl_check.startAnimation(AnimationUtil.getYShowTranslateAnimation(200));
                            rl_check.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

    }
}


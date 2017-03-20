package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.ClassManagerActivity;
import me.bandu.talk.android.phone.activity.ClassTransferActivity;
import me.bandu.talk.android.phone.activity.CreateClassActivity;
import me.bandu.talk.android.phone.bean.ClassInfoBean;
import me.bandu.talk.android.phone.middle.ClassInfoMiddle;
import me.bandu.talk.android.phone.middle.DeleteClassMiddle;
import me.bandu.talk.android.phone.utils.ShareUtils;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassSetFragment extends BaseFragment implements View.OnClickListener,BaseActivityIF{
    private View view;
    private Button bt_transferClass,bt_deleteClass;
    private TextView tv_classname_set,tv_classcode_set,tv_classtime_set;
    private DeleteClassMiddle deleteClassMiddle;
    private RelativeLayout rl_classset;
    private ImageView shareImg;
    private String cid;
    private ClassInfoMiddle infoMiddle;
    public static final int REQUEST_CLASSSET = 0,REQUEST_CLASSTRANSFER = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_class_set, null);
            initView(view);
            setData();
            setListeners();
        }
        return view;
    }

    @Override
    public void initView(View view) {
        bt_transferClass = (Button) view.findViewById(R.id.bt_transferClass);
        bt_deleteClass = (Button) view.findViewById(R.id.bt_deleteClass);
        tv_classname_set = (TextView) view.findViewById(R.id.tv_classname_set);
        tv_classcode_set = (TextView) view.findViewById(R.id.tv_classcode_set);
        tv_classtime_set = (TextView) view.findViewById(R.id.tv_classtime_set);
        rl_classset = (RelativeLayout) view.findViewById(R.id.rl_classset);
        shareImg = (ImageView) view.findViewById(R.id.share_img);
    }

    @Override
    public void setData() {
        if (getActivity() != null){
            if (((ClassManagerActivity)getActivity()).getClassInfo() != null){
                cid = ((ClassManagerActivity)getActivity()).getClassInfo().getCid();
                infoMiddle = new ClassInfoMiddle(this,getActivity());
                infoMiddle.getClassInfo(cid);
            }
        }
    }

    @Override
    public void setListeners() {
        bt_transferClass.setOnClickListener(this);
        bt_deleteClass.setOnClickListener(this);
        rl_classset.setOnClickListener(this);
        shareImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.bt_transferClass:
                intent = new Intent(getActivity(), ClassTransferActivity.class);
                intent.putExtra("cid",cid);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.bt_deleteClass:
                createDialog();
                break;
            case R.id.rl_classset:
                intent = new Intent(getActivity(), CreateClassActivity.class);
                intent.putExtra("cid",cid);
                intent.putExtra("classname",tv_classname_set.getText().toString());
                startActivityForResult(intent,REQUEST_CLASSSET);
                break;
            case R.id.share_img:
                String name = tv_classname_set.getText().toString().trim();
                String code = tv_classcode_set.getText().toString().trim();
                //ShareUtils.share(getActivity(),"http://test.new.mobile.bandu.in/share/download",name,code);
                ShareUtils.wechatShare(getActivity(),0,name,code);
                //ShareUtils.wechatShare(getActivity(),0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            boolean change = data.getBooleanExtra("change",true);
            if (change) {
                infoMiddle.getClassInfo(cid);
                GlobalParams.HOME_CHANGED = true;
            }
        }
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteClass();
            }
        }).setMessage("\n是否删除班级\n");
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void deleteClass() {
        deleteClassMiddle = new DeleteClassMiddle(this,getActivity());
        deleteClassMiddle.deleteClass(cid);
    }

    @Override
    public void successFromMid(Object... obj) {
        int code = (int) obj[1];
        switch (code){
            case ClassInfoMiddle.CLASS_INFO:
                ClassInfoBean classInfoBean = (ClassInfoBean) obj[0];
                if(classInfoBean.getStatus() == 1)
                    setClassInfoSuccess(classInfoBean);
                else {
                    UIUtils.showToastSafe(classInfoBean.getMsg());
                    getActivity().finish();
                    GlobalParams.HOME_CHANGED = true;
                }

                break;
            case DeleteClassMiddle.DELETE_CLASS:
                BaseBean baseBean = (BaseBean) obj[0];
                setDeleteClassSuccess(baseBean);
                break;
        }

    }

    private void setClassInfoSuccess(ClassInfoBean classInfoBean) {
        tv_classname_set.setText(classInfoBean.getData().getName());
        tv_classcode_set.setText(classInfoBean.getData().getCid());
        tv_classtime_set.setText(classInfoBean.getData().getCtime());
    }

    private void setDeleteClassSuccess(BaseBean baseBean) {
        Toast.makeText(getActivity(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (baseBean.getStatus() == 1){
            GlobalParams.HOME_CHANGED = true;
            getActivity().finish();
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        UIUtils.showToastSafe((int)obj[0]);
    }

}

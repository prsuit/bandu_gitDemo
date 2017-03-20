package me.bandu.talk.android.phone.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;

import butterknife.ButterKnife;
import me.bandu.talk.android.phone.activity.BaseAppCompatActivity;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  16:07
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BaseFragment extends Fragment implements BaseActivityIF {
    public void initView(View view){};
    public void setData(){};
    public void setListeners(){};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showMyprogressDialog(){
        Activity activity = getActivity();
        if (activity != null)
            ((BaseAppCompatActivity)activity).showMyprogressDialog();
    }
    public void hideMyprogressDialog(){
        Activity activity = getActivity();
        if (activity != null)
            ((BaseAppCompatActivity)activity).hideMyprogressDialog();
    }

    @Override
    public void successFromMid(Object... obj) {

    }

    @Override
    public void failedFrom(Object... obj) {

    }

    @Override
    public void onSuccess(Object result, int requestCode) {

    }

    @Override
    public void onFailed(int requestCode) {

    }
}

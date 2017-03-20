package me.bandu.talk.android.phone.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.TeacherHomeList;
import me.bandu.talk.android.phone.fragment.ClassSetFragment;
import me.bandu.talk.android.phone.fragment.MyStudentFragment;
import me.bandu.talk.android.phone.fragment.TextBookFragment;
import me.bandu.talk.android.phone.view.TableBar;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：班级管理
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ClassManagerActivity extends BaseTeacherActivity implements View.OnClickListener {
    private RelativeLayout title_left;
    private TextView tv_middle, tv_right, tv_classset, tv_mystudent, tv_settextbook;
    private FrameLayout fl_main;
    private int currentPage;
    private final int PAGE_FIRST = 0, PAGE_SECOND = 1, PAGE_THREAD = 2;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment mFragment;
    private TableBar tb_tablebar;
    private RelativeLayout rl_title;
    private TeacherHomeList.DataEntity.ListEntity classInfo;
    private static Map<Integer, Fragment> fragments = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_manager;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
        setPage();
    }

    public TeacherHomeList.DataEntity.ListEntity getClassInfo(){
        return  classInfo;
    }

    public void setBookid(int bookid){
        classInfo.setBookid(bookid + "");
    }

    public void setClassName(String className){
        classInfo.setClass_name(className);
    }


    private void setPage() {
        tb_tablebar.setCurrent(currentPage);
        clearTextView();
        //这样写可以解决滑动卡顿问题
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragments();
            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
    }

    private void setFragments() {
        transaction = fragmentManager.beginTransaction();
        mFragment =  fragments.get(currentPage);
        if(mFragment == null) {
            switch (currentPage) {
                case PAGE_FIRST:
                    mFragment = new ClassSetFragment();
                    break;
                case PAGE_SECOND:
                    mFragment = new MyStudentFragment();
                    break;
                case PAGE_THREAD:
                    mFragment = new TextBookFragment();
                    break;
            }
            fragments.put(currentPage, mFragment);
        }
        transaction.replace(R.id.fl_main,mFragment, currentPage + "");
        transaction.commit();
    }


    private void clearTextView() {
        tv_mystudent.setTextColor(getResources().getColor(R.color.text_dark_gray));
        tv_settextbook.setTextColor(getResources().getColor(R.color.text_dark_gray));
        tv_classset.setTextColor(getResources().getColor(R.color.text_dark_gray));
        switch (currentPage) {
            case PAGE_FIRST:
                tv_classset.setTextColor(getResources().getColor(R.color.blue));
                break;
            case PAGE_SECOND:
                tv_mystudent.setTextColor(getResources().getColor(R.color.blue));
                break;
            case PAGE_THREAD:
                tv_settextbook.setTextColor(getResources().getColor(R.color.blue));
                break;
        }
    }

    @Override
    public void initView() {
        title_left = (RelativeLayout) findViewById(R.id.title_left);
        tv_middle = (TextView) findViewById(R.id.title_middle);
        tv_right = (TextView) findViewById(R.id.title_right);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        tv_classset = (TextView) findViewById(R.id.tv_classset);
        tv_mystudent = (TextView) findViewById(R.id.tv_mystudent);
        tv_settextbook = (TextView) findViewById(R.id.tv_settextbook);
        tb_tablebar = (TableBar) findViewById(R.id.tb_tablebar);
        rl_title = (RelativeLayout) findViewById(R.id.title_rl);
    }
    @Override
    public void setData() {
        classInfo = (TeacherHomeList.DataEntity.ListEntity) getIntent().getSerializableExtra("classInfo");
        int page = getIntent().getIntExtra("page",-1);
        if (page >= 0 && page <= 2)
            currentPage = page;
        tv_right.setVisibility(View.GONE);
        tv_middle.setText(R.string.class_manager);
        tb_tablebar.setSize(3);
        fragmentManager = getFragmentManager();
    }
    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
        tv_classset.setOnClickListener(this);
        tv_mystudent.setOnClickListener(this);
        tv_settextbook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_classset:
                if (currentPage == PAGE_FIRST)
                    return;
                currentPage = PAGE_FIRST;
                setPage();
                break;
            case R.id.tv_mystudent:
                if (currentPage == PAGE_SECOND)
                    return;
                currentPage = PAGE_SECOND;
                setPage();
                break;
            case R.id.tv_settextbook:
                if (currentPage == PAGE_THREAD)
                    return;
                currentPage = PAGE_THREAD;
                setPage();
                break;
        }
    }

    @Override
    public void success(Object result, int requestCode) {
    }

    @Override
    public void failed(int requestCode) {

    }
}

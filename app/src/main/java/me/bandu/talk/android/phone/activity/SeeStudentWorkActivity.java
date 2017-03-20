package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.DFHT.ui.LoadPageView;
import com.DFHT.ui.uienum.LoadResult;
import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SeeStudentWorkELVAdapter;
import me.bandu.talk.android.phone.bean.DetailOfStudentBean;
import me.bandu.talk.android.phone.fragment.HomeworkSortFragment;
import me.bandu.talk.android.phone.middle.DetailofStudentMiddle;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;

/**
 * author by Mckiera
 * time: 2015/12/29  14:12
 * description:
 * updateTime:
 * update description:
 */
public class SeeStudentWorkActivity extends BaseTeacherActivity {
    @Nullable
    @Bind(R.id.elv)
    ExpandableListView elv;
    @Nullable
    @Bind(R.id.gobackSuccess)
    ImageView gobackSuccess;
    @Nullable
    @Bind(R.id.gobackError)
    ImageView gobackError;
    @Nullable
    @Bind(R.id.title_right_scuess)
    RelativeLayout relativeLayout;

    LoadPageView view;

    private String job_id;
    private String stu_job_id;
    private boolean isEvaluated;
    private boolean resultok = false;
    private String status;

    @Override
    public void initView() {
        GlobalParams.isUpdateSort = false;
        if (gobackSuccess != null)
            gobackSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(resultok ? RESULT_OK : RESULT_CANCELED);
                    onBackPressed();
                }
            });
        if (gobackError != null)
            gobackError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(resultok ? RESULT_OK : RESULT_CANCELED);
                    onBackPressed();
                }
            });
        if(relativeLayout != null) {
            if (isEvaluated){
                relativeLayout.setVisibility(View.INVISIBLE);
            }else {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SeeStudentWorkActivity.this, TeacherCommentActivity.class);
                        intent.putExtra("job_id",job_id);
                        intent.putExtra("stu_job_id",Integer.parseInt(stu_job_id));
                        startActivityForResult(intent,0);
                    }
                });
            }
            if("0".equals(status) || "1".equals(status)){
                relativeLayout.setVisibility(View.INVISIBLE);
            }
        }
        GlobalParams.HOME_CHANGED = true;
    }

    @Override
    protected View getLayoutView() {
        view = new LoadPageView(this) {
            @Override
            protected int createSuccessView() {
                return R.layout.activity_see_student_work;
            }

            @Override
            protected View createErrorView() {
                View errorView = View.inflate(UIUtils.getContext(), R.layout.error_wifi_with_title, null);
                errorView.findViewById(R.id.rlRoot).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show();
                    }
                });
                return errorView;
            }

            @Override
            protected View createLoadingView() {
                return View.inflate(UIUtils.getContext(), R.layout.error_loading_with_title, null);
            }

            @Override
            protected void load() {
                getData();
            }
        };
        return view;
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            job_id = intent.getStringExtra("job_id");
            stu_job_id = intent.getStringExtra("stu_job_id");
            isEvaluated = intent.getBooleanExtra("isEvaluated",false);
            status = intent.getStringExtra("status");
        }
        view.show();
    }

    //获取数据
    private void getData() {
        showMyprogressDialog();
        DetailofStudentMiddle middle = new DetailofStudentMiddle(this, this);
        middle.workDetail(GlobalParams.userInfo.getUid(), job_id, stu_job_id);
    }

    @Override
    public void setListeners() {

    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
    }


    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        DetailOfStudentBean bean = (DetailOfStudentBean) result;
        if (bean.getStatus() == 1) {
            view.notifyDataChanged(LoadResult.RESULT_SUCCESS);
            ButterKnife.bind(this);
            initView();
            SeeStudentWorkELVAdapter adapter = new SeeStudentWorkELVAdapter(this, bean);
            elv.setAdapter(adapter);
            for (int i = 0; i < bean.getData().getList().size(); i++) {
                elv.expandGroup(i);
            }
            elv.setGroupIndicator(null);
            elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
        }

    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        view.notifyDataChanged(LoadResult.RESULT_ERROR);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==RESULT_OK){
            relativeLayout.setVisibility(View.INVISIBLE);
            resultok = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        New_VoiceUtils.stopVoice();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(resultok?RESULT_OK:RESULT_CANCELED);
        }
        return super.onKeyDown(keyCode, event);
    }
}

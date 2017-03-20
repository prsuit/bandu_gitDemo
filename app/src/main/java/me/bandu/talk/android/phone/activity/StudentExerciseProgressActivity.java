package me.bandu.talk.android.phone.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.db.mdao.MUnitDao;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：练习下载
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentExerciseProgressActivity extends BaseStudentActivity implements View.OnClickListener{
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    @Bind(R.id.title_middle)
    TextView title_middle;

    @Bind(R.id.tv_source)
    TextView tv_source;
    @Bind(R.id.tv_sum)
    TextView tv_sum;
    @Bind(R.id.tv_progress)
    TextView tv_progress;

    private long unitid;
    private int allsum,allsource;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_exercise_progress;
    }

    @Override
    protected void toStart() {
        setData();
        initView();
        setListeners();
    }

    @Override
    public void initView() {
        title_middle.setText("查看");
        MUnitDao unitDao = new MUnitDao(this);
        Integer [] progress = unitDao.getProgress(unitid);
        tv_progress.setText(progress[0] + "/" +  progress[1]);
        if (allsum != 0)
            tv_source.setText(allsource / allsum + "");
        else
            tv_source.setText("还未进行练习");
        tv_sum.setText(allsum + "句");
    }

    @Override
    public void setData() {
        unitid = getIntent().getLongExtra("unitid",0);
        allsource = getIntent().getIntExtra("allsource",0);
        allsum = getIntent().getIntExtra("allsum",0);
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}

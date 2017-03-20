package me.bandu.talk.android.phone.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.RankListviewAdapter;
import me.bandu.talk.android.phone.bean.RankBean;
import me.bandu.talk.android.phone.middle.RankMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * 创建者：王兰新
 * 时间： 2016/5/29
 * 类描述：排行榜
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RankActivity extends BaseAppCompatActivity {

    private static final int SORT = 1;
    private static final int TOTAL_TIME = 2;

    @Bind(R.id.textinfo)
    RadioGroup textinfo;
    @Bind(R.id.tv_excellent)
    TextView tv_excellent;
    @Bind(R.id.image)
    CircleImageView image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.rank)
    TextView rank;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.up_score)
    TextView up_score;
    @Bind(R.id.lv_sort)
    ListView lv_sort;
    @Bind(R.id.tv_up)
    TextView tv_up;
    private String job_id;
    private boolean flag_practice = true;
    private RankBean scoreCache;
    private RankBean timeCache;
    private RankMiddle middle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void toStart() {
        tv_up.setVisibility(View.INVISIBLE);
        showMyprogressDialog();
        //设置头像
        if (UserUtil.getUerInfo(this).getAvatar() != null) {
            ImageLoader.getInstance().displayImage(UserUtil.getUerInfo(this).getAvatar(), image);
        }
        name.setText(UserUtil.getUerInfo(this).getName() + "");
        //请求
        job_id = getIntent().getStringExtra("job_id");
        middle = new RankMiddle(this, this);
        middle.getScoreRankInfo(job_id, new RankBean());
        textinfo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_sort_left) {
                    //排行榜
                    showMyprogressDialog();
                    showSortRank();
                } else if (checkedId == R.id.radio_sort_right) {
                    //修行榜
                    showMyprogressDialog();
                    showPractice();
                }
            }
        });
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        RankBean bean = (RankBean) result;
        if (bean != null && bean.getStatus() == 1) {
            if (requestCode == RankMiddle.SCORE_RANK) {
                scoreCache = new RankBean();
                scoreCache = bean;
            } else {
//                flag_practice = false;
                timeCache = new RankBean();
                timeCache = bean;
            }
            if (bean != null && bean.getData() != null)
                setData(requestCode, bean);
        }
        hideMyprogressDialog();
    }

    private void setData(int requestCode, RankBean bean) {
        RankBean.DataBean data = bean.getData();
        if (data.getUp_score() == 0) {
            tv_up.setVisibility(View.INVISIBLE);
            up_score.setVisibility(View.INVISIBLE);
        } else {
            /*if (data.getUp_score() == 0) {
                if (requestCode == RankMiddle.SCORE_RANK) {
                    tv_up.setVisibility(View.INVISIBLE);
                    up_score.setVisibility(View.INVISIBLE);
                } else {
                    tv_up.setVisibility(View.VISIBLE);
                    up_score.setVisibility(View.VISIBLE);
                }
            }*/
            tv_up.setVisibility(View.VISIBLE);
            up_score.setVisibility(View.VISIBLE);
        }
        rank.setText("第" + data.getRank() + "名");
        tv_excellent.setText("当前共" + data.getDone_total() + "名学生完成作业,杰出同学:");
        List<RankBean.DataBean.StudentListBean> student_list = data.getStudent_list();
        if (requestCode == RankMiddle.SCORE_RANK) {
            //分数排序
            LogUtils.i("rank--success--分数");
            score.setText(data.getScore() + "分");
            up_score.setText(data.getUp_score() + "分");
            lv_sort.setAdapter(new RankListviewAdapter(student_list, this, SORT));
        } else if (requestCode == RankMiddle.TOTAL_TIME_RANK) {
            //时间排序
            score.setText("用时" + WorkCatalogUtils.getSpeendTime(String.valueOf(data.getTotal_time())));
            up_score.setText(WorkCatalogUtils.getSpeendTime(String.valueOf(data.getSpend_time())));
            lv_sort.setAdapter(new RankListviewAdapter(student_list, this, TOTAL_TIME));
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
        UIUtils.showToastSafe("网路链接错误");
        LogUtils.i("rank--failed");
    }

    private void showPractice() {
        //显示修行榜list
//        if (flag_practice) {
        middle.getTotalTimeRankInfo(job_id, new RankBean());
//        } else {
//            setData(RankMiddle.TOTAL_TIME_RANK, scoreCache);
//        }
//        hideMyprogressDialog();
    }

    private void showSortRank() {
        middle.getScoreRankInfo(job_id, new RankBean());
//        setData(RankMiddle.SCORE_RANK, scoreCache);
        //显示排行榜list
//        new RankMiddle(this, this).getScoreRankInfo(job_id, new RankBean());
//        hideMyprogressDialog();
    }
}

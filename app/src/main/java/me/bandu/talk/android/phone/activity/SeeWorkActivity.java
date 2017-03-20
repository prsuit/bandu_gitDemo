package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.SeeWorkListAdapter;
import me.bandu.talk.android.phone.bean.SeeWorkBean;
import me.bandu.talk.android.phone.middle.SeeWorkMiddle;
import me.bandu.talk.android.phone.utils.UserUtil;
/**
 * 创建者:taoge
 * 时间：2015/11/23 14:21
 * 类描述：查看作业
 * 修改人:taoge
 * 修改时间：2015/11/23 14:21
 * 修改备注：
 */
public class SeeWorkActivity extends BaseAppCompatActivity {

    @Bind(R.id.lv)
    ListView listView;
    @Bind(R.id.title_tv)
    TextView tv;
    @Bind(R.id.num_tv)
    TextView numTv;
    @Bind(R.id.num_ntv)
    TextView nNumTv;
    @Bind(R.id.tv_nname)
    TextView nameTv;
    @Bind(R.id.title_right)
    RelativeLayout layoutRight;
    @Bind(R.id.more)
    TextView moreTv;
    @Bind(R.id.write_tv)
    TextView write_tv;
    @Bind(R.id.image)
    ImageView img;

    SeeWorkListAdapter adapter;
    List<SeeWorkBean.DataEntity.DoneListEntity> mList;
    String jobId; //作业id
    boolean isOut; //是否过期


    @Override
    protected int getLayoutId() {
        return R.layout.activity_see_work;
    }

    @Override
    protected void toStart() {
        tv.setText(R.string.see_work);
        jobId = getIntent().getStringExtra("jobId");
        isOut = getIntent().getBooleanExtra("isOut",true);
        if (!isOut) {
            layoutRight.setVisibility(View.GONE);
        } else {
            layoutRight.setVisibility(View.VISIBLE);
            moreTv.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            img.setImageResource(R.mipmap.statistics);
        }
        initData();
    }


    public void initData() {
        mList = new ArrayList();
        adapter = new SeeWorkListAdapter(this, mList);
        listView.setAdapter(adapter);
        seeWork();
    }
    public void seeWork(){
            new SeeWorkMiddle(this, this).seeWork(UserUtil.getUid(),jobId, new SeeWorkBean());
    }
    @OnClick({R.id.image,R.id.write_tv})
    void click(View v){
        switch (v.getId()) {
            case R.id.image:
                Intent intent = new Intent(this,WorkStatisticsActivity.class);
                intent.putExtra("jobId",jobId);
                startActivity(intent);
                break;
            case R.id.write_tv://写评语
                Intent intent1 = new Intent(this,TeacherCommentActivity.class);
                intent1.putExtra("job_id",jobId);
                startActivityForResult(intent1,0);
                break;
        }
    }

    @OnItemClick(R.id.lv)
    void itemClick(int position){
        Intent intent = new Intent(this, SeeStudentWorkActivity.class);
        intent.putExtra("job_id",jobId);
        intent.putExtra("stu_job_id",adapter.getStuJobId(position));
        intent.putExtra("isEvaluated",adapter.isEvaluated(position));
        startActivityForResult(intent,1);
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        SeeWorkBean data = (SeeWorkBean) result;
        if (data != null && data.getStatus() == 1) {
            int doneNum = data.getData().getDone_num();
            int total = data.getData().getTotal();
            int doingNum = total-doneNum;
            initView(doneNum,doingNum,total,data.getData().getDoing_list().toString());
            adapter.setmList(data.getData().getDone_list());
            if (adapter.isEvalued()){
                write_tv.setVisibility(View.VISIBLE);
            }else {
                write_tv.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        write_tv.setVisibility(View.INVISIBLE);
    }


    public void initView(int doneNum,int doingNum,int total,String names){
        numTv.setText(doneNum+"/"+total+"人");//已交
        nNumTv.setText("未交："+doingNum+"人");//未交
        if (names.length() > 2) {
            nameTv.setText(names.substring(1,names.length()-1));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==RESULT_OK){
            seeWork();
        }else if (requestCode==1&&resultCode==RESULT_OK){
            seeWork();
        }
    }
}

package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.ClassStatisticsListviewAdapter;
import me.bandu.talk.android.phone.bean.ClassStatisticsBean;
import me.bandu.talk.android.phone.middle.ClassStatisticsMiddle;

/**
 * Created by wanglanxin on 2016/6/5.
 */
public class ClassStatisticsActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.statistics_class_list)
    ListView statistics_class_list;
    private List<ClassStatisticsBean.DataBean.ListBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics_class;
    }

    @Override
    protected void toStart() {
        findViewById(R.id.title_left).setOnClickListener(this);
        findViewById(R.id.title_back).setOnClickListener(this);
        showMyprogressDialog();
        new ClassStatisticsMiddle(this,this).getClassStatistisc(new ClassStatisticsBean());
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        ClassStatisticsBean bean = (ClassStatisticsBean) result;
        if(bean != null && bean.getStatus() == 1) {
            setData(bean);
        }
        statistics_class_list.setOnItemClickListener(this);
    }
    private void setData(ClassStatisticsBean bean) {
        String duration = bean.getData().getDuration();
        //时间
        tv_date.setText(duration);
        list = bean.getData().getList();
        ClassStatisticsListviewAdapter adapter = new ClassStatisticsListviewAdapter(this, list);
        statistics_class_list.setAdapter(adapter);
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String cid = list.get(position).getCid()+"";
        Intent intent = new Intent(this,CheckWorkListActivity.class);
        intent.putExtra("cid",cid);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        //返回主页
        startActivity(new Intent(this,TeacherHomeActivity.class));
    }

    //返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this,TeacherHomeActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

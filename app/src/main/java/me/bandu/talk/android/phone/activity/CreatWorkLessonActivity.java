package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CreatWorkLessonAdapter;
import me.bandu.talk.android.phone.bean.UnitInfoBean;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.middle.CapterLessonMiddle;
import me.bandu.talk.android.phone.utils.ActivityManager;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkLessonActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.capter_recyclerview)
    ListView listView;
    private CreatWorkLessonAdapter adapter;
    private WorkDataBean dataBean;
    public static final int CREATWORK_CODE_OK = 160;
    private int type_code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_capter_two;
    }

    @Override
    protected void toStart() {
        //方便重置作业与作业完成时的页面小时
        ActivityManager.getActivityManager().addActivity(this);
        dataBean = (WorkDataBean) getIntent().getSerializableExtra("data");
        type_code = getIntent().getIntExtra("type_code",-1);
        if (dataBean!=null){
            showMyprogressDialog();
            new CapterLessonMiddle(this,this).getUnitInfo(dataBean.getUnitid(),new UnitInfoBean());
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode== CapterLessonMiddle.UNIT_INFO){
            UnitInfoBean info = (UnitInfoBean) result;
            adapter = new CreatWorkLessonAdapter(this,info.getData().getLesson_list(),dataBean);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CreatWorkQuizActivity.class);
        dataBean.setLesson_id(adapter.getLesson_id(position));
        dataBean.setLesson_name(adapter.getLesson_name(position));
        intent.putExtra("data",dataBean);
        intent.putExtra("type_code",type_code);
        startActivityForResult(intent, CreatWorkLessonActivity.CREATWORK_CODE_OK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CREATWORK_CODE_OK&&resultCode==RESULT_OK){
            WorkDataBean dataresult = (WorkDataBean) data.getSerializableExtra("data");
            if (dataresult.getSTATUS()==WorkDataBean.ADDING){
                dataBean = dataresult;
                adapter.setDataBean(dataBean);
            }else if (dataresult.getSTATUS()==WorkDataBean.RESET){
                Intent intent = new Intent();
                intent.putExtra("data",dataresult);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }

    @Override
    public void clickRight() {
        super.clickRight();
        Intent intent = new Intent(this, CreatWorkQuizActivity.class);
        intent.putExtra("data",dataBean);
        startActivityForResult(intent, CreatWorkLessonActivity.CREATWORK_CODE_OK);
    }

    @Nullable
    @Override
    public void goback(View view) {
        if (dataBean.getSTATUS()==WorkDataBean.ADDING){
            Intent intent = new Intent();
            intent.putExtra("data",dataBean);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (dataBean.getSTATUS()==WorkDataBean.ADDING){
            Intent intent = new Intent();
            intent.putExtra("data",dataBean);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            finish();
        }
    }
    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }
    @Override
    protected String setTitle() {
        return getString(R.string.select_content);
    }


}

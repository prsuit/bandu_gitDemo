package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CreatWorkQuizAdapter;
import me.bandu.talk.android.phone.bean.LessonInfoBean;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.middle.CapterQuizMiddle;
import me.bandu.talk.android.phone.utils.ActivityManager;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkQuizActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.capter_recyclerview)
    ListView listView;
    @Bind(R.id.title_right)
    RelativeLayout title_right;
    @Bind(R.id.more)
    TextView more;
    private CreatWorkQuizAdapter adapter;
    private WorkDataBean dataBean;
    public static final int CREATWORK_CODE_OK = 160;
    private int type_code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_capter_three;
    }
    @Override
    protected void toStart() {
        //方便重置作业与作业完成时的页面小时
        ActivityManager.getActivityManager().addActivity(this);
        dataBean = (WorkDataBean) getIntent().getSerializableExtra("data");
        type_code = getIntent().getIntExtra("type_code",-1);
        if (dataBean!=null){
            showMyprogressDialog();
            new CapterQuizMiddle(this,this).getLessonInfo(dataBean.getLesson_id(),new LessonInfoBean());
            if (dataBean.getSTATUS()==WorkDataBean.ADDING){
                title_right.setVisibility(View.VISIBLE);
                more.setText(UIUtils.getString(R.string.text_preview));
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter!=null){
            Quiz quiz = adapter.getQuiz(position);
            dataBean.setQuiz_id(quiz.getQuiz_id());
            dataBean.setQuiz_name(quiz.getQuiz_name());
            if (dataBean.addQuiz(quiz)){
                dataBean.setTitle(dataBean.getLesson_name());
                Intent intent = new Intent(this, CreatWorkPreViewActivity.class);
                intent.putExtra("data",dataBean);
                intent.putExtra("type_code",type_code);
//                intent.putExtra("num",info.getData().getQuiz_list().get(position).getSentences_count());
                startActivityForResult(intent, CreatWorkQuizActivity.CREATWORK_CODE_OK);
            }else {
                UIUtils.showToastSafe("您已选过该题目！");
            }
        }
    }
    LessonInfoBean info;
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode == CapterQuizMiddle.LESSON_INFO){
            info = (LessonInfoBean) result;
            adapter = new CreatWorkQuizAdapter(this,info.getData().getQuiz_list(),dataBean);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CREATWORK_CODE_OK&&resultCode==RESULT_OK){
            WorkDataBean dataresult = (WorkDataBean) data.getSerializableExtra("data");
            if (dataresult.getSTATUS()==WorkDataBean.ADDING){
                title_right.setVisibility(View.VISIBLE);
                more.setText(UIUtils.getString(R.string.text_preview));
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
        Intent intent = new Intent(this, CreatWorkPreViewActivity.class);
        intent.putExtra("data",dataBean);
        intent.putExtra("type_code",type_code);
        startActivityForResult(intent, CreatWorkQuizActivity.CREATWORK_CODE_OK);
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

package me.bandu.talk.android.phone.activity;


import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CreatWorkPreviewAdapter;
import me.bandu.talk.android.phone.bean.ClassStudentEntity;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.manager.FullyGridLayoutManager;
import me.bandu.talk.android.phone.middle.CreatWorkMiddle;
import me.bandu.talk.android.phone.utils.ActivityManager;
import me.bandu.talk.android.phone.utils.DateUtils;
import me.bandu.talk.android.phone.utils.PopUtils;
import me.bandu.talk.android.phone.view.MyRecycleView;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkPreViewActivity  extends BaseAppCompatActivity implements PopUtils.OnPopDismissListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.layout_dropdown)
    LinearLayout layout_dropdown;
    @Bind(R.id.creatwork_finishtime)
    TextView creatwork_finishtime;
    @Bind(R.id.read_tv)
    TextView tv_read;
    @Bind(R.id.repead_tv)
    TextView tv_repead;
    @Bind(R.id.finishlevel)
    TextView finishlevel;
    @Bind(R.id.tv_student)
    TextView tv_student;
    @Bind(R.id.text_num)
    TextView text_num;
    @Bind(R.id.creatwork)
    TextView creatwork;
    @Bind(R.id.edit_description)
    EditText edit_description;
    @Bind(R.id.checkbox_recite)
    CheckBox checkbox_recite;
    @Bind(R.id.recyclerView)
    MyRecycleView recyclerView;
    @Bind(R.id.tvNumber)
    TextView tvNumber;
    @Bind(R.id.tv_gendu)
    TextView tv_gendu;
    @Bind(R.id.tv_langdu)
    TextView tv_langdu;
    @Bind(R.id.tv_beisong)
    TextView tv_beisong;
    @Bind(R.id.repead_minus)
    ImageView repead_minus;
    @Bind(R.id.repead_plus)
    ImageView repead_plus;
    @Bind(R.id.read_minus)
    ImageView read_minus;
    @Bind(R.id.read_plus)
    ImageView read_plus;


    public static final int STUDENTLIST_CODE = 40;
    private WorkDataBean dataBean;
    private CreatWorkPreviewAdapter adapter;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String time_end = " 23:59:59";
    private String num;
    private String formatstr = "MM/dd(EEEE)HH点前";
    private String deadformatstr = "yyyy-MM-dd HH:mm:ss";
    private int type_code;

    @Override
    protected void toStart() {
        dataBean = (WorkDataBean) getIntent().getSerializableExtra("data");
        type_code = getIntent().getIntExtra("type_code",-1);
        if(type_code == 2){
            // 说明是新题型的作业，此时要把最下面的三行置灰，并且不可点击
           setDisable();
        }
        setSentenceNum();
        if (dataBean==null){
            dataBean = new WorkDataBean();
        }
        if (isVisbale()){
            finish();
        }
        dataBean.setSTATUS(WorkDataBean.NOMAL);
        findViewById(R.id.goback).setVisibility(View.INVISIBLE);
        edit_description.addTextChangedListener(this);
        checkbox_recite.setOnCheckedChangeListener(this);
        initData();
    }

    /**
     * 新题型的作业，把最下面的控件置灰 不可用
     */
    private void setDisable(){
        tv_gendu.setTextColor(getResources().getColor(R.color.gray));
        tv_langdu.setTextColor(getResources().getColor(R.color.gray));
        tv_beisong.setTextColor(getResources().getColor(R.color.gray));
        repead_minus.setClickable(false);
        repead_plus.setClickable(false);
        read_minus.setClickable(false);
        read_plus.setClickable(false);
        tv_repead.setClickable(false);
        tv_read.setClickable(false);
        checkbox_recite.setClickable(false);
        tv_repead.setTextColor(getResources().getColor(R.color.gray));
        tv_read.setTextColor(getResources().getColor(R.color.gray));
    }
    private void initData() {
        if (dataBean!=null){
            setStudentListText(dataBean.getTotal());
            //截止时间
            if (dataBean.getDeadline()==null||dataBean.getDeadline().equals("")){
                String deadstr = DateUtils.getNumAfterTimeText(formatstr,1);
                creatwork_finishtime.setText(deadstr);//设置默认时间
                dataBean.setDeadlinestr(deadstr);
                dataBean.setDeadline(DateUtils.getNumAfterDeadline(deadformatstr,1));
            }else {
                creatwork_finishtime.setText(dataBean.getDeadlinestr());//设置默认时间
            }
            //默认遍数
            setDefault(dataBean.getCurrentReadCount(),dataBean.getCurrentRepeadCount(),dataBean.getCurrentReciteFlag());
            //作业要求
            finishlevel.setText(dataBean.getLevel_str());
            //作业描述
            if (dataBean.getDescription()!=null&&!dataBean.getDescription().equals("")){
                edit_description.setText(dataBean.getDescription());
            }
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,4);
        recyclerView.setLayoutManager(manager);
        adapter = new CreatWorkPreviewAdapter(this,dataBean);
        recyclerView.setAdapter(adapter);

    }
    //设置默认的属性
    public void setDefault(int read,int repead,boolean recite){
        tv_read.setText(read+"");
        tv_repead.setText(repead+"");
        checkbox_recite.setChecked(recite);
    }
    @OnClick({R.id.creatwork,R.id.work_reset,R.id.linear_studentlist,R.id.linear_finishlevel,R.id.layout_advanced_setting,R.id.linear_finishtime,R.id.read_minus,R.id.read_plus,R.id.repead_minus,R.id.repead_plus})
    void onButtonClick(View v){
        switch (v.getId()){
            case R.id.creatwork:
                creatwork.setClickable(false);
                if (isVisbale()){
                    finish();
                }else {
                    showMyprogressDialog();
                    new CreatWorkMiddle(this,this).creatWork(dataBean);
                }
                break;
            case R.id.work_reset:
                dataBean.setSTATUS(WorkDataBean.RESET);
                Intent intent = new Intent();
                intent.putExtra("data",dataBean);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.layout_advanced_setting:
                //点击展开或者关闭高级要求
                if (layout_dropdown.getVisibility()==View.VISIBLE){
                    layout_dropdown.setVisibility(View.GONE);
                }else if (layout_dropdown.getVisibility()==View.GONE){
                    layout_dropdown.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.linear_finishtime:
                //弹出选择时间
                PopUtils.getInstance().setDate(year,month,day,hour);
                PopUtils.getInstance().showTimePopou(this, findViewById(R.id.bottom), this);
                break;
            case R.id.read_minus:
                try {
                    int times = Integer.parseInt(tv_read.getText().toString());
                    if (times>0){
                        times--;
                        tv_read.setText(times+"");
                        dataBean.setCurrentReadCount(times);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.read_plus:
                try {
                    int times = Integer.parseInt(tv_read.getText().toString());
                    times++;
                    tv_read.setText(times+"");
                    dataBean.setCurrentReadCount(times);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.repead_plus:
                try {
                    int times = Integer.parseInt(tv_repead.getText().toString());
                    times++;
                    tv_repead.setText(times+"");
                    dataBean.setCurrentRepeadCount(times);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.repead_minus:
                try {
                    int times = Integer.parseInt(tv_repead.getText().toString());
                    if (times>0){
                        times--;
                        tv_repead.setText(times+"");
                        dataBean.setCurrentRepeadCount(times);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.linear_finishlevel:
                PopUtils.getInstance().showPopDescription(this, findViewById(R.id.bottom), finishlevel.getText().toString(), this);
                break;
            case R.id.linear_studentlist:
                Intent intent_setudent = new Intent(this, SelectStudentActivity.class);
                intent_setudent.putExtra("selected", (Serializable) dataBean.getSelects());
                intent_setudent.putExtra("cid", dataBean.getCid());
                intent_setudent.putExtra("uid", dataBean.getUid());
                intent_setudent.putExtra("class_name", dataBean.getClass_name());
                startActivityForResult(intent_setudent,STUDENTLIST_CODE);
                break;
        }
    }

    @Override
    public void successFromMid(Object... obj){
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestcode = (int) obj[1];
        Object result = obj[0];
        if (requestcode==CreatWorkMiddle.CREATWORK){
            BaseBean baseBean = (BaseBean) result;
            creatwork.setClickable(true);
            if (baseBean.getStatus()==1){
                UIUtils.showToastSafe(getString(R.string.commit_success_msg));
                ActivityManager.getActivityManager().removeActivity();
                finish();
            }

        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
        int requestcode = (int) obj[0];
        if (requestcode==CreatWorkMiddle.CREATWORK){
            creatwork.setClickable(true);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==STUDENTLIST_CODE&&resultCode==RESULT_OK){
            dataBean.setSelects((List<ClassStudentEntity>) data.getSerializableExtra("list"));
            dataBean.selectToArray();//将选择学生的对象转换成uid的数组
            int total = data.getIntExtra("total",0);
            setStudentListText(total);
        }
    }

    /**
     * 设置句子个数
     */
    public void setSentenceNum(){
        List<Quiz> quizs = dataBean.getQuizList();
        int count = 0;
        for (int i = 0 ; i < quizs.size() ; i ++){
            count+=quizs.get(i).getSentence_num();
        }
        tvNumber.setText(count+"");
    }
    /**
     * 设置学生选择的文本
     * @param total
     */
    public void setStudentListText(int total){
        dataBean.setTotal(total);
        if (total!=0&&dataBean.getUids()!=null&&dataBean.getUids().size()!=0){
            if (total==dataBean.getUids().size()){
                tv_student.setText("默认全选");
            }else {
                tv_student.setText("已选："+dataBean.getUids().size()+"/"+total);
            }
        }else {
            tv_student.setText("默认全选");
        }
    }

    /***************popupwindow相关回调********************/
    @Override
    public void onCommit(String result, int level) {
        finishlevel.setText(result);
        dataBean.setLevel_str(result);
        dataBean.setScore_level(level+"");
    }

    @Override
    public void onCommit(String year, String month, String day,String hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        dataBean.setDeadline(year + "-" + month + "-" + day +" "+ hour+":00:00");
//        String deadstr = PopUtils.getInstance().getTime(year,month,day,0);
        String deadstr = DateUtils.stringToDate(year+"-"+month+"-"+day+" "+hour + ":00:00",formatstr);
        dataBean.setDeadlinestr(deadstr);
        creatwork_finishtime.setText(deadstr);
    }

    @Override
    public void onCancel() {

    }

    /********************输入框的文字监听***************************/
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        text_num.setText(s.length()+"");
    }

    @Override
    public void afterTextChanged(Editable s) {
        dataBean.setDescription(s.toString());
    }
    /**************************单选框的监听器*************************************/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dataBean.setCurrentReciteFlag(isChecked);
    }

    /**
     * 屏蔽返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断数据是否可用
     * @return
     */
    public boolean isVisbale(){
        if (dataBean.getBook_id()==null||dataBean.getBook_id().equals("")||dataBean.getBook_id().equals("0")){
            UIUtils.showToastSafe(getString(R.string.error));
            ActivityManager.getActivityManager().removeActivity();
            return true;
        }
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_creatwork_preview;
    }
    @Override
    protected String setTitle() {
        return getString(R.string.work_preview);
    }
}

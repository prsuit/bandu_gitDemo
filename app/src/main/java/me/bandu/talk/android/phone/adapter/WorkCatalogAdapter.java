package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.utils.ChivoxCreateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoWorkActivity;
import me.bandu.talk.android.phone.activity.TaskCatalogVideoActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.GetKeyBean;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.dao.Quiz;
import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.utils.DialogUtils;
import me.bandu.talk.android.phone.voiceengine.VoiceEngCreateUtils;

/**
 * 创建者：高楠
 * 时间：on 2015/12/10
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCatalogAdapter extends BaseAdapter implements View.OnClickListener, OnSpeechEngineLoaded {
    private Context context;
    List<Quiz> quizList;
    private long quiz_id;
    private TextView notify_info;
    private Map<Integer, Boolean> isDoneMap;
    private LoginBean.DataEntity uerInfo;
    private GetSingleTypeSentence getSingleTypeSentence;
    private int quizListSize;
    public HashMap<Integer, Boolean> finishWorkMap;
    private String quiz_type;
    private String description;
    private int voiceEngineOption = -1;


    public WorkCatalogAdapter(Context context, GetSingleTypeSentence getSingleTypeSentence, List<Quiz> quizList, Bundle data, TextView notify_info, LoginBean.DataEntity uerInfo) {
        this.context = context;
        this.notify_info = notify_info;
        stu_job_id = Long.parseLong(data.getString("stu_job_id"));
        this.quizList = quizList;
        isDoneMap = new HashMap<>();
        this.uerInfo = uerInfo;
        this.getSingleTypeSentence = getSingleTypeSentence;
        if (quizList != null)
            quizListSize = quizList.size() - 1;
        finishWorkMap = new HashMap<>();
    }


    public void setBean(List<Quiz> list) {
        if (list == null || list.size() == 0) {
            return;
        } else {
            this.quizList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return quizList == null ? 0 : quizList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        isDoneMap.put(position, false);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_work_catalog, null);
            holder = new ViewHolder();
            holder.tv_partname = (TextView) convertView.findViewById(R.id.tv_partname);
            holder.tv_read = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tv_repead = (TextView) convertView.findViewById(R.id.tv_repead);
            holder.tv_recite = (TextView) convertView.findViewById(R.id.tv_recite);
            holder.tv_read.setOnClickListener(this);
            holder.tv_repead.setOnClickListener(this);
            holder.tv_recite.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_read.setTag(position);
        holder.tv_repead.setTag(position);
        holder.tv_recite.setTag(position);
        Quiz quiz = quizList.get(position);
        holder.tv_partname.setText(quiz.getQuiz_name());
        if (quiz.getRecite_quiz_id() != null) {
            holder.tv_recite.setVisibility(View.VISIBLE);
            if (quiz.isReciteIsDone() || quiz.getRecite_count() == quiz.getRecite_times()) {
                holder.tv_recite.setText("√");
            } else {
                holder.tv_recite.setText(quiz.getRecite_count() + "/" + quiz.getRecite_times());
            }
//            if (isHaveHomeWorkNum)
//                isHaveHomeWorkNum = holder.tv_recite.getText().equals("√") || quiz.getRecite_count() == 1;
            isFinishHomework(position, holder.tv_recite.getText().equals("√") || quiz.getRecite_count() > 0);
            if (isShowNotifyInfo)
                isShowNotifyInfo = quiz.getRecite_count() > 0;
        } else {
            holder.tv_recite.setVisibility(View.INVISIBLE);
        }
        if (quiz.getRead_quiz_id() != null) {
            holder.tv_read.setVisibility(View.VISIBLE);
            if (quiz.isReadingIsDone() || quiz.getRead_count() == quiz.getRead_times()) {
                holder.tv_read.setText("√");
            } else {
                holder.tv_read.setText(quiz.getRead_count() + "/" + quiz.getRead_times());
            }
            isFinishHomework(position, holder.tv_read.getText().equals("√") || quiz.getRead_count() > 0);

            if (isShowNotifyInfo)
                isShowNotifyInfo = quiz.getRead_count() > 0;
        } else {
            holder.tv_read.setVisibility(View.INVISIBLE);
        }
        if (quiz.getRepeat_quiz_id() != null) {
            holder.tv_repead.setVisibility(View.VISIBLE);
            if (quiz.isRepeatIsDone() || quiz.getRepeat_count() == quiz.getRepeat_times()) {
                holder.tv_repead.setText("√");
            } else {
                holder.tv_repead.setText(quiz.getRepeat_count() + "/" + quiz.getRepeat_times());
            }
            isFinishHomework(position, holder.tv_repead.getText().equals("√") || quiz.getRepeat_count() > 0);

            if (isShowNotifyInfo)
                isShowNotifyInfo = quiz.getRepeat_count() > 0;
        } else {
            holder.tv_repead.setVisibility(View.INVISIBLE);
        }
        if (isShowNotifyInfo)
            notify_info.setVisibility(View.GONE);
        else
            notify_info.setVisibility(View.VISIBLE);

        if (quizListSize == position)
            getSingleTypeSentence.setSubmitState();
        return convertView;
    }

    private void isFinishHomework(int position, boolean b) {
        Boolean aBoolean = finishWorkMap.get(position);
        if (aBoolean == null) {
            finishWorkMap.put(position, b);
        } else {
            if (!aBoolean)
                finishWorkMap.put(position, b);
        }
    }


    public boolean isShowNotifyInfo = true;

    /**
     * true 作业全做完了至少一遍 false是有没做完的作业
     */
    public boolean isHaveHomeWorkNum() {
        if (finishWorkMap.containsValue(false))
            return false;
        return true;
    }

    private long stu_job_id = -1;
    private int currentType = -1;
    /**
     * 自己对比出来的isDone
     */
    private boolean isDone = false;

    @Override
    public void onClick(View v) {
        if (!isFastClick()) {
            DialogUtils.showMyprogressDialog(context, UIUtils.getString(R.string.loding_str), false);
            if (flag) {
            } else {
                int position = (int) v.getTag();
                Quiz quiz = quizList.get(position);
                quiz_id = quiz.getQuiz_id();
                switch (v.getId()) {
                    //currentType = 0:跟读 1:背诵 2:朗读     //PS:wangleiDB  :   1：跟读！2：朗读！3：背诵
                    case R.id.tv_read:
                        isDone = quiz.getRead_count() == quiz.getRead_times();
                        currentType = 2;//朗读
                        break;
                    case R.id.tv_repead:
                        isDone = quiz.getRepeat_count() == quiz.getRepeat_times();
                        currentType = 1;//跟读
                        break;
                    case R.id.tv_recite:
                        isDone = quiz.getRecite_count() == quiz.getRecite_times();
                        currentType = 3;//背诵
                        break;
                }
                quiz_type = quiz.getQuiz_type();
                description = quiz.getDescription();
                if (TextUtils.isEmpty(ChivoxConstants.secretKey)) {
                    getKey();
                } else {
                    int engine = voiceEngineOption != 1 ? ChivoxCreateUtil.ENGINE_CHIVOX : ChivoxCreateUtil.ENGINE_SK;
                    VoiceEngCreateUtils.createEnginAndAIRecorder(WorkCatalogAdapter.this, engine);
                }
            }
            flag = false;
        }
    }

    public void setVoiceEngineOption(int voiceEngineOption) {
        this.voiceEngineOption = voiceEngineOption;
    }

    public void getKey() {
        Map map = new HashMap();
        map.put("pkg", context.getPackageName());
        EasyNetParam param = new EasyNetParam(ConstantValue.GET_KEY, map, new GetKeyBean());
        new EasyNetAsyncTask(-52, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                if (requestCode == -52) {
                    GetKeyBean bean = (GetKeyBean) result;
                    ChivoxConstants.secretKey = bean.getData();
//                    ChivoxCreateUtil.createEnginAndAIRecorder(WorkCatalogAdapter.this);
                    int engine = voiceEngineOption != 1 ? ChivoxCreateUtil.ENGINE_CHIVOX : ChivoxCreateUtil.ENGINE_SK;
                    VoiceEngCreateUtils.createEnginAndAIRecorder(WorkCatalogAdapter.this, engine);
//                    ChivoxCreateUtil.createEnginAndAIRecorder(WorkCatalogAdapter.this, engine);
                }
            }

            @Override
            public void failed(int requestCode) {
//                DialogUtils.hideMyprogressDialog(context);
                UIUtils.showToastSafe("请检查网络是否畅通");
                flag = false;
            }
        }).execute(param);
//        ((BaseAppCompatActivity) context).hideMyprogressDialog();
    }

    //限制用户重读点击
    private boolean flag = false;
    private long type = -1;
//    private boolean myIsDone = false;

    @Override
    public void onLoadSuccess(final int state) {
        DialogUtils.hideMyprogressDialog(context);//关闭dialog
        flag = false;//TODO 位置确认
        //TODO 回调
        switch (currentType) {
            case 1:
                type = 0;
                break;
            case 2:
                type = 2;
                break;
            case 3:
                type = 1;
                break;
        }
        DBUtils.getDbUtils().getSentence(new DBUtils.inter() {
            @Override
            public void getList(List<?> list, boolean bb, int sign) {
                if (bb && getSingleTypeSentence != null && list != null/*&& myIsDone || isDone*/) {//bb list里面全都没有分true,
//                    if (list != null && list.size() > 0) {
//                        List<BestSentenceBean> bsb = (List<BestSentenceBean>) list;
                    getSingleTypeSentence.getSingleTypeSentence((List<BestSentenceBean>) list, currentType, state, quiz_id, true, quiz_type, description);
//                    }
                } else if (list != null) {
                    List<BestSentenceBean> bestSentenceBean = (List<BestSentenceBean>) list;
                    Detail detail = new Detail();
                    List<Detail.DataEntity.ListEntity> entities = new ArrayList<Detail.DataEntity.ListEntity>();
                    detail.setData(new Detail.DataEntity());
                    detail.getData().setList(entities);
                    for (BestSentenceBean bsb : bestSentenceBean) {
                        Detail.DataEntity.ListEntity le = new Detail.DataEntity.ListEntity();
                        le.setEn(bsb.getText());
                        le.setMp3(bsb.getNetAddress());
                        le.setSentence_id(bsb.getSentenceID());
                        le.setHw_quiz_id(bsb.getHw_quiz_id());
                        le.setStu_job_id(bsb.getTaskID());
                        le.setQuiz_id(bsb.getPartID());
                        le.setStu_done(false);
                        le.setAnswer(bsb.getAnswer());
                        le.setStu_mp3(bsb.getMyAddress());
                        le.setWords_score(bsb.getTextColor());
                        le.setCurrent_words_score(bsb.getTextColor());
                        le.setSeconds((int) bsb.getMyVoiceTime());
                        le.setCurrent_stu_seconds((int) bsb.getMyVoiceTime());
                        le.setStu_score((int) bsb.getMyNum());
                        le.setCurrent_mp3(TextUtils.isEmpty(bsb.getMyAddress()) ? bsb.getChishengNetAddress() : bsb.getMyAddress());
                        le.setCurrent_score((int) bsb.getMyNum());
                        le.setCurrent_type(TextUtils.isEmpty(bsb.getMyAddress()) && TextUtils.isEmpty(bsb.getChishengNetAddress()) ? DoWorkListAdapter.WORK_NOTDONE : DoWorkListAdapter.WORK_DONE);
                        le.setMp3_url(bsb.getChishengNetAddress());
                        entities.add(le);
                    }
                    Intent intent;
                    if (quiz_type != null && quiz_type.contains("video"))
                        intent = new Intent(context, TaskCatalogVideoActivity.class);
                    else
                        intent = new Intent(context, DoWorkActivity.class);
                    intent.putExtra("state", state);//驰声本地状态，不用管
                    intent.putExtra("classID", Long.valueOf(uerInfo.getCid()));
                    intent.putExtra("detail", detail);
                    intent.putExtra("quiz_id", quiz_id);
                    intent.putExtra("showBestWork", false);
                    intent.putExtra("stu_job_id", stu_job_id);
                    intent.putExtra("currentType", type + "");
                    intent.putExtra("description", description);
                    intent.putExtra("quiz_type", quiz_type);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("doTheHomework",WorkCatalogAdapter.this);
//                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
//                    ((Activity) (context)).startActivity(intent);
//                    ((Activity) (context)).startActivityForResult(intent, TaskCatalogActivity.DOWORK_CODE);
                }
            }

            @Override
            public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {

            }
        }, 1111, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, quiz_id, currentType, isDone);
    }

    @Override
    public void onLoadError() {
        flag = false;
        DialogUtils.hideMyprogressDialog(context);//关闭dialog
        UIUtils.showToastSafe("评分系统启动失败,请检查网络");
    }

    public class ViewHolder {
        TextView tv_read, tv_repead, tv_recite, tv_partname;
    }

    public interface GetSingleTypeSentence {
        //        void getSingleTypeSentence(long hw_quiz_id, long currentType, int state, long quiz_id);
        void getSingleTypeSentence(List<BestSentenceBean> bsb, long currentType, int state, long quiz_id, boolean clicState, String quiz_type, String description);

        void setSubmitState();
    }


    private static long lastClickTime;

    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

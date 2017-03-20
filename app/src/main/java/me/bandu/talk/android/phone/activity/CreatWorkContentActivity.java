package me.bandu.talk.android.phone.activity;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.CreatWorkContentAdapter;
import me.bandu.talk.android.phone.bean.CreatWorkContentBean1;
import me.bandu.talk.android.phone.bean.CreatWorkContentsBean;
import me.bandu.talk.android.phone.bean.Quiz;
import me.bandu.talk.android.phone.bean.WorkDataBean;
import me.bandu.talk.android.phone.middle.CreatWorkContentMiddle;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;

/**
 * 创建者：高楠
 * 时间：on 2016/4/8
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CreatWorkContentActivity extends BaseAppCompatActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    @Bind(R.id.creatwork_content_expand)
    ExpandableListView expandableListView;
    @Bind(R.id.title_middle)
    TextView title_middle;

    private CreatWorkContentAdapter adapter;
    private int position;
    private WorkDataBean dataBean;
    private String quizid;
    private int curPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_work_content;
    }

    @Override
    protected void toStart() {
        title_middle.setText(getString(R.string.work_content_str));
        dataBean = (WorkDataBean) getIntent().getSerializableExtra("data");
        quizid = getIntent().getStringExtra("quizid");
        curPosition = getIntent().getIntExtra("curPosition",0);
        adapter = new CreatWorkContentAdapter(null, this);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnChildClickListener(this);
        showMyprogressDialog();
        new CreatWorkContentMiddle(this, this).getWorkSentenceList(dataBean.getQuizIds());
    }

    @OnClick(R.id.btn_title_left_image)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_left_image:
                finish();
                break;
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[0];
        if (requestCode == CreatWorkContentMiddle.CREAT_WORK_CONTENT) {
            CreatWorkContentsBean bean = (CreatWorkContentsBean) obj[1];
            if (bean != null && bean.getStatus() == 1) {
                List<CreatWorkContentBean1> ewcbList = getData(bean);
                adapter.setData(ewcbList);
                if (ewcbList.size() > 0)
                    expandableListView.expandGroup(curPosition);

            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        hideMyprogressDialog();
    }

    public void expandPosition() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            if (i != position) {
                expandableListView.collapseGroup(i);
            }
            if (i == position) {
                if (!expandableListView.isGroupExpanded(i)) {
                    expandableListView.expandGroup(i);
                } else {
                    expandableListView.collapseGroup(i);
                }
            }
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        position = groupPosition;
        if (expandableListView.isGroupExpanded(position)) {
            adapter.setGroupPosition(-1);
        } else {
            adapter.setGroupPosition(position);
        }
        expandPosition();
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (adapter != null) {
            String mp3 = adapter.getMp3(groupPosition, childPosition);
            New_VoiceUtils.getInstance().startVoiceNet(mp3);
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        New_VoiceUtils.stopVoice();
    }

    public List<CreatWorkContentBean1> getData(CreatWorkContentsBean bean) {
        List<CreatWorkContentBean1> ewcbList = null;
        if (dataBean != null) {
            ewcbList = new ArrayList<>();
            List<Quiz> quizList = dataBean.getQuizList();
            int size = quizList.size();
            for (int x = 0; x < size; x++) {
                Quiz q = quizList.get(x);
                CreatWorkContentBean1 cwcb = new CreatWorkContentBean1();
                int repeat_count = q.getRepeat_count();//跟读
                int read_count = q.getRead_count();//朗读
                boolean recit = q.isRecit();//背诵
                String gendu = "";
                if (repeat_count > 0) {
                    gendu += "跟读" + repeat_count + "遍  ";
                }
                if (read_count > 0) {
                    gendu += "朗读" + read_count + "遍  ";
                }
                if (recit) {
                    gendu += "背诵1遍";
                }
                cwcb.setType(gendu);
                cwcb.setPartName(q.getQuiz_name());
                List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list = bean.getData().getQuiz_list();
                for (CreatWorkContentsBean.DataBean.QuizListBean qu : quiz_list) {
                    if (qu.getQuiz_id().equals(q.getQuiz_id())) {
                        List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list = qu.getSentence_list();
                        ArrayList<CreatWorkContentBean1.TaskSentenceEntity> tseList = new ArrayList<>();
                        for (CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean cdqs : sentence_list) {
                            CreatWorkContentBean1.TaskSentenceEntity tseBean = new CreatWorkContentBean1.TaskSentenceEntity();
                            tseBean.setText(cdqs.getEn());
                            tseBean.setMap3(cdqs.getMp3());
                            tseList.add(tseBean);
                        }
                        cwcb.setList(tseList);
                        break;
                    }
                }
                ewcbList.add(cwcb);
            }
//                    bean.getData().setQuizNameType(dataBean.getQuizList());
        }
        return ewcbList;
    }
}

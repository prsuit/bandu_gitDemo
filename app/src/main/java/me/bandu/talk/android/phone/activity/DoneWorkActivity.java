package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;

import java.util.ArrayList;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.DoWorkListAdapter;
import me.bandu.talk.android.phone.adapter.DoneWorkListAdapter;
import me.bandu.talk.android.phone.adapter.DoneWorkListBaseAdapter;
import me.bandu.talk.android.phone.adapter.DoneWorkListNewOneAdapter;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
import me.bandu.talk.android.phone.view.LoudView;

/**
 * author by Mckiera
 * time: 2015/12/18  17:04
 * description:
 * updateTime:
 * update description:
 */
public class DoneWorkActivity extends BaseStudentActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.done_work_list_activity;
    }

    @Bind(R.id.goback)
    ImageView goback;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.title_tv)
    TextView title_tv;
//    @Bind(R.id.more)
//    TextView more;
    @Bind(R.id.tvCurrentType)
    TextView tvCurrentType;
    @Bind(R.id.title_right)
    RelativeLayout title_right;
    @Bind(R.id.lv)
    ListView lv;

    @Bind(R.id.tvCurrCountAndCount)
    TextView tvCurrCountAndCount;
//    @Bind(R.id.layout1)
//    LinearLayout layout1;
//    @Bind(R.id.llRecordBtn)
//    LinearLayout llRecordBtn;

//    @Bind(R.id.rl_dowork_recordbg)
//    RelativeLayout rl_dowork_recordbg;
//
//    @Bind(R.id.tv_dowork_content)
//    TextView tv_dowork_content;
//    @Bind(R.id.lv_dowork_loudView)
//    LoudView lv_dowork_loudView;


    //驰声初始化状态.
    // OnSpeechEngineLoaded   ONLINE_OK = 1 int OFFLINE_OK = 2 int ALL_OK = 3;
    private int state;
    private Detail detail;

    private DoneWorkListBaseAdapter adapter;

    private String currentType; //0:跟读 1:背诵 2:朗读


    //题干
    private String description;
    //八种新类型
    private String quiz_type;
    //是否是八种新类型
    private boolean isNew = false;

    @Override
    protected void toStart() {
        initView(); //界面初始化.
        // 这个页面设置成启动页时候.注释掉这行代码
        setData();  //初始化数据
    }


    @Override
    public void initView() {
        goback.setVisibility(View.VISIBLE);
        title_tv.setText(UIUtils.getString(R.string.title_string_workinfo));
        image.setVisibility(View.GONE);
        title_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            state = intent.getIntExtra("state", -1);
            currentType = intent.getStringExtra("currentType");
            detail = (Detail) intent.getSerializableExtra("detail");
            description = intent.getStringExtra("description");
            quiz_type = intent.getStringExtra("quiz_type");
        }
        if (!TextUtils.isEmpty(currentType)) {
            String type = "";
            switch (currentType) {
                case "0":
                    type = "本遍为跟读作业";
                    break;
                case "1":
                    type = "本遍为背诵作业";
                    break;
                case "2":
                    type = "本遍为朗读作业";
                    break;
            }
            tvCurrentType.setText("" + type);
        }

        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(quiz_type)) {
            isNew = true;
            tvCurrCountAndCount.setText("题干");
            tvCurrCountAndCount.setVisibility(View.VISIBLE);
            tvCurrCountAndCount.setTextColor(UIUtils.getColor(R.color.teacher_color));
            tvCurrCountAndCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialogUtils.getInstance().init(DoneWorkActivity.this, description);
                    AlertDialogUtils.getInstance().initDialog(DoneWorkActivity.this,description);
                }
            });
            adapter = new DoneWorkListNewOneAdapter(detail, this, currentType, lv);
            adapter.cleanStatementResult();
            ChivoxConstants.statementResult = new ArrayList<>();
            lv.setAdapter(adapter);

        }else {
            isNew = false;
            if (detail != null) {
                adapter = new DoneWorkListAdapter(detail, this, currentType, lv);
                adapter.cleanStatementResult();
                ChivoxConstants.statementResult = new ArrayList<>();
                lv.setAdapter(adapter);
            } else {
                UIUtils.showToastSafe("作业加载失败,请重新尝试进入");
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DoneWorkActivity.this.finish();
                    }
                }, 1000);
            }
        }
    }

    @Override
    public void setListeners() {
        //do nothing
    }


    @Override
    protected void onDestroy() {
        if (adapter != null) {
            adapter.cleanStatementResult();
        }
        super.onDestroy();
    }

}

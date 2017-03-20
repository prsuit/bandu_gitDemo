package me.bandu.talk.android.phone.activity;

import android.text.Html;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
/**
 * 创建者：tg
 * 类描述：分数信息
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ScoreInfoActivity extends BaseAppCompatActivity {

    @Bind(R.id.main_tv)
    TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score_info;
    }

    @Override
    protected void toStart() {
        String styledText = "每句录音的评分按照<font color='red'>A、B、C</font>三种等级划分。";
        tv.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
    }

    @OnClick(R.id.closebtn)
    void click(){
        this.finish();
    }
}

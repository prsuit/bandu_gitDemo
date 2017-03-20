package me.bandu.talk.android.phone.activity;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.utils.UIUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.FeedTagBean;
import me.bandu.talk.android.phone.middle.FeedBackMiddle;
import me.bandu.talk.android.phone.middle.GetTagMiddle;
import me.bandu.talk.android.phone.utils.Utils;
import me.bandu.talk.android.phone.view.TagCloudView;

/**
 * 创建者：tg
 * 类描述：意见反馈
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FeedBackActivity extends BaseAppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.feed_edt)
    EditText feedEdt;
    @Bind(R.id.contact_edt)
    EditText contactEdt;
    @Bind(R.id.num_tv)
    TextView numTv;
    List<FeedTagBean.DataBean.ListBean> tags;
    TagCloudView tagCloudView;
    //String[] s = {"页面效果","评分及录音","稳定性、使用异常","教材纠错","功能不好用","其他","一点建议"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void toStart() {
        titleTv.setText(R.string.feed_back);
        initEvent();
        tags = new ArrayList<>();
        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud_view);
        new GetTagMiddle(this,this).getTag(new FeedTagBean());
    }


    @OnClick({R.id.goback,R.id.send})
    void click(View v){
        switch (v.getId()) {
            case R.id.goback:
                finish();
                break;
            case R.id.send:
                String content = feedEdt.getText().toString().trim();
                String contact = contactEdt.getText().toString().trim();
                String source ="手机型号:"+android.os.Build.MODEL +","+"手机操作系统:"+ android.os.Build.VERSION.RELEASE+","+
                        "应用版本号:"+Utils.getAppVersionName(this);
                if (!TextUtils.isEmpty(content)) {
                    if (!TextUtils.isEmpty(contact)) {
                        if (Utils.isEmail(contact)) {
                            new FeedBackMiddle(this, this).feedBack(content, source, contact,tags, new BaseBean());
                        } else {
                            UIUtils.showToastSafe(getString(R.string.mail_incorrect));
                        }
                    } else {
                        new FeedBackMiddle(this, this).feedBack(content, source, contact,tags, new BaseBean());
                    }
                } else {
                    UIUtils.showToastSafe(getString(R.string.feed_back_input));
                }
                break;
        }
    }


    @Override
    public void onSuccess(Object result, int requestCode) {
        if (1 == requestCode) {
            BaseBean bean = (BaseBean) result;
            if (bean != null && bean.getStatus() == 1) {
                finish();
                UIUtils.showToastSafe(bean.getMsg());
            } else if (bean!=null){
                UIUtils.showToastSafe(bean.getMsg());
            }
        } else if (2 == requestCode) {
            FeedTagBean tagBean = (FeedTagBean) result;
            if (tagBean != null && tagBean.getStatus() == 1) {
                tags = tagBean.getData().getList();
                tagCloudView.setTags(tags);
                tagCloudView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position) {
                        if (tags.get(position).isSelect()) {
                            tags.get(position).setSelect(false);
                        } else {
                            tags.get(position).setSelect(true);
                        }
                        tagCloudView.setTags(tags);
                    }
                });
            }
        }

    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

    public void initEvent(){
        feedEdt.setFilters(new InputFilter[]{ new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                try {
                    //转换成中文字符集的长度
                    int destLen = dest.toString().getBytes("GB18030").length;
                    int sourceLen = source.toString().getBytes("GB18030").length;
                    if (destLen + sourceLen > 200) {
                        UIUtils.showToastSafe(getString(R.string.limit_char_info));
                        return "";
                    }
                    //如果按回退键
                    if (source.length() < 1 && (dend - dstart >= 1)) {
                        numTv.setText(dend-1+"/200");
                        return dest.subSequence(dstart, dend - 1);
                    }
                    numTv.setText(destLen + sourceLen+"/200");
                    //其他情况直接返回输入的内容
                    return source;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }});

    }
}

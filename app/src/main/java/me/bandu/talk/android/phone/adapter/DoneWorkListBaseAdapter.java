package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.utils.ChivoxUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoWorkActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.holder.BaseHolderView;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * author by Mckiera
 * time: 2015/12/21  20:54
 * description: 做作业adapter
 * updateTime:
 * update description:
 */
public abstract class DoneWorkListBaseAdapter extends BaseAdapter implements BaseActivityIF {
    protected MyProgressDialog pd;
    /* item的三种状态 */
    public static final int WORK_NOTDONE = 1;  //未做
    public static final int WORK_DOING = 2;    //正在做
    public static final int WORK_DONE = 3;     //已做
    public static final int WORK_DONE_DOING = 4;//已做完又被聚焦

    protected long hw_quiz_id;

    protected List<Detail.DataEntity.ListEntity> list;// 数据源.
    protected Context context;
    protected String currentType;//0:跟读 1:背诵 2:朗读
    protected ListView lv;   //当前的listview
    protected ChivoxUtil chivoxUtil;//驰声工具. 控制开始和结束
    protected List<BaseHolderView> holders; //所有holder的集合
    protected Map<Integer, Boolean> doneMap;
    // 当前的位置
    protected int currPosition = -1;
    //最大的位置
    protected int maxPosition;
    //数据库是否需要新页
    protected boolean needNewPage;

    public DoneWorkListBaseAdapter(Detail detail, Context context, String currentType, ListView lv) {
        Animation3DUtil.getInstance().clearList();
        this.list = detail.getData().getList();
        hw_quiz_id = detail.getData().getList().get(0).getHw_quiz_id();
        this.context = context;
        this.currentType = currentType;
        this.lv = lv;
        doneMap = new HashMap<Integer, Boolean>();
        for (int i = 0; i < list.size(); i++) {
            doneMap.put(i, !TextUtils.isEmpty(list.get(i).getCurrent_mp3()));
        }
        needNewPage = doneMap.containsValue(false);
        if(needNewPage && ("1".equals(currentType) || "2".equals(currentType))){
            for(int i=0; i < doneMap.size(); i++){
                if(!doneMap.get(i)){
                    currPosition = i;
                    break;
                }
            }
        }
        holders = new ArrayList<>();
    }


    public long getHw_quiz_id() {
        return hw_quiz_id;
    }


    @Override
    public int getCount() {
        maxPosition = list.size() - 1;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View popView;
    protected String clickstr = "";

    @Override
    public void successFromMid(Object... obj) {
        if (WordsMiddle.GET_WORD == (int) obj[1]) {
            final MWordBean wordBean = (MWordBean) obj[0];
            if (wordBean.getStatus() == 1) {
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popView = PopWordUtils.getInstance().showWorkPop(context, ((DoWorkActivity) context).rlRoot, wordBean.getData(), new PopWordUtils.PopWordViewOnClick() {
                            @Override
                            public void btnOnClick(WordBean word) {
                                //UIUtils.showToastSafe(word.getQuery());
                                clickstr = word.getQuery();
                                WordsMiddle wordsMiddle = new WordsMiddle(DoneWorkListBaseAdapter.this, context);
                                wordsMiddle.createWord(word.getQuery(), word);
                            }

                            @Override
                            public void imgOnClick(WordBean word) {
                                New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio=" + word.getQuery());
                            }
                        }, new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                notifyDataSetChanged();
                            }
                        });
                    }
                }, 200);
            } else {
                UIUtils.showToastSafe("未查询到该单词的翻译结果");
            }
        } else if (WordsMiddle.CREATE_WORDS == (int) obj[1]) {
            BaseBean baseBean = (BaseBean) obj[0];
            UIUtils.showToastSafe(baseBean.getMsg());
            if (popView != null) {
                Button btn = (Button) popView.findViewById(R.id.btnAddWord);
                btn.setText("已添加");
                btn.setBackgroundResource(R.drawable.corners_gray);
                btn.setTextColor(ColorUtil.getResourceColor(context, R.color.white));
                btn.setFocusable(false);
                btn.setClickable(false);
            }
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {

    }

    @Override
    public void onFailed(int requestCode) {

    }



    public void cleanStatementResult() {
        if (ChivoxConstants.statementResult != null && ChivoxConstants.statementResult.size() > 0) {
            ChivoxConstants.statementResult.clear();
        }
    }


    public void getEachWord(TextView textView, int position) {
//        Spannable spans = (Spannable) textView.getText();
        String s = textView.getText().toString();
        SpannableString spans = new SpannableString(s);
        Integer[] indices = getIndices(textView.getText().toString().trim() + " ", ' ');
        int start = 0;
        int end = 0;
        for (int i = 0; i < indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan(position);
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
    }

    public Integer[] getIndices(String ss, char c) {
        int pos = ss.indexOf(c, 0);
        List<Integer> integers = new ArrayList<>();
        while (pos != -1) {
            integers.add(pos);
            pos = ss.indexOf(c, pos + 1);
        }
        return integers.toArray(new Integer[0]);
    }

    protected ClickableSpan getClickableSpan(final int position) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(position != currPosition){
                    currPosition = position;
                    DoneWorkListBaseAdapter.this.notifyDataSetChanged();
                    return;
                }
                final TextView tv = (TextView) widget;
                String str = tv.getText().toString().trim();
                int select_start = tv.getSelectionStart();
                int select_end = tv.getSelectionEnd();
                if (str != null && str.length() != 0 && select_start < str.length() && select_start < select_end) {
                    String s = tv.getText().subSequence(select_start, select_end).toString();
                    SpannableStringBuilder style = new SpannableStringBuilder(str);
                    style.setSpan(new ForegroundColorSpan(Color.WHITE), tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                    style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context, R.color.student_title_bg)), tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                    tv.setText(style);

                    WordsMiddle youDaoMiddle = new WordsMiddle(DoneWorkListBaseAdapter.this, context);
                    youDaoMiddle.getWord(StringUtil.quBiaoDian(s));
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //ds.setColor(ColorUtil.getResourceColor(context,R.color.text_gray));
                ds.setUnderlineText(false);
            }
        };
    }
}

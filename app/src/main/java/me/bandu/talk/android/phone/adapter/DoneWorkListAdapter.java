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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.chivox.ChivoxConstants;
import com.chivox.utils.ChivoxUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoWorkActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;
import me.bandu.talk.android.phone.view.ReversalView;

/**
 * author by Mckiera
 * time: 2015/12/21  20:54
 * description: 做作业adapter
 * updateTime:
 * update description:
 */
public class DoneWorkListAdapter extends DoneWorkListBaseAdapter implements BaseActivityIF {

    private MyProgressDialog pd;
    /* item的三种状态 */
    public static final int WORK_NOTDONE = 1;  //未做
    public static final int WORK_DOING = 2;    //正在做
    public static final int WORK_DONE = 3;     //已做
    public static final int WORK_DONE_DOING = 4;//已做完又被聚焦

    private long hw_quiz_id;

    private List<Detail.DataEntity.ListEntity> list;// 数据源.
    private Context context;
    private String currentType;//0:跟读 1:背诵 2:朗读
    private ListView lv;   //当前的listview
    private ChivoxUtil chivoxUtil;//驰声工具. 控制开始和结束
    private List<HolderView> holders; //所有holder的集合
    private Map<Integer, Boolean> doneMap;
    // 当前的位置
    private int currPosition = -1;
    //最大的位置
    private int maxPosition;
    //数据库是否需要新页
    private boolean needNewPage;

    public DoneWorkListAdapter(Detail detail, Context context, String currentType, ListView lv) {
        super(detail, context, currentType, lv);
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
        if (needNewPage && ("1".equals(currentType) || "2".equals(currentType))) {
            for (int i = 0; i < doneMap.size(); i++) {
                if (!doneMap.get(i)) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holder;
        if (convertView != null) {
            holder = (HolderView) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.done_work_list_item, null);
            holder = new HolderView(convertView);
            convertView.setTag(holder);
        }
        holders.add(position, holder);

        holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "", list.get(position).getWords_score()));
        holder.ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_VoiceUtils.getInstance().startVoiceNet(list.get(position).getMp3());
            }
        });
        holder.tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(list.get(position).getStu_mp3())) {
                    UIUtils.showToastSafe("缺少录音");
                    return;
                }
                New_VoiceUtils.getInstance().startVoiceNet(list.get(position).getStu_mp3());
            }
        });
        LogUtils.i("语音时长:" + list.get(position).getSeconds());
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < list.get(position).getSeconds(); i++) {
            if (i >= 10) {
                break;
            }
            sb.append("　　");
        }
        holder.tvDuration.setText(sb.toString() + list.get(position).getSeconds() + "\"");

        getEachWord(holder.tvContent, position);

        int score = list.get(position).getStu_score();
        if (score <= 0) {
            score = 0;
        }
        holder.rlScore.setScore(score);
        return convertView;
    }

    private View popView;
    private String clickstr = "";

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
                                WordsMiddle wordsMiddle = new WordsMiddle(DoneWorkListAdapter.this, context);
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


    public static class HolderView {
        /**
         * 内容
         */
        @Bind(R.id.tvContent)
        TextView tvContent;

        /**
         * 小喇叭
         */
        @Bind(R.id.ivHorn)
        ImageView ivHorn;

        /**
         * 已做语音内容
         */
        @Bind(R.id.llWorkContent)
        LinearLayout llWorkContent;

        /**
         * 分数(图片)
         */
//        @Bind(R.id.ivScore)
//        ImageView ivScore;

        /**
         * 分数(文本)
         */
//        @Bind(R.id.tvScore)
//        TextView tvScore;
        /**
         * 用时
         */
        @Bind(R.id.tvDuration)
        TextView tvDuration;

        /**
         * 整个item
         */
        @Bind(R.id.rlItem)
        RelativeLayout rlItem;
        /**
         * 整个item
         */
        @Bind(R.id.rlScore)
        ReversalView rlScore;

        public HolderView(View view) {
            ButterKnife.bind(this, view);
        }
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

}

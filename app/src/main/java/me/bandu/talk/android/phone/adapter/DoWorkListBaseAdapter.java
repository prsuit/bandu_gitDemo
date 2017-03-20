package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.Spannable;
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

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.chivox.ChivoxGlobalParams;
import com.chivox.bean.AIError;
import com.chivox.utils.ChivoxCreateUtil;
import com.chivox.utils.ChivoxUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.DoWorkActivity;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.MWordBean;
import me.bandu.talk.android.phone.bean.WordBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.holder.BaseHolderView;
import me.bandu.talk.android.phone.middle.ErrorMiddle;
import me.bandu.talk.android.phone.middle.WordsMiddle;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.MediaPathUtils;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.voiceengine.VoiceEngineUtils;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-02  17:07
 * <p>类描述：做作业界面 抽出来一个基类
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public abstract class DoWorkListBaseAdapter extends BaseAdapter implements BaseActivityIF {
    /* item的三种状态 */
    public static final int WORK_NOTDONE = 1;   //未做
    public static final int WORK_DOING = 2;     //正在做
    public static final int WORK_DONE = 3;      //已做
    public static final int WORK_DONE_DOING = 4;//已做完又被聚焦

    protected DoWorkListBaseAdapter thiz;

    public DoWorkListBaseAdapter(){
        thiz = this;
    }

    protected long hw_quiz_id;

    protected List<Detail.DataEntity.ListEntity> list;// 数据源.
    protected Context context;
    protected String currentType;//0:跟读 1:背诵 2:朗读     //PS:wangleiDB  :   1：跟读！2：朗读！3：背诵
    protected static final String REPEAD = "0";//跟读
    protected static final String RECITE = "1";//背诵
    protected static final String READ = "2";  //朗读
    protected ListView lv;   //当前的listview
    protected VoiceEngineUtils chivoxUtil;//驰声工具. 控制开始和结束
    // protected List<HolderView> holders; //所有holder的集合
    protected List<Integer> holdersFlag; //所有holder的集合
    protected Map<Integer, Boolean> doneMap;
    // 当前的位置
    protected int currPosition = -1;
    //最大的位置
    protected int maxPosition;
    //数据库是否需要新页
    protected boolean needNewPage;

    protected boolean showBestWork;

    protected long classID;

    protected long stu_job_id;



    public boolean isNeedNewPage() {
        return needNewPage;
    }

    public Map<Integer, Boolean> getDoneMap() {
        return doneMap;
    }
    /**
     * 设置完成的作业
     *
     * @param position 作业的位置
     * @return 成功/ 失败
     */
    public boolean setDoneWorkOK(int position) {
        if (doneMap.containsKey(position))
            return doneMap.put(position, true);
        return false;
    }

    public long getHw_quiz_id() {
        return hw_quiz_id;
    }

    /**
     * 对外提供item的文本内容
     *
     * @param position item的位置
     * @return 英文文本的内容
     */
    public String getContent(int position) {
        return list.get(position).getEn();
    }
    public String getAnswer(int position) {
        return list.get(position).getAnswer();
    }

    /**
     * 对外提供句子的ID
     *
     * @param position item的位置
     * @return 句子ID
     */
    public String getSentenceID(int position) {
        return String.valueOf(list.get(position).getSentence_id());
    }

    /**
     * 获取当前的位置
     *
     * @return 当前的位置
     */
    public int getCurrPosition() {
        if (currPosition > maxPosition) {
            currPosition = maxPosition;
        }
        return currPosition;
    }

    /**
     * 设置当前的位置
     *
     * @param currPosition 当前的位置
     */
    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
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

    protected abstract void changeItemState(int flag, BaseHolderView holder);

   // public abstract int startRecord(int position, final String content, String sentenceID, Map<String, Object> holderObj);

    public void stopRecord() {
        if (chivoxUtil == null) {
            return;
        }
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chivoxUtil.stop();
            }
        }, 100);

    }

    public void playMp3(String urlOrPath, int id, View view) {
        if (TextUtils.isEmpty(urlOrPath)) {
            UIUtils.showToastSafe("缺少音频文件,请重新尝试");
            return;
        }
        if (urlOrPath.contains("http")) {
            New_VoiceUtils.getInstance().startVoiceWithCacheListener(context, urlOrPath, view == null ? null : new MyVoiceListenerImpl(view));
        } else {
            New_VoiceUtils.getInstance().startVoiceFileWithListener(new File(urlOrPath), view == null ? null : new MyVoiceListenerImpl(view));
        }
    }
    public class MyVoiceListenerImpl implements New_VoiceUtils.VoiceListener {

        private AnimationDrawable background;

        public MyVoiceListenerImpl(View view) {
            if (view == null) {
                throw new IllegalArgumentException("需要动画的view是null");
            }
            background = (AnimationDrawable) view.getBackground();
        }

        @Override
        public void startVoice(String path) {
            background.start();
        }

        @Override
        public void endVoice(String path) {
            background.stop();
        }

        @Override
        public void error(Exception e) {
            background.stop();
        }
    }

    public void getEachWord(TextView textView, int position) {
        Spannable spans = (Spannable) textView.getText();
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


    private ClickableSpan getClickableSpan(final int position) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(position != currPosition){
                    currPosition = position;
                    notifyDataSetChanged();
                    return;
                }
                final TextView tv = (TextView) widget;
                String str = tv.getText().toString().trim();
                int select_start = tv.getSelectionStart();
                int select_end = tv.getSelectionEnd();
                if (str != null && str.length() != 0 && select_start < str.length() && select_start < select_end) {
                    String s = null;
                    try {
                        s = tv.getText().subSequence(select_start, select_end).toString();
                        SpannableStringBuilder style = new SpannableStringBuilder(str);
                        style.setSpan(new ForegroundColorSpan(Color.WHITE), tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                        style.setSpan(new BackgroundColorSpan(ColorUtil.getResourceColor(context, R.color.student_title_bg)), tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                        tv.setText(style);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    WordsMiddle youDaoMiddle = new WordsMiddle(DoWorkListBaseAdapter.this, context);
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
    private MyProgressDialog pd;
    public void showPd(Context context) {
        if (pd != null) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            pd = null;
        }
        pd = MyProgressDialog.createProgressDialog(context, "");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }
    /**
     * 是否还有 还有 未做完的 题
     *
     * @return true 有未做完的题 false 没有未做完的题
     */
    public boolean haveNext() {
        for (int i = 0; i < list.size(); i++)
            if (doneMap.containsKey(i))
                if (!doneMap.get(i))
                    return true;
        setCurrPosition(-1);
        return false;
    }

    /**
     * 获取下一个点
     *
     * @return
     */
    protected int getNextPosition() {
        for (int i = 0; i < list.size(); i++) {
            if (doneMap.containsKey(i)) {
                if (!doneMap.get(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    protected boolean getNextPosition(int position) {
        if (doneMap.containsKey(position))
            return doneMap.get(position);
        return false;
    }

    protected int getAfterPosition(int position) {
        for (int i = position; i < list.size(); i++) {
            if (doneMap.containsKey(i)) {
                if (!doneMap.get(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //TODO
    public void doNext() {
        currPosition++;
        LogUtils.e("当前的位置:" + currPosition + " 最大位置:" + maxPosition);
        if (currPosition > maxPosition) {
            boolean b = haveNext();
            LogUtils.e("当currPosition > maxPosition时    是否还有下一个了?=== " + b);
            //当前位置如果大于最大位置时候,判断是否还有没有做的题
            if (b) {//如果有
                int nextPosition = getNextPosition();//获取到正数没有做的题的位置
                onItemClick(nextPosition, "");//做这个题
            } else {//如果没有
                setCurrPosition(-1);
            }
        } else if (currPosition == maxPosition) {
            boolean b = haveNext();
            LogUtils.e("当currPosition == maxPosition时    是否还有下一个了?=== " + b);
            if (b) {//如果有
                boolean nextPosition = getNextPosition(currPosition);//查看下一题是否做过.
                if (nextPosition) {//如果做过
                    onItemClick(getNextPosition(), "");//获取到正数没有做的题的位置      做这个题
                } else {//如果没做过
                    onItemClick(currPosition, ""); //做这个没做过的题.
                }
            } else {//如果没有
                setCurrPosition(-1);
            }
        } else {
            boolean b = haveNext();
            LogUtils.e("当currPosition != maxPosition && currPosition < maxPosition 时    是否还有下一个了?=== " + b);
            if (b) {//如果有
                final int afterPosition = getAfterPosition(currPosition);//看看在当前位置之后是否还有题,如果有题,拿到题的位置
                if (afterPosition > 0) { //如果有题
                    // if (afterPosition > holders.size()) {
                    lv.smoothScrollToPosition(afterPosition);
                    notifyDataSetChanged();
                    //  }
                    UIUtils.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onItemClick(afterPosition, ""); //做这个没做过的题.
                        }
                    }, 100);

                } else { //如果之后没有提
                    onItemClick(getNextPosition(), "");//获取到正数没有做的题的位置      做这个题
                }
            } else {//如果没有
                setCurrPosition(-1);
            }
        }
    }

    public void onItemClick(final int position, final String mp3Url) {
        currPosition = position;
        //  if(holders.size() < position){
        this.lv.smoothScrollToPosition(position);
        notifyDataSetChanged();
        //  }
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                holdersFlag.set(position, WORK_DOING);
                notifyDataSetChanged();
                switch (currentType) {
                    case REPEAD:
                        if (!TextUtils.isEmpty(mp3Url))
                            New_VoiceUtils.getInstance().startVoiceWithCache(context, mp3Url);
                        break;
                }
            }
        }, 1000);

    }

    protected void closeActivity(final Activity activity) {
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChivoxCreateUtil.deleteEngineAndRecorder();
                stopRecord();
                activity.finish();
            }
        }, 1500);
    }

    public void closePd() {
        ChivoxGlobalParams.START_CHIVOX = false;
        if (pd != null && pd.isShowing()) {
            try {
                pd.dismiss();
            } catch (Exception e) {
                //TODO 这个怎么办...
            }
        }
    }

    int count = 0;

    public int startRecord(int position, String content, String sentenceID, Map<String, Object> holderObj) {
        if (chivoxUtil == null)
            chivoxUtil = new VoiceEngineUtils(context, GlobalParams.userInfo == null ? "DFHT_Android" : GlobalParams.userInfo.getUid());
        //VoiceUtils.getInstance().stopVoice();
        New_VoiceUtils.stopVoice();
        return chivoxUtil.start(content, MediaPathUtils.getPath(), sentenceID, position, holderObj, new EngOkCallBack() {

            @Override
            public void getScore(String score, String path, String sentenceID, int position, List<AIRecorderDetails> details, Map<String, Object> holderObj) {
                ChivoxGlobalParams.START_CHIVOX = false;
                closePd();
                LogUtils.i("评分成功....");
                LogUtils.i("score : " + score);
                LogUtils.i("details : " + details);
                LogUtils.i("sentenceID : " + sentenceID);
                LogUtils.i("position : " + position);
                if (!showBestWork || list.get(position).getStu_score() < Integer.valueOf(score)) {
                    holdersFlag.set(position, WORK_DONE);

                    list.get(position).setCurrent_mp3(path);

                    JSONArray jsonArray = new JSONArray();
                    boolean hasIndict = false;
                    try {
                        for (int i = 0; i < details.size(); i++) {
                            JSONObject object = new JSONObject();
                            if (details.get(i).getIndict() == 0) {//如果有编外词  把编外词默认给70~90分
                                Random random = new Random();
                                int i1 = random.nextInt(20);
                                details.get(i).setScore(i1 + 70);
                                hasIndict = true;
                            }
                            object.put("score", Integer.valueOf(details.get(i).getScore()));
                            object.put("word", details.get(i).getCharStr());
                            jsonArray.put(object);
                        }
                    } catch (Exception e) {
                        //TODO
                    }
                    if (hasIndict) {//如果有编外词, 就重新计算平均分.
                        int allScore = 0;
                        for (int i = 0; i < details.size(); i++) {
                            allScore += details.get(i).getScore();
                        }
                        score = String.valueOf(allScore / details.size());
                    }
                    int scoreInt = Integer.valueOf(score);
                    if (scoreInt <= 0)
                        scoreInt = 1;
                    list.get(position).setCurrent_score(scoreInt);
                    list.get(position).setCurrent_words_score(jsonArray.toString());
                    list.get(position).setWords_score(jsonArray.toString());
                    list.get(position).setCurrent_type(WORK_DONE);
                    list.get(position).setStu_mp3(path);
                    list.get(position).setStu_score(scoreInt);
                    int time = New_VoiceUtils.getVoiceLength(path);
                    if (time > 0 && time < 1000) {
                        time = 1000;
                    }
                    list.get(position).setSeconds(time / 1000);
                    list.get(position).setCurrent_stu_seconds(time / 1000);
                    list.get(position).setMp3_url((String) holderObj.get("mp3_url"));
                    //读完句子 评分后 存入数据库
                    //DaoUtils.getInstance().saveListEntity(list.get(position));

                    String uid = UserUtil.getUid();
                    long type = -1;
                    //0:跟读 1:背诵 2:朗读     //PS:wangleiDB  :   1：跟读！2：朗读！3：背诵
                    switch (currentType) {
                        case "0":
                            type = 1;
                            break;
                        case "1":
                            type = 3;
                            break;
                        case "2":
                            type = 2;
                            break;
                    }


                    DBUtils.getDbUtils().saveDB(Long.parseLong(uid), classID, list.get(position).getQuiz_id(), stu_job_id, type, list.get(position));
                    //设置当前position 完成
                    setDoneWorkOK(position);
                }
                //自动下一个.
                if (RECITE.equals(currentType) || READ.equals(currentType)) {
                    int currPosition = getCurrPosition();
                    LogUtils.e("松手后currPosition" + currPosition);
                    if (currPosition != -1) {
                        doNext();
                        lv.smoothScrollToPosition(DoWorkListBaseAdapter.this.currPosition);
//                        notifyDataSetChanged();
                    }
                } else {
                    currPosition = -1;
//                    notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }

            @Override
            public void faild(String content, String path, String sentenceID, int position, String msg, Map<String, Object> holderObj) {
                //FIXME 无网时候直接调用的话,在线评分会离开回调faild方法. pd关不掉.
                ChivoxGlobalParams.START_CHIVOX = false;
                closePd();
                LogUtils.i("评分失败喽~~");
                //发送错误日志给服务器
                ErrorMiddle middle = new ErrorMiddle((BaseActivityIF) context, context);
                String json = (String) holderObj.get("json");
                middle.sendError(sentenceID, msg, json);

                AIError error = JSONUtils.readValue(msg, AIError.class);

                if (ChivoxCreateUtil.onLineCreateOk) {
                    if (count++ > 0)
                        ChivoxCreateUtil.onLineCreateOk = false;
                    ChivoxCreateUtil.notifyChanged();
                    if (error != null)
                        UIUtils.showToastSafe("评分失败,错误编号=" + error.getErrId() + "");
                    else
                        UIUtils.showToastSafe("评分失败,请重新尝试");
                    return;
                }

                if (error != null)
                    UIUtils.showToastSafe("评分失败,错误编号=" + error.getErrId() + "");
                else
                    UIUtils.showToastSafe("评分系统故障,请尝试重新进入");
                ChivoxCreateUtil.deleteEngineAndRecorder();
                closeActivity((Activity) context);

            }
        });
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
                                WordsMiddle wordsMiddle = new WordsMiddle(thiz, context);
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
}

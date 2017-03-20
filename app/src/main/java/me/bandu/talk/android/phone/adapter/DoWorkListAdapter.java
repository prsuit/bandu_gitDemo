package me.bandu.talk.android.phone.adapter;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.base.AIRecorderDetails;
import com.DFHT.base.BaseBean;
import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.prompt.dialog.MyProgressDialog;
import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.DFHT.voiceengine.EngOkCallBack;
import com.chivox.ChivoxConstants;
import com.chivox.ChivoxGlobalParams;
import com.chivox.bean.AIError;
import com.chivox.utils.ChivoxCreateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
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
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.MediaPathUtils;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.PopWordUtils;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.ReversalView;
import me.bandu.talk.android.phone.voiceengine.VoiceEngineUtils;

/**
 * author by Mckiera
 * time: 2015/12/21  20:54
 * description: 做作业adapter
 * updateTime:
 * update description:
 */
public class DoWorkListAdapter extends DoWorkListBaseAdapter implements BaseActivityIF {

    private MyProgressDialog pd;
    /* item的三种状态 */
    public static final int WORK_NOTDONE = 1;   //未做
    public static final int WORK_DOING = 2;     //正在做
    public static final int WORK_DONE = 3;      //已做
    public static final int WORK_DONE_DOING = 4;//已做完又被聚焦
    private Map<Integer, Integer> timeMap;

    private long hw_quiz_id;

    private List<Detail.DataEntity.ListEntity> list;// 数据源.
    private Context context;
    private String currentType;//0:跟读 1:背诵 2:朗读     //PS:wangleiDB  :   1：跟读！2：朗读！3：背诵
    private static final String REPEAD = "0";//跟读
    private static final String RECITE = "1";//背诵
    private static final String READ = "2";  //朗读
    private ListView lv;   //当前的listview
    private VoiceEngineUtils chivoxUtil;//驰声工具. 控制开始和结束
    // private List<HolderView> holders; //所有holder的集合
    private List<Integer> holdersFlag; //所有holder的集合
    private Map<Integer, Boolean> doneMap;
    // 当前的位置
    private int currPosition = -1;
    //最大的位置
    private int maxPosition;
    //数据库是否需要新页
    private boolean needNewPage;
    private boolean changedScore;

    private boolean showBestWork;

    private long classID;

    private long stu_job_id;

    public DoWorkListAdapter(Detail detail, Context context, String currentType, boolean showBaseWork, ListView lv, long classID, long stu_job_id) {
        timeMap = new HashMap<>();
        this.stu_job_id = stu_job_id;
        Animation3DUtil.getInstance().clearList();
        this.list = detail.getData().getList();
        this.classID = classID;
        hw_quiz_id = detail.getData().getList().get(0).getHw_quiz_id();
        this.showBestWork = showBaseWork;
        this.context = context;
        this.currentType = currentType;
        this.lv = lv;
        doneMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            doneMap.put(i, !TextUtils.isEmpty(list.get(i).getCurrent_mp3()));
        }
        needNewPage = doneMap.containsValue(false);
        changedScore = doneMap.containsValue(false);
        if (needNewPage && (RECITE.equals(currentType) || READ.equals(currentType))) {
            for (int i = 0; i < doneMap.size(); i++) {
                if (!doneMap.get(i)) {
                    currPosition = i;
                    break;
                }
            }
        }
        holdersFlag = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //将所有作业设置成未做状态
            holdersFlag.add(WORK_NOTDONE);
        }
    }

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

    @Override
    protected void changeItemState(int flag, BaseHolderView holder) {
        HolderView holderView = (HolderView) holder;
        switch (flag) {
            case WORK_DOING:
                holderView.rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_doing_bg));
                holderView.llDes.setVisibility(View.VISIBLE);
                holderView.llWorkContent.setVisibility(View.INVISIBLE);

                break;
            case WORK_DONE:
                holderView.rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_done_bg));
                holderView.llDes.setVisibility(View.INVISIBLE);
                holderView.llWorkContent.setVisibility(View.VISIBLE);

                break;
            case WORK_NOTDONE:
                holderView.rlItem.setBackgroundColor(UIUtils.getColor(R.color.white));
                holderView.llDes.setVisibility(View.INVISIBLE);
                holderView.llWorkContent.setVisibility(View.INVISIBLE);
                break;
            case WORK_DONE_DOING:
                holderView.rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_doing_bg));
                holderView.llDes.setVisibility(View.INVISIBLE);
                holderView.llWorkContent.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holder;
        if (convertView != null) {
            holder = (HolderView) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.do_work_list_item, null);
            holder = new HolderView(convertView);
            convertView.setTag(holder);
        }
        // holders.add(position, holder);
        if (showBestWork) {
            holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "", list.get(position).getWords_score()), TextView.BufferType.SPANNABLE);
        } else {
            holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "", list.get(position).getCurrent_words_score()), TextView.BufferType.SPANNABLE);
        }
        getEachWord(holder.tvContent, position);

        holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());


        switch (currentType) {
            case REPEAD:
                holder.ivHorn.setVisibility(View.VISIBLE);
                break;
            case RECITE:
                holder.ivHorn.setVisibility(View.GONE);
                break;
            case READ:
                holder.ivHorn.setVisibility(View.GONE);
                break;
        }

        holder.ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMp3(list.get(position).getMp3(), position, holder.ivHorn);
            }
        });
        int time = -1;
        if (showBestWork) {
            time = list.get(position).getSeconds();
        } else {
            time = list.get(position).getCurrent_stu_seconds();
        }
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < time; i++) {
            if (i >= 5) {
                break;
            }
            sb.append("　 ");
        }
        holder.tvDuration.setText(sb.toString() + time + "\"");

        holder.tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showBestWork)
                    if (!TextUtils.isEmpty(list.get(position).getCurrent_mp3()))
                        playMp3(list.get(position).getCurrent_mp3(), position, holder.ivVoice);
                    else
                        UIUtils.showToastSafe("文件不存在,请重新尝试录音.");
                else if (!TextUtils.isEmpty(list.get(position).getStu_mp3()))
                    playMp3(list.get(position).getStu_mp3(), position, holder.ivVoice);
                else
                    UIUtils.showToastSafe("抱歉,录音已丢失");
            }
        });
        int score = list.get(position).getCurrent_score();
        if (showBestWork) {
            score = list.get(position).getStu_score();
        }
        holder.rlScore.setScore(score);

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPosition = position;
                DoWorkListAdapter.this.notifyDataSetChanged();
            }
        });
        //是true的情况.说明这个题做过.
        if (!showBestWork)
            if (!TextUtils.isEmpty(list.get(position).getCurrent_mp3())) {
                if (list.get(position).getCurrent_type() <= 0) {
                    list.get(position).setCurrent_type(WORK_DONE);
                    setDoneWorkOK(position);
                }
            } else {
                if (list.get(position).getCurrent_type() <= 0)
                    list.get(position).setCurrent_type(WORK_NOTDONE);
            }
        else {
            list.get(position).setCurrent_type(WORK_DONE);
            setDoneWorkOK(position);
        }
        try {
            holdersFlag.set(position, list.get(position).getCurrent_type());
            if (currPosition >= 0 && currPosition == position && holdersFlag.get(position) == WORK_DONE)
                holdersFlag.set(currPosition, WORK_DONE_DOING);
            else if(currPosition >= 0 && currPosition == position){
                holdersFlag.set(currPosition, WORK_DOING);
            }
        } catch (IndexOutOfBoundsException e) {
            holdersFlag.set(position, list.get(position).getCurrent_type());
            if (currPosition >= 0 && currPosition == position)
                holdersFlag.set(currPosition, WORK_DOING);
        }
        changeItemState(holdersFlag.get(position), holder);
        return convertView;
    }

    public static class HolderView extends BaseHolderView{
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
         * 未做描述
         */
        @Bind(R.id.llDes)
        LinearLayout llDes;

        /**
         * 分数(图片)
         */
//        @Bind(R.id.ivScore)
//        ImageView ivScore;
        /**
         * 小喇叭(图片)
         */
        @Bind(R.id.ivVoice)
        ImageView ivVoice;

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

        @Bind(R.id.rlScore)
        ReversalView rlScore;

        View view;

        public HolderView(View view) {
            ButterKnife.bind(this, view);
            this.view = view;
        }

        public HolderView bind(View view) {
            ButterKnife.bind(this, view);
            this.view = view;
            return this;
        }
    }

    private int count = 0;

    public int startRecord(int position, final String content, String sentenceID, Map<String, Object> holderObj) {
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
                if(!showBestWork || list.get(position).getStu_score() < Integer.valueOf(score)) {
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
                    int current_score = list.get(position).getCurrent_score();
                    if(changedScore || scoreInt > current_score) {
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
                }
                //自动下一个.
                if (RECITE.equals(currentType) || READ.equals(currentType)) {
                    int currPosition = getCurrPosition();
                    LogUtils.e("松手后currPosition" + currPosition);
                    if (currPosition != -1) {
                        doNext();
                        lv.smoothScrollToPosition(DoWorkListAdapter.this.currPosition);
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


//                if (ChivoxCreateUtil.offLineCreateOk) {
//                    if (error != null)
//                        UIUtils.showToastSafe("评分失败,错误编号=" + error.getErrId() + "");
//                    else
//                        UIUtils.showToastSafe("评分失败,请重新尝试");
//                    ChivoxCreateUtil.notifyChanged();
//                    if (count++ > 0)
//                        ChivoxCreateUtil.offLineCreateOk = false;
//                } else {

//                }
            }
        });
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

    public void aotuNext(int position) {
        onItemClick(position, "");
    }

    public void onItemClick(final int position, final String mp3Url) {
        currPosition = position;
        //  if(holders.size() < position){
        this.lv.smoothScrollToPosition(position);
        DoWorkListAdapter.this.notifyDataSetChanged();
        //  }
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                holdersFlag.set(position, WORK_DOING);
                DoWorkListAdapter.this.notifyDataSetChanged();
                switch (currentType) {
                    case REPEAD:
                        if (!TextUtils.isEmpty(mp3Url))
                            New_VoiceUtils.getInstance().startVoiceWithCache(context, mp3Url);
                        break;
                }
            }
        }, 1000);

    }

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

    public void cleanStatementResult() {
        if (ChivoxConstants.statementResult != null && ChivoxConstants.statementResult.size() > 0) {
            ChivoxConstants.statementResult.clear();
        }
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


    public void playMp3(String urlOrPath, int id, View view) {
        Log.e("mp3", "playMp3: "+urlOrPath );
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

    private ClickableSpan getClickableSpan(final int position) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(position != currPosition){
                    currPosition = position;
                    DoWorkListAdapter.this.notifyDataSetChanged();
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

                    WordsMiddle youDaoMiddle = new WordsMiddle(DoWorkListAdapter.this, context);
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

    public Integer[] getIndices(String ss, char c) {
        int pos = ss.indexOf(c, 0);
        List<Integer> integers = new ArrayList<>();
        while (pos != -1) {
            integers.add(pos);
            pos = ss.indexOf(c, pos + 1);
        }
        return integers.toArray(new Integer[0]);
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
                                WordsMiddle wordsMiddle = new WordsMiddle(DoWorkListAdapter.this, context);
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

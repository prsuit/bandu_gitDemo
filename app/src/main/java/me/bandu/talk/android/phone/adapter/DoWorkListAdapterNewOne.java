package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.holder.BaseHolderView;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.TextColorUtils;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-02  16:24
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class DoWorkListAdapterNewOne extends DoWorkListBaseAdapter {


    public DoWorkListAdapterNewOne(Detail detail, Context context, String currentType, boolean showBaseWork, ListView lv, long classID, long stu_job_id) {
        super();
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

    @Override
    protected void changeItemState(int flag, BaseHolderView holder) {
        switch (flag) {
            case WORK_DOING:
                ((HolderView) holder).rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_doing_bg));
                ((HolderView) holder).llWorkContent.setVisibility(View.INVISIBLE);

                break;
            case WORK_DONE:
                ((HolderView) holder).rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_done_bg));
                ((HolderView) holder).llWorkContent.setVisibility(View.VISIBLE);

                break;
            case WORK_NOTDONE:
                ((HolderView) holder).rlItem.setBackgroundColor(UIUtils.getColor(R.color.white));
                ((HolderView) holder).llWorkContent.setVisibility(View.INVISIBLE);
                break;
            case WORK_DONE_DOING:
                ((HolderView) holder).rlItem.setBackgroundColor(UIUtils.getColor(R.color.do_work_item_doing_bg));
                ((HolderView) holder).llWorkContent.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holder;
        if (convertView != null) {
            holder = (HolderView) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.list_item_text_reading, null);
            holder = new HolderView(convertView);
            convertView.setTag(holder);
        }
        holder.tvContent.setText(list.get(position).getEn(),TextView.BufferType.SPANNABLE);
       /* if (showBestWork) {
            holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "", list.get(position).getWords_score()), TextView.BufferType.SPANNABLE);
        } else {
            holder.tvContent.setText(TextColorUtils.changTextColor(list.get(position).getEn() + "", list.get(position).getCurrent_words_score()), TextView.BufferType.SPANNABLE);
        }*/
        getEachWord(holder.tvContent, position);

        holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());

        if(TextUtils.isEmpty(list.get(position).getMp3())){
            holder.ivHorn.setVisibility(View.GONE);
        }

        holder.rl_ivHorn.setOnClickListener(new View.OnClickListener() {
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
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPosition = position;
                notifyDataSetChanged();
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
            else if (currPosition >= 0 && currPosition == position) {
                holdersFlag.set(currPosition, WORK_DOING);
            }
        } catch (IndexOutOfBoundsException e) {
            holdersFlag.set(position, list.get(position).getCurrent_type());
            if (currPosition >= 0 && currPosition == position)
                holdersFlag.set(currPosition, WORK_DOING);
        }
        changeItemState(holdersFlag.get(position), holder);
        holder.tvPosition.setText("  " + (position + 1) + "/" + list.size() + "  ");
        return convertView;
    }


    public static class HolderView extends BaseHolderView {

        @Bind(R.id.ivHorn)
        ImageView ivHorn;

        @Bind(R.id.rl_ivHorn)
        RelativeLayout rl_ivHorn;

        @Bind(R.id.ivVoice)
        ImageView ivVoice;

        @Bind(R.id.tvPosition)
        TextView tvPosition;

        @Bind(R.id.tvContent)
        TextView tvContent;

        @Bind(R.id.llWorkContent)
        LinearLayout llWorkContent;

        @Bind(R.id.rlItem)
        LinearLayout rlItem;

        @Bind(R.id.tvDuration)
        TextView tvDuration;

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
}

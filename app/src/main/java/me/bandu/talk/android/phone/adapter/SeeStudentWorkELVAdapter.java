package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.DetailOfStudentBean;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;
import me.bandu.talk.android.phone.utils.TextColorUtils;
import me.bandu.talk.android.phone.viewholder.SeeWorkChildHolderView;
import me.bandu.talk.android.phone.viewholder.SeeWorkFatherHolderView;

/**
 * author by Mckiera
 * time: 2015/12/29  14:24
 * description:
 * updateTime:
 * update description:
 */
public class SeeStudentWorkELVAdapter extends BaseExpandableListAdapter {

    private Context context;
    private DetailOfStudentBean bean;
    private List<DetailOfStudentBean.DataEntity.ListEntity> fatherList;
    private LayoutInflater mInflater;

    public SeeStudentWorkELVAdapter(Context context, DetailOfStudentBean bean) {
        this.context = context;
        this.bean = bean;
        fatherList = bean.getData().getList();
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return fatherList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return fatherList.get(groupPosition).getSentences().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return fatherList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return fatherList.get(groupPosition).getSentences().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    boolean isExamination;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SeeWorkFatherHolderView fatherHolderView;
        if (convertView == null) {
            convertView = mInflater.from(context).inflate(R.layout.see_student_work_father_item, parent, false);
            fatherHolderView = new SeeWorkFatherHolderView(convertView);
            convertView.setTag(fatherHolderView);
        } else {
            fatherHolderView = (SeeWorkFatherHolderView) convertView.getTag();
        }
        String quiz_name = fatherList.get(groupPosition).getQuiz_name();
//        if (!isExamination && quiz_name.contains("情景问答") || quiz_name.contains("视频配音") || quiz_name.contains("选词填空") || quiz_name.contains("人机问答") || quiz_name.contains("选读答案") || quiz_name.contains("英汉转换") || quiz_name.contains("角色朗读") || quiz_name.contains("朗读文本")|| quiz_name.contains("朗读文本")) {
//            isExamination = true;
//        }
        fatherHolderView.tvQuizName.setText(quiz_name);
        List<DetailOfStudentBean.DataEntity.ListEntity.TypesEntity> types = fatherList.get(groupPosition).getTypes();
        String typeStr = "";
        for (DetailOfStudentBean.DataEntity.ListEntity.TypesEntity type : types) {
            switch (type.getType()) {
                case "repeat":
                    typeStr += "跟读" + type.getTimes() + "遍 ";
                    break;
                case "Reading":
                    typeStr += "朗读" + type.getTimes() + "遍 ";
                    break;
                case "recite":
                    typeStr += "背诵" + type.getTimes() + "遍 ";
                    break;
            }
        }
        fatherHolderView.tvCount.setText(typeStr);

        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SeeWorkChildHolderView holderView;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.see_work_child_item, null);
            holderView = new SeeWorkChildHolderView(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (SeeWorkChildHolderView) convertView.getTag();
        }
        final DetailOfStudentBean.DataEntity.ListEntity.SentencesEntity child = fatherList.get(groupPosition).getSentences().get(childPosition);
        CharSequence content;
        if (fatherList.get(groupPosition).getType_code() == 2){
            // 表示是新题型  ，文本不变色
            content = child.getEn().replaceAll("#", "\n");
        } else {
            content = TextColorUtils.changTextColor(child.getEn().replaceAll("#", "\n") + "", child.getWords_score());
        }
        holderView.tvContent.setText(content, TextView.BufferType.SPANNABLE);
        // 判断是否显示小喇叭
        if (TextUtils.isEmpty(child.getMp3())) {
            holderView.ivHorn.setVisibility(View.GONE);
        } else {
            holderView.ivHorn.setVisibility(View.VISIBLE);
        }

        holderView.ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_VoiceUtils.getInstance().startVoiceNet(child.getMp3());
            }
        });
        //Todo 音频时长.
        int lenght = child.getStu_seconds();
        LogUtils.i("音频时长" + lenght);
        if (!TextUtils.isEmpty(child.getStu_mp3())) {
            holderView.llWorkContent.setVisibility(View.VISIBLE);
        } else {
            holderView.llWorkContent.setVisibility(View.INVISIBLE);
        }
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < lenght; i++) {
            if (i >= 8) {
                break;
            }
            sb.append("　");
        }
        LogUtils.i("sb的长度是:" + sb.length());
        holderView.tvDuration.setText(sb.toString() + child.getStu_seconds() + "\"");
        holderView.tvScore.setText(child.getStu_score() + "");
        holderView.tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(child.getStu_mp3())) {
                    UIUtils.showToastSafe("缺少学生录音");
                } else {
                    New_VoiceUtils.getInstance().startVoiceNet(child.getStu_mp3());
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ExpandableNewWord;
import me.bandu.talk.android.phone.utils.New_VoiceUtils;

/**
 * author by Mckiera
 * time: 2016/2/19  15:06
 * description:
 * updateTime:
 * update description:
 */
public class NewWordExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ExpandableNewWord newWord;
    private EditType type;
    // public Map<Integer,ViewGroup> llChildMap;
    public Map<Integer, Boolean> deleteMap;

    private  ExpandableListView elv;

    public Map<Integer, Boolean> getDeleteMap() {
        return deleteMap;
    }

    public EditType getType() {
        return type;
    }

    public void setType(EditType type) {
        this.type = type;
    }

    public NewWordExpandableListViewAdapter(Context context, ExpandableNewWord newWord,  ExpandableListView elv) {
        mContext = context;
        this.newWord = newWord;
        type = EditType.EDITABLE;
        deleteMap = new HashMap<>();
        for(int i = 0; i< newWord.getData().getList().size(); i++){
            deleteMap.put(i, false);
        }
        this.elv = elv;
    }

    public enum EditType {
        EDITABLE, UNEDITABLE
    }

    @Override
    public int getGroupCount() {
        return newWord.getData().getList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return newWord.getData().getList().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<String> explains = newWord.getData().getList().get(groupPosition).getContent().getBasic().getExplains();
        if (explains == null)
            return newWord.getData().getList().get(groupPosition).getContent().getTranslation().get(0);
        return explains.get(childPosition);
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

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = View.inflate(mContext, R.layout.expandlistview_group_item, null);
            holder.cbClick = (CheckBox) convertView.findViewById(R.id.cbClick);
            holder.tvWordGroup = (TextView) convertView.findViewById(R.id.tvWordGroup);
            holder.llFather = (LinearLayout) convertView.findViewById(R.id.llFather);
            holder.v_line = convertView.findViewById(R.id.v_line);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.cbClick.setClickable(false);
        holder.v_line.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
       // deleteMap.put(groupPosition, false);
        switch (type) {
            case UNEDITABLE:
                holder.cbClick.setVisibility(View.VISIBLE);
                holder.llFather.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // boolean checked = holder.cbClick.isChecked();
                        boolean checked = newWord.getData().getList().get(groupPosition).isClick();

                        LogUtils.i("当前状态:"+checked);
                        holder.cbClick.setChecked(!checked);
                        newWord.getData().getList().get(groupPosition).setClick(!checked);
                        boolean fan = holder.cbClick.isChecked();
                        LogUtils.i("当前状态:"+fan);
                        deleteMap.put(groupPosition, fan);
                        notifyDataSetChanged();
                    }
                });
                break;
            case EDITABLE:
                holder.cbClick.setVisibility(View.GONE);
                holder.llFather.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = elv.isGroupExpanded(groupPosition);
                        if(b){
                            elv.collapseGroup(groupPosition);
                        }else
                            elv.expandGroup(groupPosition);
                    }
                });
                break;
        }
        holder.cbClick.setChecked(newWord.getData().getList().get(groupPosition).isClick());
        holder.tvWordGroup.setText(newWord.getData().getList().get(groupPosition).getWord());
        if(isExpanded){
            holder.iv.setImageResource(R.mipmap.expandablelistviewindicatordown);
        }else{
            holder.iv.setImageResource(R.mipmap.expandablelistviewindicator);
        }
        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.expandlistview_child_item, null);
            holder = new ChildHolder();
            holder.tvChildList = (TextView) convertView.findViewById(R.id.tvChildList);
            holder.llYb = (LinearLayout) convertView.findViewById(R.id.llYb);
            holder.ivHorn = (ImageView) convertView.findViewById(R.id.ivHorn);
            holder.tvYb = (TextView) convertView.findViewById(R.id.tvYb);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        List<String> childStr;
        final ExpandableNewWord.DataEntity.ListEntity.ContentEntity.BasicEntity basic = newWord.getData().getList().get(groupPosition).getContent().getBasic();

        if (basic == null) {
            childStr = newWord.getData().getList().get(groupPosition).getContent().getTranslation();
            holder.llYb.setVisibility(View.GONE);
        } else {
            childStr = basic.getExplains();
            holder.llYb.setVisibility(View.VISIBLE);
            holder.tvYb.setText("美[" + basic.getPhonetic() + "]");
            holder.ivHorn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    New_VoiceUtils.getInstance().startVoiceNet("http://dict.youdao.com/dictvoice?audio=" + newWord.getData().getList().get(groupPosition).getContent().getQuery());
                }
            });
        }
        String content = "";
        for (String str : childStr) {
            content += str + "\r\n";
        }
        holder.tvChildList.setText(content);
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class GroupHolder {
        public CheckBox cbClick;
        public TextView tvWordGroup;
        public LinearLayout llFather;
        public View v_line;
        public ImageView iv;
    }

    public static class ChildHolder {
        public TextView tvChildList;
        public LinearLayout llYb;
        public ImageView ivHorn;
        public TextView tvYb;
    }
}

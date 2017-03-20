package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ExerciseTextbookTypeBean;
import me.bandu.talk.android.phone.utils.ColorUtil;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * author by gaoye
 * time: 2016/2/19  15:06
 * description:
 * updateTime:
 * update description:
 */
public class StudentTextbookChoseTypeExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExerciseTextbookTypeBean.DataEntity.ListEntity> types;
    private List<Integer> cats,subs;
    public static final int CAT = 0;
    public static final int SUB = 1;
    private View.OnClickListener onClickListener;
    private Map<Integer,List<Integer>> subMap;
    private Map<Integer,Integer> catMap;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public StudentTextbookChoseTypeExpandableAdapter(Context context, List<ExerciseTextbookTypeBean.DataEntity.ListEntity> types,
                                                     CompoundButton.OnCheckedChangeListener onCheckedChangeListener,
                                                     List<Integer> cats, List<Integer> subs, View.OnClickListener onClickListener) {
        this.context = context;
        this.types = types;
        this.onCheckedChangeListener = onCheckedChangeListener;
        this.cats = cats;
        this.subs = subs;
        this.onClickListener = onClickListener;
        this.subMap = new HashMap<>();
        this.catMap = new HashMap<>();
        for (int i = 0;i<types.size();i++){
            List<ExerciseTextbookTypeBean.DataEntity.ListEntity.ListEntity2> entity2s = types.get(i).getList();
            List<Integer> integers = new ArrayList<>();
            for (int j = 0;j<entity2s.size();j++){
                integers.add(StringUtil.getIntegerNotnull(entity2s.get(j).getSub_cat_id()));
            }
            subMap.put(i,integers);
            catMap.put(i,StringUtil.getIntegerNotnull(types.get(i).getCat_id()));
        }
    }

    @Override
    public int getGroupCount() {
        return types.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<ExerciseTextbookTypeBean.DataEntity.ListEntity.ListEntity2> list = types.get(groupPosition).getList();
        int count = (list.size() + 1 % 2 == 0 ? list.size() / 2 : list.size() / 2 + 1) - 1;
        if (count < 0)
            count = 0;
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return types.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return types.get(groupPosition).getList();
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

    public static class GroupHolder {
        private TextView tv_text;
        private ImageView iv_icon,iv_group_arrows;
        private TextView cb_left,cb_right;
        private RelativeLayout ll_expand;
    }
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final ExerciseTextbookTypeBean.DataEntity.ListEntity entity = types.get(groupPosition);
        final List<ExerciseTextbookTypeBean.DataEntity.ListEntity.ListEntity2> list = entity.getList();
        final GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = View.inflate(context, R.layout.layout_textbook_type_group_item, null);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_group_icon);
            holder.tv_text = (TextView) convertView.findViewById(R.id.tv_group_text);
            holder.cb_left = (TextView) convertView.findViewById(R.id.cb_left);
            holder.cb_right = (TextView) convertView.findViewById(R.id.cb_right);
            holder.ll_expand = (RelativeLayout) convertView.findViewById(R.id.ll_expand);
            holder.iv_group_arrows = (ImageView) convertView.findViewById(R.id.iv_group_arrows);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(16,context) * 4) / 2,ScreenUtil.dp2px(30,context));
            params.setMargins(0,0,0,0);
            holder.cb_left.setLayoutParams(params);
            holder.cb_left.setTag(false);
            holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            params.setMargins(ScreenUtil.dp2px(16,context),0,0,0);
            holder.cb_right.setLayoutParams(params);
            holder.cb_right.setTag(false);
            holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));

            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        if (isExpanded){
            holder.iv_group_arrows.setImageResource(R.mipmap.expandablelistviewindicatordown);
        }else {
            holder.iv_group_arrows.setImageResource(R.mipmap.expandablelistviewindicator);
        }
        holder.tv_text.setText(StringUtil.getShowText(entity.getCat_name()));

        if (entity.getCat_id() == 1){
            holder.iv_icon.setImageResource(R.mipmap.textbook_icon2);
        }else if (entity.getCat_id() == 2){
            holder.iv_icon.setImageResource(R.mipmap.textbook_icon3);
        }else if (entity.getCat_id() == 3){
            holder.iv_icon.setImageResource(R.mipmap.textbook_icon1);
        }

        holder.ll_expand.setTag(groupPosition);
        holder.ll_expand.setOnClickListener(onClickListener);

        final int[] catorsub_left = new int[2];
        catorsub_left[0] = CAT;
        catorsub_left[1] = entity.getCat_id();
        holder.cb_left.setText("全部");


        holder.cb_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Boolean)holder.cb_left.getTag() == true){
                    if (cats.contains(catorsub_left[1])){
                        cats.remove(new Integer(catorsub_left[1]));
                    }
                }else {
                    if (!cats.contains(catorsub_left[1])){
                        cats.add(catorsub_left[1]);
                    }

                    List<Integer> subList = subMap.get(groupPosition);
                    if (subList != null){
                        List<Integer> delList = new ArrayList<>();
                        for (int i = 0;i<subList.size();i++){
                            for (int j = 0;j<subs.size();j++){
                                if (subList.get(i) == subs.get(j)){
                                    delList.add(subList.get(i));
                                }
                            }
                        }

                        if (subs.containsAll(delList))
                            subs.removeAll(delList);
                    }
                }

                notifyDataSetChanged();
            }
        });

        if (cats.contains(types.get(groupPosition).getCat_id())){
            holder.cb_left.setBackgroundResource(R.drawable.textbook_type_selected);
            holder.cb_left.setTag(true);
            holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.textbook_selected));
        }else {
            holder.cb_left.setBackgroundResource(R.drawable.textbook_type_noselect);
            holder.cb_left.setTag(false);
            holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
        }

       /* if (!catMap.containsKey(groupPosition)){
            catMap.put(groupPosition,catorsub_left[1]);
        }*/

        if (list.size() > 0){
            final int[] catorsub_right = new int[2];
            catorsub_right[0] = SUB;
            catorsub_right[1] = list.get(0).getSub_cat_id();
            holder.cb_right.setText(StringUtil.getShowText(list.get(0).getSub_cat_name()));
            holder.cb_right.setVisibility(View.VISIBLE);
            holder.cb_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Boolean) holder.cb_right.getTag() == true){
                        if (subs.contains(catorsub_right[1])){
                            subs.remove(new Integer(catorsub_right[1]));
                        }
                    }else {
                        if (!subs.contains(catorsub_right[1])){
                            subs.add(catorsub_right[1]);
                        }

                        Integer catValue = catMap.get(groupPosition);
                        if (cats.contains(catValue)){
                            cats.remove(catValue);
                        }
                    }



                    notifyDataSetChanged();
                }
            });

            if (subs.contains(list.get(0).getSub_cat_id())){
                holder.cb_right.setBackgroundResource(R.drawable.textbook_type_selected);
                holder.cb_right.setTag(true);
                holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.textbook_selected));
            }else {
                holder.cb_right.setBackgroundResource(R.drawable.textbook_type_noselect);
                holder.cb_right.setTag(false);
                holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            }

            /*if (subMap.containsKey(groupPosition)){
                List values = subMap.get(groupPosition);
                if (!values.contains(catorsub_right[1])){
                    values.add(catorsub_right[1]);
                }
            }else {
                List<Integer> values = new ArrayList<>();
                values.add(catorsub_right[1]);
                subMap.put(groupPosition,values);
            }*/
        }else {
            holder.cb_right.setVisibility(View.GONE);
        }


        return convertView;
    }


    public static class ChildHolder {
        /*public LinearLayout ll_main;
        public LinearLayout.LayoutParams params;
        public LinearLayout.LayoutParams rowParams;*/
        private TextView cb_left,cb_right;
    }
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final List<ExerciseTextbookTypeBean.DataEntity.ListEntity.ListEntity2> entitys = types.get(groupPosition).getList();
        final ChildHolder holder;
        if (convertView == null) {
            //convertView = new LinearLayout(context);
            holder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_textbook_type_child_item,null);
            holder.cb_left = (TextView) convertView.findViewById(R.id.cb_left);
            holder.cb_right = (TextView) convertView.findViewById(R.id.cb_right);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(16,context) * 4) / 2,ScreenUtil.dp2px(30,context));
            params.setMargins(0,0,0,0);
            holder.cb_left.setLayoutParams(params);
            holder.cb_left.setTag(false);
            holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            params.setMargins(ScreenUtil.dp2px(16,context),0,0,0);
            holder.cb_right.setLayoutParams(params);
            holder.cb_right.setTag(false);
            holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            /* AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.ll_main = (LinearLayout) convertView;
            holder.ll_main.setLayoutParams(params);
            holder.ll_main.setOrientation(LinearLayout.VERTICAL);

            holder.params = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(16,context) * 3) / 2,ScreenUtil.dp2px(30,context));
            holder.params.setMargins(ScreenUtil.dp2px(8,context),0,0,0);
            holder.rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtil.dp2px(40,context));*/
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }


        final int left = childPosition * 2 + 2 - 1;
        final int right = left + 1;
        final int[] catorsub_left = new int[2];
        catorsub_left[0] = SUB;
        if (left >= 0 && left < entitys.size()){
            catorsub_left[1] = entitys.get(left).getSub_cat_id();
            holder.cb_left.setText(StringUtil.getShowText(entitys.get(left).getSub_cat_name()));

            holder.cb_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Boolean)holder.cb_left.getTag() == true){
                        if (subs.contains(catorsub_left[1])){
                            subs.remove(new Integer(catorsub_left[1]));
                        }
                    }else {
                        if (!subs.contains(catorsub_left[1])){
                            subs.add(catorsub_left[1]);
                        }

                        Integer catValue = catMap.get(groupPosition);
                        if (cats.contains(catValue)){
                            cats.remove(catValue);
                        }
                    }



                    notifyDataSetChanged();
                }
            });
            if (subs.contains(entitys.get(left).getSub_cat_id())){
                holder.cb_left.setBackgroundResource(R.drawable.textbook_type_selected);
                holder.cb_left.setTag(true);
                holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.textbook_selected));
            }else {
                holder.cb_left.setBackgroundResource(R.drawable.textbook_type_noselect);
                holder.cb_left.setTag(false);
                holder.cb_left.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            }
        }else {
            //holder.cb_left.setVisibility(View.GONE);
        }


        /*if (subMap.containsKey(groupPosition)){
            List<Integer> values = subMap.get(groupPosition);
            if (!values.contains(catorsub_left[1])){
                values.add(catorsub_left[1]);
            }
        }else {
            List<Integer> values = new ArrayList<>();
            values.add(catorsub_left[1]);
            subMap.put(groupPosition,values);
        }*/


        if (right >= 0 && right < entitys.size()){
            final int[] catorsub_right = new int[2];
            catorsub_right[0] = SUB;
            catorsub_right[1] = entitys.get(right).getSub_cat_id();
            holder.cb_right.setText(StringUtil.getShowText(entitys.get(right).getSub_cat_name()));
            holder.cb_right.setVisibility(View.VISIBLE);

            holder.cb_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Boolean)holder.cb_right.getTag() == true){
                        if (subs.contains(catorsub_right[1])){
                            subs.remove(new Integer(catorsub_right[1]));
                        }
                    }else {
                        if (!subs.contains(catorsub_right[1])){
                            subs.add(catorsub_right[1]);
                        }

                        Integer catValue = catMap.get(groupPosition);
                        if (cats.contains(catValue)){
                            cats.remove(catValue);
                        }
                    }

                    notifyDataSetChanged();
                }
            });

            if (subs.contains(entitys.get(right).getSub_cat_id())){
                holder.cb_right.setBackgroundResource(R.drawable.textbook_type_selected);
                holder.cb_right.setTag(true);
                holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.textbook_selected));
            }else {
                holder.cb_right.setBackgroundResource(R.drawable.textbook_type_noselect);
                holder.cb_right.setTag(false);
                holder.cb_right.setTextColor(ColorUtil.getResourceColor(context,R.color.text_dark_gray));
            }

            /*if (subMap.containsKey(groupPosition)){
                List<Integer> values = subMap.get(groupPosition);
                if (!values.contains(catorsub_right[1])){
                    values.add(catorsub_right[1]);
                }
            }else {
                List<Integer> values = new ArrayList<>();
                values.add(catorsub_right[1]);
                subMap.put(groupPosition,values);
            }*/
        }else {
            holder.cb_right.setVisibility(View.GONE);
        }



        /*holder.ll_main.removeAllViews();
        LinearLayout ll_row = null;
        for (int i = 0;i<=entitys.size();i++){
            if (i % 2 == 0){
                ll_row = new LinearLayout(context);
                ll_row.setGravity(Gravity.CENTER_VERTICAL);
                ll_row.setLayoutParams(holder.rowParams);
                holder.ll_main.addView(ll_row);
            }
            CheckBox checkBox = (CheckBox) LayoutInflater.from(context).inflate(R.layout.layout_checkbox_student_textbooktype,null);
            checkBox.setSingleLine();

            int[] catorsub = new int[2];
            if (i == 0){
                catorsub[0] = CAT;
                catorsub[1] = types.get(groupPosition).getCat_id();
                //checkBox.setText(StringUtil.getShowText(types.get(0).getCat_name()));
                checkBox.setText("全部");
                if (cats.contains(types.get(groupPosition).getCat_id())){
                    checkBox.setChecked(true);
                }
            }else {
                catorsub[0] = SUB;
                catorsub[1] = entitys.get(i - 1).getSub_cat_id();
                checkBox.setText(StringUtil.getShowText(entitys.get(i - 1).getSub_cat_name()));
                if (subs.contains(entitys.get(i - 1).getSub_cat_id())){
                    checkBox.setChecked(true);
                }
            }
            checkBox.setTag(catorsub);
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
            checkBox.setLayoutParams(holder.params);
            ll_row.addView(checkBox);
        }*/
        return convertView;
    }




    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }




}

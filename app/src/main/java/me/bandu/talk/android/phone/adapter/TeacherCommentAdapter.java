package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.dao.Comment;
import me.bandu.talk.android.phone.dao.DaoUtils;

/**
 * 创建者：高楠
 * 时间：on 2016/1/28
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherCommentAdapter extends BaseAdapter {

    private final Context context;
    private List<Comment> myself;
    private List<String> myself_names = new ArrayList<>();
    private List<Boolean> myself_flag = new ArrayList<>();
    private List<String> good = new ArrayList<>();
    private List<String> good_comment = new ArrayList<>();
    private List<Boolean> good_flags = new ArrayList<>();
    private List<String> bad =new ArrayList<>();
    private List<String> bad_comment = new ArrayList<>();
    private List<Boolean> bad_flags = new ArrayList<>();
    private List<String> selections = new ArrayList<>();
    private int data_type = 0;//0:good 1:bad 2:self
    private List<String> comments;
    private int MAX_TEXT = 50;
    public void setDefaultData(){
        good.add("轻重音");
        good.add("发音");
        good.add("音量");
        good.add("语调");
        good.add("语速");
        good.add("完整性");
        good.add("熟练");
        good.add("感情色彩");
        good_comment.add("轻重音把握不错");
        good_comment.add("发音准确");
        good_comment.add("声音洪亮");
        good_comment.add("语调把握不错");
        good_comment.add("语速适宜");
        good_comment.add("作业完整");
        good_comment.add("课文很熟练");
        good_comment.add("感情色彩丰富");
        bad.add("轻重音");
        bad.add("发音");
        bad.add("音量");
        bad.add("语调");
        bad.add("语速");
        bad.add("完整性");
        bad.add("熟练");
        bad_comment.add("轻重音有待调整");
        bad_comment.add("注意发音");
        bad_comment.add("声音小");
        bad_comment.add("语调有待调整");
        bad_comment.add("语速有待调整");
        bad_comment.add("作业未做完");
        bad_comment.add("课文不熟练");
    }
    public TeacherCommentAdapter(Context context){
        this.context = context;
        setDefaultData();
        for (int i = 0 ; i < good.size();i++){
            good_flags.add(false);
        }
        for (int i = 0 ;i <bad.size();i++){
            bad_flags.add(false);
        }
        getDataFromDb();
        setGoodComment();
    }

    public void getDataFromDb() {
        myself = DaoUtils.getInstance().getCommentList();
        if (myself!=null){
            for (int i = 0;i<myself.size();i++){
                myself_names.add(myself.get(i).getName());
                myself_flag.add(false);
            }
        }
    }

    @Override
    public int getCount() {
        return comments==null?0:comments.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_teachercomment,null);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (CommentViewHolder) convertView.getTag();
        }
        holder.textView.setText(comments.get(position));
        boolean flag = false;
        if (data_type==0){
            flag = good_flags.get(position);
        }else if (data_type==1){
            flag = bad_flags.get(position);
        }else if (data_type==2){
            flag = myself_flag.get(position);
        }
        if (flag){
            holder.textView.setBackgroundResource(R.drawable.grid_item_click);
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.textView.setTextColor(context.getResources().getColor(R.color.btn_blue));
            holder.textView.setBackgroundResource(R.drawable.grid_item_normal);
        }
        return convertView;
    }
    public class CommentViewHolder{
        TextView textView;
        public CommentViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.tv_comment);
        }
    }
    public void setItemSelect(int position,int l,boolean isCustom){
        if (isCustom){
            l=0;
        }else {
            if (l>0){
                l++;
            }
        }
        if (data_type==0){
            boolean flag = good_flags.get(position);
            if (flag){
                good_flags.set(position,false);
                selections.remove(good_comment.get(position));
            }else {
                int length = good_comment.get(position).length();
                if (l+length<=MAX_TEXT){
                    good_flags.set(position,true);
                    selections.add(good_comment.get(position));
                }else {

                    UIUtils.showToastSafe("评语不能超过50个字！");
                }
            }
        }else if (data_type==1){
            boolean flag = bad_flags.get(position);
            if (flag){
                bad_flags.set(position,false);
                selections.remove(bad_comment.get(position));
            }else {
                int length = bad_comment.get(position).length();
                if (l+length<=MAX_TEXT){
                    bad_flags.set(position,true);
                    selections.add(bad_comment.get(position));
                }else {
                    UIUtils.showToastSafe("评语不能超过50个字！");
                }
            }
        }else if (data_type==2){
            boolean flag = myself_flag.get(position);
            if (flag){
                myself_flag.set(position,false);
                selections.remove(myself.get(position).getComment());
            }else {
                int length = myself.get(position).getComment().length();
                if (l+length<=MAX_TEXT){
                    myself_flag.set(position,true);
                    selections.add(myself.get(position).getComment());
                }else {
                    UIUtils.showToastSafe("评语不能超过50个字！");
                }
            }
        }
        notifyDataSetChanged();
    }
    public void setGoodComment(){
        this.comments = good;
        data_type=0;
        notifyDataSetChanged();
    }
    public void setBadComment(){
        this.comments = bad;
        data_type = 1;
        notifyDataSetChanged();
    }
    public void setMyselfComment(){
        this.comments = myself_names;
        data_type = 2;
        notifyDataSetChanged();
    }
    public String getSelections(){
        StringBuffer str = new StringBuffer();
        int position = 0;
        for (int i = 0 ; i <selections.size();i++){
            if (position==0){
                str.append(selections.get(i));
            }else {
                str.append("、"+selections.get(i));
            }
            position++;
        }
        return str.toString();
    }
//    public String getComent(){
//        StringBuffer str = new StringBuffer();
//        int position = 0;
//        for (int i = 0 ; i < good_flags.size();i++){
//            if (good_flags.get(i)){
//                if (position==0){
//                    str.append(good_comment.get(i));
//                }else {
//                    str.append("、"+good_comment.get(i));
//                }
//                position++;
//            }
//        }
//        for (int i = 0 ; i < bad_flags.size();i++){
//            if (bad_flags.get(i)){
//                if (position==0){
//                    str.append(bad_comment.get(i));
//                }else {
//                    str.append("、"+bad_comment.get(i));
//                }
//                position++;
//            }
//        }
//        for (int i = 0 ; i < myself_flag.size();i++){
//            if (myself_flag.get(i)){
//                if (position==0){
//                    str.append(myself.get(position).getComment());
//                }else {
//                    str.append("、"+myself.get(position).getComment());
//                }
//                position++;
//            }
//        }
//        return str.toString();
//    }
    public List<String> getGoods(){
        List<String> goods = new ArrayList<>();
        for (int i = 0 ; i < good_flags.size();i++){
            if (good_flags.get(i)){
                goods.add(good.get(i));
            }
        }
        return goods;
    }
    public List<String> getBads(){
        List<String> bads = new ArrayList<>();
        for (int i = 0 ; i < bad_flags.size();i++){
            if (bad_flags.get(i)){
                bads.add(bad.get(i));
            }
        }
        return bads;
    }
    public void selectNone(){
        for (int i = 0 ; i < good_flags.size();i++){
            good_flags.set(i,false);
        }
        for (int i = 0 ; i < bad_flags.size();i++){
            bad_flags.set(i,false);
        }
        for (int i = 0 ; i < myself_flag.size();i++){
            myself_flag.set(i,false);
        }
        selections.removeAll(selections);
        notifyDataSetChanged();
    }
    public void destroy(){
        myself.clear();
        myself_names.clear();
        myself_flag.clear();
        good.clear();
        good_comment.clear();
        good_flags.clear();
        bad.clear();
        bad_comment.clear();
        bad_flags.clear();
        selections.clear();
    }
}

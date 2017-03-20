package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ChoseStudentBean;
import me.bandu.talk.android.phone.bean.ChoseStudentListBean;
import me.bandu.talk.android.phone.bean.ChoseStudentListEntity;
import me.bandu.talk.android.phone.view.CircleImageView;

/**
 * 创建者：高楠
 * 时间：on 2016/1/27
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentAdapter extends BaseAdapter {
    private List<ChoseStudentListEntity> data;
    private List<ChoseStudentListEntity> data_a;
    private List<ChoseStudentListEntity> data_b;
    private List<ChoseStudentListEntity> data_c;
    private int count_a;
    private int count_b;
    private int count_c;
    private Context context;

    public ChoseStudentAdapter(Context context, ChoseStudentListBean data){
        this.context = context;
        data_a = data.getData().getA();
        data_b = data.getData().getB();
        data_c = data.getData().getC();
        if (data_a!=null&&data_a.size()!=0)
            count_a = data_a.size();
        if (data_b!=null&&data_b.size()!=0)
            count_b = data_b.size();
        if (data_c!=null&&data_c.size()!=0)
            count_c = data_c.size();
        if (count_a!=0){
            ChoseStudentListEntity b1 = new ChoseStudentListEntity();
            b1.setName("A(优秀)");
            b1.setLetter("A(优秀)");
            b1.setGroup(true);
            if (count_a==getCountA())
                b1.setSelect(true);
            data_a.add(0,b1);
        }
        if (count_b!=0){
            ChoseStudentListEntity b2 = new ChoseStudentListEntity();
            b2.setName("B(良好)");
            b2.setLetter("B(良好)");
            b2.setGroup(true);
            if (count_b==getCountB())
                b2.setSelect(true);
            data_b.add(0,b2);
        }
        if (count_c!=0){
            ChoseStudentListEntity b3 = new ChoseStudentListEntity();
            b3.setName("C(一般)");
            b3.setLetter("C(一般)");
            b3.setGroup(true);
            if (count_c==getCountC())
                b3.setSelect(true);
            data_c.add(0,b3);
        }
        this.data = new ArrayList<>();
        this.data.addAll(data_a);
        this.data.addAll(data_b);
        this.data.addAll(data_c);
    }


    @Override
    public int getCount() {
        return data==null?0:data.size();
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
        ViewHolderChild holder ;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_studentlist,null);
            holder = new ViewHolderChild(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        ChoseStudentListEntity bean = data.get(position);
        holder.checkBox.setChecked(bean.isSelect());
        holder.tv_name.setText(bean.getName());
        if (bean.isGroup()){
            holder.image.setVisibility(View.GONE);
            holder.indicator.setVisibility(View.VISIBLE);
            holder.line_long.setVisibility(View.VISIBLE);
        }else {
            holder.image.setVisibility(View.VISIBLE);
            holder.indicator.setVisibility(View.GONE);
            holder.line_long.setVisibility(View.GONE);
            if (bean.getAvatar()!=null&&!bean.getAvatar().equals("")){
                ImageLoader.getInstance().displayImage(bean.getAvatar(),holder.image);
            }else {
                holder.image.setImageResource(R.mipmap.default_avatar);//默认图片
            }
        }
        return convertView;
    }
    public class ViewHolderChild{
        public CheckBox checkBox;
        public TextView tv_name;
        public CircleImageView image;
        public View indicator,line_long;
        public ViewHolderChild(View view){
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            tv_name = (TextView) view.findViewById(R.id.name);
            image = (CircleImageView) view.findViewById(R.id.image);
            indicator = view.findViewById(R.id.indicator);
            line_long = view.findViewById(R.id.line_long);
        }
    }
    public void setItemSelected(int position){
        if (data!=null&&data.size()>position){
            ChoseStudentListEntity bean = data.get(position);
            boolean flag = bean.isSelect()? false:true;
            bean.setSelect(flag);
            if (bean.isGroup()){
                if (bean.getName().contains("A")){
                    selectLetters(flag,"A");
                }
                if (bean.getName().contains("B")){
                    selectLetters(flag,"B");
                }
                if (bean.getName().contains("C")){
                    selectLetters(flag,"C");
                }
            }else {
                if (bean.getLetter().equals("A")){
                    int count = getCountA();
                    if (count_a>0&&count_a==count){
                        selectGroup("A",true);
                    }else {
                        selectGroup("A",false);
                    }
                }
                if (bean.getLetter().equals("B")){
                    int count = getCountB();
                    if (count_b>0&&count_b==count){
                        selectGroup("B",true);
                    }else {
                        selectGroup("B",false);
                    }
                }
                if (bean.getLetter().equals("C")){
                    int count = getCountC();
                   if (count_c>0&&count_c==count){
                       selectGroup("C",true);
                    }else{
                       selectGroup("C",false);
                   }
                }
            }
            notifyDataSetChanged();
        }
    }
    public void selectAllItem(){
        if (data!=null){
            for (int i = 0 ;i<data.size();i++){
                data.get(i).setSelect(true);
            }
            notifyDataSetChanged();
        }
    }
    public void selectNoneItem(){
        if (data!=null){
            for (int i = 0 ;i<data.size();i++){
                data.get(i).setSelect(false);
            }
            notifyDataSetChanged();
        }
    }
    public void selectGroup(String letter,boolean flag ){
        if (data!=null&&data.size()!=0){
            for (int i = 0 ; i < data.size();i++){
                ChoseStudentListEntity entity = data.get(i);
                if (entity.isGroup()&&entity.getLetter().contains(letter)){
                    entity.setSelect(flag);
                    break;
                }
            }
        }
    }
    public void selectLetters(boolean flag,String letters){
        if (data!=null){
            for (int i = 0 ; i<data.size();i++){
                if (data.get(i).getLetter().contains(letters)){
                    data.get(i).setSelect(flag);
                }
            }
        }
    }
    public int getCountA(){
        int count = 0;
        if (data_a!=null){
            for (int i = 0 ; i<data_a.size();i++){
                if (data_a.get(i).isSelect()&&data_a.get(i).getLetter().equals("A")){
                    count++;
                }
            }
        }
        return count;
    }
    public int getCountB(){
        int count = 0;
        if (data_b!=null){
            for (int i = 0 ; i<data_b.size();i++){
                if (data_b.get(i).isSelect()&&data_b.get(i).getLetter().equals("B")){
                    count++;
                }
            }
        }
        return count;
    }
    public int getCountC(){
        int count = 0;
        if (data_c!=null){
            for (int i = 0 ; i<data_c.size();i++){
                if (data_c.get(i).isSelect()&&data_c.get(i).getLetter().equals("C")){
                    count++;
                }
            }
        }
        return count;
    }
    public ArrayList<Integer> getSelections(){
        ArrayList<Integer> selections = new ArrayList<>();
        for ( int i = 0 ; i < data.size() ; i++){
            ChoseStudentListEntity entity = data.get(i);
            if (!entity.isGroup()&&entity.isSelect()){
                selections.add(entity.getStu_job_id());
            }
        }
        return selections;
    }

}

package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.ClassStudentEntity;
import me.bandu.talk.android.phone.viewholder.SelectStudentHolder;

/**
 * 创建者：高楠
 * 时间：2015/11/26
 * 类描述：选择学生
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SelectStudentAdapter extends BaseAdapter implements View.OnClickListener{


    private Context context;
    private List<ClassStudentEntity> list;
    private String current = "A";
    private List<String> list_letters;
//    private List<ClassStudentEntity> selects;
    private DisplayImageOptions options;

    public SelectStudentAdapter(Context context,List<ClassStudentEntity> list,List<ClassStudentEntity> selects){
        this.context = context;
        this.list = list;
//        this.selects = selects;
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.default_avatar)          // image在加载过程中，显示的图片
                .showImageForEmptyUri(R.mipmap.default_avatar)  // empty URI时显示的图片
                .showImageOnFail(R.mipmap.default_avatar)       // 不是图片文件 显示图片
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .build();
        list_letters = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            if (list.get(i).getAlpha().equals(current)){
                list_letters.add("");
            }else {
                current = list.get(i).getAlpha();
                list_letters.add(current);
            }
            if (selects!=null&&selects.size()!=0){
                for (int j = 0;j<selects.size();j++){
                    if (list.get(i).getUid().equals(selects.get(j).getUid())){
                        list.set(i,selects.get(j));
                        selects.remove(j);
                    }
                }
            }else {
                list.get(i).setSelected(true);
            }
        }
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        SelectStudentHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_student,null);
            holder = new SelectStudentHolder(convertView);
            convertView.setOnClickListener(this);
            convertView.setTag(holder);
        }else {
            holder = (SelectStudentHolder)convertView.getTag();
        }
        holder.position = position;
        if (list_letters.get(position).equals("")){
            holder.tv_letter.setVisibility(View.GONE);
        }else {
            current = list.get(position).getAlpha();
            holder.tv_letter.setVisibility(View.VISIBLE);
            holder.tv_letter.setText(list_letters.get(position));
        }
        holder.tv_name.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(list.get(position).getAvatar(),holder.iv_cover,options);
        holder.checkBox.setChecked((list.get(position).isSelected()));
        return convertView;
    }


    public int getLetterPosition(String str){
        if (list_letters!=null){
            for (int i = 0;i<list_letters.size();i++){
                if (str.equals(list_letters.get(i))){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void onClick(View v) {
        SelectStudentHolder holder = (SelectStudentHolder)v.getTag();
        boolean flag = !list.get(holder.position).isSelected();
        list.get(holder.position).setSelected(flag);
        holder.checkBox.setChecked(flag);
    }
    public void selectAll(){
        if (list!=null&&list.size()!=0){
            for (int i = 0 ;i < list.size();i++){
                list.get(i).setSelected(true);
            }
            notifyDataSetChanged();
        }
    }
    public void selectNone(){
        if (list!=null&&list.size()!=0){
            for (int i = 0 ;i < list.size();i++){
                list.get(i).setSelected(false);
            }
            notifyDataSetChanged();
        }
    }
    public List<ClassStudentEntity> getSelected(){
        return list;
    }
    public boolean isOK(){
        for (int i = 0 ; i < list.size();i++){
            if (list.get(i).isSelected()){
                return true;
            }
        }
        return false;
    }
}

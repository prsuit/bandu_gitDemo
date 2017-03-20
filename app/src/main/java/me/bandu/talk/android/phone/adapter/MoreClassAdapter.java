package me.bandu.talk.android.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.UserClassListBean;

/**
 * Created by fanyu on 2016/7/5.
 * 学生个人中心中的多班级列表的适配器
 */
public class MoreClassAdapter extends BaseAdapter {
    private Context context;
    private List<UserClassListBean.DataBean.ClassListBean> list;
    public MoreClassAdapter(Context context, List<UserClassListBean.DataBean.ClassListBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolderItem itemList;
        UserClassListBean.DataBean.ClassListBean listBean = list.get(position);
        if (convertView == null){
            convertView  = LayoutInflater.from(context).inflate(R.layout.item_moreclass_list,null);
            itemList = new ViewHolderItem(convertView);
            convertView.setTag(itemList);
        }else{
            itemList = (ViewHolderItem)convertView.getTag();
        }

            if(listBean.getStatus()==1) {
                itemList.tv_class.setText("班级" + (++position));
                itemList.tv_classname.setText(listBean.getClass_name());
                itemList.tv_classnumberid.setText(listBean.getCid() + "");
            }
        return convertView;
    }

    public class ViewHolderItem{
        public ViewHolderItem(View view){
             //item里面的班级号
            tv_class= (TextView) view.findViewById(R.id.tv_class);
            //班级名字
            tv_classname= (TextView) view.findViewById(R.id.tv_classname);
            //班级编号：
            tv_classid= (TextView) view.findViewById(R.id.tv_classid);
            //班级数字编号
            tv_classnumberid= (TextView) view.findViewById(R.id.tv_classnumberid);

        }
        TextView tv_class,tv_classname,tv_classid ,tv_classnumberid;
    }
}

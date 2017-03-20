//package me.bandu.talk.android.phone.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import java.util.List;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.activity.SelectCapterLessonActivity;
//import me.bandu.talk.android.phone.activity.SelectCapterUnitActivity;
//import me.bandu.talk.android.phone.bean.BookInfoEntity;
//import me.bandu.talk.android.phone.viewholder.CapterOneHolder;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第一步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//public class CapterUnitAdapter extends BaseAdapter implements View.OnClickListener{
//    private Context context;
//    private List<BookInfoEntity> list;
//    private String book_id;
//    private Bundle data;
//    public CapterUnitAdapter(Context context, List<BookInfoEntity> list,Bundle data){
//        this.context = context;
//        this.list = list;
//        this.data = data;
//        this.book_id = data.getString("book_id");
//    }
//
//    @Override
//    public int getCount() {
//        return list==null?0:list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        CapterOneHolder holder = null;
//        if (convertView==null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
//            holder = new CapterOneHolder(convertView);
//            convertView.setOnClickListener(this);
//            convertView.setTag(holder);
//        }else {
//            holder = (CapterOneHolder) convertView.getTag();
//        }
//        convertView.setTag(R.layout.list_item_t,position);
//        holder.tv.setText(list.get(position).getUnit_name());
//        return convertView;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(context, SelectCapterLessonActivity.class);
////        Bundle bundle = new Bundle();
//        data.putString("unit_id",list.get((int)v.getTag(R.layout.list_item_t)).getUnit_id());
//        data.putString("unit_name",list.get((int)v.getTag(R.layout.list_item_t)).getUnit_name());
//        intent.putExtra("data",data);
//        ((Activity)context).startActivityForResult(intent, SelectCapterUnitActivity.CREATWORK_CODE_OK);
////        ((Activity)context).finish();
//    }
//}

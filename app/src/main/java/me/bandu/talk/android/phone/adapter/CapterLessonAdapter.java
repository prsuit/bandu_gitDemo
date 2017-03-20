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
//
//import java.util.List;
//
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.bean.UnitInfoEntity;
//import me.bandu.talk.android.phone.viewholder.CapterTwoHolder;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第二步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//public class CapterLessonAdapter extends BaseAdapter implements View.OnClickListener{
//    Context context;
//    List<UnitInfoEntity> list;
//    Bundle bundle;
//    public CapterLessonAdapter(Context context, List<UnitInfoEntity> list, Bundle bundle){
//        this.context = context;
//        this.list = list;
//        this.bundle = bundle;
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
//        CapterTwoHolder holder;
//        if (convertView==null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
//            holder = new CapterTwoHolder(convertView);
//            convertView.setTag(holder);
//        }else {
//            holder = (CapterTwoHolder) convertView.getTag();
//        }
//        convertView.setTag(R.layout.list_item_t,position);
//        convertView.setOnClickListener(this);
//        holder.tv.setText(list.get(position).getLesson_name());
////        holder.llroot.setOnClickListener(new MyOnClick(position));
//        return convertView;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(context, SelectCapterQuizActivity.class);
//        bundle.putString("lesson_id",list.get((int)v.getTag(R.layout.list_item_t)).getLesson_id());
//        bundle.putString("lesson_name",list.get((int)v.getTag(R.layout.list_item_t)).getLesson_name());
//        intent.putExtra("data",bundle);
//        ((Activity)context).startActivityForResult(intent, SelectCapterUnitActivity.CREATWORK_CODE_OK);
////        ((Activity)context).finish();
//    }
//
////    public class MyOnClick implements View.OnClickListener{
////
////        private int position;
////
////        public MyOnClick(int position){
////            this.position = position;
////        }
////
////        @Override
////        public void onClick(View v) {
////            Intent intent = new Intent(context, SelectCapterQuizActivity.class);
////            bundle.putString("lesson_id",list.get(position).getLesson_id());
////            bundle.putString("lesson_name",list.get(position).getLesson_name());
////            intent.putExtra("data",bundle);
////            ((Activity)context).startActivityForResult(intent, SelectCapterUnitActivity.CREATWORK_CODE_OK);
////        }
////    }
//}

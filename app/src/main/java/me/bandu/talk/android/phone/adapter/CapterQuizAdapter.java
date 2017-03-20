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
//import com.DFHT.utils.UIUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.bean.LessonInfoEntity;
//import me.bandu.talk.android.phone.viewholder.CapterThreeHolder;
//
///**
// * 创建者：高楠
// * 时间：2015/11/23
// * 类描述：选择教材第三步
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//public class CapterQuizAdapter extends BaseAdapter implements View.OnClickListener{
//    private Context context;
//    private List<LessonInfoEntity> list;
//    private Bundle bundle;
//    private ArrayList<String> arrayList;
//    private boolean isAdd;
//    public CapterQuizAdapter(Context context, List<LessonInfoEntity> list, Bundle bundle, boolean isAdd, ArrayList<String> arrayList){
//        this.context = context;
//        this.list = list;
//        this.bundle = bundle;
//        this.isAdd = isAdd;
//        this.arrayList = arrayList;
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
//        CapterThreeHolder holder;
//        if (convertView==null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_t,null);
//            holder = new CapterThreeHolder(convertView);
//            convertView.setTag(R.layout.list_item_t,position);
//            convertView.setOnClickListener(this);
//            convertView.setTag(holder);
//        }else {
//            holder = (CapterThreeHolder) convertView.getTag();
//        }
//        holder.tv.setText(list.get(position).getQuiz_name());
//        return convertView;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(context, WorkPreviewActivity.class);
//        if (isAdd){
//            boolean flag = true;
//            if (arrayList!=null){
//                for (int i = 0 ;i<arrayList.size();i++){
//                    if (list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_id().equals(arrayList.get(i))){
//                        UIUtils.showToastSafe("该题已选择！");
//                        flag = false;
//                    }
//                }
//            }
//            if (flag){
//                intent.putExtra("quiz_id",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_id());
//                intent.putExtra("quiz_name",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_name());
//                ((Activity)context).setResult(((Activity)context).RESULT_OK,intent);
//                ((Activity) context).finish();
//            }
//        }else {
//            bundle.putString("quiz_id",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_id());
//            bundle.putString("quiz_name",list.get((int)v.getTag(R.layout.list_item_t)).getQuiz_name());
//            intent.putExtra("data",bundle);
//            ((Activity)context).startActivityForResult(intent, SelectCapterUnitActivity.CREATWORK_CODE_OK);
////            ((Activity) context).finish();
//        }
//    }
//}

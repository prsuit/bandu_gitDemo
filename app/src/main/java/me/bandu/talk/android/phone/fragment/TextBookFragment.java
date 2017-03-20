package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.ClassManagerActivity;
import me.bandu.talk.android.phone.activity.SelectGradeActivity;
import me.bandu.talk.android.phone.adapter.BookAdapter;
import me.bandu.talk.android.phone.bean.TeacherHomeList;
import me.bandu.talk.android.phone.bean.TextBookInfoBean;
import me.bandu.talk.android.phone.middle.TextBookInfoMiddle;
import me.bandu.talk.android.phone.utils.BitmapUtil;

/**
 * 创建者：gaoye
 * 时间：2015/11/19  15:45
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TextBookFragment extends BaseFragment implements View.OnClickListener, BaseActivityIF {
    private View view, view_tbook, view_no_tbook;
    private ExpandableListView elv_books;
    private BookAdapter adapter;
    private List<TextBookInfoBean.DataBean.ContentsBean> units;
    private List<List<TextBookInfoBean.DataBean.ContentsBean.LessonListBean>> lessons;
    private Button bt_bookreset;
    private TextView tv_name, tv_version, tv_date;
    private ImageView iv_bookimg;
    private TextBookInfoMiddle textBookInfoMiddle;
    private TextBookInfoBean textBookInfoBean;
    private TeacherHomeList.DataEntity.ListEntity classInfo;
    private LayoutInflater inflater;
    private ImageButton ib_addtextbook;
    private String bookid;
    private final int REQUEST_SETBOOK = 0;
    private ImageView iv_book_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view_tbook = inflater.inflate(R.layout.fragment_textbook, null);
            view_tbook.setTag(0);
            view_no_tbook = inflater.inflate(R.layout.fragment_textbook_notext, null);
            view_no_tbook.setTag(1);

            classInfo = ((ClassManagerActivity) getActivity()).getClassInfo();
            bookid = ((ClassManagerActivity) getActivity()).getClassInfo().getBookid();
            if (bookid == null || "0".equals(bookid)) {
                view = view_no_tbook;
            } else {
                view = view_tbook;
            }

            initView(view);
            setData();
            setListeners();
        }
        return view;
    }

    private void setTextBook() {
        bookid = ((ClassManagerActivity) getActivity()).getClassInfo().getBookid();
        int tag = (int) view.getTag();
        if (bookid == null || "0".equals(bookid)) {
            //无绑定教材
            if (tag == 1)
                return;
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
            view = view_no_tbook;
            parent.addView(view);
        } else {
            if (tag == 1) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (null != parent) {
                    parent.removeView(view);
                }
                view = view_tbook;
                parent.addView(view);
            }
            textBookInfoMiddle.getTextbookInfo(bookid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView(View view) {
        elv_books = (ExpandableListView) view_tbook.findViewById(R.id.elv_books);
        bt_bookreset = (Button) view_tbook.findViewById(R.id.bt_bookreset);
        tv_date = (TextView) view_tbook.findViewById(R.id.tv_date);
        tv_name = (TextView) view_tbook.findViewById(R.id.tv_bookname);
        tv_version = (TextView) view_tbook.findViewById(R.id.tv_version);
        iv_bookimg = (ImageView) view_tbook.findViewById(R.id.iv_bookimg);
        iv_book_out = (ImageView) view_tbook.findViewById(R.id.iv_book_down_bind); //教材下架的图标

        ib_addtextbook = (ImageButton) view_no_tbook.findViewById(R.id.ib_addtextbook);

    }

    @Override
    public void setData() {
        textBookInfoMiddle = new TextBookInfoMiddle(this, getActivity());

        units = new ArrayList<>();
        lessons = new ArrayList<>();
        adapter = new BookAdapter(getActivity(), units, lessons);
        elv_books.setAdapter(adapter);
        if (bookid != null && !"0".equals(bookid)) {
            textBookInfoMiddle.getTextbookInfo(bookid);
        }
    }

    @Override
    public void setListeners() {
        bt_bookreset.setOnClickListener(this);
        ib_addtextbook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_bookreset:
                intent = new Intent(getActivity(), SelectGradeActivity.class);
                intent.putExtra("classid", classInfo.getCid());
                startActivityForResult(intent, REQUEST_SETBOOK);
                break;
            case R.id.ib_addtextbook:
                intent = new Intent(getActivity(), SelectGradeActivity.class);
                intent.putExtra("classid", classInfo.getCid());
                startActivityForResult(intent, REQUEST_SETBOOK);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            int bookid = data.getIntExtra("bookid", -1);
            if (bookid != -1) {
                ((ClassManagerActivity) getActivity()).setBookid(bookid);
                setTextBook();
            }
        }
    }

    @Override
    public void successFromMid(Object... obj) {
        textBookInfoBean = (TextBookInfoBean) obj[0];
        if (textBookInfoBean != null) {
            if (textBookInfoBean.getData() != null) {
                if (textBookInfoBean.getData().getContents() != null) {
                    units.removeAll(units);
                    units.addAll(textBookInfoBean.getData().getContents());
                    lessons.removeAll(lessons);
                    for (int i = 0; i < units.size(); i++) {
                        List<TextBookInfoBean.DataBean.ContentsBean.LessonListBean> list = new ArrayList<>();
                        list.addAll(textBookInfoBean.getData().getContents().get(i).getLesson_list());
                        lessons.add(list);
                    }
                    adapter.notifyDataSetChanged();
                }

                if (textBookInfoBean.getData() != null) {
                    tv_name.setText(textBookInfoBean.getData().getBook_name());
                }
                if (textBookInfoBean.getData() != null) {
                    tv_version.setText(textBookInfoBean.getData().getVersion());
                }
                if (textBookInfoBean.getData() != null) {
                    tv_date.setText(textBookInfoBean.getData().getGrade());
                }
                if (textBookInfoBean.getData() != null) {
                    if (textBookInfoBean.getData().getStatus() == 0) {
                        //说明教材下架
                        iv_book_out.setVisibility(View.VISIBLE); //下架的图标显示
                    } else {
                        iv_book_out.setVisibility(View.GONE);
                    }
                }
                // 解决布置作业默认教材图片的oom
                try {
                    if (bmp == null) {
                        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.book);
                    }
                    Bitmap bitmap = BitmapUtil.zoomImage(getActivity(), bmp, bmp.getWidth() / 2, bmp.getHeight() / 2);
                    iv_bookimg.setImageBitmap(bitmap);
                    ImageLoader.getInstance().displayImage(textBookInfoBean.getData().getCover(), iv_bookimg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    Bitmap bmp;

    @Override
    public void onStart() {
        super.onStart();
        if (bmp == null)
            bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.book);
    }

    @Override
    public void failedFrom(Object... obj) {

    }

    @Override
    public void onSuccess(Object result, int requestCode) {

    }

    @Override
    public void onFailed(int requestCode) {

    }
}

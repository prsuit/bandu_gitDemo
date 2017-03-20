package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.StudentTextbookAdapter;
import me.bandu.talk.android.phone.adapter.StudentTextbookChoseTypeExpandableAdapter;
import me.bandu.talk.android.phone.bean.ExerciseTextBookChoseBean;
import me.bandu.talk.android.phone.bean.ExerciseTextbookTypeBean;
import me.bandu.talk.android.phone.middle.ExerciseTextbookChoseMiddle;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.view.SearchEditText;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：教材选择
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentTextbookChoseActivity extends BaseStudentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.rb_grade)
    RadioButton rb_grade;
    @Bind(R.id.rb_hot)
    RadioButton rb_hot;
    @Bind(R.id.rl_filter)
    RelativeLayout rl_filter;
    @Bind(R.id.lv_books)
    ListView lv_books;
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    @Bind(R.id.title_edit)
    SearchEditText title_edit;
    @Bind(R.id.title_right)
    TextView title_right;
    @Bind(R.id.rg_chose)
    RadioGroup rg_chose;
    @Bind(R.id.ll_chose)
    LinearLayout ll_chose;
    @Bind(R.id.title_rl)
    RelativeLayout title_rl;
    @Bind(R.id.tv_filter)
    TextView tv_filter;
    private final int REQUEST_SEARCH_CODE = 0;
    private StudentTextbookAdapter adapter;
    private TextView footView;
    private boolean showType, showFilter;
    private PopupWindow typePop, filterPop;
    private ExerciseTextbookChoseMiddle textbookChoseMiddle;
    private List<ExerciseTextBookChoseBean.DataEntity.ListEntity> books;
    private int currentPage = 1, size = 5;
    private ExerciseTextBookChoseBean.DataEntity.HeaderEntity heads;
    private int currentVersion = -1, currentSubject = -1;
    private String currentSort = "0";
    private boolean loadmore;
    private String keyword = "";
    private int[] grades = new int[]{};
    private int[] stars = new int[]{};
    private int[] cats = new int[]{};
    private int[] subs = new int[]{};
    private List<Integer> catList = new ArrayList<>();
    private List<Integer> subList = new ArrayList<>();
    private List<Integer> catListClear = new ArrayList<>();
    private Drawable db_up,db_down;
    private List<ExerciseTextbookTypeBean.DataEntity.ListEntity> types;
    private ExpandableListView elv_type;

    @Override
    protected int getLayoutId() {
       /* //状态栏 @ 顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A
        //导航栏 @ 底部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//B*/

        return R.layout.activity_student_textbook_chose;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
        toNetGetData();
    }

    @Override
    public void initView() {
        footView = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_student_textbook_loadmore, null);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(50, this));
        footView.setLayoutParams(params);
        footView.setVisibility(View.GONE);
        title_right.setText("选类别");
        title_right.setVisibility(View.INVISIBLE);
        /*title_right.setFocusable(false);
        title_right.setClickable(false);*/
        title_edit.setCursorVisible(false);
        title_edit.getClearImageView().setFocusable(false);
        title_edit.getClearImageView().setClickable(false);
        title_edit.getClearImageView().setVisibility(View.INVISIBLE);
        title_edit.editTextHide();
        title_edit.setText("全部类别");
        //布局中将radiagroup的weight设置为了2 但是宽度被压缩了 所以得手动设置宽度
        LinearLayout.LayoutParams rg_params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(this)/3 * 2, ViewGroup.LayoutParams.MATCH_PARENT);
        rg_chose.setLayoutParams(rg_params);
    }

    @Override
    public void setData() {
        db_down = getResources().getDrawable(R.mipmap.down);
        db_up = getResources().getDrawable(R.mipmap.up);
        db_down.setBounds(0, 0, db_down.getMinimumWidth(), db_down.getMinimumHeight());
        db_up.setBounds(0, 0, db_up.getMinimumWidth(), db_up.getMinimumHeight());
        tv_filter.setCompoundDrawables(null,null,db_down,null);

        lv_books.addFooterView(footView);
        books = new ArrayList<>();
        adapter = new StudentTextbookAdapter(this, books);
        lv_books.setAdapter(adapter);
        types = new ArrayList<>();
        textbookChoseMiddle = new ExerciseTextbookChoseMiddle(this, this);
        textbookChoseMiddle.getExerciseTextbookTypes();
    }

    private void toNetGetData() {
        cats = new int[catList.size()];
        for (int i = 0;i<catList.size();i++){
            cats[i] = catList.get(i);
        }

        subs = new int[subList.size()];
        for (int i = 0;i<subList.size();i++){
            subs[i] = subList.get(i);
        }
        textbookChoseMiddle.getExerciseTextbook(currentVersion, currentSubject, grades, currentSort, currentPage, size, stars,keyword,cats,subs);
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);
        footView.setOnClickListener(this);
        rl_filter.setOnClickListener(this);
        lv_books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!UserUtil.isLogin()) {
                    Toast.makeText(StudentTextbookChoseActivity.this, "请先进行登录", Toast.LENGTH_SHORT).show();
                } else {
                    if (books.size() > position) {
                        ExerciseTextBookChoseBean.DataEntity.ListEntity book = books.get(position);
                        Intent intent = new Intent(StudentTextbookChoseActivity.this, StudentExerciseDownloadActivity.class);
                        intent.putExtra("bookid", book.getBook_id());
                        intent.putExtra("subject", StringUtil.getIntegerNotnull(book.getSubject()));
                        intent.putExtra("category",book.getCategory());
                        startActivity(intent);
                    }
                }
            }
        });


        title_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Intent intent = new Intent(StudentTextbookChoseActivity.this,StudentBookSearchActivity.class);
                    intent.putExtra("keyword",keyword);
                    startActivityForResult(intent,REQUEST_SEARCH_CODE);
                }
                return true;
            }
        });

        rg_chose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (rb != null){
                    currentSort = (String) rb.getTag();
                    toNetGetData();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH_CODE) {
            if (resultCode == RESULT_OK){
                String keyword = data.getStringExtra("keyword");
                    currentVersion = -1;
                    currentSubject = -1;
                    //grades = new int[]{};
                    //stars = new int[]{};
                    //currentSort = "0";
                    currentPage = 1;
                    size = 5;
                    this.keyword = keyword;
                    title_edit.setText(keyword);
                    catList.clear();
                    catList.addAll(catListClear);
                    subList.clear();
                    toNetGetData();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.rl_filter:
                showFilter = !showFilter;
                createFilterPopupwindow(showFilter);
                break;
            case R.id.bt_cancle:
                if (filterPop != null && filterPop.isShowing()) {
                    filterPop.dismiss();
                }
                break;
            case R.id.bt_commit:
                startCommit();
                break;
            case R.id.tv_loadmore:
                loadmore = true;
                currentPage++;
                toNetGetData();
                break;
            case R.id.title_right:
                showType = !showType;
                createTypePopupwindow(showType);
                break;
            case R.id.bt_confirm:
                typeComfim();
                break;
            case R.id.ll_expand:
                int groupPosition = (int) view.getTag();
                if (elv_type.isGroupExpanded(groupPosition)){
                    elv_type.collapseGroup(groupPosition);
                }else {
                    elv_type.expandGroup(groupPosition);
                }
                break;
        }
    }

    private void typeComfim() {
        keyword = "";
        title_edit.setText("全部类别");

        toNetGetData();
        if (typePop.isShowing())
            typePop.dismiss();
    }

    private void startCommit() {
        if (filterPop != null && filterPop.isShowing()) {
            filterPop.dismiss();
        }
        if (gradesList != null && starList != null) {
            gradesList.removeAll(gradesList);
            starList.removeAll(starList);
            if (rbs != null) {
                for (CheckBox rb : rbs) {
                    if (rb != null)
                        if (rb.isChecked()) {
                            Map<String, Integer> map = (Map<String, Integer>) rb.getTag();
                            if (map.containsKey("grade")) {
                                gradesList.add(map.get("grade"));
                            } else if (map.containsKey("star")) {
                                starList.add(map.get("star"));
                            }
                        }
                }
            }
            grades = new int[gradesList.size()];
            stars = new int[starList.size()];
            for (int i = 0; i < gradesList.size(); i++) {
                grades[i] = gradesList.get(i);
            }
            for (int i = 0; i < starList.size(); i++) {
                stars[i] = starList.get(i);
            }
            currentPage = 1;
            //keyword = "";
            toNetGetData();
        }
    }


    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        int code = (int) obj[1];
        if (code == ExerciseTextbookChoseMiddle.EXERCISE_TEXTBOOK_CHOSE){
            ExerciseTextBookChoseBean bean = (ExerciseTextBookChoseBean) obj[0];
            if (!loadmore)
                books.removeAll(books);
            else {
                if (bean.getData().getList().size() == 0) {
                    Toast.makeText(StudentTextbookChoseActivity.this, "已到最后一页", Toast.LENGTH_SHORT).show();
                    currentPage--;
                }
            }
            books.addAll(bean.getData().getList());
            adapter.notifyDataSetChanged();
            heads = bean.getData().getHeader();
            loadmore = false;
            footView.setVisibility(View.VISIBLE);
            rb_hot.setText(StringUtil.getShowText(heads.getSorts().get(0).getName()));
            rb_hot.setTag(StringUtil.getShowText(heads.getSorts().get(0).getValue()));
            rb_grade.setText(StringUtil.getShowText(heads.getSorts().get(1).getName()));
            rb_grade.setTag(StringUtil.getShowText(heads.getSorts().get(1).getValue()));
            if (books.size() == 0){
                lv_books.setVisibility(View.GONE);
            }else {
                lv_books.setVisibility(View.VISIBLE);
            }
        }else if (code == ExerciseTextbookChoseMiddle.STUDENT_TEXTBOOK_TYPES){
            ExerciseTextbookTypeBean typeBean = (ExerciseTextbookTypeBean) obj[0];
            types.clear();
            types.addAll(typeBean.getData().getList());

            for (int i = 0;i<types.size();i++){
                catList.add(types.get(i).getCat_id());
            }
            catListClear.addAll(catList);
            title_right.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
        if (loadmore)
            currentPage--;
        loadmore = false;
    }

    private void createTypePopupwindow(boolean showType) {
        if (typePop == null) {
            typePop = new PopupWindow();
            final View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow_textbook_type, null);
            typePop.setContentView(view);
            typePop.setWidth(ScreenUtil.getScreenWidth(this));
            typePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            typePop.setFocusable(true);
            typePop.setBackgroundDrawable(new BitmapDrawable());
            typePop.setInputMethodMode(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
            typePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            typePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StudentTextbookChoseActivity.this.showType = false;
                }
            });
        }
        typePop.getContentView().setBackgroundColor(getResources().getColor(R.color.white));
        StudentTextbookChoseTypeExpandableAdapter typeExpandableAdapter
                = new StudentTextbookChoseTypeExpandableAdapter(this,types,this,catList,subList,this);
        elv_type = (ExpandableListView) typePop.getContentView().findViewById(R.id.lv_types);
        Button bt_confirm = (Button) typePop.getContentView().findViewById(R.id.bt_confirm);
        elv_type.setAdapter(typeExpandableAdapter);
        elv_type.setGroupIndicator(null);
        bt_confirm.setOnClickListener(this);
        /*for (int i = 0;i<types.size();i++){
            elv_type.expandGroup(i);
        }*/

        if (showType) {
            typePop.showAsDropDown(title_rl);
        } else {
            typePop.dismiss();
        }
    }

    private List<CheckBox> rbs;
    private List<Integer> gradesList;
    private List<Integer> starList;

    private void createFilterPopupwindow(boolean showFilter) {
        if (filterPop == null) {
            if (heads == null) {
                return;
            }
            List<ExerciseTextBookChoseBean.DataEntity.HeaderEntity.FiltersEntity> grades = heads.getFilters();
            List<ExerciseTextBookChoseBean.DataEntity.HeaderEntity.StarsEntity> stars = heads.getStars();
            filterPop = new PopupWindow();
            rbs = new ArrayList<>();
            gradesList = new ArrayList<>();
            starList = new ArrayList<>();
            LinearLayout ll_main = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_textbook_chose, null);
            LinearLayout ll_grade = (LinearLayout) ll_main.findViewById(R.id.ll_grade);
            LinearLayout ll_star = (LinearLayout) ll_main.findViewById(R.id.ll_star);
            filterPop.setContentView(ll_main);
            filterPop.setWidth(ScreenUtil.getScreenWidth(this));
            filterPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            filterPop.setFocusable(true);
            filterPop.setBackgroundDrawable(new BitmapDrawable());
            filterPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StudentTextbookChoseActivity.this.showFilter = false;
                    tv_filter.setCompoundDrawables(null,null,db_down,null);
                }
            });
            Button bt_commit = (Button) ll_main.findViewById(R.id.bt_commit);
            Button bt_cancle = (Button) ll_main.findViewById(R.id.bt_cancle);
            bt_commit.setOnClickListener(this);
            bt_cancle.setOnClickListener(this);
            LinearLayout layout_row = null;
            int width = (ScreenUtil.getScreenWidth(this) - ScreenUtil.dp2px(5, this) * 6 - ScreenUtil.dp2px(16, this) * 2) / 3;
            LinearLayout.LayoutParams params_radio = new LinearLayout.LayoutParams(width, ScreenUtil.dp2px(30, this));
            params_radio.setMargins(ScreenUtil.dp2px(5, this), 0, ScreenUtil.dp2px(5, this), 0);

            for (int i = 0; i < grades.size(); i++) {
                if (i % 3 == 0) {
                    LinearLayout.LayoutParams params_raw = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params_raw.topMargin = ScreenUtil.dp2px(5, this);
                    layout_row = new LinearLayout(this);
                    layout_row.setLayoutParams(params_raw);
                    layout_row.setOrientation(LinearLayout.HORIZONTAL);
                    ll_grade.addView(layout_row);
                }
                CheckBox radioButton = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox, null);
                radioButton.setId(10000 + i);
                Map<String, Integer> map = new HashMap<>();
                map.put("grade", grades.get(i).getValue());
                radioButton.setTag(map);
                radioButton.setText(grades.get(i).getName());
                rbs.add(radioButton);

                layout_row.addView(radioButton);

                radioButton.setLayoutParams(params_radio);
            }

            width = (ScreenUtil.getScreenWidth(this) - ScreenUtil.dp2px(2, this) * 10 - ScreenUtil.dp2px(16, this) * 2) / 5;
            params_radio = new LinearLayout.LayoutParams(width, ScreenUtil.dp2px(30, this));
            params_radio.setMargins(ScreenUtil.dp2px(2, this), 0, ScreenUtil.dp2px(2, this), 0);
            for (int i = 0; i < stars.size(); i++) {
                if (i % 5 == 0) {
                    LinearLayout.LayoutParams params_raw = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params_raw.topMargin = ScreenUtil.dp2px(5, this);
                    layout_row = new LinearLayout(this);
                    layout_row.setLayoutParams(params_raw);
                    layout_row.setOrientation(LinearLayout.HORIZONTAL);
                    ll_star.addView(layout_row);
                }
                CheckBox radioButton = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_checkbox, null);
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setSingleLine();
                radioButton.setId(20000 + i);
                Map<String, Integer> map = new HashMap<>();
                map.put("star", grades.get(i).getValue());
                radioButton.setTag(map);
                radioButton.setText(stars.get(i).getName());
                rbs.add(radioButton);

                layout_row.addView(radioButton);

                radioButton.setLayoutParams(params_radio);
            }
        }
        if (showFilter) {
            filterPop.showAsDropDown(ll_chose);
            tv_filter.setCompoundDrawables(null,null,db_up,null);
        } else {
            filterPop.dismiss();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int[] catorsub = (int[]) buttonView.getTag();
            if (catorsub[0] == StudentTextbookChoseTypeExpandableAdapter.CAT){
                if (isChecked){
                    if (!catList.contains(catorsub[1])){
                        catList.add(catorsub[1]);
                    }
                }else {
                    if (catList.contains(catorsub[1])){
                        Integer integer = new Integer(catorsub[1]);
                        catList.remove(integer);
                    }
                }

            }else if(catorsub[0] == StudentTextbookChoseTypeExpandableAdapter.SUB){
                if (isChecked){
                    if (!subList.contains(catorsub[1])){
                        subList.add(catorsub[1]);
                    }
                }else {
                    if (subList.contains(catorsub[1])){
                        Integer integer = new Integer(catorsub[1]);
                        subList.remove(integer);
                    }
                }
            }
    }
}

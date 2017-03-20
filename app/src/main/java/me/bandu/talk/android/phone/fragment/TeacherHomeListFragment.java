package me.bandu.talk.android.phone.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.ui.LoadPageView;
import com.DFHT.ui.uienum.LoadResult;
import com.DFHT.utils.UIUtils;
import com.DFHT.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.CreateClassActivity;
import me.bandu.talk.android.phone.activity.TeacherHomeActivity;
import me.bandu.talk.android.phone.adapter.TeacherHomeListAdapter;
import me.bandu.talk.android.phone.bean.TeacherHomeList;
import me.bandu.talk.android.phone.middle.TeacherHomeListMiddle;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/23 14:35
 * 类描述：教师首页列表.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherHomeListFragment extends Fragment implements BaseActivityIF {

    private LoadPageView loadPageView;

    @Nullable
    @Bind(R.id.rvMyClass)
    RecyclerView rvMyClass;

    @Nullable
    @Bind(R.id.srlMyClass)
    SwipeRefreshLayout srlMyClass;

    private int cur_page = 1;

    LinearLayoutManager manager;
    TeacherHomeListMiddle middle;
    TeacherHomeList homeListAll;
    TeacherHomeListAdapter adapter;

    public static boolean isLoadingMore = true;
    public static boolean isLoading = false;
    public static boolean haveMore = true;


    private int total_more = -1;
    private int total_virtual = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loadPageView == null) {
            loadPageView = new LoadPageView(getActivity()) {
                @Override
                protected int createSuccessView() {
                    return R.layout.teacher_home_list_fragment;
                }

                @Override
                protected void load() {
                    loadDate();
                    haveMore = true;
                }

                @Override
                protected View createErrorView() {
                    View errorView = View.inflate(UIUtils.getContext(), com.DFHT.R.layout.error_wifi, null);
                    errorView.findViewById(com.DFHT.R.id.rlRoot).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cur_page = 1;
                            homeListAll = null;
                            show();
                        }
                    });
                    return errorView;
                }

                @Override
                protected View createEmptyView() {
                    View view = UIUtils.inflate(R.layout.teacher_home_empty_fragment);
                    view.findViewById(R.id.ivCreateClass).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), CreateClassActivity.class);
                            startActivity(intent);
                        }
                    });
                    return view;
                }
            };
            loadPageView.show();
        } else {
            ViewUtils.removeParent(loadPageView);
        }
        reloadData();
        // ButterKnife.bind(this, loadPageView);
        return loadPageView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(String message) {
        if ("msg".equals(message)) {
            reloadData();
        }
    }

    private void reloadData() {
        if (GlobalParams.HOME_CHANGED) {
            GlobalParams.HOME_CHANGED = false;
            homeListAll = null;
            cur_page = 1;
            loadPageView.show();
        }
    }

    public void loadDate() {
        isLoadingMore = false;
        if (middle == null)
            middle = new TeacherHomeListMiddle(getActivity(), this);
        middle.getStuList(cur_page);
        cur_page++;
        if(getActivity()!=null){
            ((TeacherHomeActivity)getActivity()).showMyprogressDialog();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void successFromMid(Object... obj) {
        //do nothing
    }

    @Override
    public void failedFrom(Object... obj) {
        //do nothing
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {

        isLoadingMore = true;
        isLoading = false;
        if (srlMyClass != null) {
            srlMyClass.setRefreshing(false);
        }
        TeacherHomeList homeList = (TeacherHomeList) result;
        if (homeList.getStatus() == 1) {
            total_more = Integer.valueOf(homeList.getData().getTotal());

            if (homeList.getData().getList() != null && homeList.getData().getList().size() > 0) {
                TeacherHomeActivity activity = (TeacherHomeActivity) getActivity();
                if (activity != null)
                    activity.showDialog();

                loadPageView.notifyDataChanged(LoadResult.RESULT_SUCCESS);
                ButterKnife.bind(this, loadPageView);

                srlMyClass.setColorSchemeResources(R.color.blueStatus);

                srlMyClass.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        GlobalParams.HOME_CHANGED = false;
                        homeListAll = null;
                        cur_page = 1;
                        loadPageView.show();
                    }
                });


                rvMyClass.setLayoutManager(new LinearLayoutManager(getActivity()));
                if (homeListAll == null) {
                    homeListAll = homeList;
                } else {
                    boolean canAdd = true;
                    for (int i = 0; i < homeListAll.getData().getList().size(); i++) {
                        for (int j = 0; j < homeList.getData().getList().size(); j++) {
                            if (homeListAll.getData().getList().get(i).getCid().equals(homeList.getData().getList().get(j).getCid())) {
                                canAdd = false;
                                break;
                            }
                        }
                    }
                    if (canAdd)
                        homeListAll.getData().getList().addAll(homeList.getData().getList());
                }
                if(adapter == null){
                    adapter = new TeacherHomeListAdapter(this, homeListAll);
                    rvMyClass.setAdapter(adapter);
                }else {
                    adapter.setList(homeListAll);
                    adapter.notifyDataSetChanged();
                }

                total_virtual = homeListAll.getData().getList().size();
                if (total_virtual >= total_more) {
                    haveMore = false;
                }
                manager = (LinearLayoutManager) rvMyClass.getLayoutManager();
                rvMyClass.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        int lastVisibleItem = manager.findLastVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();
                        //下拉刷新
                        if (lastVisibleItem + 1 == totalItemCount) {

                            boolean isRefreshing = srlMyClass.isRefreshing();
                            if (isRefreshing) {
                                adapter.notifyItemRemoved(adapter.getItemCount());
                                return;
                            }
                            if (!isLoading && isLoadingMore) {
                                isLoading = true;
                                GlobalParams.HOME_CHANGED = false;
                                homeListAll = null;
                                cur_page = 1;
                                loadPageView.show();
                            }
                        }

                        //上拉加载更多

                        //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                        // dy>0 表示向下滑动
                        if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                            if (isLoadingMore && haveMore) {
                                loadDate();
                            } else {
                                isLoadingMore = false;
                            }
                        }
                    }
                });
            } else {
                loadPageView.notifyDataChanged(LoadResult.RESULT_EMPTY);
            }
        } else {
            loadPageView.notifyDataChanged(LoadResult.RESULT_ERROR);
        }
        ButterKnife.bind(this, loadPageView);
        if(getActivity()!=null){
            ((TeacherHomeActivity)getActivity()).hideMyprogressDialog();
        }
    }

    @Override
    public void onFailed(int requestCode) {
        if(getActivity()!=null){
            ((TeacherHomeActivity)getActivity()).hideMyprogressDialog();
        }
        isLoadingMore = true;
        isLoading = false;
        loadPageView.notifyDataChanged(LoadResult.RESULT_ERROR);
        if (srlMyClass != null) {
            srlMyClass.setRefreshing(false);
        }
    }
}

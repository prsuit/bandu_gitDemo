package com.DFHT.base;

import android.os.Bundle;

import com.DFHT.ui.LoadPageView;
import com.DFHT.ui.uienum.LoadResult;

import java.util.List;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/16 16:26
 * 类描述：这个基类是负责有空数据加载的activity. 无网络重新加载,数据为空,加载中的视图.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseActivityWithLoadPage extends BaseActivity {
    private LoadPageView loadPageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPageView = new LoadPageView(this) {
            @Override
            protected int createSuccessView() {
                return lauyoutView();
            }

            @Override
            protected void load() {
                loadData();
            }

        };
        loadPageView.show();//必须调用一次,否则不会执行.
        setContentView(loadPageView);
    }

    protected void notifyDataChanged(LoadResult result) {
        if (loadPageView != null) {
            loadPageView.notifyDataChanged(result);
        }
    }

    /**
     * Activity成功的视图
     *
     * @return 成功的视图.
     */
    protected abstract int lauyoutView();

    /**
     * 加载数据的方法
     * 该方法会在 activity开启时候就会执行一次.
     * 该方法执行在子线程中.
     */
    protected abstract void loadData();

    public static LoadResult checkData(List obj) {
        if (obj == null)
            return LoadResult.RESULT_ERROR;
        else {
            if (obj.size() <= 0)
                return LoadResult.RESULT_EMPTY;
            else
                return LoadResult.RESULT_SUCCESS;
        }
    }

}

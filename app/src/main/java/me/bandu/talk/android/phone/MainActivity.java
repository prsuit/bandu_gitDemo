package me.bandu.talk.android.phone;

import android.os.Bundle;
import android.os.SystemClock;
import com.DFHT.base.BaseActivityWithLoadPage;
import com.DFHT.ui.uienum.LoadResult;

public class MainActivity extends BaseActivityWithLoadPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

    }

    @Override
    protected int lauyoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep( 5000 );
                notifyDataChanged(LoadResult.RESULT_ERROR);
            }
        }.start();

    }

    @Override
    public void success(Object result, int requestCode) {

    }

    @Override
    public void failed(int requestCode) {

    }
}

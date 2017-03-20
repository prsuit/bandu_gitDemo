package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.ViewPageAdapter;
import me.bandu.talk.android.phone.utils.CacheUtils;
import me.bandu.talk.android.phone.utils.SystemApplation;
/**
 * 创建者：tg
 * 类描述：导航页
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class GuideActivity extends BaseAppCompatActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.point1)
    TextView point1;
    @Bind(R.id.point2)
    TextView point2;
    @Bind(R.id.point3)
    TextView point3;
    @Bind(R.id.btn)
    Button btn;

    ViewPageAdapter adapter;
    List<View> views;
    List<View> views1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void toStart() {
        initView();
        CacheUtils.getInstance().putStringCache(this,"1","isInstall");
    }

    public void initView() {
        btn.setVisibility(View.INVISIBLE);
        views = new ArrayList<>();
        views1 = new ArrayList<>();
        views1.add(point1);
        views1.add(point2);
        views1.add(point3);
        View view1 = LayoutInflater.from(this).inflate(R.layout.imagelayout, null);
//        ImageView imageView1 = (ImageView) view1.findViewById(R.id.iv1);
//        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.guide1, imageView1, ImageLoaderOption.getOptions2());
        views.add(view1);
        View view2 = LayoutInflater.from(this).inflate(R.layout.imagelayout2, null);
//        ImageView imageView2 = (ImageView) view2.findViewById(R.id.iv2);
//        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.guide2, imageView2, ImageLoaderOption.getOptions2());
        views.add(view2);
        View view3 = LayoutInflater.from(this).inflate(R.layout.imagelayout3, null);
//        ImageView imageView3 = (ImageView) view3.findViewById(R.id.iv3);
//        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.guide3, imageView3, ImageLoaderOption.getOptions2());
        views.add(view3);
        adapter = new ViewPageAdapter(views);
        viewPager.setAdapter(adapter);
        // viewPager.setOffscreenPageLimit(3);
        point1.getBackground().setAlpha(100);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < views.size(); i++) {
                    if (i == position) {
                        views1.get(position).getBackground().setAlpha(100);
                    } else {
                        views1.get(i).getBackground().setAlpha(255);
                    }
                }

                if (position == 2) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.btn)
    public void click() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SystemApplation.getInstance().exit(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

}

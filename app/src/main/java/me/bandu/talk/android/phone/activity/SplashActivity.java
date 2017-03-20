package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.Bind;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.VersionBean;
import me.bandu.talk.android.phone.utils.CacheUtils;
import me.bandu.talk.android.phone.utils.ImageLoaderOption;
import me.bandu.talk.android.phone.utils.Utils;
/**
 * 创建者：tg
 * 类描述：启动页
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SplashActivity extends BaseAppCompatActivity {

    @Bind(R.id.image)
    ImageView imageView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void toStart() {
        //new VersionMiddle(this,this).getNewVersion(Utils.getVerCode(this),new VersionBean());
        initView();
    }

    public void initView() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        imageView.setAnimation(animation);
        ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.welcome, imageView, ImageLoaderOption.getOptions2());
        animation.startNow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    String isInstall = CacheUtils.getInstance().getStringCache(SplashActivity.this, "isInstall");
                    boolean hasUserInfo = new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user", "user.data").exists();
                    if ("1".equals(isInstall)||hasUserInfo) {
                        startActivity(new Intent(SplashActivity.this, MainActivity1.class));
                        SplashActivity.this.finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        SplashActivity.this.finish();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        VersionBean bean = (VersionBean) result;
        if (bean != null && bean.getStatus() == 1) {
            if (bean.getData().getVersion() > Utils.getVerCode(this)) {
                //有新版本
                GlobalParams.versionInfo = bean.getData();
            } else {
                GlobalParams.versionInfo = null;
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
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

/*    StringBuilder stringBuilder = new StringBuilder();
    int i;
    public StringBuilder listFile(File file){
        File[] files =	file.listFiles();//列出该目录下的所有文件及文件夹
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                stringBuilder.append(files[i].getPath()+"\n");
                System.out.println(files[i].getPath());
            }else{
                listFile(files[i]);//递归调用
            }
        }
        return  stringBuilder;
    }*/
}

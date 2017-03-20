package me.bandu.talk.android.phone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.File;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.utils.SaveBeanToFile;

/**
 * 创建者:taoge
 * 时间：2015/12/11
 * 类描述：公共主页，app入口
 * 修改人:taoge
 * 修改时间：2015/12/11
 * 修改备注：
 */
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp= getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        if (isLogin) {
         LoginBean.DataEntity info =  SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class,
                 new File(getDir("user",MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
            if (info != null) {
                info.setIs_first_login(false);
                GlobalParams.userInfo = info;
                startActivity(new Intent(this,MainActivity1.class));
/*                Intent intent = null;
                if (info.getRole() == 1) {
                    //进入老师主页
                    intent = new Intent(this, TeacherHomeActivity.class);
                } else if (info.getRole() == 2) {
                    //进入学生主页
                    intent = new Intent(this, StudentHomeActivity.class);
                }
                startActivity(intent);*/
                this.finish();
            } else {
                startActivity(new Intent(this,GuideActivity.class));
            }
        } else {
            startActivity(new Intent(this,GuideActivity.class));
        }
        finish();
    }
}

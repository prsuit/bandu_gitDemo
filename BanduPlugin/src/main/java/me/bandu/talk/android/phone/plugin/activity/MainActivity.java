package me.bandu.talk.android.phone.plugin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Mckiera on 2016-03-31.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this);
        btn.setText("插件页面");
        setContentView(btn);
    }
}

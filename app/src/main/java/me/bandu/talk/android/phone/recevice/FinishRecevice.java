package me.bandu.talk.android.phone.recevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fanyu on 2016/6/21.
 */
public class FinishRecevice extends BroadcastReceiver {
   private DoSomething doSomething;
    public FinishRecevice(DoSomething doSomething){
        this.doSomething = doSomething;
    }
    public static final String FINISH_ACTION = "finish_action";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case FINISH_ACTION:
                if(doSomething != null)
                    doSomething.doSomething();
                break;
        }
    }

    public interface DoSomething{
        void doSomething();
    }
}

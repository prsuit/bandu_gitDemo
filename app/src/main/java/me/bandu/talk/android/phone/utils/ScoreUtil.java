package me.bandu.talk.android.phone.utils;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：gaoye
 * 时间：2015/12/28 19:15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ScoreUtil {
    public static short getScore(int score){
        if (score < 55)
            return 'C';
        else if (score < 85)
            return 'B';
        else
            return 'A';
    }
}

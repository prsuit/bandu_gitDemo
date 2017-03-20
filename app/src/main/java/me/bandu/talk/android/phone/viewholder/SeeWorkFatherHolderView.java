package me.bandu.talk.android.phone.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.bandu.talk.android.phone.R;

/**
 * author by Mckiera
 * time: 2015/12/29  16:54
 * description:
 * updateTime:
 * update description:
 */
public class SeeWorkFatherHolderView {
    @Bind(R.id.tvQuizName)
    public TextView tvQuizName;
    @Bind(R.id.tvCount)
    public TextView tvCount;
    public SeeWorkFatherHolderView(View view){
        ButterKnife.bind(this, view);
    }
}

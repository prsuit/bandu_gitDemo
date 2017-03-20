package me.bandu.talk.android.phone.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class SeeWorkChildHolderView {
    @Bind(R.id.tvContent)
    public TextView tvContent;
    @Bind(R.id.tvDuration)
    public TextView tvDuration;
    @Bind(R.id.tvScore)
    public TextView tvScore;
    @Bind(R.id.ivHorn)
    public ImageView ivHorn;
    @Bind(R.id.llWorkContent)
    public LinearLayout llWorkContent;


    public SeeWorkChildHolderView(View view){
        ButterKnife.bind(this, view);
    }
}

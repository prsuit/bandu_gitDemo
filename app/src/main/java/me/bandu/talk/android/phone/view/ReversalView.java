package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.utils.Rotate3dAnimation;


/**
 * author by Mckiera
 * time: 2016-03-29  10:11
 * description:
 * updateTime:
 * update description:
 */
public class ReversalView extends RelativeLayout {
    private ImageView ivScore;
    private TextView tvScore;
    private int score;
    private View curView;

    public ReversalView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.reversalview_item, this);
        ivScore = (ImageView) findViewById(R.id.ivScore);
        tvScore = (TextView) findViewById(R.id.tvScore);
        curView = ivScore;
        tvScore.setVisibility(View.GONE);
        ivScore.setVisibility(View.VISIBLE);
    }

    public ReversalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReversalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setScore(int score) {
        this.score = score;

        if (score < 55)
            ivScore.setImageResource(R.mipmap.icon_circle_c);
        else if (score < 85)
            ivScore.setImageResource(R.mipmap.icon_circle_b);
        else
            ivScore.setImageResource(R.mipmap.icon_circle_a);

        tvScore.setText(score + "分");


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curView instanceof ImageView) {
                    imageStart();
                    curView = tvScore;
                } else {
                    textStart();
                    curView = ivScore;
                }
            }
        });
    }

    public void imageStart() {
        // 获取布局的中心点位置，作为旋转的中心点
        float centerX = this.getWidth() / 2f;
        float centerY = this.getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为0到90度，这使得ListView将会从可见变为不可见
        final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90, centerX, centerY,
                310.0f, true);
        // 动画持续时间500毫秒
        rotation.setDuration(100);
        // 动画完成后保持完成的状态
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rotation.setAnimationListener(new TurnToImageView());
        this.startAnimation(rotation);
    }

    public void textStart() {
        // 获取布局的中心点位置，作为旋转的中心点
        float cX = this.getWidth() / 2f;
        float cY = this.getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
        final Rotate3dAnimation rot = new Rotate3dAnimation(360, 270, cX, cY, 310.0f, true);
        // 动画持续时间500毫秒
        rot.setDuration(100);
        // 动画完成后保持完成的状态
        rot.setFillAfter(true);
        rot.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rot.setAnimationListener(new TurnToListView());
        this.startAnimation(rot);
    }

    /**
     * 注册在ListView点击动画中的动画监听器，用于完成ListView的后续动画。
     *
     * @author guolin
     */
    class TurnToImageView implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        /**
         * 当ListView的动画完成后，还需要再启动ImageView的动画，让ImageView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            end();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        public void end() {
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = ReversalView.this.getWidth() / 2f;
            float centerY = ReversalView.this.getHeight() / 2f;
            // 将ListView隐藏
            ivScore.setVisibility(View.GONE);
            // 将ImageView显示
            tvScore.setVisibility(View.VISIBLE);
            tvScore.requestFocus();
            // 构建3D旋转动画对象，旋转角度为270到360度，这使得ImageView将会从不可见变为可见
            final Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            ReversalView.this.startAnimation(rotation);
        }

    }

    /**
     * 注册在ImageView点击动画中的动画监听器，用于完成ImageView的后续动画。
     *
     * @author guolin
     */
    class TurnToListView implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 当ImageView的动画完成后，还需要再启动ListView的动画，让ListView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            end();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        private void end() {
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = ReversalView.this.getWidth() / 2f;
            float centerY = ReversalView.this.getHeight() / 2f;
            // 将ImageView隐藏
            tvScore.setVisibility(View.GONE);
            // 将ListView显示
            ivScore.setVisibility(View.VISIBLE);
            ivScore.requestFocus();
            // 构建3D旋转动画对象，旋转角度为90到0度，这使得ListView将会从不可见变为可见，从而回到原点
            final Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            ReversalView.this.startAnimation(rotation);
        }

    }

}

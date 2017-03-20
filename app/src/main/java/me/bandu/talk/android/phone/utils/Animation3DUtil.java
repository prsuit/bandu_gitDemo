package me.bandu.talk.android.phone.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/12/11
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Animation3DUtil {
    private Animation3DUtil() {
    }

    static Animation3DUtil animation3DUtil;

    public static Animation3DUtil getInstance() {
        if (animation3DUtil == null) {
            animation3DUtil = new Animation3DUtil();
        }
        return animation3DUtil;
    }

    private ViewGroup layout;
    private View imageView;
    private View textView;

    private List<Integer> positions;
    private List<View> imageViews;
    private List<View> textViews;
    private List<ViewGroup> layouts;

    public void setView(ViewGroup layout, View first, View seconde, int position) {
        if (layout!=null&&first!=null&&seconde!=null){
            if (layouts == null)
                layouts = new ArrayList<>();
            if (position<layouts.size()){
                layouts.set(position,layout);
            }else{
                layouts.add(position, layout);
            }

            if (imageViews == null)
                imageViews = new ArrayList<>();
            if (position<imageViews.size()){
                imageViews.set(position,first);
            }else{
                imageViews.add(position, first);
            }

            if (textViews == null)
                textViews = new ArrayList<>();
            if (position<textViews.size()){
                textViews.set(position,seconde);
            }else if (textViews!=null){
                textViews.add(position, seconde);
            }

            if (positions == null)
                positions = new ArrayList<>();
            if (position<positions.size()){
                positions.set(position,position);
            }else {
                positions.add(position,position);
            }
        }
    }

    public void clearList(){
        if(layouts != null)
            layouts.clear();
        if(imageViews != null)
            imageViews.clear();
        if(textViews != null)
            textViews.clear();
        if(positions != null)
            positions.clear();
    }

    public void setView(ViewGroup layout, View first, View seconde) {
        this.layout = layout;
        imageView = first;
        textView = seconde;
    }

    public void imageStart() {
        // 获取布局的中心点位置，作为旋转的中心点
        float centerX = layout.getWidth() / 2f;
        float centerY = layout.getHeight() / 2f;
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
        layout.startAnimation(rotation);
    }
    public void imageStart(int position) {
        // 获取布局的中心点位置，作为旋转的中心点
        float centerX = layouts.get(position).getWidth() / 2f;
        float centerY = layouts.get(position).getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为0到90度，这使得ListView将会从可见变为不可见
        final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90, centerX, centerY,
                310.0f, true);
        // 动画持续时间500毫秒
        rotation.setDuration(100);
        // 动画完成后保持完成的状态
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rotation.setAnimationListener(new TurnToImageView(position));
        layouts.get(position).startAnimation(rotation);
    }

    public void textStart() {
        // 获取布局的中心点位置，作为旋转的中心点
        float cX = layout.getWidth() / 2f;
        float cY = layout.getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
        final Rotate3dAnimation rot = new Rotate3dAnimation(360, 270, cX, cY, 310.0f, true);
        // 动画持续时间500毫秒
        rot.setDuration(100);
        // 动画完成后保持完成的状态
        rot.setFillAfter(true);
        rot.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rot.setAnimationListener(new TurnToListView());
        layout.startAnimation(rot);
    }
    public void textStart(int position) {
        // 获取布局的中心点位置，作为旋转的中心点
        float cX = layouts.get(position).getWidth() / 2f;
        float cY = layouts.get(position).getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
        final Rotate3dAnimation rot = new Rotate3dAnimation(360, 270, cX,
                cY, 310.0f, true);
        // 动画持续时间500毫秒
        rot.setDuration(100);
        // 动画完成后保持完成的状态
        rot.setFillAfter(true);
        rot.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rot.setAnimationListener(new TurnToListView(position));
        layouts.get(position).startAnimation(rot);
    }

    public void imageStart2() {
        // 获取布局的中心点位置，作为旋转的中心点
        float centerX = layout.getMeasuredWidth() / 2f;
        float centerY = layout.getMeasuredHeight() / 2f;
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
        layout.startAnimation(rotation);
    }

    public void textStart2() {
        // 获取布局的中心点位置，作为旋转的中心点
        float cX = layout.getMeasuredWidth() / 2f;
        float cY = layout.getMeasuredHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的
        final Rotate3dAnimation rot = new Rotate3dAnimation(360, 270, cX,
                cY, 310.0f, true);
        // 动画持续时间500毫秒
        rot.setDuration(100);
        // 动画完成后保持完成的状态
        rot.setFillAfter(true);
        rot.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rot.setAnimationListener(new TurnToListView());
        layout.startAnimation(rot);
    }

    /**
     * 注册在ListView点击动画中的动画监听器，用于完成ListView的后续动画。
     *
     * @author guolin
     */
    class TurnToImageView implements Animation.AnimationListener {
        private int position;
        public TurnToImageView (){
            position = -1;
        }
        public TurnToImageView (int position){
            this.position = position;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        /**
         * 当ListView的动画完成后，还需要再启动ImageView的动画，让ImageView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            if(position == -1){
                end();
            }else
                ends();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        public void end(){
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = layout.getWidth() / 2f;
            float centerY = layout.getHeight() / 2f;
            // 将ListView隐藏
            imageView.setVisibility(View.GONE);
            // 将ImageView显示
            textView.setVisibility(View.VISIBLE);
            textView.requestFocus();
            // 构建3D旋转动画对象，旋转角度为270到360度，这使得ImageView将会从不可见变为可见
            final Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layout.startAnimation(rotation);
        }
        public void ends(){
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = layouts.get(this.position).getWidth() / 2f;
            float centerY = layouts.get(this.position).getHeight() / 2f;
            // 将ListView隐藏
            imageViews.get(position).setVisibility(View.INVISIBLE);
            // 将ImageView显示
            textViews.get(position).setVisibility(View.VISIBLE);
            textViews.get(position).requestFocus();
            // 构建3D旋转动画对象，旋转角度为270到360度，这使得ImageView将会从不可见变为可见
            final Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layouts.get(position).startAnimation(rotation);
        }

    }

    /**
     * 注册在ImageView点击动画中的动画监听器，用于完成ImageView的后续动画。
     *
     * @author guolin
     */
    class TurnToListView implements Animation.AnimationListener {

       private int position;
       public TurnToListView(){
           this.position = -1;
       }
       public TurnToListView(int position){
           this.position = position;
       }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 当ImageView的动画完成后，还需要再启动ListView的动画，让ListView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            if(position == -1)
                end();
            else
                ends();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        private void end(){
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = layout.getWidth() / 2f;
            float centerY = layout.getHeight() / 2f;
            // 将ImageView隐藏
            textView.setVisibility(View.GONE);
            // 将ListView显示
            imageView.setVisibility(View.VISIBLE);
            imageView.requestFocus();
            // 构建3D旋转动画对象，旋转角度为90到0度，这使得ListView将会从不可见变为可见，从而回到原点
            final Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layout.startAnimation(rotation);
        }
        private void ends(){
            // 获取布局的中心点位置，作为旋转的中心点
            float centerX = layouts.get(position).getWidth() / 2f;
            float centerY = layouts.get(position).getHeight() / 2f;
            // 将ImageView隐藏
            textViews.get(position).setVisibility(View.INVISIBLE);
            // 将ListView显示
            imageViews.get(position).setVisibility(View.VISIBLE);
            imageViews.get(position).requestFocus();
            // 构建3D旋转动画对象，旋转角度为90到0度，这使得ListView将会从不可见变为可见，从而回到原点
            final Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
                    310.0f, false);
            // 动画持续时间500毫秒
            rotation.setDuration(100);
            // 动画完成后保持完成的状态
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            layouts.get(position).startAnimation(rotation);
        }

    }


}

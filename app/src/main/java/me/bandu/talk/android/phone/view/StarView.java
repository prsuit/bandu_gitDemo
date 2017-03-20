package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.bandu.talk.android.phone.R;
/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:49
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StarView extends RelativeLayout {
	AttributeSet attrs;
	Context context;
	LayoutInflater inflater;
	ImageView iv1,iv2,iv3,iv4,iv5;
	int num;

	public StarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.attrs = attrs;
		this.inflater = LayoutInflater.from(context);
		init();
	}

	public StarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StarView(Context context) {
		this(context, null);
	}

	private void init() {
		View child = inflater.inflate(R.layout.layout_star, null);
		this.addView(child);
		iv1 = (ImageView) child.findViewById(R.id.iv1);
		iv2 = (ImageView) child.findViewById(R.id.iv2);
		iv3 = (ImageView) child.findViewById(R.id.iv3);
		iv4 = (ImageView) child.findViewById(R.id.iv4);
		iv5 = (ImageView) child.findViewById(R.id.iv5);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.star_view);
		int num = a.getInt(R.styleable.star_view_star_number, 0);
		a.recycle();
		
		setNumber(num);
		
	}

	public void setNumber(int num) {
		this.num = num;
		seletcallnoselect();
		switch(num){
		case 5:
			iv5.setImageResource(R.mipmap.star_select);
		case 4:
			iv4.setImageResource(R.mipmap.star_select);
		case 3:
			iv3.setImageResource(R.mipmap.star_select);
		case 2:
			iv2.setImageResource(R.mipmap.star_select);
		case 1:
			iv1.setImageResource(R.mipmap.star_select);
		case 0:
			break;
		}
	}

	private void seletcallnoselect() {
		iv5.setImageResource(R.mipmap.star_noselect);
		iv4.setImageResource(R.mipmap.star_noselect);
		iv3.setImageResource(R.mipmap.star_noselect);
		iv2.setImageResource(R.mipmap.star_noselect);
		iv1.setImageResource(R.mipmap.star_noselect);
	}

	public int getNumber(){
		return num;
	}

}

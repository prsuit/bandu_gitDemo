package me.bandu.talk.android.phone.sortlistview;

import java.util.Comparator;

import me.bandu.talk.android.phone.bean.SchoolBean;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator1 implements Comparator<SchoolBean.DataEntity.ListEntity> {


	public int compare(SchoolBean.DataEntity.ListEntity o1, SchoolBean.DataEntity.ListEntity o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}
}

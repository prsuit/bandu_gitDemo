package com.chivox.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class AIRecorderStatics {
	@JSONField(name = "char")
	private String charStr;
	private int count;
	private int score;
	public String getCharStr() {
		return charStr;
	}
	public void setCharStr(String charStr) {
		this.charStr = charStr;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}

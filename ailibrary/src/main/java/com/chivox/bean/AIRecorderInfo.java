package com.chivox.bean;

public class AIRecorderInfo {
	private double clip;
	private double snr;
	private int tipId;
	private int trunc;
	private int volume;
	private String tips;

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public double getClip() {
		return clip;
	}

	public void setClip(double clip) {
		this.clip = clip;
	}

	public double getSnr() {
		return snr;
	}

	public void setSnr(double snr) {
		this.snr = snr;
	}

	public int getTipId() {
		return tipId;
	}

	public void setTipId(int tipId) {
		this.tipId = tipId;
	}

	public int getTrunc() {
		return trunc;
	}

	public void setTrunc(int trunc) {
		this.trunc = trunc;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}

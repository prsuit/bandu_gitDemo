package com.chivox.bean;

import com.DFHT.base.AIRecorderDetails;

import java.util.List;

public class AIRecorderResultLocal {
	private int accuracy;
	private long delaytime;
	private int forceout;
	private int integrity;
	private int overall;
	private long pretime;
	private int pron;
	private int rank;
	private long systime;
	private int textmode;
	private int usehookw;
	private int useref;
	private long wavetime;
	private String res;
	private String version;
	private List<AIRecorderDetails> details;
	private List<AIRecorderStatics> statics;
	private AIRecorderFluency fluency;
	private AIRecorderInfo info;
	private AIRecorderRhythm rhythm;
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public long getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(long delaytime) {
		this.delaytime = delaytime;
	}
	public int getForceout() {
		return forceout;
	}
	public void setForceout(int forceout) {
		this.forceout = forceout;
	}
	public int getIntegrity() {
		return integrity;
	}
	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}
	public int getOverall() {
		return overall;
	}
	public void setOverall(int overall) {
		this.overall = overall;
	}
	public long getPretime() {
		return pretime;
	}
	public void setPretime(long pretime) {
		this.pretime = pretime;
	}
	public int getPron() {
		return pron;
	}
	public void setPron(int pron) {
		this.pron = pron;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getSystime() {
		return systime;
	}
	public void setSystime(long systime) {
		this.systime = systime;
	}
	public int getTextmode() {
		return textmode;
	}
	public void setTextmode(int textmode) {
		this.textmode = textmode;
	}
	public int getUsehookw() {
		return usehookw;
	}
	public void setUsehookw(int usehookw) {
		this.usehookw = usehookw;
	}
	public int getUseref() {
		return useref;
	}
	public void setUseref(int useref) {
		this.useref = useref;
	}
	public long getWavetime() {
		return wavetime;
	}
	public void setWavetime(long wavetime) {
		this.wavetime = wavetime;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<AIRecorderDetails> getDetails() {
		return details;
	}
	public void setDetails(List<AIRecorderDetails> details) {
		this.details = details;
	}
	public List<AIRecorderStatics> getStatics() {
		return statics;
	}
	public void setStatics(List<AIRecorderStatics> statics) {
		this.statics = statics;
	}
	public AIRecorderFluency getFluency() {
		return fluency;
	}
	public void setFluency(AIRecorderFluency fluency) {
		this.fluency = fluency;
	}
	public AIRecorderInfo getInfo() {
		return info;
	}
	public void setInfo(AIRecorderInfo info) {
		this.info = info;
	}
	public AIRecorderRhythm getRhythm() {
		return rhythm;
	}
	public void setRhythm(AIRecorderRhythm rhythm) {
		this.rhythm = rhythm;
	}
	
}

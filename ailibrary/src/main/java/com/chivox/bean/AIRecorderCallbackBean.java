package com.chivox.bean;



public class AIRecorderCallbackBean {
	private String applicationId;
	private String recordId;
	private String audioUrl;
	private int eof;
	private AIRecorderResult result;
	
	public String getAudioUrl() {
		return audioUrl;
	}
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public int getEof() {
		return eof;
	}
	public void setEof(int eof) {
		this.eof = eof;
	}
	public AIRecorderResult getResult() {
		return result;
	}
	public void setResult(AIRecorderResult result) {
		this.result = result;
	}
	
}

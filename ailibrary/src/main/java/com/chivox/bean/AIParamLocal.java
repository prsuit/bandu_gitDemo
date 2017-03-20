package com.chivox.bean;


import com.chivox.ChivoxConstants;

public class AIParamLocal {
	private AIApp app = new AIApp();
	private AIAudio audio = new AIAudio();
	private AIRequest request = new AIRequest();
	private String coreProvideType = "native";
	private String serialNumber;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public AIApp getApp() {
		return app;
	}

	public void setApp(AIApp app) {
		this.app = app;
	}

	public AIAudio getAudio() {
		return audio;
	}

	public void setAudio(AIAudio audio) {
		this.audio = audio;
	}

	public AIRequest getRequest() {
		return request;
	}

	public void setRequest(AIRequest request) {
		this.request = request;
	}

	public String getCoreProvideType() {
		return coreProvideType;
	}

	public void setCoreProvideType(String coreProvideType) {
		this.coreProvideType = coreProvideType;
	}


	public class AIApp{
		private String userId;

		public String getUserId() {
			return userId == null ? ChivoxConstants.userId : userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
	
	public class AIAudio{
		private String audioType;
		private int channel = 1;
		private int sampleBytes = 2;
		private int sampleRate = 16000;
		public String getAudioType() {
			return audioType == null ? "wav" : audioType;
		}
		public void setAudioType(String audioType) {
			this.audioType = audioType;
		}
		public int getChannel() {
			return channel ;
		}
		public int getSampleBytes() {
			return sampleBytes;
		}
		public void setSampleBytes(int sampleBytes) {
			this.sampleBytes = sampleBytes;
		}
		public int getSampleRate() {
			return sampleRate;
		}
		public void setSampleRate(int sampleRate) {
			this.sampleRate = sampleRate;
		}
	}
	
	public class AIRequest{
		private String coreType;
		private int rank = 100;
		private String refText;
		private int robust = 0;

		public int getRobust() {
			return robust;
		}

		public void setRobust(int robust) {
			this.robust = robust;
		}

		public String getCoreType() {
			return coreType == null ? "en.sent.child" : coreType;
		}
		public void setCoreType(String coreType) {
			this.coreType = coreType;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public String getRefText() {
			return refText;
		}
		public void setRefText(String refText) {
			this.refText = refText;
		}
	}
}

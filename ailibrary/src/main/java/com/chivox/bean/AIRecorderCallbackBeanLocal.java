package com.chivox.bean;



public class AIRecorderCallbackBeanLocal {
	private String tokenId;
	private String version;
	private int eof;
	private AIRecorderResultLocal result;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getEof() {
		return eof;
	}

	public void setEof(int eof) {
		this.eof = eof;
	}

	public AIRecorderResultLocal getResult() {
		return result;
	}

	public void setResult(AIRecorderResultLocal result) {
		this.result = result;
	}
}

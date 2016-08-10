package com.lp.finance.platform.rest;

public class Echo {

	int echoId;
	String echoRes;
	
	public int getEchoId() {
		return echoId;
	}
	public void setEchoId(int echoId) {
		this.echoId = echoId;
	}
	public String getEchoRes() {
		return echoRes;
	}
	public void setEchoRes(String echoRes) {
		this.echoRes = echoRes;
	}
	@Override
	public String toString() {
		return "Echo [echoId=" + echoId + ", echoRes=" + echoRes + "]";
	}
	
}

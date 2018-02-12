package com.waspring.framework.antenna.monitor.model;

public class VisitorDetail {

	private String visitorId;
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	private long stayTime;//

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public long getStayTime() {
		return stayTime;
	}

	public void setStayTime(long stayTime) {
		this.stayTime = stayTime;
	}

}

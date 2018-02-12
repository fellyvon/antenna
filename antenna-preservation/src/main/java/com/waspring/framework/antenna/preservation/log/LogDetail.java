package com.waspring.framework.antenna.preservation.log;

/**
 * 日志详情
 * 
 * @author felly
 *
 */
public class LogDetail implements java.io.Serializable {

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean getIsServer() {
		return isServer;
	}

	public void setIsServer(boolean isServer) {
		this.isServer = isServer;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public long getRequetTimestamp() {
		return requetTimestamp;
	}

	public void setRequetTimestamp(long requetTimestamp) {
		this.requetTimestamp = requetTimestamp;
	}

	public long getResponseTimestamp() {
		return responseTimestamp;
	}

	public void setResponseTimestamp(long responseTimestamp) {
		this.responseTimestamp = responseTimestamp;
	}
 

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getReponseContent() {
		return reponseContent;
	}

	public void setReponseContent(String reponseContent) {
		this.reponseContent = reponseContent;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private boolean isServer;
	private String containerId, action, requestId;
	private long requetTimestamp, responseTimestamp;
	private String requestContent;
	private String reponseContent;
	private int code;

}

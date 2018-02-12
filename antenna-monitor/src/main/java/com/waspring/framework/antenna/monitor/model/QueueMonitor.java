package com.waspring.framework.antenna.monitor.model;

public class QueueMonitor {

	private String queueId;
	private int cap;
	private String executor;
	private String hander;
	private int blockNum;

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getHander() {
		return hander;
	}

	public void setHander(String hander) {
		this.hander = hander;
	}

	public int getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}
}

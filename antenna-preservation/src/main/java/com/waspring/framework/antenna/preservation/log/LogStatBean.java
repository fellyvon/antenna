package com.waspring.framework.antenna.preservation.log;

/**
 * 日志统计单条记录实体
 * 
 * @author felly
 *
 */
public class LogStatBean implements java.io.Serializable {

	private String containerId;
	private String action;

	private int year;
	private int month;
	private int day;
	private int times;/// 调用次数
	private long maxExecuteTime;// 最大执行时长
	private long lastTimestamp;/// 最后调用时间

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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public long getMaxExecuteTime() {
		return maxExecuteTime;
	}

	public void setMaxExecuteTime(long maxExecuteTime) {
		this.maxExecuteTime = maxExecuteTime;
	}

	public long getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

}

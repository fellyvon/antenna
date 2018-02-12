package com.waspring.framework.antenna.monitor.model;

/**
 * 进程监控页面数据bean定义
 * @author felly
 *
 */
public class ProcessMonitor {
   private String pid;
   private int cap;
   private int visitorNum;
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public int getCap() {
	return cap;
}
public void setCap(int cap) {
	this.cap = cap;
}
public int getVisitorNum() {
	return visitorNum;
}
public void setVisitorNum(int visitorNum) {
	this.visitorNum = visitorNum;
}
   
 
}

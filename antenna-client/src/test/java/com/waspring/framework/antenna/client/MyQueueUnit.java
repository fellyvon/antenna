package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

public class MyQueueUnit   implements  IQueueUnit{
	
	@Override
	public IQueueUnit getQueueUnit(String mode)   {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IQueueUnit linkQueueUnit(IQueueUnit next) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMode() {
		 
		return FILE;
	}
	@Override
	public String getPersistentCommand() {
		 return toString();
	}
	public   MyQueueUnit(String xx) {
		setNn(xx);
	}
	private String nn;
	public String getNn() {
		return nn;
	}
	public void setNn(String nn) {
		this.nn = nn;
	}
	
	public String toString() {
		return nn;
	}
}

package com.waspring.framework.antenna.preservation.queue;

/**
 * 队列异常定义
 * 
 * @author felly
 * @date 2018-02-01
 * 
 */
public class QueueException extends Exception {
	public QueueException(String str) {
		super(str);
	}

	public QueueException(Exception e) {
		super(e);
	}

}

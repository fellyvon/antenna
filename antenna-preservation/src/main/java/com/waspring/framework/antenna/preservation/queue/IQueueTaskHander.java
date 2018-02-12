package com.waspring.framework.antenna.preservation.queue;

import com.waspring.framework.antenna.preservation.log.IQueryLog;

/**
 * 任务的具体处理,一般都是交给客户端来实现;
 * 
 * @author felly
 *
 */
public interface IQueueTaskHander<T extends IQueueBean> {
	
	/**
	 * 业务处理实现
	 * @param bean
	 */
	void handle(T bean);
	
	
	IQueryLog getQueryLog();
}

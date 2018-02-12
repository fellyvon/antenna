package com.waspring.framework.antenna.preservation.queue.executor;

import java.util.Queue;

import com.waspring.framework.antenna.preservation.queue.IQueueTaskHander;

/**
 * 队列执行器
 * @author felly
 * @date 2018-02-01
 * 
 */
public interface IQueueExecutor<T extends Queue> extends Runnable {
	// 处理
	void execute() throws Exception;

	T getQueue();
	
	void bindQueue(T queue);
	
	void bindTaskHander(IQueueTaskHander hander);
	
	IQueueTaskHander getTaskHander();
	
	void setRunnable(boolean runnable);
}

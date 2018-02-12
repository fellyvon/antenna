package com.waspring.framework.antenna.preservation.queue.executor;

import com.waspring.framework.antenna.config.parse.IConfigureQueue;

/**
 * 隊列執行器管理
 * 
 * @author felly
 *
 */
public interface IQueueExecutorManager {

	IQueueExecutor applyQueueExecutor(IConfigureQueue queueConfig);
	IQueueExecutor [] getQueueExecutors();
	IQueueExecutor getQueueExecutor(String id);
}

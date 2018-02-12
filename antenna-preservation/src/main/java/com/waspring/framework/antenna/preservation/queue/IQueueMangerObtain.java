package com.waspring.framework.antenna.preservation.queue;

import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutorManager;

public interface IQueueMangerObtain {

	
	
	IQueueExecutorManager getQueueExecutorManager();
	
	IConfigureQueueManager getConfigureQueueManager();
}

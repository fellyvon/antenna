package com.waspring.framework.antenna.access;

import com.waspring.framework.antenna.preservation.queue.IQueueManger;

public interface IQueueManagerFactory {
	
	    String QUEUE_MANAGER_CACHE_NAME="_QUEUE_MANAGER_CACHE_NAME_";
	  
	  IQueueManger getQueueManger();
}

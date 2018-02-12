package com.waspring.framework.antenna.preservation.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;

/**
 * 阻塞队列管理器
 * 
 * @author felly
 * 
 *
 */
public class BlockQueueManager extends AbstractQueueManger {

	@SuppressWarnings("rawtypes")
	@Override
	public Queue createQuque(int cap) {
		return new ArrayBlockingQueue(cap);
	}

	public BlockQueueManager(IConfigureQueueManager queueManagerConfig) {
		super(queueManagerConfig);
	}
}

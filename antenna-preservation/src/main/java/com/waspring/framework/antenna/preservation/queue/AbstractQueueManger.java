package com.waspring.framework.antenna.preservation.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutor;
import com.waspring.framework.antenna.preservation.queue.executor.IQueueExecutorManager;
import com.waspring.framework.antenna.preservation.queue.executor.QueueExecutorManagerImpl;

/**
 * 队列管理器抽象实现
 * 
 * @author felly
 *
 */
public abstract class AbstractQueueManger implements IQueueManger, IQueueMangerObtain {
	@Override
	public IQueueExecutorManager getQueueExecutorManager() {

		return queueExecutorManager;
	}

	@Override
	public IConfigureQueueManager getConfigureQueueManager() {
		return queueManagerConfig;
	}

	@Override
	public boolean isRunning() {
		return runing;
	}

	private boolean runing = false;
	//// 线程池Runtime.getRuntime().availableProcessors()*2
	private ExecutorService pool = null;

	private Lock lock = new ReentrantLock(false);

	@Override
	public IQueueManger run() {
		if (runing) {
			System.out.println("队列管理器已经开始工作！");
			return this;
		}
		lock.lock();/// 防止被多线程调用并发
		try {
			if (pool == null || pool.isShutdown()) {
				pool = Executors.newCachedThreadPool();
			}
		} catch (Exception e) {
              
		} finally {
			lock.unlock();
		}

		runing = true;
		System.out.println(this+"队列管理器开始工作....");
		return this;
	}

	/**
	 * 
	 */
	@Override
	public IQueueManger stop(boolean force) {
		if (!runing) {
			System.out.println("队列管理器已经停止工作!");
			return this;
		}
		IQueueExecutor queueExecutors[] = queueExecutorManager.getQueueExecutors();
		for (IQueueExecutor queueExecutor : queueExecutors) {
			queueExecutor.setRunnable(false);
		}

		if (force) {
			pool.shutdownNow();
		} else {
			pool.shutdown();
		}
		runing = false;
		System.out.println("队列管理器被停止.");
		return this;
	}

	private IConfigureQueueManager queueManagerConfig;

	public AbstractQueueManger(IConfigureQueueManager queueManagerConfig) {
		this.queueManagerConfig = queueManagerConfig;
	}

	private IQueueExecutorManager queueExecutorManager = new QueueExecutorManagerImpl(this);

	@Override
	public int getMaxQueueNum() {
		if (queueManagerConfig == null) {
			return IQueueManger.MAX_QUEUE_NUM;
		}
		return queueManagerConfig.getMaxQueue();
	}

	private Map<String, Queue> queues = new HashMap<String, Queue>();

	@Override
	public Queue applyQueue(String name) {
		Queue que = queues.get(name);
		if (que != null) {
			return que;
		}
		if (getMaxQueueNum() < queues.size()) {
			throw new RuntimeException("queue is full!");
		}
		int cap = IQueueManger.MAX_DATA_NUM;
		IConfigureQueue queueConfig = queueManagerConfig.getIConfigureQuque(name);
		if (queueConfig != null) {
			cap = queueConfig.getMaxIdle();
			if (cap < 1) {
				cap = IQueueManger.MAX_DATA_NUM;
			}
		}
		Queue queue = createQuque(cap);
		initQueue(name, queue, queueConfig);

		return queue;
	}

	public void initQueue(String name, Queue queue, IConfigureQueue queueConfig) {
		queues.put(name, queue);
		IQueueExecutor executor = queueExecutorManager.applyQueueExecutor(queueConfig);
		executor.bindQueue(queue);
		pool.execute(executor);
	}

	/**
	 * 具體的由客戶端來實現
	 * 
	 * @param cap
	 * @return
	 */
	public abstract Queue createQuque(int cap);

	@Override
	public Queue[] getQueues() {
		Queue[] queueArray = new Queue[queues.size()];
		return queues.values().toArray(queueArray);
	}

}

package com.waspring.framework.antenna.preservation.queue.executor;

import java.util.Queue;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.preservation.queue.IQueueTaskHander;

public abstract class AbstractQueueExecutor<T extends Queue> implements IQueueExecutor<T> {
	private Logger log = Logger.getLogger(getClass());
	private T queue;
	private boolean runnable = true;
	private boolean blockable = false;
	private long executorFrequency = 5000;

	public AbstractQueueExecutor(boolean blockable, long executorFrequency) {
		this.blockable = blockable;
		if (executorFrequency > 0) {
			this.executorFrequency = executorFrequency;
		}

	}

	@Override
	public void run() {
		while (runnable) {
			try {
				execute();
			} catch (Exception e) {
				LogUtil.error(log, e);
			} finally {
				// 非柱塞隊列採用輪訓
				if (blockable) {
					try {
						Thread.sleep(executorFrequency);
					} catch (InterruptedException e) {

					}
				}
			}

		}

	}

	@Override
	public abstract void execute() throws Exception;

	@Override
	public T getQueue() {

		return queue;
	}

	@Override
	public void bindQueue(T queue) {
		this.queue = queue;

	}

	private IQueueTaskHander hander;

	@Override
	public void bindTaskHander(IQueueTaskHander hander) {
		this.hander = hander;

	}

	@Override
	public IQueueTaskHander getTaskHander() {
		return hander;
	}

	@Override
	public void setRunnable(boolean runnable) {
		this.runnable = runnable;

	}

}

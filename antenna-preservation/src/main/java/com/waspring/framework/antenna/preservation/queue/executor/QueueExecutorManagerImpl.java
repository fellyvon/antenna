package com.waspring.framework.antenna.preservation.queue.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.core.util.ReflectUtil;
import com.waspring.framework.antenna.preservation.log.FileTaskHander;
import com.waspring.framework.antenna.preservation.queue.IQueueManger;
import com.waspring.framework.antenna.preservation.queue.IQueueTaskHander;
import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

/**
 * 队列执行器实现
 * 
 * @author felly
 *
 */
public class QueueExecutorManagerImpl implements IQueueExecutorManager {
	@Override
	public IQueueExecutor getQueueExecutor(String id) {
	 return      queueExecutors.get(id);
	}

	private Logger log = Logger.getLogger(getClass());
	private IQueueManger queueManager = null;

	public QueueExecutorManagerImpl(IQueueManger queueManager) {
		this.queueManager = queueManager;
	}

	private Map<String, IQueueExecutor> queueExecutors = new HashMap<String, IQueueExecutor>();

	@Override
	public IQueueExecutor[] getQueueExecutors() {
		IQueueExecutor[] queueExecutorArray = new IQueueExecutor[queueExecutors.size()];
		return queueExecutors.values().toArray(queueExecutorArray);
	}

	@Override
	public IQueueExecutor applyQueueExecutor(IConfigureQueue queueConfig) {
		if (queueConfig == null) {
			throw new RuntimeException("queue config is null!");
		}
		IQueueExecutor executor = queueExecutors.get(queueConfig.getId());
		if (executor != null) {
			return executor;
		}

		String executorClass = queueConfig.getExecutor();

		if (StringUtils.isEmpty(executorClass)) {
			executor = getQueueExecutor(queueConfig);
		} else {
			executor = ReflectUtil.<IQueueExecutor>constructorNewInstance(executorClass, new Class[] {},
					new Object[] {});

		}

		if (executor.getTaskHander() == null) {
			IQueueTaskHander hander = null;
			String handerClass = queueConfig.getTaskHander();
			if (StringUtils.isEmpty(handerClass)) {
				hander = new FileTaskHander();
			} else {
				hander = ReflectUtil.<IQueueTaskHander>constructorNewInstance(handerClass, new Class[] {},
						new Object[] {});
			}

			executor.bindTaskHander(hander);
		}

		queueExecutors.put(queueConfig.getId(), executor);
		return executor;
	}

	/**
	 * 默認執行器,支持阻塞和非阻塞队列
	 */
	private IQueueExecutor getQueueExecutor(final IConfigureQueue queueConfig) {

		return new AbstractQueueExecutor<BlockingQueue>(
				IConfigureQueue.QUEUE_TYPE_NOBLOCK.equals(queueConfig.getQuqueType()),
				queueConfig.getExecutorFrequency()) {
			@Override
			public void execute() throws Exception {
				getTaskHander().handle((IQueueUnit) (getQueue().take()));

			}
		};

	}

}

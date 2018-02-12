package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 隊列配置
 * @author felly
 *
 */
public class ConfigureQueueImpl extends AbstractConfiure implements IConfigureQueue {
    @Override
	public long getExecutorFrequency() {
    	return Long.parseLong(getProperty(IConfigureQueue.EXECUTOR_FREQUENCY));
	}

	private   IConfigureQueueManager ququeManager=null;
	public   ConfigureQueueImpl( IConfigureQueueManager ququeManager) {
		this.ququeManager=ququeManager;
	}
	@Override
	public int  getMaxIdle() {
		
		return Integer.parseInt(getProperty(IConfigureQueue.MAXIDLE));
	}

	@Override
	public String getExecutor() {
		
		return getProperty(IConfigureQueue.EXECUTOR);
	}

	@Override
	public String getQuqueType() {
		
		return getProperty(IConfigureQueue.QUQUE_TYPE);
	}

	@Override
	public String getTaskHander() {
		
		return getProperty(IConfigureQueue.TASK_HANDER);
	}

 

	@Override
	public IConfigure getParent() {
		
		return ququeManager;
	}

	@Override
	public String getElementName() {
		
		return ConfigureEnum.Queue.getName();
	}

	@Override
	public boolean hasNext() {
		
		return false;
	}

}

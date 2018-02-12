package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 队列容器配置实现
 * @author felly
 *
 */
public class ConfigureQueueMangerImpl extends AbstractConfiure implements IConfigureQueueManager {

	@Override
	public int getMaxQueue() {
		 
		return Integer.parseInt(getProperty(MAX_NUM));
	}
	@Override
	public String getManagerclass() {
	 
		return  getProperty(MANAGER_CLASS);
	}

	private Map<String,IConfigureQueue> quques=new HashMap<String,IConfigureQueue>();
	private IConfigureApplication application=null;
	public ConfigureQueueMangerImpl(IConfigureApplication application) {
		this.application=application;
	}
	@Override
	public IConfigureQueue[] getIConfigureQuques() {
		IConfigureQueue []  ququesArray=new  IConfigureQueue[quques.size()];
		return quques.values().toArray(ququesArray);
	}

	@Override
	public void addConfigureQuque(IConfigureQueue quque) {
		if(quque==null)
		{
			return;
		}
		quques.put(quque.getId(), quque);
	}

	@Override
	public void removeConfigureQuque(IConfigureQueue quque) {
		if(quque==null)
		{
			return;
		}
		quques.remove(quque.getId());
	}

	@Override
	public IConfigureQueue getIConfigureQuque(String id) {
		
		return quques.get(id);
	}

	@Override
	public IConfigure getParent() {
		
		return application;
	}

	@Override
	public String getElementName() {
		
		return ConfigureEnum.QueueManager.getName();
	}

	@Override
	public boolean hasNext() {
		
		return !quques.isEmpty();
	}

}

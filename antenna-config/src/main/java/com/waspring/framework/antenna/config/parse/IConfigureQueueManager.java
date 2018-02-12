package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 队列管理器
 * @author felly
 *
 */
public interface IConfigureQueueManager extends IConfigure {

	IConfigureQueue[] getIConfigureQuques();

	void addConfigureQuque(IConfigureQueue quque);

	void removeConfigureQuque(IConfigureQueue quque);

	IConfigureQueue getIConfigureQuque(String id);
	
	int getMaxQueue();
	
	String getManagerclass();
	
	String MAX_NUM="max-num";
	String MANAGER_CLASS="managerclass";

}

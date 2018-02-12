package com.waspring.framework.antenna.access;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.util.ReflectUtil;
import com.waspring.framework.antenna.preservation.queue.BlockQueueManager;
import com.waspring.framework.antenna.preservation.queue.IQueueManger;

/**
 * queue管理器创建工厂
 * 
 * @author felly
 *
 */
public class QueueManagerFactory  implements IQueueManagerFactory {

	public static final String  QUEUE_MANAGE_NAME="_QUEUE_MANAGE_NAME_";
	private Logger log = Logger.getLogger(getClass());
	private IApplication application = null;
	private ICache<String, IQueueManger> cache = null;

	public QueueManagerFactory(IApplication application) throws Exception {
		this.application = application;
		cache = application.<String, IQueueManger>getCache(IQueueManagerFactory.QUEUE_MANAGER_CACHE_NAME);
	 
 	 }
	
 
	/**
	 * 防止并发产生多个队列管理器
	 */
	@Override
	public synchronized IQueueManger getQueueManger() {
		IQueueManger qm=cache.get(QueueManagerFactory.QUEUE_MANAGE_NAME);
		if(qm!=null) {
			return qm;
		}
		IConfigureQueueManager  configureQueueManager=((IConfigureApplication)application.getRootConfigure())
				.getConfigureQueueManager();
		
		String className=configureQueueManager.getManagerclass();
		if(!StringUtils.isEmpty(className)) {
			qm= ReflectUtil.<IQueueManger>constructorNewInstance(className,
					new Class[] { IConfigureQueueManager.class }, new Object[] { configureQueueManager });
			
		}
		
		if(qm==null) {
			qm=new    BlockQueueManager(configureQueueManager);
		}
		
		cache.put(QueueManagerFactory.QUEUE_MANAGE_NAME, qm);
		return qm.run();

	}

}

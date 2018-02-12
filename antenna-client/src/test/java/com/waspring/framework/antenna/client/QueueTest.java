package com.waspring.framework.antenna.client;

import java.util.Queue;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.util.LifecycleUtils;

public class QueueTest {
	public static void main(String fss[])throws Exception{
		PropertiesUtil.loadConfig("classpath://root.properties");
		IApplication application = ApplicationUtil.getApplication().setConfigFilePath("classpath://config.xml");
		LifecycleUtils.init(application);
 
		Queue queue=application.getQueue("logqueue");
		Queue queue2=application.getQueue("logqueue");
		System.out.println(queue==queue2);
		///Queue queue2=qm.applyQueue("logqueuexx");
		queue.add(new MyQueueUnit("123"));
		queue.add(new MyQueueUnit("321"));
		queue.add(new MyQueueUnit("456"));
		queue.add(new MyQueueUnit("789"));
		queue.add(new MyQueueUnit("000"));
		queue.add(new MyQueueUnit("hhh")); 
		queue.add(new MyQueueUnit("xxx"));
		queue.add(new MyQueueUnit("111"));
		 Thread.sleep(1000);
		//qm.stop(true);
		LifecycleUtils.destory(application);
		System.out.println("queue stop!");
	}
	
 
} 

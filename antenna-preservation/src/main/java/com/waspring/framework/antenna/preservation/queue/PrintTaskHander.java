package com.waspring.framework.antenna.preservation.queue;

import com.waspring.framework.antenna.preservation.log.IQueryLog;

/**
 * 一个队列处理的实现例子，这里来处理具体的持久等动作
 * @author felly
 *
 */
@SuppressWarnings("rawtypes")
public class PrintTaskHander implements IQueueTaskHander {

	@Override
	public void handle(IQueueBean bean) {
		String command=bean.getPersistentCommand();//内容
		String mode=bean.getMode();//命令格式
		
		 System.out.println("PrintTaskHander="+mode+"."+command);
	}

	@Override
	public IQueryLog getQueryLog() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}

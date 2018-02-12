package com.waspring.framework.antenna.preservation.log;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

/**
 * 抽象日志记录节点，采用责任链来处理不用的日志存放方式
 * @author felly
 *
 */
@SuppressWarnings("serial")
public abstract  class AbstractVisitorLogQueueUnit implements IQueueUnit {
	protected Logger log=Logger.getLogger(getClass());

	private   IQueueUnit next;
	@Override
	public abstract  String getMode() ;

	/**
	 * 抽象实现，便利链节点获取处理方式
	 */
	@Override
	public   IQueueUnit  getQueueUnit(String mode)  {
		mode=StringUtils.trimToEmpty(mode);
		if(mode.equals(getMode())) {
			return this;
		}
		
		if(next!=null) {
			return next.getQueueUnit(mode);
		}
		
	 return null;
	}
	
	
 
	/**
	 * 用于串联各个模式的日志节点处理对象
	 * @param next 返回下一节点
	 * @return
	 */
	public IQueueUnit linkQueueUnit(IQueueUnit next) {
		this.next=next;
		return next;
	}
	
	/**
	 * 具体客户端实现
	 */
	public abstract String getPersistentCommand() ;
 

}

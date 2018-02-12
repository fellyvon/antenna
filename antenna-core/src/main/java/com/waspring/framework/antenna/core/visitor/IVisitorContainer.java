package com.waspring.framework.antenna.core.visitor;

import java.util.Collection;
import java.util.Queue;

import com.waspring.framework.antenna.core.ILifecycle;
import com.waspring.framework.antenna.core.filter.IFilter;

/**
 * <title>访问者容器， 负责 访问者的生命周期管理</title><br/>
 * 1、 访问者在容器中存在的时间会很短暂，即一次服务的调用时间<br/>
 * 2、容器内访问者的生命周期从创建、注册进入容器、执行服务、返回（回滚）执行结果、脱离容器、销毁。<br/>
 * 3、容器提供了允许存在的最大并发访问者数量等控制。<br/>
 * 4、
 * 
 * @author felly
 *
 */
public interface IVisitorContainer extends ILifecycle {
	int ALLOW_MAX_NUM = 2000;/// 默认最大允许并发处理请求量

	/**
	 * 创建访问者
	 * 
	 * @param request
	 *            请求参数对象
	 * 
	 * @return
	 */
	IVisitorAware establishVisitor(IRequest request)  ;

	/**
	 * 释放
	 * 
	 * @param visitor
	 */
	void releaseVisitor(IVisitor  visitor) throws VisitException;

	Collection<IVisitorAware> getVisitors();//// 获取全部访问者集合\
	
	IVisitorAware getVisitor(String id);
	 
	int getAvailableMaximum();/// 获取最大可用数量

	int getAllowMaximum();/// 获取允许最大的数量

	String getId();//// 容器ID
	
	long  getTimeout();////传输认证可靠有效时长，单位ms

	IdGenerator getIdGenerator();
	////过滤器对象
	IFilter getIFilter();

	String getKey();

	String getSercret();
	
	//////获取日志读写队列
	Queue getLogQueue();

}

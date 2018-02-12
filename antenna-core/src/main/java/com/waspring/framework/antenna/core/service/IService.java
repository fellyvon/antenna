package com.waspring.framework.antenna.core.service;

import com.waspring.framework.antenna.core.ILifecycle;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 *  业务逻辑处理抽象
 * 
 * @author felly
 *
 */
public interface IService extends  ILifecycle {

	String getServiceName();/// 服务名称



	/**
	 * 服务的执行
	 * 
	 * @param action
	 *            动作
	 * @param request
	 * @throws ServiceException
	 */
	void service(IRequest request, IResponse response) throws  Exception;

}

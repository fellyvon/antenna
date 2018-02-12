package com.waspring.framework.antenna.service;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.visitor.ExceptionResponse;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 抽象服务实现
 * 
 * @author felly
 *
 */
public abstract class AbstractService implements IService {

	protected Logger log = Logger.getLogger(getClass());

 

	@Override
	public void destory() {

	}

	@Override
	public void init() throws Exception {

	}

	public abstract String getServiceName();

	@Override
	public abstract void service(IRequest request, IResponse reponse) throws  Exception;

	/**
	 * 统一的异常返回
	 * 
	 * @param code
	 * @param request
	 * @param e
	 * @return
	 */
	protected IResponse doException(final int code, IRequest request, final Exception e) {
		return new ExceptionResponse(e);
	}

}

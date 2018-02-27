package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.hander.IHander;

/**
 * 请求过程数据对象，通过该类获取当前正在处理的请求和返回结果。
 * 
 * @author felly
 *
 */
public interface IVisitorAware extends IVisitor {

	void setHander(IHander hander);

	void setRequest(IRequest request);

	void setResponse(IResponse response);

	void registerContainer(IVisitorContainer container);

	void setApplicaiton(IApplication applicaiton);

	void agency();/// 委托受理执行

	

}

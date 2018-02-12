package com.waspring.framework.antenna.access.manager;

import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IdGenerator;
import com.waspring.framework.antenna.core.visitor.VisitException;

/**
 * 处理web 服务对应的容器声明
 * 
 * @author felly
 *
 */
public abstract class AbstractWebVisitorContainer extends AbstractVisitorContainer implements IVisitorContainer {

	public AbstractWebVisitorContainer(IConfigureContainer configContainer) {
		 super(configContainer);
	}

	@Override
	public abstract long getTimeout();

	@Override
	public abstract IdGenerator getIdGenerator();

	@Override
	public abstract String getKey();

	@Override
	public abstract String getSercret();

	@Override
	public abstract String getId();

	@Override
	public abstract IHander getHander(IRequest request);

	/**
	 * 创建访问者并执行访问
	 */

	public IVisitorAware establishVisitor(javax.servlet.ServletRequest request) throws VisitException {

		IRequest normalRequest = ApplicationUtil.instanceServerRequest(getId(), request);
		return establishVisitor(normalRequest);

	}

}

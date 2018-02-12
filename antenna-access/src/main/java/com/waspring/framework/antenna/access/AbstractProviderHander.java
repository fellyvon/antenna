package com.waspring.framework.antenna.access;

import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.core.hander.IProvider;
import com.waspring.framework.antenna.core.visitor.IRequest;

/**
 * 提供者处理的抽象
 * 
 * @author felly
 * 
 *
 */
public abstract class AbstractProviderHander extends AbstractHander implements IProvider {

	public AbstractProviderHander() {

	}
	public AbstractProviderHander(IConfigureProvider configure) {
		super(configure);
	}

	@Override
	public abstract void rollback(IRequest request);

}

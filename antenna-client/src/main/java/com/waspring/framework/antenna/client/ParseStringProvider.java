package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.access.AbstractProviderHander;
import com.waspring.framework.antenna.core.visitor.IRequest;

/**
 * 服务提供-demo
 * @author felly
 *
 */
public class ParseStringProvider extends AbstractProviderHander {

	public ParseStringProvider() {
		registerService(new ParseStringService());
	}

	@Override
	public void rollback(IRequest request) {

	}

}

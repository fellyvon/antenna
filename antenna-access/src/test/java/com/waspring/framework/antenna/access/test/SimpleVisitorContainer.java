package com.waspring.framework.antenna.access.test;

import java.util.Queue;

import com.waspring.framework.antenna.access.AbstractRequest;
import com.waspring.framework.antenna.access.Application;
import com.waspring.framework.antenna.access.id.UUIDGenerator;
import com.waspring.framework.antenna.access.manager.AbstractVisitorContainer;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IdGenerator;

/**
 * 
 * @author felly
 *
 */
public class SimpleVisitorContainer extends AbstractVisitorContainer {

	@Override
	public Queue getLogQueue() {
		 return null;
	}

	@Override
	public String getId() {
		
		return null;
	}

	@Override
	public String getKey() {
		
		return null;
	}

	@Override
	public String getSercret() {
		
		return null;
	}

	@Override
	public com.waspring.framework.antenna.core.visitor.IdGenerator getIdGenerator() {

		return null;
	}

	public SimpleVisitorContainer(IConfigureContainer configureContainer) {
		super(configureContainer);
	}

	@Override
	public IHander getHander(IRequest request) {

		return new SimpleHander();
	}

	public static void main(String fsfs[]) throws Exception {

		IVisitorContainer s1 = Application.getApplication().applyContainer("1");
		IRequest request = new AbstractRequest() {

			@Override
			public IdGenerator getIdGenerator() {

				return new UUIDGenerator();
			}

			@Override
			public String getSign() {

				return "122";
			}

			@Override
			public String getIP() {

				return "192.168.1.1";
			}

			@Override
			public String getKey() {

				return "1233";
			}

			@Override
			public String getSecret() {

				return "dfsdsg2";
			}

			@Override
			public boolean isServer() {

				return true;
			}

		};
		request.addParameter("name", "fcs");
		System.out.println(request.getRequestId());
		IVisitorAware a = s1.establishVisitor(request);
		System.out.println(a.getResponse());

	}

	@Override
	public long getTimeout() {
		
		return 0;
	}

}

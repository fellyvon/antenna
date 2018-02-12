package com.waspring.framework.antenna.access.test;

import com.github.rholder.retry.BlockStrategy;
import com.waspring.framework.antenna.access.AbstractProviderHander;
import com.waspring.framework.antenna.core.hander.AcceptException;
import com.waspring.framework.antenna.core.hander.AcceptUnkownException;
import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 第一个hander
 * 
 * @author felly
 *
 */
public class SimpleHander extends AbstractProviderHander {
	private BlockStrategy s;
	public SimpleHander() {
		 registerService(new IService() {
			@Override
			public void service(IRequest request, IResponse resonse) throws AcceptException {
				String name = String.valueOf(request.getParameter("name"));

				if (name == null || "".equals(name) || "null".equals(name)) {
					throw new AcceptUnkownException("名称没传入");
				}

				System.out.println("执行成功");
			}

			@Override
			public void destory() {
				// TODO Auto-generated method stub
				// BlockStrategies.threadSleepStrategy()
			}

			@Override
			public void init() throws Exception {
				   
				
			}

			@Override
			public String getServiceName() {
				// TODO Auto-generated method stub
				return "sexy";
			}
		});

	}

	@Override
	public void rollback(IRequest request) {
		System.out.print("回滚了");

	}

}

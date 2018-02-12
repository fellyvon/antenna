package com.waspring.framework.antenna.core.visitor;

import java.util.UUID;

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

	/**
	 * 默认构造
	 * 
	 * @author felly
	 *
	 */
	public static class Builder {

		public static IVisitorAware create() {

			return new IVisitorAware() {
				private IRequest request;
				private IResponse response;
				private IHander hander;
				private IVisitorContainer container;
				private String id = "Visitor-" + UUID.randomUUID();
				private IApplication applicaiton;

				@Override
				public IApplication getApplicaiton() {
					return applicaiton;
				}

				@Override
				public void setApplicaiton(IApplication applicaiton) {
					this.applicaiton = applicaiton;
				}

				public String getId() {
					return id;
				}

				@Override
				public long getTimestamp() {

					return System.currentTimeMillis();
				}

				@Override
				public IVisitorContainer getVisitorContainer() {
					return container;
				}

				@Override
				public void registerContainer(IVisitorContainer container) {
					this.container = container;

				}

				@Override
				public void setRequest(IRequest request) {
					this.request = request;

				}

				@Override
				public void setResponse(IResponse response) {
					this.response = response;

				}

				@Override
				public IRequest getRequest() {
					return request;
				}

				@Override
				public IResponse getResponse() {
					return response;
				}

				@Override
				public IHander getHander() {
					return hander;
				}

				@Override
				public void setHander(IHander hander) {
					this.hander = hander;
				}

				@Override
				public void agency() {

					if (hander == null) {
						setResponse(new NoHanderResponse(request.getAction()));
						return;
					}

					hander.execute();
				}

			};
		}
	}

}

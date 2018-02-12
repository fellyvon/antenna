package com.waspring.framework.antenna.access.manager;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.access.Application;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.access.util.ExceptionCoverter;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureException;
import com.waspring.framework.antenna.config.parse.IConfigureExceptions;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.ThreadContext;
import com.waspring.framework.antenna.core.visitor.AbortResponse;
import com.waspring.framework.antenna.core.visitor.FailReleaseException;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.core.visitor.IVisitor;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.SimpleResponse;

/**
 * 抽象访问者容器
 * 
 * @author felly
 *
 */
public abstract class AbstractVisitorContainer implements IVisitorContainer {
	@Override
	public Queue getLogQueue() {
		 return ApplicationUtil.getApplication().getQueue(configContainer.getLogQueue());
	}

	protected Logger log = Logger.getLogger(getClass());

	private Map<String, IVisitorAware> visitors = new java.util.concurrent.ConcurrentHashMap<String, IVisitorAware>();
	private int allowMaximum = IVisitorContainer.ALLOW_MAX_NUM;
	private IConfigureContainer configContainer;

	public AbstractVisitorContainer(IConfigureContainer configContainer) {
		this.configContainer = configContainer;
	}

	public IConfigureContainer getConfigureContainer() {
		return configContainer;
	}

	@Override
	public IFilter getIFilter() {
		 
		return ApplicationUtil.getApplication().getFilter(this, configContainer.getFilter());
	}

	@Override
	public abstract String getId();

	@Override
	public void init() throws Exception {
		LogUtil.debug(log, getId() + "容器初始化开始!");
		
		//// TODO 参数等初始化在这里实现
		IConfigureContainer configureContainer = ((IConfigureApplication) ApplicationUtil.getApplication()
				.getRootConfigure()).getConfigureContainer(getId());
		allowMaximum=configureContainer.getAllowMaximum();
		// 初始化异常列表
		IConfigureExceptions configureExceptionGroup = configureContainer.getConfigureExceptions();
		if(configureExceptionGroup==null) {
			return;
		}
		IConfigureException configureExceptions[] = configureExceptionGroup.getConfigureException();
		for (IConfigureException ce : configureExceptions) {
			ExceptionCoverter.registerException(ce.getId(), Class.forName(ce.getExceptionClass()));
			LogUtil.debug(log, "容器初始化异常定义：" + ce.getId() + "!");
		}

		LogUtil.debug(log, getId() + "容器初始化结束!");
	}

	@Override
	public void releaseVisitor(IVisitor  visitor) throws FailReleaseException {
		LogUtil.debug(log, getId() + "容器释放Visitor" + visitor.getId() + "开始!");
		//// 防止在未执行的情况下就进行了释放
		if (!visitor.getHander().isExecute() && !visitor.getHander().isRollback()
				&& !visitor.getHander().isExecuteFail()) {
			LogUtil.debug(log, getId() + "容器释放Visitor" + visitor.getId() + "出现异常!");
			throw new FailReleaseException("释放失败！");
		}

		try {
			LifecycleUtils.destory(visitor.getHander());/// 释放hander
			LogUtil.debug(log, getId() + "容器释放Hander" + visitor.getHander() + "结束!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		visitors.remove(visitor.getId());
		LogUtil.debug(log, getId() + "容器释放Visitor" + visitor.getId() + "结束!");
	}

	@Override
	public void destory() {
		LogUtil.debug(log, getId() + "容器释放开始!");
		for (Map.Entry<String, IVisitorAware> et : visitors.entrySet()) {
			IVisitorAware v = et.getValue();
			if (!v.getHander().isCommit()) {
				v.getHander().rollback();
				v.setResponse(new AbortResponse());
				LogUtil.debug(log, getId() + "容器终端访问：" + et.getKey() + "!");
			}
		}
		/// 全部移出
		visitors.clear();
		LogUtil.debug(log, getId() + "容器释放结束！");
	}

	/**
	 * 创建访问者并执行访问
	 */
	@Override
	public IVisitorAware establishVisitor(IRequest request) {
		LogUtil.debug(log, getId() + "容器申请Visitor开始！");
		/// 创建visitor
		IVisitorAware visitor = bindVisitor(IVisitorAware.Builder.create());
		LogUtil.debug(log, getId() + "容器申请Visitor成功:" + visitor.getId() + "！");
		//// 设置请求对象
		setRequest(request, visitor);
		LogUtil.debug(log, getId() + "容器为" + visitor.getId() + "设置请求参数！");
		/// 设置返回对象
		setResponse(visitor, null);
		LogUtil.debug(log, getId() + "容器为" + visitor.getId() + "设置返回结果对象！");
		/// 注册到容器
		registerContainer(visitor);
		LogUtil.debug(log, visitor.getId() + "注册到容器" + getId());
		///// 绑定受理者，并委托受理者执行请求
		setHander(request, visitor);
		LogUtil.debug(log, getId() + "容器为" + visitor.getId() + "设置处理程序！" + visitor.getHander());
		/// 委托受理
		visitor.agency();
		LogUtil.debug(log, visitor.getId() + "请求处理程序执行！");
		return visitor;
	}

	private IVisitorAware bindVisitor(IVisitorAware visitor) {
		if (ThreadContext.getVisitor() == null) {
			ThreadContext.bind(visitor);
		} else {
			ThreadContext.unbindVisitor();
			ThreadContext.bind(visitor);
		}
		visitors.put(visitor.getId(), visitor);
		return visitor;
	}

	private void setRequest(IRequest request, IVisitorAware visitor) {
		if (visitor.getRequest() == null) {
			visitor.setRequest(request);
		}
	}

	private void setResponse(IVisitorAware visitor, IResponse response) {

		if (response == null) {
			response = new SimpleResponse() {
			};
		}
		visitor.setResponse(response);
	}

	private void registerContainer(IVisitorAware visitor) {
		if (visitor.getVisitorContainer() == null) {
			visitor.registerContainer(this);
		}
		if(visitor.getApplicaiton()==null) {
			visitor.setApplicaiton(Application.getApplication());
		}
	}

	private void setHander(IRequest request, IVisitorAware visitor) {
		if (visitor.getHander() == null) {
			IHander hander = getHander(request);//// 获取
			visitor.setHander(hander);
		}
	}

	/**
	 * 获取具体的处理hander，由客户端自己来实现
	 * 
	 * @param action
	 * @return
	 */
	public abstract IHander getHander(IRequest request);

	@Override
	public Collection<IVisitorAware> getVisitors() {
		return visitors.values();
	}

	/**
	 * 
	 */
	@Override
	public IVisitorAware getVisitor(String id) {
		return visitors.get(id);
	}

	@Override
	public int getAvailableMaximum() {
		int len = getVisitors().size();

		return allowMaximum - len;
	}

	@Override
	public int getAllowMaximum() {

		return allowMaximum;
	}

}

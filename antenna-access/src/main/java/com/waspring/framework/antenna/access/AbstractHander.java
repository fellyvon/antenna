package com.waspring.framework.antenna.access;

import java.util.Queue;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.access.manager.AbstractVisitorContainer;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.property.PropertiesUtil;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.AcceptUnkownException;
import com.waspring.framework.antenna.core.hander.HanderEvent;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.hander.IHanderListener;
import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.service.ServiceException;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.ThreadContext;
import com.waspring.framework.antenna.core.visitor.ExceptionResponse;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;
import com.waspring.framework.antenna.core.visitor.IVisitor;
import com.waspring.framework.antenna.core.visitor.IVisitorAware;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.RequestValidationExcetion;
import com.waspring.framework.antenna.core.visitor.RollBackResponse;
import com.waspring.framework.antenna.core.visitor.VisitException;
import com.waspring.framework.antenna.preservation.log.VisitorLogFileQueueUnit;
import com.waspring.framework.antenna.preservation.log.VisitorLogJDBCQueueUnit;
import com.waspring.framework.antenna.preservation.queue.IQueueUnit;

/**
 * 抽象受理者<br/>
 * 1、隐藏受理的具体细节，将执行延迟到service来调用用户的业务逻辑。</br>
 * 
 * 
 * @author felly
 *
 */
public abstract class AbstractHander implements IHander {

	protected Logger log = Logger.getLogger(getClass());

	public AbstractHander() {
	}

	private IConfigure configure;

	public AbstractHander(IConfigure configure) {
		this.configure = configure;
	}

	public IConfigure getConfigure() {
		return configure;
	}

	public void setConfigure(IConfigure configure) {
		this.configure = configure;
	}

	private IVisitorContainer container = null;

	private boolean executeFail = false;

	protected IService service = null;

	protected boolean executed;
	protected boolean commited;
	protected boolean rollbacked;

	protected IVisitorAware visitor = null;
	protected IRequest request = null;
	@SuppressWarnings("rawtypes")
	protected IResponse response = null;
	private IHanderListener<IVisitor> handerListener = null;
	private  HanderEvent<IVisitor>  handerEvent=null;

	@Override
	public void init() throws Exception {
		LogUtil.info(log, "init start");
		initVisitor();
		initRequest();
		initResponse();
		initHander();
		initContianer();
		initQueueUnit();
		onInit();//// 初始化事件产生
		LogUtil.info(log, "init end");
	}
	
 

	/**
	 * 执行前的校验
	 * 
	 * @throws Exception
	 */
	private void pre() throws ServiceException {
		LogUtil.debug(log, "pre start");
		validateCap();
		validateRequestByself(request);
		validateRequestTransfer(request);
		validatePrescription(request);
		LogUtil.debug(log, "pre end");
	}

	@Override
	public void execute() {
		onExecuteBefore();/// 执行之前发生
		long start = System.currentTimeMillis();

		LogUtil.debug(log, "execute start");
		if (executed) {/// 执行过就不用再执行了，即保证只有一次执行
		 
			return;
		}
		executed = true;/// 标识执行了

		try {
			pre();
			IService _service = getService();
			if (_service == null) {
				throw new ServiceException("at " + getClass().getName() + ",no service,Please register services!");
			}
			LogUtil.debug(log, request);
			/////// 过滤器执行
			doFilter();
			//// 业务方法执行
			execueService(_service);
			onExecuteAfter();/// 执行之后发生
			LogUtil.debug(log, response);
		} catch (Exception e) {
			onExecuteException(e);
			if (e instanceof AcceptUnkownException) {

				rollback();/// 回退状态
				onRollback();
			} else {
				response = new ExceptionResponse(e,PropertiesUtil.isDebug());
				executeFail = true; ///// 失败状态
			}
		} finally {
			commit();//// 最终都要提交释放资源
		    
		}

		long end = System.currentTimeMillis() - start;
		LogUtil.debug(log, "execute end,cust:" + end + "ms");
	}

	/**
	 * 业务方法执行
	 * 
	 * @throws Exception
	 */
	private void execueService(IService _service) throws Exception {
		if (commited) {
			return;
		}
		_service.service(request, response);
	}

	/**
	 * 过滤器执行
	 */
	private void doFilter() {
		IFilter filter=getVisitorContainer().getIFilter();
		if(filter!=null) {
		filter.doFilter(request, response);
		}
	}

	@Override
	public void rollback() {
		rollback(request);
		response = new RollBackResponse();
		rollbacked = true;
	}

	/**
	 * 获取日志节点处理器,可以通過本方法扩展！
	 */
	public IQueueUnit getQueueUnit() {////
		IQueueUnit queueUnit = new VisitorLogFileQueueUnit(visitor,request, response);
		queueUnit.linkQueueUnit(new VisitorLogJDBCQueueUnit(visitor,request, response));
		return queueUnit;
	}

	private void initQueueUnit() {
		if (queueUnit == null) {
			queueUnit = getQueueUnit();
		}
	}

	private IQueueUnit queueUnit = null;

	@Override
	public void commit() {

		try {
			
			response.finish();
			visitor.setResponse(response);
			destoryService();
			releaseVisitor();
		} catch (VisitException e) {
			/// 如果释放失败，说明程序没有执行 即 当前并非为 executed 状态或者 rollback状态
			LogUtil.error(log, e);
		} finally {
			/// 日志记录
			logHandle();
			////
			commited = true;
			onCommit();////提交发生
		}
	}

	private void logHandle() {
		String mode = ((AbstractVisitorContainer) getVisitorContainer()).getConfigureContainer().getLogMode();
		IQueueUnit queueUnitItem = queueUnit.getQueueUnit(mode);
		if (queueUnitItem == null) {
			LogUtil.warn(log, "IQueueUnit 不存在，无法记录日志！");
			return;
		}
		Queue queue = getVisitorContainer().getLogQueue();
		if (queue == null) {
			LogUtil.warn(log, "queue申请失败，无法记录日志！");
			return;
		}
		  if(queue.size()<=((IManagerObtain)ApplicationUtil.getApplication()).getQueueManagerFactory().getQueueManger().getMaxQueueNum()) {
			  queue.add(queueUnitItem);
		  }	
		  else {
			  LogUtil.warn(log, "因为队列满，丢弃数据"+queueUnitItem.getPersistentCommand());
		  }

	}

	/**
	 * 初始化service，一个线程只会初始化一次
	 * 
	 * @param service
	 * @return
	 */
	private IService initService(IService service) {
		try {
			LifecycleUtils.init(service);///
		} catch (Exception e) {
			e.printStackTrace();
		}
		return service;
	}

	private void destoryService() {
		try {
			LifecycleUtils.destory(getService());/// 释放service
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放visitor
	 */
	private void releaseVisitor() throws VisitException {
		getVisitorContainer().releaseVisitor(visitor);
	}

	private IVisitorContainer getVisitorContainer() {
		return container;
	}

	/**
	 * 数据回退的处理
	 * 
	 * @param request
	 */
	public abstract void rollback(IRequest request);

	@Override
	public boolean isExecuteFail() {

		return executeFail;
	}

	@Override
	public boolean isCommit() {

		return commited;
	}

	@Override
	public boolean isExecute() {
		return executed;
	}

	@Override
	public boolean isRollback() {

		return rollbacked;
	}

	public IVisitorAware getVisitor() {
		return visitor;
	}

	public void setVisitor(IVisitorAware visitor) {
		this.visitor = visitor;
	}

	public IRequest getRequest() {
		return request;
	}

	public IResponse getResponse() {
		return response;
	}

	@Override
	public void destory() {

	}

	@Override
	public void registerService(IService service) {
		if (this.service == service) {
			return;//// 防止重复执行
		}
		this.service = initService(service);

	}

	@Override
	public IService getService() {
		return service;
	}

	private void initContianer() {
		IVisitorAware visitor = getVisitor();
		IVisitorContainer visitorContainer = visitor.getVisitorContainer();
		container = visitorContainer;
	}

	public void initRequest() {
		this.request = getVisitor().getRequest();
	}

	public void initResponse() {
		this.response = getVisitor().getResponse();
	}

	private void initVisitor() throws Exception {
		if (visitor == null) {
			setVisitor(ThreadContext.getVisitor());
			if (visitor == null) {
				throw new RuntimeException("初始化失败");/// game over
			}
		}

	}

	private void initHander() {
		if (getVisitor() != null && getVisitor().getHander() == null) {
			getVisitor().setHander(this);
		}
	}

	private void validateRequestTransfer(IRequest request) throws ServiceException {
		if (request == null) {
			throw new ServiceException("必须传入请求参数对象request!");

		}
		if (!request.validateSign()) {
			throw new ServiceException("验证失败，期望sign=" + request.getSign() + ",实际sign=" + request.bornSign());
		}

	}

	private void validatePrescription(IRequest request) throws ServiceException {
		if (ApplicationUtil.devloperMode) {
			return;
		}
		long pix = Math.abs(request.getTimestamp() - System.currentTimeMillis());
		long timeout = getVisitorContainer().getTimeout();
		if (pix > timeout) {
			throw new ServiceException("传输时效校验失败，最大允许:" + timeout + ",实际:" + pix);
		}

	}

	private void validateCap() throws ServiceException {
		if (getVisitorContainer().getAvailableMaximum() <= 0) {
			throw new ServiceException("容量满，稍后重试!");
		}
	}

	private void validateRequestByself(IRequest request) throws ServiceException {
		try {
			request.selfVerification();
		} catch (RequestValidationExcetion e) {
			throw new ServiceException(e.getMsg());
		}

	}

	@Override
	public void registerHanderListener(IHanderListener listener) {
		this.handerListener = listener;

	}

	@Override
	public IHanderListener getHanderListener() {

		return handerListener;
	}

	/**
	 * 初始化的時候發生
	 */
	private void onInit() {
		if(handerEvent==null) {
			handerEvent=new HanderEvent<IVisitor>();
			handerEvent.setSource(getVisitor());
		}
	
		if (handerListener == null) {

			return;
		}

		handerListener.onInit(handerEvent);

	}

	private void onExecuteBefore() {
		if (handerListener == null) {

			return;
		}

		handerListener.onExecuteBefore(handerEvent);

	}

	private void onExecuteAfter() {
		if (handerListener == null) {

			return;
		}

		handerListener.onExecuteAfter(handerEvent);

	}

	private void onExecuteException(Exception e) {
		if (handerListener == null) {

			return;
		}

		handerListener.onExecuteException(handerEvent, e);

	}

	/**
	 * 提交的额时候发生
	 * 
	 * @param e
	 */
	private void onCommit() {
		if (handerListener == null) {

			return;
		}

		handerListener.onCommit(handerEvent);

	}

	/**
	 * 回滚的时候发生
	 * 
	 * @param e
	 */
	private void onRollback() {
		if (handerListener == null) {

			return;
		}

		handerListener.onRollback(handerEvent);

	}

	/**
	 * 销毁发生
	 */
	private void onDestory() {
		if (handerListener == null) {

			return;
		}

		handerListener.onDestory(handerEvent);

	}

}

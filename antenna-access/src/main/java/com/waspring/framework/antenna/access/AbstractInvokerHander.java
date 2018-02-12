package com.waspring.framework.antenna.access;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 调用者处理的抽象
 * 
 * @author felly
 * 
 *
 */
public abstract class AbstractInvokerHander extends AbstractHander implements Invoker, Callable<Boolean> {

	public AbstractInvokerHander() {
	}

	public AbstractInvokerHander(IConfigureInvoker configure) {
		super(configure);
	}

	@Override
	public abstract void rollback(IRequest request);
	
	/***
	 * 
	 */
	public Boolean call() throws Exception {
		super.execute();
		return callResultHandle(response);
	}

	/**
	 * 重写execute, 分为两种情况来执行，有重试的，通过重试器执行，否则直接调用父类的execute
	 */
	public void execute() {
		if (retryer != null) {
			try {
				retryer.call(this);
			} catch (ExecutionException e) {
				e.printStackTrace();
				LogUtil.error(log, e);
			} catch (RetryException e) {
				e.printStackTrace();
				LogUtil.error(log, e);
			}
		} else {
			super.execute();
		}

	}

	/**
	 * 调用结果的判定，由具体业务使用来设置 
	 * 
	 * @return
	 */
	public abstract boolean callResultHandle(IResponse reponse) throws Exception;

	private Retryer<Boolean> retryer = null;

	public void register(Retryer<Boolean> retryer) {
		this.retryer = retryer;
	}

	private String postURL;

	@Override
	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}

}

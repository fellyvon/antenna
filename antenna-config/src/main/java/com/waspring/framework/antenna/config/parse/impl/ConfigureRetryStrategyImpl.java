package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureAttemptTimeLimiter;
import com.waspring.framework.antenna.config.parse.IConfigureRetryBlock;
import com.waspring.framework.antenna.config.parse.IConfigureRetryException;
import com.waspring.framework.antenna.config.parse.IConfigureRetryListener;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStop;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategys;
import com.waspring.framework.antenna.config.parse.IConfigureRetryWait;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 策略项配置
 * 
 * @author felly
 *
 */
public class ConfigureRetryStrategyImpl extends AbstractConfiure implements IConfigureRetryStrategy {

	private IConfigureRetryStrategys strategys = null;

	public ConfigureRetryStrategyImpl(IConfigureRetryStrategys strategys) {
		super();
		this.strategys = strategys;
	}

	private IConfigureRetryListener configureRetryListener = null;

	@Override
	public IConfigureRetryListener getConfigureRetryListener() {

		return configureRetryListener;
	}

	@Override
	public void setConfigureRetryListener(IConfigureRetryListener configureRetryListener) {
		this.configureRetryListener = configureRetryListener;

	}

	private IConfigureAttemptTimeLimiter configureAttemptTimeLimiter = null;

	@Override
	public IConfigureAttemptTimeLimiter getConfigureAttemptTimeLimiter() {
		return configureAttemptTimeLimiter;
	}

	@Override
	public void setConfigureAttemptTimeLimiter(IConfigureAttemptTimeLimiter configureAttemptTimeLimiter) {
		this.configureAttemptTimeLimiter = configureAttemptTimeLimiter;

	}

	private IConfigureRetryBlock configureRetryBlock = null;

	@Override
	public IConfigureRetryBlock getConfigureRetryBlock() {

		return configureRetryBlock;
	}

	@Override
	public void setConfigureRetryBlock(IConfigureRetryBlock configureRetryBlock) {
		this.configureRetryBlock = configureRetryBlock;

	}

	private IConfigureRetryException configureRetryException = null;

	@Override
	public IConfigureRetryException getConfigureRetryException() {

		return configureRetryException;
	}

	@Override
	public void setConfigureRetryException(IConfigureRetryException configureRetryException) {
		this.configureRetryException = configureRetryException;

	}

	private IConfigureRetryStop configureRetryStop;

	@Override
	public IConfigureRetryStop getConfigureRetryStop() {

		return configureRetryStop;
	}

	@Override
	public void setConfigureRetryStop(IConfigureRetryStop configureRetryStop) {
		this.configureRetryStop = configureRetryStop;

	}

	private IConfigureRetryWait configureRetryWait = null;

	@Override
	public IConfigureRetryWait getConfigureRetryWait() {

		return configureRetryWait;
	}

	@Override
	public void setConfigureRetryWait(IConfigureRetryWait configureRetryWait) {
		this.configureRetryWait = configureRetryWait;

	}

	@Override
	public IConfigure getParent() {

		return strategys;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.RetryStrategy.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}

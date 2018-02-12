package com.waspring.framework.antenna.access.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.github.rholder.retry.AttemptTimeLimiters;
import com.github.rholder.retry.BlockStrategies;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.StopStrategy;
import com.github.rholder.retry.WaitStrategies;
import com.github.rholder.retry.WaitStrategy;
import com.waspring.framework.antenna.access.invoke.InvokerRetryListener;
import com.waspring.framework.antenna.config.parse.IConfigureAttemptTimeLimiter;
import com.waspring.framework.antenna.config.parse.IConfigureRetryBlock;
import com.waspring.framework.antenna.config.parse.IConfigureRetryException;
import com.waspring.framework.antenna.config.parse.IConfigureRetryListener;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStop;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.config.parse.IConfigureRetryWait;
import com.waspring.framework.antenna.core.util.ReflectUtil;

/**
 * 根据情况来产生Retryer<Boolean> retryer
 * 
 * @author felly
 *
 */
public abstract class RetryerUtil {

	/**
	 * 重试器的构建
	 * 
	 * @param strategy
	 * @return
	 */
	public static Retryer<Boolean> buildRetryer(IConfigureRetryStrategy strategy) {
		boolean hav = false;
		RetryerBuilder<Boolean> retyerBuilder = RetryerBuilder.<Boolean>newBuilder();

		IConfigureRetryListener configureListener = strategy.getConfigureRetryListener();
		retyerBuilder = RetryerUtil.handleLisener(hav, retyerBuilder, configureListener);

		IConfigureAttemptTimeLimiter configureAttemptTimeLimiter = strategy.getConfigureAttemptTimeLimiter();
		retyerBuilder = RetryerUtil.handleConfigureAttemptTimeLimiter(hav, retyerBuilder, configureAttemptTimeLimiter);

		IConfigureRetryBlock configureRetryBlock = strategy.getConfigureRetryBlock();
		retyerBuilder = RetryerUtil.handleConfigureRetryBlock(hav, retyerBuilder, configureRetryBlock);

		IConfigureRetryException configureRetryException = strategy.getConfigureRetryException();
		retyerBuilder = RetryerUtil.handleConfigureRetryException(hav, retyerBuilder, configureRetryException);

		IConfigureRetryStop configureRetryStop = strategy.getConfigureRetryStop();
		retyerBuilder = RetryerUtil.handleConfigureRetryStop(hav, retyerBuilder, configureRetryStop);

		IConfigureRetryWait configureRetryWait = strategy.getConfigureRetryWait();

		retyerBuilder = RetryerUtil.handleConfigureRetryWait(hav, retyerBuilder, configureRetryWait);

		//// 有策略的情况下才会执行重试，否则就执行一次
		return hav ? retyerBuilder.build() : null;
	}

	private static RetryerBuilder<Boolean> handleConfigureRetryWait(boolean hav, RetryerBuilder<Boolean> retyerBuilder,
			IConfigureRetryWait configureRetryWait) {
		if (configureRetryWait != null) {
			String wait = configureRetryWait.getWait();
			long increment = configureRetryWait.increment();
			long sleeptime = configureRetryWait.sleeptime();
			WaitStrategy waitStrategy = Strategys.getWaitStrategy(wait, sleeptime, increment);
			if (waitStrategy == null) {
				return retyerBuilder;
			}
			retyerBuilder.withWaitStrategy(waitStrategy);
			hav = true;
		}

		return retyerBuilder;
	}

	private static RetryerBuilder<Boolean> handleConfigureRetryStop(boolean hav, RetryerBuilder<Boolean> retyerBuilder,
			IConfigureRetryStop configureRetryStop) {
		if (configureRetryStop != null) {
			String stop = configureRetryStop.getStop();
			long delayinmillis = configureRetryStop.delayinmillis();
			int attemptnumber = configureRetryStop.attemptnumber();
			StopStrategy stopStrategy = Strategys.getStopStrategy(stop, delayinmillis, attemptnumber);
			if (stopStrategy == null) {
				return retyerBuilder;
			}

			retyerBuilder.withStopStrategy(stopStrategy);
			hav = true;
		}

		return retyerBuilder;
	}

	private static RetryerBuilder<Boolean> handleConfigureRetryException(boolean hav,
			RetryerBuilder<Boolean> retyerBuilder, IConfigureRetryException configureRetryException) {
		if (configureRetryException != null) {
			String exceptions = configureRetryException.getException();
			if (StringUtils.isEmpty(exceptions)) {
				return retyerBuilder;
			}
			String exceptionArray[] = exceptions.split(",");//// 多个exception

			for (String expId : exceptionArray) {
				Class e = ExceptionCoverter.getException(expId);
				if (e != null) {
					retyerBuilder = retyerBuilder.retryIfExceptionOfType(e);
					hav = true;
				}
			}

		}
		return retyerBuilder;
	}

	/**
	 * 等待时候做的事情
	 * 
	 * @param hav
	 * @param retyerBuilder
	 * @param configureRetryBlock
	 */
	private static RetryerBuilder<Boolean> handleConfigureRetryBlock(boolean hav, RetryerBuilder<Boolean> retyerBuilder,
			IConfigureRetryBlock configureRetryBlock) {
		if (configureRetryBlock != null) {
			// configureRetryBlock.get
			long sleepTime = configureRetryBlock.sleepTime();

			retyerBuilder = retyerBuilder.withBlockStrategy(BlockStrategies.threadSleepStrategy());

			hav = true;

		}

		return retyerBuilder;
	}

	//// 超时设置
	private static RetryerBuilder<Boolean> handleConfigureAttemptTimeLimiter(boolean hav,
			RetryerBuilder<Boolean> retyerBuilder, IConfigureAttemptTimeLimiter configureAttemptTimeLimiter) {
		if (configureAttemptTimeLimiter != null) {

			long limiter = configureAttemptTimeLimiter.getLimiter();

			if (limiter <= 0) {
				retyerBuilder = retyerBuilder.withAttemptTimeLimiter(AttemptTimeLimiters.<Boolean>noTimeLimit());
			} else {
				retyerBuilder = retyerBuilder.withAttemptTimeLimiter(
						AttemptTimeLimiters.<Boolean>fixedTimeLimit(limiter, TimeUnit.MILLISECONDS));

			}

			hav = true;

		}
		return retyerBuilder;
	}

	/**
	 * 监听处理
	 * 
	 * @param retyerBuilder
	 * @param configureListener
	 * @return
	 */
	private static RetryerBuilder<Boolean> handleLisener(boolean hav, RetryerBuilder<Boolean> retyerBuilder,
			IConfigureRetryListener configureListener) {

		if (configureListener != null) {
			String className = configureListener.getListener();
			if (StringUtils.isEmpty(className)) {
				return retyerBuilder;
			}
			InvokerRetryListener listener = (InvokerRetryListener) ReflectUtil.constructorNewInstance(className,
					new Class[] {}, new Object[] {});
			if (listener != null) {
				retyerBuilder = retyerBuilder.withRetryListener(listener);
				hav = true;
			}

		}

		return retyerBuilder;
	}

	private final static class Strategys {

		static StopStrategy getStopStrategy(String name, long delayTime, int attempt) {
			if (IConfigureRetryStop.NERVER.equals(name)) {
				return StopStrategies.neverStop();
			} else if (IConfigureRetryStop.DELAY.equals(name)) {
				return StopStrategies.stopAfterDelay(delayTime, TimeUnit.MILLISECONDS);
			} else if (IConfigureRetryStop.ATTEMPT.equals(name)) {
				return StopStrategies.stopAfterAttempt(attempt);
			} else {
				// TODO
			}

			return null;
		}

		static WaitStrategy getWaitStrategy(String name, long sleepTime, long increment) {
			if (IConfigureRetryWait.FIXED.equals(name)) {
				return WaitStrategies.fixedWait(sleepTime, TimeUnit.MILLISECONDS);
			} else if (IConfigureRetryWait.RANDOM.equals(name)) {
				return WaitStrategies.randomWait(sleepTime, TimeUnit.MILLISECONDS);
			} else if (IConfigureRetryWait.INCREMENTING.equals(name)) {
				return WaitStrategies.incrementingWait(sleepTime, TimeUnit.MILLISECONDS, increment,
						TimeUnit.MILLISECONDS);
			} else if (IConfigureRetryWait.EXPONENTIAL.equals(name)) {
				return WaitStrategies.exponentialWait(increment, sleepTime, TimeUnit.MILLISECONDS);
			} else if (IConfigureRetryWait.FIBONACCI.equals(name)) {
				return WaitStrategies.fibonacciWait(increment, sleepTime, TimeUnit.MILLISECONDS);
			}

			else {
				///// TODO
			}

			return null;
		}

	}

}

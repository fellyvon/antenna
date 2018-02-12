package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public interface IConfigureRetryStrategy extends IConfigure {
 
	String RETRY_LISTENER = "retry-listener";// 自定义重试监听器，可以用于异步记录错误日志；
	IConfigureRetryListener getConfigureRetryListener();
	void setConfigureRetryListener(IConfigureRetryListener configureRetryListener);
	String ATTEMPT_TIME_LIMITER = "attempt-time-limiter";// 单次任务执行时间限制（如果单次任务执行超时，则终止执行当前任务）；
	IConfigureAttemptTimeLimiter getConfigureAttemptTimeLimiter();
	void setConfigureAttemptTimeLimiter(IConfigureAttemptTimeLimiter configureAttemptTimeLimiter);
	 
	String BLOCK_STRATEGIES = "block-strategies";/// 任务阻塞策略（通俗的讲就是当前任务执行完，下次任务还没开始这段时间做什么……），默认策略为：BlockStrategies.THREAD_SLEEP_STRATEGY,
													/// 也就是调用 Thread.sleep(sleepTime);
	IConfigureRetryBlock getConfigureRetryBlock();
	void setConfigureRetryBlock(IConfigureRetryBlock configureRetryBlock);
	 

	String RETRY_EXCEPTION = "retry-exception"; /// 指定出现的异常才进行重试

	IConfigureRetryException getConfigureRetryException();
	void setConfigureRetryException(IConfigureRetryException configureRetryException);
	 

	/**
	 * 停止重试策略，提供三种：<br/>
	 * StopAfterDelayStrategy ：<br/>
	 * 设定一个最长允许的执行时间；比如设定最长执行10s，无论任务执行次数，只要重试的时候超出了最长时间，则任务终止，并返回重试异常RetryException；<br/>
	 * NeverStopStrategy ：<br/>
	 * 不停止，用于需要一直轮训知道返回期望结果的情况；<br/>
	 * StopAfterAttemptStrategy ：<br/>
	 * 设定最大重试次数，如果超出最大重试次数则停止重试，并返回重试异常；<br/>
	 */
	String STOP_STRATEGY = "stop-strategy";

	IConfigureRetryStop getConfigureRetryStop();
	void setConfigureRetryStop(IConfigureRetryStop configureRetryStop);
	 

	/**
	 * 等待时长策略（控制时间间隔），返回结果为下次执行时长：<br/>
	 * FixedWaitStrategy：<br/>
	 * 固定等待时长策略；<br/>
	 * RandomWaitStrategy：<br/>
	 * 随机等待时长策略（可以提供一个最小和最大时长，等待时长为其区间随机值）<br/>
	 * IncrementingWaitStrategy：<br/>
	 * 递增等待时长策略（提供一个初始值和步长，等待时间随重试次数增加而增加）<br/>
	 * ExponentialWaitStrategy：<br/>
	 * 指数等待时长策略；<br/>
	 * FibonacciWaitStrategy ：<br/>
	 * Fibonacci 等待时长策略；<br/>
	 * ExceptionWaitStrategy ：<br/>
	 * 异常时长等待策略；<br/>
	 * CompositeWaitStrategy ：<br/>
	 * 复合时长等待策略；<br/>
	 */
	String WAIT_STRATEGY = "wait_strategy";

	IConfigureRetryWait getConfigureRetryWait();
	void setConfigureRetryWait(IConfigureRetryWait configureRetryWait);
	 

}

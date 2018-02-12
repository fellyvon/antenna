package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 定义重试的重试等待策略
 * 
 * @author felly
 *
 */
public interface IConfigureRetryWait extends IConfigure {

	String getWait();

	long sleeptime();/// 延迟时间/毫秒

	long increment();/// 执行次数

	String WAIT = "wait";
	String SLEEP_TIME = "sleeptime";
	String INCREMENT = "increment";
	
	///[fixed][random[incrementing][exponential][fibonacci]
	
	String FIXED="fixed";
	String  RANDOM="random";
	String  INCREMENTING="incrementing";
	String  EXPONENTIAL="exponential";
	String  FIBONACCI="fibonacci";
}

package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 定义重试的终止条件
 * 
 * @author felly
 *
 */
public interface IConfigureRetryStop extends IConfigure {

	String getStop();

	long delayinmillis();/// 延迟时间/毫秒

	int  attemptnumber();/// 执行次数

	String STOP = "stop";
	String DELAYINMILLIS = "delayinmillis";
	String ATTEMPTNUMBER = "attemptnumber";

	String ATTEMPT = "attempt";
	String DELAY = "delay";
	String NERVER = "never";

}

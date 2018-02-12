package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 柱塞策略配置对象
 * @author felly
 *
 */
public interface IConfigureRetryBlock extends IConfigure {
   
	long  sleepTime();//毫秒
	String SLEEP_TIME="sleeptime";
}

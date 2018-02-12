package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

public interface IConfigureAttemptTimeLimiter extends IConfigure {

	long  getLimiter();///单位毫秒
   
	String LIMITER = "limiter";
}

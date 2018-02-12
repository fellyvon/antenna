package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 定义需要重试的异常类型
 * 
 * @author felly
 *
 */
public interface IConfigureRetryException extends IConfigure {

	String getException();

	String EXCEPTION = "exception";
}

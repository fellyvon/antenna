package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public interface IConfigureException extends IConfigure {
	 
   String EXCEPTION_CLASS="exceptionclass";
	String getExceptionClass();

}

package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 过滤器
 * 
 * @author felly
 *
 */
public interface IConfigureFilter extends IConfigure {

	String FILTER_CLASS = "filterclass";

	String getFilterClass();

}

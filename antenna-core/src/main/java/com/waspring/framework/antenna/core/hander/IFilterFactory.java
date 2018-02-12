package com.waspring.framework.antenna.core.hander;

import com.waspring.framework.antenna.core.filter.IFilter;

/**
 * 处理器工厂
 * 
 * @author felly
 *
 */
public interface IFilterFactory {
	 
	/**
	 * 创建处理器
	 * 
	 * @param action
	 * @return
	 */
	IFilter createFilter(String containerId, String filterId);

 

}

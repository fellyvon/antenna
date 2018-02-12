package com.waspring.framework.antenna.core.hander;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 处理器工厂
 * 
 * @author felly
 *
 */
public interface IHanderFactory {
	 
	/**
	 * 创建处理器
	 * 
	 * @param action
	 * @return
	 */
	IHander createHander(String containerId, String action);

	/**
	 * 回调处理
	 * @param hander 生成的hander对象，配置信息
	 * @param configure
	 */
	void callback(IHander hander, IConfigure configure);/// 回调处理

}

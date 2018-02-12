package com.waspring.framework.antenna.core.hander;

/**
 * 服务调用者
 * 
 * @author felly
 *
 */
public interface Invoker extends IHander {
	/**
	 * 调用地址
	 * @return
	 */
    String getPostURL();
}

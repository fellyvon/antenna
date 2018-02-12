package com.waspring.framework.antenna.core.visitor;

/**
 * 
 * @author felly
 *
 */
public interface IVisitorContainerFactory {

 
	/**
	 * 容器申请
	 * 
	 * @param containerId
	 * @return
	 */
	IVisitorContainer createVisitorContainer(String containerId);
}

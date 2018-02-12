package com.waspring.framework.antenna.config.io;

/**
 * 资源抽象
 * 
 * @author felly
 *
 */
public interface IResource {

	ITypeInputStream getInputStream(IURL url) throws NoResourseException;/// 获取输入流
     
	////获取协议
	String supportProtocol();

	///关联资源处理节点
	IResource link(IResource resourse);

}

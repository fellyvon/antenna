package com.waspring.framework.antenna.core.hander;

import com.waspring.framework.antenna.core.ILifecycle;
import com.waspring.framework.antenna.core.service.IService;

/**
 * 受理员.<br/>
 * 生命周期定义为：初始化，预处理， 处理后,销毁
 * 
 * 
 * @author felly
 *
 */
public interface IHander extends ILifecycle {
	/*
	 * 执行处理
	 */
	void execute();/////

	/*
	 * 执行成功提交处理
	 */
	void commit();///

	/*
	 * 执行出了问题回滚补救
	 */
	void rollback();///

	/*
	 * 是否处理完成
	 * 
	 * @return
	 */
	boolean isCommit();///

	/*
	 * 判断是否开始了
	 * 
	 */
	boolean isExecute();////

	/*
	 * 判断是否回滚了
	 */
	boolean isRollback();///

	/*
	 * 执行了但是是失败了
	 */
	boolean isExecuteFail();//
	/*
	 * 注册对应的服务
	 */

	void registerService(IService service);////

	/*
	 * 获取服务，这个服务其实是个链表，由客户端自己来控制实现方式
	 */
	IService getService();///
	
	
	/***
	 * 注册异常处理器
	 */
	void registerHanderListener(IHanderListener listener);
	
	/**
	 * 获取异常处理器
	 */
	
	IHanderListener getHanderListener();
	
	
	

}

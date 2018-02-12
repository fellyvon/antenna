package com.waspring.framework.antenna.core.hander;

/**
 * hander处理事件监听器
 * 
 * @author felly
 *
 */
public interface IHanderListener<T> {

	 /**
	  * 初始化發生
	  * @param event
	  */
	void onInit(HanderEvent<T> event);
/**
 * 執行業務邏輯之前發生
 * @param event
 */
	void onExecuteBefore(HanderEvent<T> event);
/**
 * 執行業務邏輯之後
 * @param event
 */
	void onExecuteAfter(HanderEvent<T> event);
/**
 * 業務邏輯執行出現異常
 * @param event
 */
	void onExecuteException(HanderEvent<T> event,Exception e);
	
	/**
	 * 在提交的时候发生
	 */
	void onCommit(HanderEvent<T> event);
	
	/**
	 * 在回滚的时候发生
	 */
	void onRollback(HanderEvent<T> event );
	
/**
 * 銷毀發生
 * @param event
 */
	void onDestory(HanderEvent<T> event);

}

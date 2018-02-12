package com.waspring.framework.antenna.core.hander;

/**
 * 事件消息传递对象
 * 
 * @author felly
 *
 */
public class HanderEvent<T> {

 
	private T  source;

 
	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

 
	 
}

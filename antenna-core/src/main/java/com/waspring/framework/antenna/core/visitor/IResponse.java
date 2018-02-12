package com.waspring.framework.antenna.core.visitor;

import java.util.Collection;

/**
 * 返回参数抽象
 * 
 * @author felly
 *
 * @param <T>
 */
public interface IResponse<T> extends java.io.Serializable {

	int getSubcode();
	void setSubcode(int subcode);
	/**
	 * 返回码设置
	 * 
	 * @param code
	 */
	void setCode(int code);

	/**
	 * 获取返回码<br/>
	 * <ul>
	 * <li>大于0为成功</li>
	 * <li>小于等于0为失败</li>
	 * </ul>
	 * 
	 * @return
	 */
	int getCode();

	/**
	 * 返回消息管理
	 * 
	 * @param message
	 */
	void setMessage(String message);

	String getMessage();

	/**
	 * 数据设置
	 * 
	 * @param data
	 */
	void setData(T data);

	T getData();

	/**
	 * 记录总数设置
	 * 
	 * @param count
	 */
	void setCount(int count);

	/**
	 * 获取记录总数
	 */
	int getCount();

	/**
	 * 加入消息列表
	 * 
	 * @param trace
	 */
	void addTrace(String trace);

	/**
	 * 获取消息列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Collection getTraces();
	
	
	long getTimestamp();////获取提交后的时间戳
	void finish();

}

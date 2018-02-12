package com.waspring.framework.antenna.core.visitor;

import java.util.Map;

/**
 * 请求封装
 * 
 * @author felly
 *
 */
public interface IRequest extends java.io.Serializable {

	/**
	 * 获取请求体，进行了编码
	 * 
	 * @return
	 * @throws Exception
	 */
	String getBody() throws Exception;

	/**
	 * 请求ID
	 * 
	 * @return
	 */
	String getRequestId();

	/**
	 * 时间戳
	 * 
	 * @return
	 */
	long getTimestamp();

	/**
	 * 一次调用凭证
	 * 
	 * @return
	 */
	String getNonce();

	/**
	 * 产生凭证
	 * 
	 * @return
	 */
	String bornSign();

	/**
	 * 获取凭证
	 */
	String getSign();

 

	/**
	 * 校验凭证
	 */
	boolean validateSign();

	/**
	 * 设置参数
	 */
	void addParameter(String name, Object value);

	void addAllParameter(Map<String, Object> paras);

	/**
	 * 获取请求参数
	 */

	Object getParameter(String name);

	/**
	 * 获取请求参数集
	 */
	Map getParameterMap();

	/**
	 * 是服务端?
	 * 
	 * @return
	 */
	boolean isServer();

	/**
	 * 服务地址
	 * 
	 * @return
	 */
	String getServerPath();

	/**
	 * 得到请求类型,POST,GET,PUT等
	 * 
	 * @return
	 */
	String getMethod();

	/**
	 * 得到动作名称.<br/>
	 * 动作决定流转到那个Hander.
	 * 
	 * @return
	 */
	String getAction();

	void markServiced(boolean serviced);/// 设置为是否还继续执行后续的Servie

	boolean isServiced();///

	/**
	 * 获取消息头信息,消息传输字符，多为控制类参数
	 */
	Map<String, String> getHeader();

	/**
	 * 设置消息头参数
	 */
	void addHeader(String name, String value);
	void addAllHeader(Map<String, String> headers);
	String ACTION = "action";
	String NONCE = "nonce";
	String TIMESTAMP = "timestamp";
	String SIGN="sign";
	String METHOD="method";

	/**
	 * 自我参数验证
	 * 
	 * @return
	 */
	void selfVerification() throws RequestValidationExcetion;

}

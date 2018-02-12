package com.waspring.framework.antenna.core.visitor;

import javax.servlet.ServletRequest;

/**
 * servlet的请求添加 元素request支持
 * 
 * @author felly
 *
 */
public interface IWebRequest extends IRequest {

	void setWebRequest(javax.servlet.ServletRequest request);

	ServletRequest getWebRequest();
}

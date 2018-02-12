package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 * 超时返回对象定义
 * 
 * @author felly
 *
 */
public class TimeoutResponse extends SimpleResponse {
	public TimeoutResponse() {

		init(ResponseCode.TIMEOUT);

	}

}

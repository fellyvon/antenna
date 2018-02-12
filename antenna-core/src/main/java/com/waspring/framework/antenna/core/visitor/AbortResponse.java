package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 * 强制结束返回对象定义
 * 
 * @author felly
 *
 */
public class AbortResponse extends SimpleResponse {

	public AbortResponse() {
		init(ResponseCode.ABORT);
	}
}

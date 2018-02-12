package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 *当无处理程序的时候直接返回定义
 * 
 * @author felly
 *
 */
public class NoHanderResponse extends SimpleResponse {

	public NoHanderResponse(String action) {
		init(ResponseCode.NOHANDER);
		setMessage("action="+action+"对应的处理程序不存在！");
	}
}

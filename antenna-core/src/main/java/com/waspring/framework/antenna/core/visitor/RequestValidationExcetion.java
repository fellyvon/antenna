package com.waspring.framework.antenna.core.visitor;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.core.util.ExceptionUtil;

/**
 * 请求验证异常
 * 
 * @author felly
 *
 */
public class RequestValidationExcetion extends Exception {

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	private int errorCode = -1;
	private String msg = "";/// 描述信息
	public RequestValidationExcetion(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
		this.msg = msg;

	}
	
	public RequestValidationExcetion(int errorCode, String msg, String errorInfo) {
		super(errorInfo);
		this.errorCode = errorCode;
		this.msg = msg;

	}

	public RequestValidationExcetion(int errorCode, String msg, Exception e) {
		super(e);
		this.errorCode = errorCode;
		this.msg = msg;
		if (StringUtils.isEmpty(msg)) {
			msg = ExceptionUtil.parseException(e).getMessage();
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

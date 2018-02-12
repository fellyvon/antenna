package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.util.ExceptionUtil;
import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 * 异常返回定义
 * 
 * @author felly
 *
 */
public class ExceptionResponse extends SimpleResponse {

	public ExceptionResponse(String message, Exception e) {
		setMessage(message);
		setCode(ResponseCode.EXCEPTION.getCode());
		
		if(isdebug) {
			setData(e);
		}
	}

	public ExceptionResponse(int code, String message, Exception e) {
		setMessage(message);
		
	 
		setCode(code);
		
		if(isdebug) {
			setData(e);
		}
	}
	
	 

	public ExceptionResponse(Exception e) {
		this(ExceptionUtil.parseException(e).getMessage(), e);
	}
	
	private boolean isdebug=false;
	public ExceptionResponse(Exception e,boolean isdebug) {
		this(ExceptionUtil.parseException(e).getMessage(), e);
		this.isdebug=isdebug;
	}

}

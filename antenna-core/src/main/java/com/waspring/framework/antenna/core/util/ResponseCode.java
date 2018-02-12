package com.waspring.framework.antenna.core.util;

/**
 * 状态码定义, 注意：调用成功的情况下，根据http状态来确定，即通过response的subcode
 * 
 * @author felly
 *
 */
public enum ResponseCode {
	

	SUCCESS(1, "调用成功"),
	EXCEPTION(-1, "出现业务异常！"),
	ERROR(-2, "严重错误！"),
	TIMEOUT(-3, "处理超时，强制结束！"),
	ABORT(-4, "强制结束，业务调用失败"),
	ROLLBACK(-5, "处理失败，回滚结束！"),
	NOHANDER(-6, "无处理程序配置！")
	;
	private int code;
	private String description;

	private ResponseCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

package com.waspring.framework.antenna.core.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.waspring.framework.antenna.core.util.ResponseCode;

/**
 * 简单的返回结果对象实现
 * 
 * @author felly
 *
 */
public abstract class SimpleResponse implements IResponse {
	private int code;
	private int subcode;
	private long timestamp;

	@Override
	public long getTimestamp() {
		 
		return timestamp;
	}

	////提交调用，获取提交时间
	@Override
	public void finish() {
		timestamp=System.currentTimeMillis();
	}

	@Override
	public int getSubcode() {

		return subcode;
	}

	@Override
	public void setSubcode(int subcode) {
		this.subcode = subcode;

	}

	private String message;
	private int count;
	private Object data;
	private List<String> traceList = new ArrayList<String>();

	protected void init(ResponseCode code) {
		setCode(code.getCode());
		setMessage(code.getDescription());
	}

	@Override
	public void setCode(int code) {
		this.code = code;

	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setData(Object data) {
		this.data = data;

	}

	@Override
	public Object getData() {

		return data;
	}

	@Override
	public void setCount(int count) {
		this.count = count;

	}

	@Override
	public int getCount() {

		return count;
	}

	@Override
	public void addTrace(String trace) {
		traceList.add(trace);
	}

	@Override
	public Collection getTraces() {

		return traceList;
	}

	@Override
	public String toString() {

		return JSON.toJSONString(this);

	}

}

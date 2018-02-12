package com.waspring.framework.antenna.access;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.core.util.Base64Util;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IdGenerator;
import com.waspring.framework.antenna.core.visitor.RequestValidationExcetion;

/**
 * 基础请求对象抽象
 * 
 * @author felly
 *
 */
public abstract class AbstractRequest implements IRequest {

	@Override
	public String toString() {
		return JSON.toJSONString(getParameterMap());
	}

	@Override
	public void selfVerification() throws RequestValidationExcetion {
		if(ApplicationUtil.devloperMode) {
			return;
		}
		if (StringUtils.isEmpty(getAction())) {
			throw new RequestValidationExcetion(-2, "期望header中的action");
		}
		if (StringUtils.isEmpty(getSign())) {
			throw new RequestValidationExcetion(-3, "期望传输认证sign字段");
		}
		if (StringUtils.isEmpty(getNonce())) {
			throw new RequestValidationExcetion(-4, "期望nonce字段");
		}
		if (getTimestamp() == 0) {
			throw new RequestValidationExcetion(-4, "期望时间戳timestamp字段");
		}
		if (StringUtils.isEmpty(getRequestId())) {
			throw new RequestValidationExcetion(-5, "期望requestId字段");
		}
		if (StringUtils.isEmpty(getKey())) {
			throw new RequestValidationExcetion(-6, "期望key字段");
		}
		if (StringUtils.isEmpty(getSecret())) {
			throw new RequestValidationExcetion(-7, "期望secret字段");
		}

	}

	@Override
	public String getBody() throws Exception {
		Map treeMap = new java.util.TreeMap();
		treeMap.putAll(getParameterMap());
		treeMap.putAll(getHeader());
		treeMap.put(IRequest.NONCE, getNonce());
		treeMap.put(IRequest.TIMESTAMP, getTimestamp());

		StringBuilder builder = new StringBuilder();
		for (Object key : treeMap.keySet()) {
			builder.append(key + "=" + treeMap.get(key));
		}

		return Base64Util.encodeSafe(builder.toString());

	}

	public abstract IdGenerator getIdGenerator();

	public AbstractRequest() {
		requestId = String.valueOf(getIdGenerator().generateId(this));
		timestamp = System.currentTimeMillis();
		nonce = DigestUtils.md5Hex(timestamp + getIP());
	}

	@Override
	public void addAllParameter(Map<String, Object> paras) {
		parameters.putAll(paras);

	}

	@Override
	public void addAllHeader(Map<String, String> headers) {
		headers.putAll(headers);

	}

	public abstract String getIP();

	public abstract String getKey();

	public abstract String getSecret();

	private String requestId;
	private long timestamp;
	private String nonce;
 
	@SuppressWarnings("rawtypes")
	private Map<String, Object> parameters = new HashMap<String, Object>();

	private Map<String, String> headers = new HashMap<String, String>();

	@Override
	public Map<String, String> getHeader() {
		return headers;
	}

	/**
	 * 获取action
	 */
	public String getAction() {
		String action= headers.get(IRequest.ACTION);
		if(StringUtils.isEmpty(action)) {
			action=String.valueOf(parameters.get(IRequest.ACTION));
		}
		return action;
	}

	@Override
	public void addHeader(String key, String value) {
		headers.put(key, value);

	}

	private boolean serviced;
	private String method;

	private String serverPath;

	@Override
	public String getRequestId() {

		return requestId;
	}

	@Override
	public long getTimestamp() {

		return timestamp;
	}

	@Override
	public String getNonce() {

		return nonce;
	}

	@Override
	public String bornSign() {
		try {
			String source = getKey() + getBody() + getSecret();

			return DigestUtils.shaHex(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getSign() {

		String sign= headers.get(IRequest.SIGN);
		if(StringUtils.isEmpty(sign)) {
			sign=String.valueOf(parameters.get(IRequest.SIGN));
		}
		return sign;
	}

	 

	@Override
	public boolean validateSign() {
		if(ApplicationUtil.devloperMode) {
			return true;
		}
		return StringUtils.stripToEmpty(getSign()).equals(StringUtils.stripToEmpty(bornSign()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addParameter(String name, Object value) {
		parameters.put(name, value);

	}

	@Override
	public Object getParameter(String name) {

		return parameters.get(name);
	}

	@Override
	public Map getParameterMap() {

		return parameters;
	}

	@Override
	public abstract boolean isServer();

	@Override
	public String getServerPath() {

		return serverPath;
	}

	@Override
	public String getMethod() {

		String method= headers.get(IRequest.METHOD);
		if(StringUtils.isEmpty(method)) {
			method=String.valueOf(parameters.get(IRequest.METHOD));
		}
		if(StringUtils.isEmpty(method)) {
			method="get";
		}
		return method;
	}

	@Override
	public void markServiced(boolean serviced) {
		this.serviced = serviced;

	}

	@Override
	public boolean isServiced() {

		return serviced;
	}

}

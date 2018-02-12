package com.waspring.framework.antenna.access;

import org.apache.commons.lang.StringUtils;

/**
 * 封装一个Key,用于读取存取hander用
 * 
 * @author felly
 * 
 *
 */
public class HanderKey implements java.io.Serializable {

	private String containerId, action;

	public HanderKey(String containerId, String action) {
		this.containerId = containerId;
		this.action = action;
	}

	//// 采用 ax+b=y 保证 出现code重复
	@Override
	public int hashCode() {

		return StringUtils.trimToEmpty(containerId).hashCode() * 37 + StringUtils.trimToEmpty(action).hashCode();
	}

	@Override
	public boolean equals(Object key) {
		if (!(key instanceof HanderKey)) {
			return false;
		}
		HanderKey hkey = (HanderKey) key;
		return StringUtils.trimToEmpty(containerId).equals(StringUtils.trimToEmpty(hkey.getContainerId()))
				&& StringUtils.trimToEmpty(action).equals(StringUtils.trimToEmpty(hkey.getAction()));
	}

	public boolean valid() {
		return !StringUtils.isEmpty(getAction()) && StringUtils.isEmpty(getContainerId());

	}

	public String getContainerId() {
		return containerId;
	}

	public String getAction() {
		return action;
	}

	public String toString() {
		return containerId + "." + action;
	}
}

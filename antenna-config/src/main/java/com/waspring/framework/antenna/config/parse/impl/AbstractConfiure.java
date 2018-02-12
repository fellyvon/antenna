package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 抽象配置管理的实现
 * 
 * @author felly
 *
 */
public abstract class AbstractConfiure implements IConfigure {
	protected Logger log=Logger.getLogger(getClass());
	@Override
	public abstract IConfigure getParent();

 

	private String content;
	private Map<String, String> properties = new HashMap<String, String>();

	@Override
	public String getId() {
		return properties.get(ID);
	}

  

	public AbstractConfiure() {
	}

	@Override
	public abstract String getElementName();/// 根据节点来决定

	@Override
	public abstract boolean hasNext();

	@Override
	public Map<String, String> getPropertyMap() {
		return properties;
	}

	@Override
	public String getProperty(String key) {
		return properties.get(key);
	}

	@Override
	public void putProperty(String key, String value) {
		properties.put(key, value);
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

}

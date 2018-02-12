package com.waspring.framework.antenna.config.io;

/**
 * 协议定义
 * 
 * @author felly
 *
 */
public enum ProtocolEnum {

	FILE("file://"), CLASS_PATH("classpath://"), HTTP("http://");

	private ProtocolEnum(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

}

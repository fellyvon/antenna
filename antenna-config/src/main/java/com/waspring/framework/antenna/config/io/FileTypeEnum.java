package com.waspring.framework.antenna.config.io;

/**
 * 协议定义
 * 
 * @author felly
 *
 */
public enum FileTypeEnum {

	XML("xml"), PROPERTIES("properties");

	private FileTypeEnum(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

}

package com.waspring.framework.antenna.config.io;

import org.apache.commons.lang.StringUtils;

/**
 * 默认URL解析实现
 * 
 * @author felly
 *
 */
public class DefaultURL implements IURL {

	private String protocol, url, fileFormat;

	/**
	 * 协议判定,并赋值
	 */
	private boolean isValidProtocol(String url) {
		if (StringUtils.isEmpty(url)) {
			return false;
		}
		ProtocolEnum supports[] = ProtocolEnum.values();
		boolean supportable = false;
		for (ProtocolEnum pe : supports) {
			if (url.startsWith(pe.getName())) {
				supportable = true;
				setProtocol(pe.getName());
				if (pe.equals(ProtocolEnum.FILE) || pe.equals(ProtocolEnum.CLASS_PATH)) {
					setUrl(url.replace(pe.getName(), ""));
				} else {
					setUrl(url);
				}
				break;
			}
		}
		try {
			String fileFormat = url.substring(url.lastIndexOf(".") + 1);
			setFileFormat(fileFormat);
		} catch (Exception e) {////默认采用xml
			setFileFormat(FileTypeEnum.XML.getName());
		}

		return supportable;

	}

	/**
	 * url格式<br/>
	 * classpath://meta-info/config.xml<br/>
	 * http://www.xx.com/meta-info/config.xml<br/>
	 * file://d://meta-info/config.xml<br/>
	 * 
	 * @param url
	 */
	public DefaultURL(String url) {
		if (!isValidProtocol(url)) {
			throw new RuntimeException("URL格式不准确或者有错误，格式为'协议://地址'");
		}
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public String getFileFormat() {
		return fileFormat;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
}

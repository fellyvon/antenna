package com.waspring.framework.antenna.config.parse.impl;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.waspring.framework.antenna.config.io.FileTypeEnum;
import com.waspring.framework.antenna.config.parse.ConfigureTranseferUtil;
import com.waspring.framework.antenna.config.parse.INodeParse;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * XML解析
 * 
 * @author felly
 *
 */
public class XMLParser extends AbstractParser {

	@Override
	public String supportFormat() {
		return FileTypeEnum.XML.getName();
	}

	@Override
	public IConfigure innerhandle(InputStream in) throws Exception {
		Document doc=parseXML(in);
		return ConfigureTranseferUtil.parsebyXML(doc, getINodeParse());
	}

	@Override
	public INodeParse getINodeParse() throws Exception {
		return null;///默认并没有扩展实现，交给子类
	}

	private Document parseXML(InputStream in) throws Exception {
		InputSource is = new InputSource(in);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		return doc;
	}

}

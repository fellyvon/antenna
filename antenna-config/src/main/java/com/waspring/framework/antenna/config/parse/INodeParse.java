package com.waspring.framework.antenna.config.parse;

import org.w3c.dom.Document;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * XML节点解析扩展
 * 
 * @author felly
 *
 */
public interface INodeParse {

	IConfigure nodeParse(Document nodeList);
	
	INodeParse  next();
	
	 
}
